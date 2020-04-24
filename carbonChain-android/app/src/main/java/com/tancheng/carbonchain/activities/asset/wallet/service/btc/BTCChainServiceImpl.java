package com.tancheng.carbonchain.activities.asset.wallet.service.btc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.spark.blockchain.rpcclient.Bitcoin;
import com.spark.blockchain.rpcclient.BitcoinRPCClient;
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
import org.bitcoinj.script.Script;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * created by tc_collin on 2020/4/16 17:00
 * Description: btc相关服务 -使用JSON-RPC方式调用
 * version: 1.0
 */
public class BTCChainServiceImpl implements IchainService {
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
            String from = transaction.getFrom();
            String to = transaction.getTo();
            long value = transaction.getValue().longValue();

            String gasPrice = transaction.getGasPrice();
            List<UTXO> unSpentBTCList = getUnspent(from);
            if (unSpentBTCList != null && unSpentBTCList.size() > 0) {
                long feeRate = 10;
                if (!StringUtils.isEmpty(gasPrice)) {
                    feeRate = new BigDecimal(feeRate).longValue();
                }
                long fee = getFee(value, unSpentBTCList, feeRate);
                String rawTransaction = signBTCTransactionData(unSpentBTCList, from, to, privateKey, value, fee);
                String txHash = sendTx(rawTransaction);
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
                LogUtils.i("BTC transaction success txHash => " + txHash);
                return true;
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

    /**
     * 使用rpc方式
     *
     * @param address
     * @return
     */
    public static List<UTXO> getUnspent(String address) {
        try {
            List<UTXO> utxos = Lists.newArrayList();
            BitcoinRPCClient clint = BTCClintUtil.getClint();
            List<Bitcoin.Unspent> unspents = clint.listUnspent(0, 999999, address);
            for (Bitcoin.Unspent unspent : unspents) {
                String tx_hash = unspent.txid();
                int tx_output_n = unspent.vout();
                String script = unspent.scriptPubKey();
                BigDecimal value = unspent.amount();
                int confirmations = unspent.confirmations();
                UTXO utxo = new UTXO(Sha256Hash.wrap(tx_hash),
                        Long.valueOf(tx_output_n),
                        Coin.valueOf(value.longValue()),
                        0, false, new Script(Hex.decode(script)));
                utxos.add(utxo);
            }
            return utxos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
     * @return
     * @throws Exception
     */
    public static String signBTCTransactionData(List<UTXO> unSpentBTCList, String from, String to, String privateKey, long value, long fee) throws Exception {
        NetworkParameters networkParameters = BTCClintUtil.getNetworkParams();
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
     * @return
     */
    public static String sendTx(String tx) {
        try {
            BitcoinRPCClient clint = BTCClintUtil.getClint();
            String s = clint.sendRawTransaction(tx);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
        }
        return "";
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
        // https://api.blockcypher.com/v1/btc/test3/addrs/mpp4LJNo7iqooB5syY416X78SX8LCoDN9R
        boolean mainNet = false;
        String netType = mainNet ? "main" : "test3";
        String url = "https://api.blockcypher.com/v1/btc/" + netType + "/addrs/" + address;
        OkHttpClient client = new OkHttpClient();
        List<TransactionRecord> resultDatas = new ArrayList<>();
        try {
            String response = client.newCall(new Request.Builder().url(url).build()).execute().body().string();
            JSONObject jsonObject = JSONObject.parseObject(response);
            if (jsonObject != null) {
                JSONArray txList = jsonObject.getJSONArray("txrefs");
                if (txList != null) {
                    List<Map> txListMaps = JSONArray.parseArray(txList.toJSONString(), Map.class);
                    if (txListMaps != null && txListMaps.size() == 0) {
                        for (int i = 0; i < txListMaps.size(); i++) {
                            Map outputsMap = txListMaps.get(i);
                            TransactionRecord record = new TransactionRecord();
                            boolean spent = (boolean) outputsMap.get("spent");
                            String tx_hash = outputsMap.get("tx_hash").toString();
                            String block_height = outputsMap.get("block_height").toString();
                            int tx_output_n = (int) outputsMap.get("tx_output_n");
                            String value = outputsMap.get("value").toString();
                        }
                        return resultDatas;
                    }
                }
            }
            return resultDatas;
        } catch (Exception e) {
            e.printStackTrace();
            return resultDatas;
        }
    }

    @Override
    public TransactionRecord getTxByHash(String txHash) {
        boolean mainNet = false;
//        https://blockchain.info/rawtx/$tx_hash
        try {
            BitcoinRPCClient clint = BTCClintUtil.getClint();
            Bitcoin.RawTransaction transaction = clint.getTransaction(txHash);
            TransactionRecord record = null;
            if (transaction != null) {
                record = new TransactionRecord();
                record.setStatus(transaction.confirmations() > 0 ? 1 : 0);
                record.setBlockNumber(transaction.blockHash() + "");
                record.setRemark(transaction.confirmations() + "");
                record.setTimeStamp(transaction.blocktime());
            }
            return record;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
            return null;
        }
    }

}
