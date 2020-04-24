package com.carbonchain.server.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.carbonchain.server.constant.ErrorCode;
import com.carbonchain.server.dto.GeneralJsonResult;
import com.carbonchain.server.mapper.MemberInfoMapper;
import com.carbonchain.server.mapper.WalletAccountMapper;
import com.carbonchain.server.mapper.WalletCoinMapper;
import com.carbonchain.server.model.WalletAccount;
import com.carbonchain.server.model.WalletCoin;
import com.carbonchain.server.service.wallet.WalletConstants;
import com.carbonchain.server.service.wallet.WalletType;
import com.carbonchain.server.service.wallet.WalletCreatorFactory;
import com.carbonchain.server.service.wallet.pojo.TransactionModel;
import com.carbonchain.server.service.wallet.pojo.TransferData;
import com.carbonchain.server.service.wallet.btc.BtcService;
import com.carbonchain.server.service.wallet.eth.EtherService;
import com.carbonchain.server.service.wallet.pojo.request.WalletRequestModel;
import com.carbonchain.server.service.wallet.pojo.response.WalletInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * created by collin on 2020/3/23 12:06
 * Description: 钱包相关接口
 * version: 1.0
 */
@Path("/wallet")
@Slf4j
@Consumes({MediaType.APPLICATION_JSON})
public class WalletController extends ControllerBase {
    @Autowired
    EtherService etherService;
    @Autowired
    BtcService btcService;
    @Autowired
    WalletCreatorFactory walletCreatorFactory;
    @Autowired
    WalletAccountMapper walletAccountMapper;
    @Autowired
    WalletCoinMapper coinMapper;
    @Autowired
    MemberInfoMapper memberInfoMapper;

    /**
     * 钱包列表
     * @param model
     * @return
     */
    @POST
    @Path("/myWallets")
    public GeneralJsonResult getMyWallet(@RequestBody WalletRequestModel model) {
        String userId = model.getUserId();
        if (StringUtils.isEmpty(userId)){
            return GeneralJsonResult.error(ErrorCode.API_PARAM_ERROR,"非法参数userId不能为空");
        }

        EntityWrapper<WalletAccount> walletWrapper = new EntityWrapper<>();
        walletWrapper.eq("user_id",userId);
        List<WalletAccount> walletAccounts = walletAccountMapper.selectList(walletWrapper);
        return  GeneralJsonResult.success(walletAccounts);
    }

    /**
     * 获取助记词
     *
     * @return
     */
    @POST
    @Path("/getMnemonic")
    public GeneralJsonResult getMnemonic(@RequestBody WalletRequestModel model) {
        String walletType = model.getWalletType();
        String walletId = model.getWalletId();

        WalletAccount conditionWrapper = new WalletAccount();
        conditionWrapper.setId(Integer.parseInt(walletId));
        conditionWrapper.setType(Integer.parseInt(walletType));
        WalletAccount walletAccount = walletAccountMapper.selectOne(conditionWrapper);
        if (walletAccount!=null){
            String mnemonic =  walletAccount.getMnemonic();
            //todo 对助记词解密
            return GeneralJsonResult.success(walletAccount.getMnemonic());
        }else{
            return GeneralJsonResult.error(ErrorCode.API_PARAM_ERROR,"未查到该钱包助记词");
        }
    }

