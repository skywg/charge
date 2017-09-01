package com.iycharge.server.api.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.cache.RCharger;
import com.iycharge.server.admin.cache.RConnector;
import com.iycharge.server.api.util.IDCreator;
import com.iycharge.server.ccu.service.ChargingFlowService;
import com.iycharge.server.ccu.util.Utility;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.Car;
import com.iycharge.server.domain.entity.charger.ChargerStatus;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.PaidFrom;
import com.iycharge.server.domain.entity.utils.RedisUtil;
import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.domain.service.OrderService;

import net.sf.json.JSONObject;

/**
 * 充电相关接口
 * @author bwang
 */
@RestController
@RequestMapping(value="/api/charging")
public class ChargingRestController {
    
    private Logger logger = LoggerFactory.getLogger(ChargingRestController.class);
    
    private static ExecutorService executorService = Executors.newFixedThreadPool(4);
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Autowired
    private ChargingFlowService chargingFlowService;
    
    /**
     * 检测充电接口连接状态
     * @param chargerCode   电桩编号
     * @param chargerConn   充电接口
     * @return
     */
    @RequestMapping(value="/connector/status", method=RequestMethod.POST)
    public JSONObject checkConnectorStatus(@RequestParam String chargerCode, @RequestParam String chargerConn) {
        logger.info("checkConnectorStatus params ==> {chargerCode : " + chargerCode + ", chargerConn : " + chargerConn +"}");
        
        JSONObject result = new JSONObject();
        
        if(EntityUtil.getCharger(chargerCode) != null) {
            RCharger rcharger = (RCharger)redisUtil.get(RedisUtil.PREFIX_CHARGER + chargerCode);
            if(rcharger != null) {
                List<RConnector> connList = rcharger.getConnectorList();
                if(connList != null && connList.size() > 0) {
                    // 0 : 未连接     1 ： 已连接
                    if(!connList.get(0).isCarConnStatus()) {
                        result.put("state", 0);
                        result.put("errorDescr", "请插枪");
                    } else {
                        if(connList.get(0).getStatus() == ChargerStatus.IDLE) {
                            result.put("state", 1);
                        } else if (connList.get(0).getStatus() == ChargerStatus.CHARGING
                                || connList.get(0).getStatus() == ChargerStatus.OCCUPIED) {
                            result.put("state", 0);
                            result.put("errorDescr", "充电桩正在充电中,请您使用其它电桩");
                        } else if (connList.get(0).getStatus() == ChargerStatus.REPAIR) {
                            result.put("state", 0);
                            result.put("errorDescr", "充电桩故障,请您使用其它电桩");
                        } else if (connList.get(0).getStatus() == ChargerStatus.OFFLINE) {
                            result.put("state", 0);
                            result.put("errorDescr", "充电桩离线,请您使用其它电桩");
                        }
                    }
                }
            }
        } else {
            result.put("state", 0); // 0 : 未连接     1 ： 已连接
            result.put("errorDescr", "电桩不存在");
        }
        return result;
    }
    
    @Transactional
    @RequestMapping(value="/auth", method=RequestMethod.POST)
    public JSONObject chargingAuth(@RequestParam String chargerCode, @RequestParam String chargerConn, Principal principal) {
        logger.info("chargingAuth params ==> {chargerCode : " + chargerCode + ", chargerConn : " + chargerConn +", principal : " + principal.getName() + "}");
        JSONObject result  = new JSONObject();
        try {       
            //验证账户余额
            Account account = accountService.findById(Long.valueOf(principal.getName()));
            if(account.getMoney().doubleValue() <= 0) {
                result.put("state", 0); 
                result.put("errorDescr", "余不足请充值");
                return result;
            }
            
            String orderId = IDCreator.generator(IDCreator.BUS_CHARGING);
            
            //向电桩下发充电认证报文
            JSONObject params = new JSONObject();
            params.put("chargerCode", chargerCode);
            params.put("chargerConn", chargerConn);
            params.put("orderId", orderId);
            params.put("balance", account.getMoney());
            params.put("phone"  , account.getPhone());
            
            Car car = account.getCar();
            params.put("vin"    , car != null ? car.getVinNumber() : null);
            
            Future<Object> sendMsgFuture = chargingFlowService.sendAuthMsg(params);
            if(sendMsgFuture == null || !sendMsgFuture.get().equals("success")) {
                result.put("state", 0); 
                result.put("errorDescr", "请求超时");
                return result;
            } else {
                Future<String> authRespFuture = executorService.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        //获取下发认证报文的返回结果
                        int counter = 1;
                        String obj = null;                   
                        while(counter < 50) {
                            obj = (String)redisUtil.get(RedisUtil.PREFIX_AUTH + orderId);                        
                            if(obj != null) {
                               break;
                            }
                            
                            counter++;
                            Thread.sleep(100);
                        }
                        return obj;
                    }
                });
                
