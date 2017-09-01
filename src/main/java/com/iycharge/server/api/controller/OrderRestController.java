package com.iycharge.server.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.api.request.OrderRequest;
import com.iycharge.server.api.util.IDCreator;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.OrderStatus;
import com.iycharge.server.domain.entity.order.RechargeRecord;
import com.iycharge.server.domain.entity.utils.AlipayPaymentUtil;
import com.iycharge.server.domain.entity.utils.AlipayWeiXinPaymentUtil;
import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.domain.service.ChargerService;
import com.iycharge.server.domain.service.OrderService;
import com.iycharge.server.domain.service.RechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/orders/")
public class OrderRestController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ChargerService chargerService;
    @Autowired
    private RechargeRecordService rechargeRecordService;
    
    /**
     * 根据订单id查询订单 
     * @param orderId       订单Id
     * @param principal     查询订单的用户
     * @return
     */
    @Cacheable("order")
    @RequestMapping("{orderId}")
    @JsonView(BaseEntity.Summary.class)
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId, Principal principal) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!order.getAccount().getId().equals(Long.valueOf(principal.getName())))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @RequestMapping(value = "createOrder", method = RequestMethod.POST)
    @JsonView(BaseEntity.Summary.class)
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request, Principal principal) {
        if (request.getAccountId().equals(Long.valueOf(principal.getName()))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Account account = accountService.findById(Long.valueOf(principal.getName()));
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Order order = new Order();
        order.setOrderId(IDCreator.generator(IDCreator.BUS_CHARGING));
        order.setAccount(account);
        Charger charger = chargerService.findById(request.getChargerId());
        if (charger == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        order.setCharger(charger);
        if (request.getPaidFrom() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        order.setPaidFrom(request.getPaidFrom());
        order.setStatus(OrderStatus.UNPAID);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @RequestMapping(value = "createRechargeRecord")
    @JsonView(BaseEntity.Summary.class)
    public ResponseEntity<?> createRechargeRecord(HttpServletRequest request,
							 HttpServletResponse response,
							 @RequestParam("payFrom") String paidFrom, 
	                         @RequestParam("money") BigDecimal money, 
	                         Principal principal) {
        Account account = accountService.findById(Long.valueOf(principal.getName()));
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        //生成订单
        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setAccount(account);
        rechargeRecord.setTradeNo(IDCreator.generator(IDCreator.BUS_RECHARGE));
        rechargeRecord.setPaidFrom(paidFrom);
        rechargeRecord.setMoney(money);
        rechargeRecord.setStatus(OrderStatus.UNPAID);
        rechargeRecordService.save(rechargeRecord);
        
        if("WEBCHART".equals(paidFrom)){
    		return AlipayWeiXinPaymentUtil.getRechargePayInfo(request,response,rechargeRecord);
        }else{
        	return AlipayPaymentUtil.getRechargePayInfo(rechargeRecord);
        }
        
    }
    
    
 // 微信异步通知
 	@RequestMapping(value = "/admin/notify")
 	@ResponseBody
 	public ResponseEntity<?> notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
 		 return new ResponseEntity<>("success", HttpStatus.OK);
 	}
}
