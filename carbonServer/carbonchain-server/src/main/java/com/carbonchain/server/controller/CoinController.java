package com.carbonchain.server.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.carbonchain.server.constant.ErrorCode;
import com.carbonchain.server.dto.GeneralJsonResult;
import com.carbonchain.server.mapper.WalletAccountMapper;
import com.carbonchain.server.mapper.WalletCoinMapper;
import com.carbonchain.server.model.WalletAccount;
import com.carbonchain.server.model.WalletCoin;
import com.carbonchain.server.service.wallet.WalletConstants;
import com.carbonchain.server.service.wallet.pojo.request.WalletRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * created by tc_collin on 2020/3/24 14:24
 * Description: 代币管理
 * version: 1.0
 */
@Path("/wallet/coin")
@Slf4j
@Consumes({MediaType.APPLICATION_JSON})
public class CoinController {

    @Autowired
    private WalletCoinMapper coinMapper;
    @Autowired
    private WalletAccountMapper walletMapper;

    /**
     * 代币列表
     *
     * @param model
     * @return
     */
    @POST
    @Path("/coinlist")
    public GeneralJsonResult coinlist(@RequestBody WalletRequestModel model) {
        String walletType = model.getWalletType();

        if (StringUtils.isEmpty(walletType)) {
            return GeneralJsonResult.error(ErrorCode.API_PARAM_ERROR, "非法参数");
        }

        EntityWrapper<WalletCoin> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("wallet_type", walletType)
                .orderBy("serial_num", true);
        List<WalletCoin> walletCoins = coinMapper.selectList(entityWrapper);
        return GeneralJsonResult.success(walletCoins);
    }

    /**
     * 增、删钱包代币
     *
     * @param model
     * @return
     */
    @POST
    @Path("/updateMyCoins")
    public GeneralJsonResult updateMyCoinList(@RequestBody WalletRequestModel model) {
        String walletId = model.getWalletId();
        String coinId = model.getCoinId();
        int action = model.getAction();
        if (StringUtils.isEmpty(walletId)) {
            return GeneralJsonResult.error(ErrorCode.API_PARAM_ERROR, "非法参数");
        }

        WalletAccount condition = new WalletAccount();
        condition.setId(Integer.parseInt(walletId));
        WalletAccount walletAccount = walletMapper.selectOne(condition);
        if (walletAccount == null)
            return GeneralJsonResult.error(ErrorCode.PARAM_ERROR,"未查到钱包信息");
        String coins = walletAccount.getCoins();
        if (action == 0) {//删除
            if (StringUtils.isEmpty(coins)) {
                String[] coinArray = coins.split(WalletConstants.COIIN_SPLIT_CHAR);
                StringBuffer coinsBuffer = new StringBuffer();
                for (String coin : coinArray) {
                    if (coin.equals(coinId))
                        continue;
                    coinsBuffer.append(coin).append(WalletConstants.COIIN_SPLIT_CHAR);
                }
                if (StringUtils.isNotEmpty(coinsBuffer)) {
                    coins = coinsBuffer.toString().substring(0, coinsBuffer.length() - 1);
                }
                walletAccount.setCoins(coins);
                walletMapper.updateById(walletAccount);
            }
        } else if (action == 1) {//添加
            String newCoins = coins + WalletConstants.COIIN_SPLIT_CHAR;
            walletAccount.setCoins(newCoins);
            walletMapper.updateById(walletAccount);
        }
        return GeneralJsonResult.success();
    }

    //---------------后台管理系统API-------------//

    /**
     * 新增币种
     *
     * @param model
     * @return
     */
    @POST
    @Path("/addCoin")
    public GeneralJsonResult addCoin(@RequestBody WalletCoin model) {
        int walletType = model.getWalletType();
        String symbol = model.getSymbol();
        Integer decimals = model.getDecimals();
        String name = model.getName();

        if (StringUtils.isEmpty(walletType + "")
                || StringUtils.isEmpty(symbol)
                || StringUtils.isEmpty(decimals + "")
                || StringUtils.isEmpty(name)) {
            return GeneralJsonResult.error(ErrorCode.API_PARAM_ERROR,"非法参数");
        }
        Integer insert = coinMapper.insert(model);
        if (insert>0){
            return GeneralJsonResult.success();
        }
        return GeneralJsonResult.error("币种新增失败!");
    }

    /**
     * 修改币种
     * @param model
     * @return
     */
    @POST
    @Path("/updateCoin")
    public GeneralJsonResult updateCoin(@RequestBody WalletCoin model) {
        int walletType = model.getWalletType();
        String symbol = model.getSymbol();
        Integer decimals = model.getDecimals();
        String name = model.getName();

        if (StringUtils.isEmpty(walletType + "")
                || StringUtils.isEmpty(symbol)
                || StringUtils.isEmpty(decimals + "")
                || StringUtils.isEmpty(name)) {
            return GeneralJsonResult.error(ErrorCode.API_PARAM_ERROR,"非法参数");
        }
        Integer update = coinMapper.updateById(model);
        if (update>0){
            return GeneralJsonResult.success();
        }
        return GeneralJsonResult.error("币种修改失败!");
    }

}