                String authRespResult = authRespFuture.get();
                if(JSONObject.fromObject(authRespResult).getInt("result") == 1) {
                    //创建订单
                    Order order = new Order();
                    order.setAccount(account);               
                    order.setCharger(EntityUtil.getCharger(chargerCode));
                    order.setOrderId(orderId);
                    order.setPaidFrom(PaidFrom.WALLET.name());
                    orderService.save(order);
                    
                    result.put("state", 1);
                    result.put("orderId", orderId);
                } else {
                    result.put("state", 0); 
                    result.put("errorDescr", "请求失败");
                }               
            }
        } catch (Exception e) {
            e.printStackTrace();
            
            result.put("state", 0); 
            result.put("errorDescr", "请求失败");
            return result;
        }
        return result;
    }
    
    /**
     * 电桩启动
     * @param chargerCode       电桩编号     
     * @param chargerConn       充电接口
     * @param orderId           订单号
     * @return
     */
    @RequestMapping(value="/start", method=RequestMethod.POST)
    public JSONObject startCharger(@RequestParam String chargerCode, @RequestParam String chargerConn, @RequestParam String orderId) {
        logger.info("startCharger params ==> {chargerCode : " + chargerCode + ", chargerConn : " + chargerConn +", orderId : " + orderId + "}");
        
        JSONObject result = new JSONObject();        
        try {
            JSONObject params = new JSONObject();
            params.put("chargerCode", chargerCode);
            params.put("chargerConn", chargerConn);
            params.put("orderId", orderId);
            
            //发送启动电桩指令
            Future<Object> sendMsgFuture = chargingFlowService.sendStarChargerMsg(params);
            
            if(sendMsgFuture == null || !sendMsgFuture.get().equals("success")) {
                throw new Exception("启动失败");
            }
            
            //获取电桩返回结果
            Future<JSONObject> startRespFuture = executorService.submit(new Callable<JSONObject>() {
                @Override
                public JSONObject call() throws Exception {
                     //获取下发认证报文的返回结果
                    int counter = 1;
                    JSONObject obj = null;  
                    final String key = RedisUtil.PREFIX_START + orderId;
                    while(counter < 50) {
                        if(redisUtil.hasKey(key)) {
                            obj = JSONObject.fromObject(redisUtil.get(key));
                            break;
                        }
                        counter++;
                        Thread.sleep(100);
                    }
                    return obj;
                }
            });
            
            //超时时间设为5秒
            JSONObject startResp = startRespFuture.get(5, TimeUnit.SECONDS);
            
            result.put("state", startResp.getInt("result")); 
            result.put("errorDescr", startResp.getInt("result") == 0 ? "启动失败" : null);
            
        } catch (TimeoutException e1) {
            e1.printStackTrace();
            result.put("state", 0); 
            result.put("errorDescr", "请求超时");
        } catch (Exception e2) {
            e2.printStackTrace();
            result.put("state", 0); 
            result.put("errorDescr", "启动失败！");
        }
        
        return result;
    }
    
    /**
     * 电桩停机
     * @param chargerCode       电桩编号     
     * @param chargerConn       充电接口
     * @param orderId           订单号
     * @return
     */
    @RequestMapping(value="/stop", method=RequestMethod.POST)
    public JSONObject stopCharger(@RequestParam String chargerCode, @RequestParam String chargerConn, @RequestParam String orderId) {
        logger.info("stopCharger params ==> {chargerCode : " + chargerCode + ", chargerConn : " + chargerConn +", orderId : " + orderId + "}");
        JSONObject result = new JSONObject();        
        try {
            JSONObject params = new JSONObject();
            params.put("chargerCode", chargerCode);
            params.put("chargerConn", chargerConn);
            params.put("orderId", orderId);
            
            //发送电桩停机指令
            Future<Object> sendMsgFuture = chargingFlowService.sendStopChargerMsg(params);
            
            if(sendMsgFuture == null || !sendMsgFuture.get().equals("success")) {
                throw new Exception("停机失败");
            }
            
            //获取电桩返回结果
            Future<JSONObject> startRespFuture = executorService.submit(new Callable<JSONObject>() {
                @Override
                public JSONObject call() throws Exception {
                     //获取下发认证报文的返回结果
                    int counter = 1;
                    final String key = RedisUtil.PREFIX_STOP + orderId;
                    JSONObject obj = null;                   
                    while(counter < 50) {
                        if(redisUtil.hasKey(key)) {
                            obj = JSONObject.fromObject(redisUtil.get(key));
                            break;
                        }
                        
                        counter++;
                        Thread.sleep(100);
                    }
                    return obj;
                }
            });
            
            //超时时间设为5秒
            JSONObject startResp = startRespFuture.get(5, TimeUnit.SECONDS);
            
            result.put("state", startResp.getInt("result")); 
            result.put("errorDescr", startResp.getInt("result") == 0 ? "启动失败！" : null);
            
        } catch (TimeoutException e1) {
            e1.printStackTrace();
            result.put("state", 0); 
            result.put("errorDescr", "请求超时");
        } catch (Exception e2) {
            e2.printStackTrace();
            result.put("state", 0); 
            result.put("errorDescr", "停机失败");
        }

        return result;
    }
    
    /**
     * 充电进度数据
     * @param chargerCode       电桩编号
     * @param chargerConn       充电接口
     * @return
     */
    @RequestMapping(value="/progress", method=RequestMethod.POST)
    public JSONObject chargingProgress(@RequestParam String chargerCode, @RequestParam String chargerConn, @RequestParam String orderId) {
        logger.info("chargingProgress params ==> {chargerCode : " + chargerCode + ", chargerConn : " + chargerConn + "}");
        JSONObject result = new JSONObject();
        try {
            RCharger rcharger = (RCharger)redisUtil.get(RedisUtil.PREFIX_CHARGER + chargerCode);
            if(rcharger == null) {
                throw new Exception("非法请求");
            }
            
            List<RConnector> connList = rcharger.getConnectorList();
            if(connList != null && connList.size() > 0) {
                RConnector rconnector = connList.get(0);
                
                result.put("state", 1); 
                
                result.put("chargerCode" , chargerCode); 
                result.put("remainedTime", rconnector.getRemainedChargingTime()/60 + "时" + rconnector.getRemainedChargingTime()%60 + "分钟");
                result.put("chargeTime"  , rconnector.getChargingTime()/3600 + "时" + (rconnector.getChargingTime()/60)%60 + "分钟");
                result.put("electric"    , rconnector.getElectricity() + "kwh"); 
                result.put("soc"         , rconnector.getSocVal() + "%");
                
                if(!StringUtils.isEmpty(orderId) && redisUtil.hasKey(RedisUtil.PREFIX_STOP + orderId)) {
                    //JSONObject obj = JSONObject.fromObject(redisUtil.get(RedisUtil.PREFIX_STOP + orderId));
                    result.put("status", "true");
                } else {
                    result.put("status", "false");
                }
            } else {
                result.put("state", 0); 
                result.put("state", "非法请求"); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("state", 0); 
            result.put("errorDescr", e.getMessage());
        }
        return result;
    }
    
    /**
     * 充电记录查询接口
     * @param chargerCode       电桩编号 
     * @param chargerConn       充电接口
     * @param orderId           订单号
     * @return
     */
    @RequestMapping(value="/result", method=RequestMethod.POST)
    public JSONObject chargingOrder(@RequestParam String chargerCode, @RequestParam String chargerConn, @RequestParam String orderId) {
        logger.info("chargingOrder params ==> {chargerCode : " + chargerCode + ", chargerConn : " + chargerConn + ", orderId : " + orderId + "}");
        JSONObject result = new JSONObject();       
        try {
            //获取电桩返回结果
            Future<JSONObject> startRespFuture = executorService.submit(new Callable<JSONObject>() {
                @Override
                public JSONObject call() throws Exception {
                     //获取下发认证报文的返回结果
                    int counter = 1;
                    final String key = RedisUtil.PREFIX_ORDER + orderId;
                    JSONObject obj = null;                   
                    while(counter < 50) {
                        if(redisUtil.hasKey(key)) {
                            obj = JSONObject.fromObject(redisUtil.get(key));
                            break;
                        }
                        
                        counter++;
                        Thread.sleep(100);
                    }
                    return obj;
                }
            });
            
            //超时时间设为5秒
            JSONObject order = startRespFuture.get(5, TimeUnit.SECONDS);
            if(order == null) {
                result.put("state", 0); 
                result.put("errorDescr", "请求超时！");
            } else {
                result.put("chargerCode", chargerCode);
                result.put("startTime", Utility.formatDate(new Date(order.getLong("startTime")), "yyyy-MM-dd HH:mm:ss"));
                result.put("endTime", Utility.formatDate(new Date(order.getLong("endTime")), "yyyy-MM-dd HH:mm:ss"));
                result.put("chargeTime", order.getLong("chargeTime") / 3600 + "小时" + (order.getLong("chargeTime") / 60) % 60 + "分钟");
                result.put("electric", order.getDouble("electric") + "kwh");
                result.put("money", order.getDouble("money"));
            }
        } catch (TimeoutException e1) {
            e1.printStackTrace();
            result.put("state", 0); 
            result.put("errorDescr", "请求超时！");
        } catch (Exception e2) {
            e2.printStackTrace();
            result.put("state", 0); 
            result.put("errorDescr", "查询订单失败！");
        }
        
        orderService.findByOrderId(orderId);
        return result;
    }
}
