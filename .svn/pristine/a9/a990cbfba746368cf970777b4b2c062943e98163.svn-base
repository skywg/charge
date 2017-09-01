package com.iycharge.server.ccu.exec;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.request.CardAuthReq;
import com.iycharge.server.ccu.msg.response.CardAuthResp;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.AccountStation;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.account.CardStatus;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.service.AccountStationService;
import com.iycharge.server.domain.service.CardService;
import com.iycharge.server.domain.service.ChargerService;

/**
 * 刷卡充电认证信息上传
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_52)
@Service("MSG_" + MsgType._0X_52)
public class CardAuthMsgExecutor extends RequestLogicExecutorBase {
    
    /**
     * 验证成功
     */
    private static final int RESP_CODE_1 = 1;
    
    /**
     * 无效卡
     */
    private static final int RESP_CODE_2 = 2;
    
    /**
     * 密码错误
     */
    private static final int RESP_CODE_3 = 3;
    
    /**
     * 余额不足
     */
    private static final int RESP_CODE_4 = 4;
    
    /**
     * 卡已锁定
     */
    private static final int RESP_CODE_5 = 5;
   // ==================新增=======================//
    /**
     * 卡无权限
     */
    private static final int RESP_CODE_6 = 6;
    
    /**
     * 卡无在使用中
     */
    private static final int RESP_CODE_7 = 7;
    /**
     * 充电桩在平台未注册
     */
    private static final int RESP_CODE_8 = 8;
    // =================新增========================// 
    /**
     * 允许充值的最小金额
     */
    private static final int MIN_BALANCE = 10;
    
    private Logger logger = LoggerFactory.getLogger(CardAuthMsgExecutor.class);
    
    @Autowired
    private CardService cardService;
  //==================新增====================//
    @Autowired
    private AccountStationService accountStationService;
    @Autowired
    private ChargerService chargerService;
  //==================新增====================//
    @Override
    public void run() {
        CardAuthReq req = new CardAuthReq(MsgType._0X_52, msgObject);
        if(req.check()) {
            logger.info("CardAuth=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            try {
            Map<String, Object> reqData = req.parse();
            Card  card =  cardService.findByCardNo((String)reqData.get("cardNo"));
            Map<String, Object> respData  = new HashMap<>();
            respData.put("chargerNo", reqData.get("chargerNo"));
            if(card != null) {
                Account account = card.getAccount();
                if(card.getStatus() == CardStatus.NORMAL) {
                    if(card.getMoney().doubleValue() < 0) {
                        respData.put("respCode", RESP_CODE_5);
                    } else if (card.getMoney().doubleValue() < MIN_BALANCE) {
                        respData.put("respCode", RESP_CODE_4);
                    } else {
                    	if(account!=null&&account.getAccountType().equals("COMPANY")){
                            String chargerNo = (String)reqData.get("chargerNo");
                            Charger charger = chargerService.findByCode(chargerNo);
                            if(charger!=null && !StringUtils.isEmpty(charger.getCode())){
                            	  Station station = charger.getStation();
                                  List<AccountStation> permits = accountStationService.findByAccountAndStation(account,station);
                                  if(permits==null||permits.size()<1){
                                	  respData.put("respCode", RESP_CODE_6);
                                  } else {
                                	  respData.put("respCode", RESP_CODE_1);
                                  }
                            } else {
                            	respData.put("respCode", RESP_CODE_6);
                            } 
                        } else {
                        	respData.put("respCode", RESP_CODE_1);
                        }
                    }
                } else if(card.getStatus() == CardStatus.CHARGERING_CARD){
                	respData.put("respCode", RESP_CODE_7);
                } else {
                    respData.put("respCode", RESP_CODE_5);
                }
                
                respData.put("balance", card.getMoney());
                respData.put("phone"  , card.getPhoneNo());
            } else {
                respData.put("respCode", RESP_CODE_2);
                respData.put("balance", new BigDecimal(0));
                respData.put("phone", null);
            }
            
            CardAuthResp cardAuthResp = new CardAuthResp(MsgType._0X_53);
            cardAuthResp.setResponseBody(respData);
            cardAuthResp.format();         
            channel.writeAndFlush(cardAuthResp.getMsgObject());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    }
}
