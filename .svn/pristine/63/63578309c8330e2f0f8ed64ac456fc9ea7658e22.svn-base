package com.iycharge.server.admin.message.listener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.iycharge.server.ccu.msg.entity.ChargerCommLog;
import com.iycharge.server.ccu.msg.entity.ChargingRecord;
import com.iycharge.server.ccu.service.ChargerCommLogService;
import com.iycharge.server.ccu.service.ChargingRecordService;
import com.iycharge.server.ccu.util.Utility;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.OrderItem;
import com.iycharge.server.domain.entity.order.OrderStatus;
import com.iycharge.server.domain.entity.price.ParamSettingResult;
import com.iycharge.server.domain.entity.price.ParamType;
import com.iycharge.server.domain.service.OrderService;
import com.iycharge.server.domain.service.ParamSettingResultService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 消息监听器
 * @author bwang
 */
@Component("ccuMessageListener")
public class CCUMessageListener implements MessageListener {
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    @Resource
    private ChargerCommLogService chargerCommLogService;
    
    @Resource
    private ParamSettingResultService paramSettingResultService;
    
    @Resource
    private OrderService orderService;
    
    @Resource
    private ChargingRecordService chargingRecordService;
    
    @Override
    public void onMessage(Message message, byte[] pattern) {
       String channel = (String)redisTemplate.getKeySerializer().deserialize(message.getChannel());
       if(MessageChannel.MSG_CHANNAL_CONN.equals(channel)) {
           saveChargerCommLog(message.getBody());
       } else if(MessageChannel.MSG_CHANNAL_PARAM.equals(channel)) {
           updateParamSettingResult(ParamType.PARAM.name(), message.getBody());
       } else if(MessageChannel.MSG_CHANNAL_PRICE.equals(channel)){
           updateParamSettingResult(ParamType.PRICE.name(), message.getBody());
       } else if(MessageChannel.MSG_CHANNAL_INTERVAL.equals(channel)) {
           updateParamSettingResult(ParamType.INTERVAL.name(), message.getBody());
       }else if(MessageChannel.MSG_CHANNAL_RECORD.equals(channel)) {
           saveChargingRecordLog(message.getBody());
       } else if(MessageChannel.MSG_CHANNAL_EVENT.equals(channel)) {
           
       }
    }
    
    //保存 电桩断连日志
    private void saveChargerCommLog(byte[] body) {
        ChargerCommLog log =  (ChargerCommLog)redisTemplate.getValueSerializer().deserialize(body);
        chargerCommLogService.save(log);       
    }
    
    //更新参数设计结果
    private void updateParamSettingResult(String paramType, byte[] body) {
        JSONObject result = JSONObject.fromObject(redisTemplate.getValueSerializer().deserialize(body));
        ParamSettingResult paramSettingResult = paramSettingResultService.findLatestResult(result.getString("chargerCode"), paramType);
        if(paramSettingResult != null) {
            paramSettingResult.setResult(result.getInt("result") > 0);
            paramSettingResultService.save(paramSettingResult);
        }
    }
    
    //保存充电记录相关数据
    private void saveChargingRecordLog(byte[] body) {
        ChargingRecord chargingRecord = (ChargingRecord)redisTemplate.getValueSerializer().deserialize(body);
        chargingRecordService.save(chargingRecord);
        
        //更新充电记录
        Order order = new Order();
        order.setStatus(chargingRecord.isPayFlag() ? OrderStatus.PAID : OrderStatus.UNPAID);
        order.setStartAt(chargingRecord.getStartAt());
        order.setEndAt(chargingRecord.getEndAt());
        order.setStartSoc(chargingRecord.getStartSoc());
        order.setEndSoc(chargingRecord.getEndSoc());
        order.setDegree(chargingRecord.getDegree());
        order.setIfName(chargingRecord.getIfName());
        order.setIfType(String.valueOf(chargingRecord.getIfType()));
        order.setChargeTime(chargingRecord.getChargeTime());
        if(chargingRecord.getAuthType() == 1) {
            //刷卡充电
            Card card = new Card();
            card.setCardNo(chargingRecord.getOrderId());
            order.setCard(card);
        } else {
            //app充电
            order.setOrderId(chargingRecord.getOrderId());
        }
        
        Charger charger = new Charger();
        charger.setCode(chargingRecord.getChargerNo());
        order.setCharger(charger);
        BigDecimal payment = new BigDecimal(0); 
        List<OrderItem> orderItems = new ArrayList<>();
        JSONArray array = JSONArray.fromObject(chargingRecord.getItemDetail());
        if(!array.isEmpty()) {
            OrderItem orderItem = null;
            for(int i=0; i<array.size(); i++) {
                JSONObject item = array.getJSONObject(i);
                if("".equals(item.getString("startAt")) || "".equals(item.getString("endAt"))) {
                    continue;
                }
                orderItem = new OrderItem();
                orderItem.setStartAt(Utility.parseDate(item.getString("startAt"), "yyyyMMddHHmmss"));
                orderItem.setEndAt(Utility.parseDate(item.getString("endAt"), "yyyyMMddHHmmss"));
                orderItem.setDegree(new BigDecimal(item.getString("degree")));
                orderItem.setPrice(new BigDecimal(item.getString("price")));
                orderItem.setFee(new BigDecimal(item.getString("fee")));
                orderItem.setMoney(new BigDecimal(item.getString("money")));
                payment = payment.add(orderItem.getMoney());
                orderItem.setOrder(order);
                orderItems.add(orderItem);                        
            }
        }
        order.setMoney(payment);
        order.setOrderItems(orderItems);
        orderService.updateOrder(order);
    }
    
}