    /**
     * 获取钱包详情
     * @param model
     * @return
     */
    @POST
    @Path("/getWlletInfo")
    public GeneralJsonResult getMyWalletInfo(@RequestBody WalletRequestModel model) {
        String userId =model.getUserId();
        String walletId = model.getWalletId();

        if (StringUtils.isEmpty(userId)||StringUtils.isEmpty(walletId)){
           return GeneralJsonResult.error(ErrorCode.API_PARAM_ERROR,"非法参数");
        }
        WalletInfoModel walletInfoModel = new WalletInfoModel();
        //钱包信息
        WalletAccount walletWrapper = new WalletAccount();
        walletWrapper.setUserId(userId);
        walletWrapper.setId(Integer.parseInt(walletId));
        WalletAccount walletAccount = walletAccountMapper.selectOne(walletWrapper);

        if (walletAccount!=null){
            //代币列表
            String coins = walletAccount.getCoins();
            String[] coinArray = coins.split(WalletConstants.COIIN_SPLIT_CHAR);

            EntityWrapper<WalletCoin> entityWrapper = new EntityWrapper<>();
            entityWrapper.in("id",coinArray);
            List<WalletCoin> walletCoins = coinMapper.selectList(entityWrapper);
            List<WalletInfoModel.CoinResponse> coinInfos = Collections.emptyList();
            for (WalletCoin coin:walletCoins) {
                WalletInfoModel.CoinResponse coinInfo = new WalletInfoModel.CoinResponse();
                BeanUtils.copyProperties(coin,coinInfo);
                BigDecimal coinBalance= BigDecimal.ZERO;
                WalletType of = WalletType.of(walletAccount.getType());
                switch (of){
                    case eth:
                         coinBalance = etherService.getBalance(walletAccount.getAddress(), coin.getAddress());
                        break;
                    case btc:
                        coinBalance = btcService.getBalance(walletAccount.getAddress(), coin.getAddress());
                        break;
                }
                coinInfo.setBalance(coinBalance);
            }
            BeanUtils.copyProperties(walletAccount,walletInfoModel);
            walletInfoModel.setWalletCoinlist(coinInfos);
        }
        return GeneralJsonResult.success(walletInfoModel);
    }

    /**
     * 转账
     * @param model
     * @return
     */
    @POST
    @Path("/sendToken")
    public GeneralJsonResult sendToken(@RequestBody TransferData model) {
        String coinTypeStr = model.getCoinType();
        String from = model.getFrom();
        String to = model.getTo();
        BigDecimal value = model.getValue();
        String gas = model.getGas();
        String gasPrice = model.getGasPrice();

        if (StringUtils.isEmpty(coinTypeStr) || StringUtils.isEmpty(from) || StringUtils.isEmpty(to) || BigDecimal.ZERO.compareTo(value) >= 0) {
            return GeneralJsonResult.error(ErrorCode.API_PARAM_ERROR,"非法参数");
        }
        boolean result = false;
        int cointTypeInt = Integer.parseInt(coinTypeStr);
        switch (WalletType.of(cointTypeInt)) {
            case eth:
                result = etherService.transferToken(model);
                break;
            case btcTest:
            case btc:
                result  = btcService.transferToken(model);
                break;
        }
        if (result){
            return GeneralJsonResult.success("转账成功");
        }
        return  GeneralJsonResult.error("转账失败！");
    }

    /**
     * 交易记录
     * @param model
     * @return
     */
    @POST
    @Path("/transactionRecords")
    public GeneralJsonResult getTransactionRecords(WalletRequestModel model){
        String coinId = model.getCoinId();
        String walletAddress = model.getWalletAddress();

        if (StringUtils.isEmpty(coinId)||StringUtils.isEmpty(walletAddress)){
            return GeneralJsonResult.error(ErrorCode.API_PARAM_ERROR,"非法参数");
        }
       List<TransactionModel> transactionList = Collections.emptyList();
        WalletCoin condition = new WalletCoin();
        condition.setId(Integer.parseInt(coinId));
        WalletCoin walletCoin = coinMapper.selectOne(condition);
        if (walletCoin!=null){
            WalletType typeOf = WalletType.of(walletCoin.getWalletType());
            switch (typeOf){
                case eth:
                    transactionList = etherService.getTransactions(walletAddress,walletCoin.getAddress());
                    break;
                case btcTest:
                case btc:
                    transactionList = btcService.getTransactions(walletAddress);
                    break;
            }
        }

        return GeneralJsonResult.success(transactionList);
    }

}
