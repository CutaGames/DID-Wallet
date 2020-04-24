package com.tancheng.carbonchain.activities.asset.wallet.service.btc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.TransactionRecord;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TransferData;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.service.IchainService;
import com.tancheng.carbonchain.activities.asset.wallet.utils.AESCrypt;
import com.tancheng.carbonchain.db.gen.TransationRecordDaoUtils;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.core.UTXO;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * created by tc_collin on 2020/4/16 17:00
 * Description: btc相关服务
 * version: 1.0
 */
public class BTCChainServiceThreePartImpl implements IchainService {
    @Override
    public boolean transferToken(TransferData transaction) {
        try {
            //查询keystore文件地址
            ETHWallet wallet = WalletDaoUtils.getIntence().getWalletByAddr(transaction.getFrom());
            if (wallet == null) {
                LogUtils.e("未查到转账钱包地址信息");
                return false;
            }
            String privateKeyEncode = wallet.getPrivateKey();
            String privateKey = AESCrypt.decrypt(privateKeyEncode, transaction.getPassword());
            boolean mainNet = false;
            String from = transaction.getFrom();
            String to = transaction.getTo();

//            long value = transaction.getValue().longValue();
            long value = 10;

            String gasPrice = transaction.getGasPrice();
            List<UTXO> unSpentBTCList = getUnspent(from, mainNet);
            if (unSpentBTCList != null && unSpentBTCList.size() > 0) {
                long feeRate = 10;
                if (!StringUtils.isEmpty(gasPrice)) {
                    feeRate = new BigDecimal(feeRate).longValue();
                }
                long fee = getFee(value, unSpentBTCList, feeRate);
                String rawTransaction = signBTCTransactionData(unSpentBTCList, from, to, privateKey, value, fee, mainNet);
                String txHash = sendTx(rawTransaction, mainNet);
                if (StringUtils.isEmpty(txHash)) {
                    return false;
                }
                //入库
                TransactionRecord record = new TransactionRecord();
                record.setStatus(0);
                record.setValue(0.001);
                record.setTxHash(txHash);
                record.setToAddr(to);
                record.setFromAddr(from);
                record.setWalletType(WalletType.ETH.getWalletType());
                record.setDirect(0);
                record.setTokenId((int) transaction.getTokenInfo().getId());
                record.setTimeStamp(new Date());
                record.setRemark(transaction.getRemark());
                TransationRecordDaoUtils txDaoUtil = TransationRecordDaoUtils.getIntence();
                txDaoUtil.insert(record);
                LogUtils.i("交易hash=> " + txHash);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
        }
        return false;
    }

    /**
     * 余额
     *
     * @param address
     * @param mainNet
     * @return
     */
    public String balance(String address, boolean mainNet) {
        String host = mainNet ? "blockchain.info" : "testnet.blockchain.info";
        String url = "https://" + host + "/balance?active=" + address;
        OkHttpClient client = new OkHttpClient();
        String response = null;
        try {
            response = client.newCall(new Request.Builder().url(url).build()).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Map<String, Object>> result = new Gson().fromJson(response, Map.class);
        Map<String, Object> balanceMap = result.get(address);
        BigDecimal finalBalance = BigDecimal.valueOf((double) balanceMap.get("final_balance"));
        BigDecimal balance = finalBalance.divide(new BigDecimal(100000000));
        return balance.toPlainString();
    }

    /***
     * 获取未消费列表
     * @param address ：地址
     * @return
     */
    public static List<UTXO> getUnspent(String address, boolean mainNet) {
        List<UTXO> utxos = Lists.newArrayList();
        String host = mainNet ? "blockchain.info" : "testnet.blockchain.info";
        String url = "https://" + host + "/unspent?active=" + address;
        try {

            OkHttpClient client = new OkHttpClient();
            String response = client.newCall(new Request.Builder().url(url).build()).execute().body().string();
            if (StringUtils.equals("No free outputs to spend", response)) {
                return utxos;
            }

            JSONObject jsonObject = JSONObject.parseObject(response);
            JSONArray unspentOutputs = jsonObject.getJSONArray("unspent_outputs");
            List<Map> outputs = JSONArray.parseArray(unspentOutputs.toJSONString(), Map.class);
            if (outputs == null || outputs.size() == 0) {
                System.out.println("交易异常，余额不足");
            }
            for (int i = 0; i < outputs.size(); i++) {
                Map outputsMap = outputs.get(i);
                String tx_hash = outputsMap.get("tx_hash").toString();
                String tx_hash_big_endian = outputsMap.get("tx_hash_big_endian").toString();
                String tx_index = outputsMap.get("tx_index").toString();
                String tx_output_n = outputsMap.get("tx_output_n").toString();
                String script = outputsMap.get("script").toString();
                String value = outputsMap.get("value").toString();
                String value_hex = outputsMap.get("value_hex").toString();
                String confirmations = outputsMap.get("confirmations").toString();
                UTXO utxo = new UTXO(Sha256Hash.wrap(tx_hash_big_endian), Long.valueOf(tx_output_n), Coin.valueOf(Long.valueOf(value)),
                        0, false, new Script(Hex.decode(script)));
                utxos.add(utxo);
            }
            return utxos;
        } catch (Exception e) {
            return null;
        }
    }

    /***
     * 获取未消费列表
     * @param address ：地址
     * @return
     */
    public static List<UTXO> getUnspent2(String address, boolean mainNet) {
        //https://sochain.com/api/v2/get_tx_unspent/BTCTEST/mmEhSocSE8d6c3Hfv2zCtHVKXeoBcmpAmP
        List<UTXO> utxos = Lists.newArrayList();
        String netType = mainNet ? "BTC" : "BTCTEST";
        String url = "https://sochain.com/api/v2/get_tx_unspent/" + netType + "/";
        try {
            OkHttpClient client = new OkHttpClient();
            String response = client.newCall(new Request.Builder()
                    .url(url)
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .addHeader("user-agent", "android")
                    .build()).execute().body().string();
            if (StringUtils.isEmpty(response)) {
                return utxos;
            }

            JSONObject jsonObject = JSONObject.parseObject(response);
            String status = jsonObject.getString("status");
            if ("fail".equals(status)) {
                return utxos;
            }
            JSONObject data = jsonObject.getJSONObject("data");
            if (data != null) {
                JSONArray unspentOutputs = data.getJSONArray("txs");
                List<Map> outputs = JSONArray.parseArray(unspentOutputs.toJSONString(), Map.class);
                if (outputs == null || outputs.size() == 0) {
                    System.out.println("交易异常，余额不足");
                }
                for (int i = 0; i < outputs.size(); i++) {
//                    "txid" : "5696473a88c574b9489f8c97acf940b3aab8805d73214984fabff903dc0b670a",
//                    "output_no" : 0,
//                    "script_asm" : "OP_DUP OP_HASH160 3ebdb431c06938ff9bf22f3679c804a3e5da9b69 OP_EQUALVERIFY OP_CHECKSIG",
//                    "script_hex" : "76a9143ebdb431c06938ff9bf22f3679c804a3e5da9b6988ac",
//                    "value" : "0.01000000",
//                    "confirmations" : 36,
//                    "time" : 1587181967

                    Map outputsMap = outputs.get(i);
                    String tx_hash = outputsMap.get("txid").toString();
                    String tx_hash_big_endian = outputsMap.get("tx_hash_big_endian").toString();
                    String tx_output_n = outputsMap.get("output_no").toString();
                    String script = outputsMap.get("script_hex").toString();
                    String value = outputsMap.get("value").toString();
                    String confirmations = outputsMap.get("confirmations").toString();
                    UTXO utxo = new UTXO(Sha256Hash.wrap(tx_hash),
                            Long.valueOf(tx_output_n),
                            Coin.valueOf(Long.valueOf(value)),
                            0, false, new Script(Hex.decode(script)));
                    utxos.add(utxo);
                }
                LogUtils.e("uspent size:" + utxos.size());
                return utxos;
            }
            return utxos;
        } catch (Exception e) {
            return utxos;
        }
    }

    /***
     * 获取未消费列表
     * @param address ：地址
     * @return
     */
    public static List<UTXO> getUnspent3(String address, boolean mainNet) {
        // https://api.blockcypher.com/v1/btc/test3/addrs/mg3A67jfHebL2iFoEXYwZ5zChNvV6ApqRR
        // https://api.blockcypher.com/v1/btc/test3/txs/0c590aa8b3093fe9d0f6b2a3f9e032847752eb9c15ba4795fb42a068c74340ab

        List<UTXO> utxos = Lists.newArrayList();
        String netType = mainNet ? "main" : "test3";
        String url = "https://api.blockcypher.com/v1/btc/" + netType + "/addrs/" + address;
        try {
            OkHttpClient client = new OkHttpClient();
            String response = client.newCall(new Request.Builder()
                    .url(url)
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .addHeader("user-agent", "android")
                    .build()).execute().body().string();
            if (StringUtils.isEmpty(response)) {
                return utxos;
            }

            JSONObject jsonObject = JSONObject.parseObject(response);
            if (jsonObject != null) {
                JSONArray txList = jsonObject.getJSONArray("txrefs");
                if (txList != null) {
                    List<Map> txListMaps = JSONArray.parseArray(txList.toJSONString(), Map.class);
                    if (txListMaps == null || txListMaps.size() == 0) {
                        System.out.println("交易异常，余额不足");
                    }
                    for (int i = 0; i < txListMaps.size(); i++) {
                        Map outputsMap = txListMaps.get(i);
                        boolean spent = (boolean) outputsMap.get("spent");
                        if (!spent) {
                            String tx_hash = outputsMap.get("tx_hash").toString();
                            int tx_output_n = (int) outputsMap.get("tx_output_n");
                            String value = outputsMap.get("value").toString();

                            //查询签名脚本
                            String url_script = " https://api.blockcypher.com/v1/btc/" + netType + "/txs/" + tx_hash + "?instart=0&outstart=" + tx_output_n + "&limit=1";
                            String responseScript = client.newCall(new Request.Builder()
                                    .url(url_script)
                                    .build()).execute().body().string();
                            if (!StringUtils.isEmpty(responseScript)) {
                                JSONObject jsonObjectScript = JSONObject.parseObject(responseScript);
                                if (jsonObjectScript != null) {
                                    JSONArray outputs = jsonObjectScript.getJSONArray("outputs");
                                    if (outputs != null && outputs.size() > 0) {
                                        JSONObject jsonObject1 = outputs.getJSONObject(0);
                                        String script = jsonObject1.get("script").toString();
                                        UTXO utxo = new UTXO(Sha256Hash.wrap(tx_hash),
                                                Long.valueOf(tx_output_n),
                                                Coin.valueOf(Long.valueOf(value)),
                                                0, false, new Script(Hex.decode(script)));
                                        utxos.add(utxo);
                                    }
                                }
                            }
                        }
                    }
                    LogUtils.e("uspent size:" + utxos.size());
                    return utxos;
                }
            }
            return utxos;
        } catch (Exception e) {
            return utxos;
        }
    }

    /**
     * 离线签名
     *
     * @param unSpentBTCList
     * @param from
     * @param to
     * @param privateKey
     * @param value
     * @param fee
     * @param mainNet
     * @return
     * @throws Exception
     */
    public static String signBTCTransactionData(List<UTXO> unSpentBTCList, String from, String to, String privateKey, long value, long fee, boolean mainNet) throws Exception {
        NetworkParameters networkParameters = null;
        if (mainNet)
            networkParameters = MainNetParams.get();
        else
            networkParameters = TestNet3Params.get();

        Transaction transaction = new Transaction(networkParameters);
        DumpedPrivateKey dumpedPrivateKey = DumpedPrivateKey.fromBase58(networkParameters, privateKey);

        ECKey ecKey = dumpedPrivateKey.getKey();

        long totalMoney = 0;
        List<UTXO> utxos = new ArrayList<>();
        //遍历未花费列表，组装合适的item
        for (UTXO us : unSpentBTCList) {
            if (totalMoney >= (value + fee))
                break;
            UTXO utxo = new UTXO(us.getHash(), us.getIndex(), us.getValue(), us.getHeight(), us.isCoinbase(), us.getScript());
            utxos.add(utxo);
            totalMoney += us.getValue().value;
        }

        transaction.addOutput(Coin.valueOf(value), Address.fromBase58(networkParameters, to));
        // transaction.

        //消费列表总金额 - 已经转账的金额 - 手续费 就等于需要返回给自己的金额了
        long balance = totalMoney - value - fee;
        //输出-转给自己
        if (balance > 0) {
            transaction.addOutput(Coin.valueOf(balance), Address.fromBase58(networkParameters, from));
        }
        //输入未消费列表项
        for (UTXO utxo : utxos) {
            TransactionOutPoint outPoint = new TransactionOutPoint(networkParameters, utxo.getIndex(), utxo.getHash());
            transaction.addSignedInput(outPoint, utxo.getScript(), ecKey, Transaction.SigHash.ALL, true);
        }

        return Hex.toHexString(transaction.bitcoinSerialize());
    }

    /**
     * 全网广播交易
     *
     * @param tx
     * @param mainNet
     * @return
     */
    public static String sendTx(String tx, boolean mainNet) {
        String url = "";
        if (mainNet) {
            url = "https://api.blockcypher.com/v1/btc/main/txs/push";
        } else {
            url = "https://api.blockcypher.com/v1/btc/test3/txs/push";
        }
        String response = null;
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tx", tx);
            response = client.newCall(new Request.Builder().url(url).post(RequestBody.create(MediaType.parse("application/json"), jsonObject.toJSONString())).build()).execute().body().string();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
        }
        return response;
    }

    /**
     * 获取费率
     *
     * @param level 3 fastestFee 2 halfHourFee 1 hourFee default fastestFee
     * @return
     */
    public static String feeRate(int level) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://bitcoinfees.earn.com/api/v1/fees/recommended";
        String response = null;
        try {
            response = client.newCall(new Request.Builder().url(url).build()).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(response);
        switch (level) {
            case 1:
                return jsonObject.getBigDecimal("hourFee").toPlainString();
            case 2:
                return jsonObject.getBigDecimal("halfHourFee").toPlainString();
            default:
                return jsonObject.getBigDecimal("fastestFee").toPlainString();
        }
    }

    /**
     * 获取矿工费用
     *
     * @param amount
     * @param utxos
     * @return
     */
    public Long getFee(long amount, List<UTXO> utxos, long defaultFeeRate) {
        Long feeRate = defaultFeeRate;
        if (defaultFeeRate <= 0) {
            feeRate = Long.valueOf(feeRate(3));//获取费率
        }

        LogUtils.e("费率：" + feeRate);
        Long utxoAmount = 0L;
        Long fee = 0L;
        Long utxoSize = 0L;
        for (UTXO us : utxos) {
            utxoSize++;
            if (utxoAmount >= (amount + fee)) {
                break;
            } else {
                utxoAmount += us.getValue().value;
                fee = (utxoSize * 148 + 34 * 2 + 10) * feeRate;
            }
        }
        return fee;
    }

    @Override
    public BigDecimal getBalance(String addr, String contract) {
        BigDecimal balance = BigDecimal.ZERO;
        String balanceStr = balance(addr, false);
        if (!StringUtils.isEmpty(balanceStr)) {
            balance = new BigDecimal(balanceStr);
        }
        LogUtils.i("BTC Balance:" + balanceStr);
        return balance;
    }

    public List<TransactionRecord> getTxListByAddress(String address) {
        //https://blockchain.info/rawaddr/$bitcoin_address
        boolean mainNet = false;
        String host = mainNet ? "blockchain.info" : "testnet.blockchain.info";
        String url = "https://" + host + "/rawaddr/" + address;
        OkHttpClient client = new OkHttpClient();
        String response = null;
        try {
            response = client.newCall(new Request.Builder().url(url).build()).execute().body().string();
            if (!StringUtils.isEmpty(response)) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                JSONArray unspentOutputs = jsonObject.getJSONArray("txs");
                List<Map> txArray = JSONArray.parseArray(unspentOutputs.toJSONString(), Map.class);
                if (txArray == null || txArray.size() == 0) {
                    System.out.println("交易异常，余额不足");
                }
                for (int i = 0; i < txArray.size(); i++) {
                    LogUtils.i("交易详情：" + i);
                }
                return null;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TransactionRecord getTxByHash(String txHash) {
        boolean mainNet = false;
//        https://blockchain.info/rawtx/$tx_hash
        String host = mainNet ? "blockchain.info" : "testnet.blockchain.info";
        String url = "https://" + host + "/rawtx/" + txHash;
        OkHttpClient client = new OkHttpClient();
        String response = null;
        try {
            response = client.newCall(new Request.Builder().url(url).build()).execute().body().string();
            if (!StringUtils.isEmpty(response)) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                Long block_height = jsonObject.getLong("block_height");
                if (block_height != null && block_height > 0) {
                    TransactionRecord record = new TransactionRecord();
                    record.setBlockNumber(block_height + "");
                    record.setStatus(1);
                    return record;
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
