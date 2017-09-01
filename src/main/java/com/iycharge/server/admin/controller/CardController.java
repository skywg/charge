package com.iycharge.server.admin.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.Put;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.common.utils.VeDate;
import com.iycharge.server.domain.entity.account.Account;


import com.iycharge.server.api.util.IDCreator;

import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.account.CardRechargeForShow;
import com.iycharge.server.domain.entity.account.CardRechargeLog;
import com.iycharge.server.domain.entity.account.CardRechargeRecord;
import com.iycharge.server.domain.entity.account.CardStatus;
import com.iycharge.server.domain.entity.account.OrderForShow;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.RechargeRecord;
import com.iycharge.server.domain.entity.price.ParamTemplateQueryParam;
import com.iycharge.server.domain.entity.utils.Utils;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelUtil;
import com.iycharge.server.domain.service.AccountService;

import com.iycharge.server.domain.service.CardRechargeLogService;

import com.iycharge.server.domain.service.CardRechargeRecordService;
import com.iycharge.server.domain.service.CardService;
import com.iycharge.server.domain.service.ManagerLogService;
import com.iycharge.server.domain.service.OrderService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Created by godfrey on 16/10/8.
 */

@Controller
@RequestMapping("/admin/cards/")
public class CardController {
    private static final String CARD_INDEX_FILE="admin/card/index";
    private static final String CARD_CHECK_FILE="admin/card/check";

    private static final int TIME_INTERVAL_WEEK=7;
    private static final int TIME_INTERVAL_MONTH=30;
    private static final int TIME_INTERVAL_Three_MONTH=90;
    private static final int TIME_INTERVAL_YEAR=365;

    @Autowired
    private CardService cardService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CardRechargeRecordService cardRechargeRecordService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private CardRechargeLogService cardRechargeLogService;
    @Autowired
    private ManagerLogService managerLogService;
    @ModelAttribute("cardTypes")
	public List<DictData> types11() {
	     return EntityUtil.getDictDatas(CategoryConstant.CARD_TYPE);
	 }
    @ModelAttribute("certificateTypes")
	public List<DictData> types1() {
	     return EntityUtil.getDictDatas(CategoryConstant.CERTIFICATE_TYPE);
	}
    @ModelAttribute("accountTypes")
	public List<DictData> types3() {
	     return EntityUtil.getDictDatas(CategoryConstant.ACCOUNT_STATUS);
	}
	@ModelAttribute("cardstatus")
    public List<CardStatus> types() {
        return CardStatus.asList();
    }
	
	@ModelAttribute("intprises")
    public List<Account> intprises() {
        return accountService.findByAccountType("COMPANY");
    }
	@ModelAttribute("allAccounts")
    public List<Account> allAccounts() {
        return accountService.findAll();
    }
	
	@ModelAttribute("chargerIfs")
    public List<DictData> chargerIfs() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.CONNECTOR_TYPE);
        return lst != null?lst:(new ArrayList<DictData>());
    }
	@ModelAttribute("paidFroms")
    public List<DictData> paidFroms() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.PAID_FROM);
        return lst != null?lst:(new ArrayList<DictData>());
    }
    /**
               首页查询的卡记录
     */
    // TODO: 16/10/13 需要根据分页请求
    @RequestMapping("/")
    public String index(HttpServletRequest request,Model model,@ModelAttribute Card card, @PageableDefault(sort = "sendDate", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
    	HttpSession session = request.getSession();
   	 	session.removeAttribute("sessionCard");
    	PageWrapper<Card> page = new PageWrapper<>(cardService.findAll(pageable), "/admin/cards/");
        model.addAttribute("page", page);
        model.addAttribute("card", card);
        return CARD_INDEX_FILE;
    }

    /**
     * 根据卡号查询周数据(充值记录,充电记录)供储值卡详情页面显示
     * @param cardNo
     * @param model
     * @param recordPage
     * @param orderPage
     * @return
     */

    @RequestMapping("/checkDetailed/{id}")
    public String checkDetailed(@PathVariable("id")  String cardNo, Model model,
            @Qualifier("CardRechargeRecord") @PageableDefault(sort = "tradeNo", direction = Sort.Direction.ASC, size=15) Pageable recordPage,
            @Qualifier("Order") @PageableDefault(sort = "orderId", direction = Sort.Direction.ASC, size=15) Pageable orderPage) {
       Card card = cardService.findByCardNo(cardNo);
        if(null!=card) {
            model.addAttribute("card", cardService.findByCardNo(cardNo));
        }
        model.addAttribute("certificateTypes", EntityUtil.getDictDatas(CategoryConstant.CERTIFICATE_TYPE));
        model.addAttribute("cardTypes", EntityUtil.getDictDatas(CategoryConstant.CARD_TYPE));
        model.addAttribute("timeIntervalA", TIME_INTERVAL_WEEK);
        model.addAttribute("timeIntervalB", TIME_INTERVAL_WEEK);
//        model.addAttribute("rechargeRecordPage",new PageWrapper<>(cardRechargeRecordService.findAll(recordPage),"/admin/cards/rechargeRecordSearch"));
//        model.addAttribute("chargeRecordPage",new PageWrapper<>(orderService.findAll(orderPage),"/admin/cards/chargeRecordSearch"));
        PageWrapper<CardRechargeRecord> pageA = new PageWrapper<>(cardRechargeRecordService.findByCardNoWeekRecord(cardNo, recordPage),"/admin/cards");
        model.addAttribute("cardRechargeRecords", pageA);
        model.addAttribute("PageSize", pageA.getSize());
        model.addAttribute("PageCountA",pageA.getTotal());
        PageWrapper<Order> pageB = new PageWrapper<>(orderService.searchOrderByCardNoAndDays(cardNo, TIME_INTERVAL_WEEK, orderPage),"/admin/cards");
        model.addAttribute("chargeRecordPages",pageB);
        model.addAttribute("PageCountB", pageB.getTotal());
        model.addAttribute("currentPage",0);
        return CARD_CHECK_FILE;
    }

    /**
     * 根据会员昵称、持卡人,持卡人证件号查询
     * @param card
     * @param pageable
     * @param modle
     * @return
     */
    @RequestMapping("/search")
    public String search(HttpServletRequest request,@ModelAttribute Card card,@PageableDefault(sort = "sendDate", direction = Sort.Direction.DESC, size=15) Pageable pageable,Model modle){
    	HttpSession session = request.getSession();
        if("1".equals(card.getFlag())){
        	session.setAttribute("sessionCard", card);
        }else{
        	Card ch = (Card)session.getAttribute("sessionCard");
       	 if(ch!=null){
       		card = ch;
       	 }
        }    
        Page<Card> tmp =cardService.search(card,pageable);
        String key="";
        if(card.getAccount()!=null&&StringUtils.isNotEmpty(card.getAccount().getNickname())){
        	key+="account.nickname="+card.getAccount().getNickname()+"&";
        }
        if(StringUtils.isNotEmpty(card.getOwner())){
        	key+="owner="+card.getOwner()+"&";
        }
        if(StringUtils.isNotEmpty(card.getCertificateId())){
        	key+="certificateId="+card.getCertificateId()+"&";
        }
        if(StringUtils.isNotEmpty(card.getCardNo())){
        	key+="cardNo="+card.getCertificateId()+"&";
        }
        if(!StringUtils.isEmpty(key)){
    		key = key.substring(0,key.length()-1);
    	}
        if(null!=tmp) {
            PageWrapper<Card> page = new PageWrapper<>(tmp, "/admin/cards/search?"+key);
            modle.addAttribute("page", page);
            modle.addAttribute("card", card);
        }
        return CARD_INDEX_FILE;
    }


//    @RequestMapping("/recordSearch/{id}/{te}")
//    public  String searchRecord(@PathVariable("id") String id,@PathVariable("te") String te,  Model model, @Qualifier("CardRechargeRecord") @PageableDefault(sort = "tradeNo", direction = Sort.Direction.DESC) Pageable recordPage, @Qualifier("Order") @PageableDefault(sort = "orderId", direction = Sort.Direction.DESC) Pageable orderPage){
//        Card card = cardService.findByCardNo(id);
//        if(null!=card) {
//            model.addAttribute("card", card);
//        }
//        model.addAttribute("certificateTypes", CertificateType.asList());
//        model.addAttribute("cardTypes", CardType.asList());
//        model.addAttribute("rechargeRecordPage",new PageWrapper<>(cardRechargeRecordService.findByCardNoWeekRecord(id, recordPage),"/admin/cards"));
//        model.addAttribute("chargeRecordPage",new PageWrapper<>(orderService.searchOrderByCardNoAndDays(id, TIME_INTERVAL_WEEK, orderPage),"/admin/cards"));
//
//        return CARD_CHECK_FILE;
//    }
    /**
     * 查询卡充值记录
     * @param cardNo
     * @param model
     * @param recordPage
     * @return
     */
    @RequestMapping(value="/rechargeRecordSearch")
    @ResponseBody
    public Map<String,Object>  rechargeRecordSearch(@RequestParam("cardNo") String cardNo, 
                                              @RequestParam("timeInterval") Integer timeInterval, 
                                              @PageableDefault(sort = "tradeTime", direction = Sort.Direction.DESC, size=15) Pageable recordPage){
    	/*ModelAndView modelAndView = new ModelAndView("/admin/card/cardRechargeRecord");*/
        Map<String,Object> map = new HashMap<>();
    	Page page= null;
        List<CardRechargeRecord> rechargers = new ArrayList<>();
       switch(timeInterval) {
           case TIME_INTERVAL_WEEK:
               page = cardRechargeRecordService.findByCardNoWeekRecord(cardNo, recordPage);
               rechargers = page.getContent();
               break;
           case TIME_INTERVAL_MONTH:
               page = cardRechargeRecordService.findByCardNoMonthRecord(cardNo, recordPage);
               rechargers = page.getContent();
               break;
           case TIME_INTERVAL_Three_MONTH:
               page = cardRechargeRecordService.findByCardNoThreeMonthRecord(cardNo, recordPage);
               rechargers = page.getContent();
               break;
           case TIME_INTERVAL_YEAR:
               page = cardRechargeRecordService.findByCardNoYearRecord(cardNo, recordPage);
               rechargers = page.getContent();
               break;
           default:
               break;
       }
       map.put("rechargers", getShowCardRecharges(rechargers));
       map.put("page", page.getNumber());
       map.put("size", page.getTotalPages());
       map.put("totalPage",page.getTotalPages());
       map.put("PageCount", page.getTotalElements());
       map.put("timeIntervalA",timeInterval);
       return map;
    }
    private List<CardRechargeForShow> getShowCardRecharges(List<CardRechargeRecord> rechargers){
    	List<CardRechargeForShow> crlfs = new ArrayList<>();
    	if(rechargers!=null){
    		for(CardRechargeRecord crr : rechargers){
    			CardRechargeForShow crs = new CardRechargeForShow();
    			crs.setCardNo(crr.getCard().getCardNo());
    			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			String tsStr = sdf.format(crr.getTradeTime());  
    			crs.setChargerTime(tsStr);
    			crs.setMoney(crr.getMoney().toString());
    			crs.setType(crr.getRechargeType());
    			crs.setTradeNo(crr.getTradeNo());
    			crlfs.add(crs);
        	}
        	Collections.sort(crlfs, new Comparator<CardRechargeForShow>(){  
    			@Override
    			public int compare(CardRechargeForShow o1, CardRechargeForShow o2) {
    				// TODO Auto-generated method stub
    				if(Long.parseLong(o1.getTradeNo()) > Long.parseLong(o2.getTradeNo())){  
                        return -1;  
                    }  
                    if(Long.parseLong(o1.getTradeNo()) == Long.parseLong(o2.getTradeNo())){  
                        return 0;  
                    }  
                    return 1;
    			}  
            }); 
    	}
    	return crlfs;
    }

    /**
     * 查询充电记录
     */
    @RequestMapping(value="/chargeRecordSearch")
    @ResponseBody
    public Map<String,Object> chargeRecordSearch(@RequestParam("cardNo") String cardNo,
                                           @RequestParam("timeInterval") Integer timeInterval,
                                           @PageableDefault(sort = "startAt", direction = Sort.Direction.DESC, size=15) Pageable orderPage){
        Map<String,Object> map = new HashMap<>();
    	List<Order> orderList = new ArrayList<>();
        if(timeInterval == TIME_INTERVAL_WEEK || timeInterval==TIME_INTERVAL_MONTH || timeInterval ==TIME_INTERVAL_Three_MONTH || timeInterval==TIME_INTERVAL_YEAR) {
        	PageWrapper<Order> page = new PageWrapper<>(orderService.searchOrderByCardNoAndDays(cardNo, timeInterval, orderPage), "/admin/cards/chargeRecordSearch");
        	orderList = page.getContent();
            map.put("rechargers", getShowOrders(orderList));
            map.put("page", page.getNumber());
            map.put("totalPage",page.getTotalPages());
            map.put("PageCount", page.getTotal());
            map.put("timeIntervalB",timeInterval);
        	
        }       
        return map;
    }
    private List<OrderForShow> getShowOrders(List<Order> orderList){
    	List<OrderForShow> orders = new ArrayList<>();
    	if(orderList!=null){
    		for(Order order : orderList){
    			String cardNoOrPhoneNum =null;
    			if("CARD".equals(order.getPaidFrom())){
    				cardNoOrPhoneNum = order.getCard().getCardNo();
    			}else{
    				if(order.getAccount()!=null){
    					cardNoOrPhoneNum = order.getAccount().getPhone();
    				}
    			}
    			OrderForShow ofs = new OrderForShow(order.getOrderId(),
    					order.getStartAt().toString(), 
    					order.getStationName(),
    					order.getChargerName(),
    					order.getIfName(), 
    					order.formatChargingTime(), 
    					order.getDegree().toString(), 
    					order.getMoney().toString(),
    					EntityUtil.getDictTile(CategoryConstant.PAID_FROM, order.getPaidFrom()),
    					order.getStatus().getTitle(),cardNoOrPhoneNum);
    			orders.add(ofs);
    		}
    		
    		Collections.sort(orders, new Comparator<OrderForShow>(){  
    			@Override
    			public int compare(OrderForShow o1, OrderForShow o2) {
    				// TODO Auto-generated method stub
    				if(Long.parseLong(o1.getOrderId()) > Long.parseLong(o2.getOrderId())){  
                        return -1;  
                    }  
                    if(Long.parseLong(o1.getOrderId()) == Long.parseLong(o2.getOrderId())){  
                        return 0;  
                    }  
                    return 1;
    			}  
            }); 
    	}
    	return orders;
    }
    /**
	 * 跳转增加页面
	 */
	@RequestMapping(value="/add")
	public String add(Model model) {
		String sendDate = VeDate.getStringDateShort();
		Calendar calendar = Calendar.getInstance();
		Date expiredDate = new Date(System.currentTimeMillis());
        calendar.setTime(expiredDate);
        calendar.add(Calendar.YEAR, 10);
         expiredDate = calendar.getTime();
		//Date date =new Date();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-hh");
		String time = sdf.format(expiredDate);
       
		//String time2 =sdf.format(date1);
		Card card = new Card();
	    model.addAttribute("card", card);
	    model.addAttribute("sendDate", sendDate);
	    model.addAttribute("expiredDate", time);
	    
		return "admin/card/add";
	}
    /**
     * 新增保存
     */
   @Transactional
   @RequestMapping(value="/addcard", method=RequestMethod.POST) 
   public String addcard(Model model,@ModelAttribute Card card,@RequestParam("cf")String cf,BindingResult result, RedirectAttributes redirectAttributes,HttpServletRequest request){
	    Card card2 =cardService.findByCardNo(card.getCardNo());
	    Long accountId = card.getAccount().getId();
	    Account account = accountService.findById(accountId);
	    if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("failed", "添加充电卡时发生错误，请检查后重新尝试。");
            return "admin/card/add";
        }
	    if(!"COMPANY".equals(account.getAccountType())){
	    	List<Card> cards = account.getCards();
	    	int cardNumber = 0;
	    	for(int i = 0 ; i < cards.size() ; i++){
	    		if(cards.get(i).getStatus().name().equals("NORMAL")){
	    			cardNumber++;
	    		}
	    	}
	    	if(cardNumber>=1){
	    		String sendDate = VeDate.getStringDateShort();
				Calendar calendar = Calendar.getInstance();
				Date expiredDate = new Date(System.currentTimeMillis());
		        calendar.setTime(expiredDate);
		        calendar.add(Calendar.YEAR, 10);
		         expiredDate = calendar.getTime();
				//Date date =new Date();
				SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-hh");
				String time = sdf.format(expiredDate);
				model.addAttribute("sendDate", sendDate);
				model.addAttribute("expiredDate", time);
		    	model.addAttribute("card", card);
	       	    model.addAttribute("failed", "个人会员只允许持有一张充电卡");
	            return "admin/card/add";
	    	}
	    }
	    if (card2!=null) {
	    	String sendDate = VeDate.getStringDateShort();
			Calendar calendar = Calendar.getInstance();
			Date expiredDate = new Date(System.currentTimeMillis());
	        calendar.setTime(expiredDate);
	        calendar.add(Calendar.YEAR, 10);
	         expiredDate = calendar.getTime();
			//Date date =new Date();
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-hh");
			String time = sdf.format(expiredDate);
			model.addAttribute("sendDate", sendDate);
			model.addAttribute("expiredDate", time);
	    	model.addAttribute("card", card);
       	    model.addAttribute("failed", "添加充电卡时物理卡号重复！");
            return "admin/card/add";
		}
	    card.setMoney(BigDecimal.valueOf(0));
	    card.setStatus(CardStatus.NORMAL);
		card.setCertificateType(cf);
		card.setUpdatedAt(new Date());
	    cardService.save(card);
	    ManagerLog log = Utils.setLog(request, "CARD", "ADD", "新增充电卡:"+card.getCardNo());
        log.setStatus(true);
        managerLogService.save(log);
   		return "redirect:/admin/cards/search";
   }
   /**
    * 跳转编辑页面
    */
   @RequestMapping(value="/edit/{id}")
	public String edit(Model model,  @PathVariable("id")  String cardNo) {
	    model.addAttribute("card",cardService.findByCardNo(cardNo));
	   // model.addAttribute("account", new Account());
		return "admin/card/edit";
	}
   /**
    * 编辑保存
    */
   @Transactional
   @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
   public String update(@PathVariable("id")  String cardNo,@Valid Card form,@RequestParam("cf")String cf,HttpServletRequest request) {
	  Card card = cardService.findByCardNo(cardNo);
	  card.setCertificateType(cf);
   	  setBean(form, card);
   	  ManagerLog log = Utils.setLog(request, "CARD", "EDIT", "编辑充电卡:"+card.getCardNo());
   	  cardService.save(card);
   	  log.setStatus(true);
   	  managerLogService.save(log);
   	return "redirect:/admin/cards/search";        	        
   }
   private void setBean(Card from,Card card){
	    card.setPayCardNo(from.getPayCardNo());
		card.setCardType(from.getCardType());
		card.setOwner(from.getOwner());
		card.setCertificateId(from.getCertificateId());
		card.setPhoneNo(from.getPhoneNo());
		card.setExpiredDate(from.getExpiredDate());
		card.setAccount(from.getAccount());
		card.setRemark(from.getRemark());
		card.setUpdatedAt(new Date());
		
	}
    /**
     * 挂失
     */
   @RequestMapping("/loss")
   public  String cardloss(@RequestParam("cardNo")String cardNo,@RequestParam("remark") String remark,HttpServletRequest request) {
	   Card card =cardService.findByCardNo(cardNo);
	   card.setStatus(CardStatus.REPORT_OF_LOSS);
	   card.setRemark(remark);
	   ManagerLog log = Utils.setLog(request, "CARD", "LOSSCARD", "挂失充电卡:"+card.getCardNo());
	   cardService.save(card);
	   log.setStatus(true);
	   managerLogService.save(log);
	   return "redirect:/admin/cards/search";
   }
   /**
    * 注销
    */
  @RequestMapping("/cancelled")
  public  String cancelled(@RequestParam("cardNo")String cardNo,@RequestParam("remark") String remark,HttpServletRequest request) {
	   Card card =cardService.findByCardNo(cardNo);
	   card.setStatus(CardStatus.CANCELLED_CARD);
	   card.setRemark(remark);
	   ManagerLog log = Utils.setLog(request, "CARD", "CANCELCARD", "注销充电卡:"+card.getCardNo());
	   cardService.save(card);
	   log.setStatus(true);
	   managerLogService.save(log);
	   return "redirect:/admin/cards/search";
  }
  /**
   * 启用
   */
  @RequestMapping("/start")
  public  String start(@RequestParam("cardNo")String cardNo,@RequestParam("remark") String remark,HttpServletRequest request) {
	   Card card =cardService.findByCardNo(cardNo);
	   card.setStatus(CardStatus.NORMAL);
	   card.setRemark(remark);
	   ManagerLog log = Utils.setLog(request, "CARD", "EDIT", "启用充电卡:"+card.getCardNo());
	   cardService.save(card);
	   log.setStatus(true);
	   managerLogService.save(log);
	   return "redirect:/admin/cards/search";
  }
    /**
     * 冲电卡充值
     * @param model
     * @param cardNo
     * @return
     */
    @RequestMapping("/toCharging")
    public String toCharging(Model model,String cardNo){
    	Card card = cardService.findByCardNo(cardNo);
    	model.addAttribute("card", card);
    	return "admin/card/recharge";
    	
    }
    @Transactional
    @RequestMapping("/charging")
    public String charging(Model model,
    		String chargerNumber,
    		String cardNo,String remark,
    		HttpSession session,HttpServletRequest request){
    	CardRechargeLog crl = new CardRechargeLog();
    	CardRechargeRecord crr = new CardRechargeRecord();
    	Card card = cardService.findByCardNo(cardNo);
    	Account account = card.getAccount();
    	String params = null;
    	ManagerLog log = Utils.setLog(request, "CARD", "REMIT", params);
    	try{
    		if(card.getMoney() == null){
				card.setMoney(new BigDecimal(0));
			}
    		//充值
        	BigDecimal account_money_afterCharging = account.getMoney().subtract(new BigDecimal(chargerNumber));
        	BigDecimal card_money_afterCharging = card.getMoney().add(new BigDecimal(chargerNumber));
        	account.setMoney(account_money_afterCharging);
        	card.setMoney(card_money_afterCharging);
        	crl.setStatus(true);
        	//保存数据库
        	account = accountService.save(account);
        	card = cardService.save(card);
    	}catch(Exception e){
    		crl.setStatus(false);
    	}
    	//记录操作日志
    	crl.setCardNo(card.getCardNo());
    	crl.setLogTime(new Date());
    	crl.setLogType("单卡划账");
    	crl.setManager((Manager)session.getAttribute("user"));
    	crl.setMoney(new BigDecimal(chargerNumber));
    	crl.setRemark(remark);
    	crl.setAccount(account);
    	//记录充值日志
    	crr.setCard(card);
    	crr.setMoney(new BigDecimal(chargerNumber));
    	crr.setRechargeType("单卡划账");
    	crr.setTradeNo(IDCreator.generator(IDCreator.BUS_CARD_RECHARGE));
    	crr.setTradeTime(new Date());
    	cardRechargeLogService.save(crl);
    	cardRechargeRecordService.save(crr);
    	params = "充电卡单卡划账-会员号:"+account.getRealName()+",卡号:"+card.getCardNo()+",划账金额:"+chargerNumber+",交易号:"+IDCreator.generator(IDCreator.BUS_CARD_RECHARGE);
    	log.setStatus(true);
    	log.setParams(params);
    	managerLogService.save(log);
    	
    	return "redirect:/admin/cards/search"; 
    }
    /**
     * 批量转账
     */
    @RequestMapping("/toBatchCharging")
    public String toBatchCharging(Model model){
    	List<Account> accounts = accountService.findByAccountType("COMPANY");
    	model.addAttribute("accounts", accounts);
    	if(accounts == null || accounts.size()==0){
    		model.addAttribute("accounts", new ArrayList<Account>());
    		model.addAttribute("account", new Account());
    	}
    	int valid_number = 0;
    	int invalid_number = 0;
    	if(accounts!=null && accounts.size()>0){
    		Account account = accounts.get(0);
    		model.addAttribute("account", account);
    		List<Card> cards = account.getCards();
    		List<Card> calid_vards = new ArrayList<>();
    		for(Card card : cards){
    			if(card.getStatus().name().equals("NORMAL")){
    				valid_number ++;
    				calid_vards.add(card);
    			}else{
    				invalid_number++;
    			}
    		}
    		model.addAttribute("valid_number", valid_number);
    		model.addAttribute("invalid_number",invalid_number);
    		model.addAttribute("validCards",calid_vards);
    	}
    	return "admin/card/debit";
    	
    }
    
    /**
     * ajax同步数据
     */
    @RequestMapping("/toBatchCharging_ajax")
    public void ajax(Model model,Long id,HttpServletResponse response){
    	Account account = accountService.findById(id);
    	JSONObject jsonObj = new JSONObject();
    	int valid_number = 0;
    	int invalid_number = 0;
    	List<Card> cards = account.getCards();
    	List<String> cardNos = new ArrayList<>();
    	for(Card card : cards){
    		if(card.getStatus().name().equals("NORMAL")){
    			valid_number ++;
    			cardNos.add(card.getCardNo()+"("+card.getOwner()+")");
    		}else{
    			invalid_number++;
    		}
    	}
    	JSONArray cardJsons = JSONArray.fromObject(cardNos);
    	jsonObj.put("valid_number", String.valueOf(valid_number));
    	jsonObj.put("invalid_number", String.valueOf(invalid_number));
    	jsonObj.put("phone", account.getPhone());
    	jsonObj.put("status", account.getStatus().getTitle());
    	jsonObj.put("money",account.getMoney().toString());
    	jsonObj.put("cardNos", cardJsons);
    	PrintWriter out = null;
    	try {
			out = response.getWriter();
			out.write(jsonObj.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
    }
    
    
    @Transactional
    @RequestMapping("/batchCharging")
    public String batchCharging(Model model,
    		String chargerNumber,
    		Long id,String remark,
    		HttpSession session,String selectedCards,HttpServletRequest request){
    	Account account = accountService.findById(id);
    	Manager manager = (Manager)session.getAttribute("user");
    	String params = null;
    	ManagerLog log = Utils.setLog(request, "CARD", "REMIT", params);
    	List<Card> allCards = account.getCards();
    	List<Card> cards = new ArrayList<>();
    	if(selectedCards!=null&&!selectedCards.trim().equals("")){
    		String str[] = selectedCards.split(",");
    		for(int i = 0 ; i < allCards.size() ; i++){
    			Card tmp = allCards.get(i);
    			for(String cardNo : str){
    				if(cardNo.equals(tmp.getCardNo())){
    					cards.add(tmp);
    					break;
    				}
    			}
    		}
    	}else{
    		cards = account.getCards();
    	}
    	List<CardRechargeLog> crls = new ArrayList<>();
    	List<CardRechargeRecord> crrs = new ArrayList<>();
    	int cardsNumber = 0;
    	List<String> cardNos = new ArrayList<>();
    	for(Card card : cards){
    		if(card.getStatus().name().equals("NORMAL")){
    			if(card.getMoney() == null){
    				card.setMoney(new BigDecimal(0));
    			}
    			cardNos.add(card.getCardNo());
            	BigDecimal card_money_afterCharging = card.getMoney().add(new BigDecimal(chargerNumber));
            	card.setMoney(card_money_afterCharging);
            	//记录操作日志
            	CardRechargeLog crl = new CardRechargeLog();
            	crl.setCardNo(card.getCardNo());
            	crl.setLogTime(new Date());
            	crl.setLogType("批量划账");
            	crl.setManager(manager);
            	crl.setMoney(new BigDecimal(chargerNumber));
            	crl.setRemark(remark);
            	crl.setAccount(account);
            	crl.setStatus(true);
            	crls.add(crl);
            	CardRechargeRecord crr = new CardRechargeRecord();
            	//记录充值日志
            	crr.setCard(card);
            	crr.setMoney(new BigDecimal(chargerNumber));
            	crr.setRechargeType("批量划账");
            	crr.setTradeNo(IDCreator.generator(IDCreator.BUS_CARD_RECHARGE));
            	crr.setTradeTime(new Date());
            	crrs.add(crr);
            	
            	cardsNumber++;
    		}
    	}
    	Double all = Double.parseDouble(chargerNumber)*cardsNumber;
    	BigDecimal surplus = account.getMoney().subtract(new BigDecimal(String.valueOf(all)));
    	account.setMoney(surplus);
    	accountService.save(account);
    	cardService.saveAll(cards);
    	cardRechargeLogService.saveAll(crls);
    	cardRechargeRecordService.saveAll(crrs);
    	params = "充电卡批量划账-会员号:"+account.getRealName()+",充值卡数量:"+cardNos.size()+",每张卡所充金额:"+chargerNumber+",交易号:"+IDCreator.generator(IDCreator.BUS_CARD_RECHARGE);
    	log.setStatus(true);
    	log.setParams(params);
    	managerLogService.save(log);
    	return "redirect:/admin/cards/search"; 
    }
    //excel导出
    @RequestMapping(value="/excle_output")
    public ResponseEntity<InputStreamResource> output(String nickname,String owner,
    		String certificateId,HttpServletRequest request){
 	   Map<String,String> conditionMap = new HashMap<>();
 	   conditionMap.put("realName", nickname);
 	   conditionMap.put("owner", owner);
 	   conditionMap.put("certificateId", certificateId);
 	   Card card = new Card();
 	   Account account = new Account();
 	   account.setNickname(nickname);
 	   card.setAccount(account);
 	   card.setOwner(owner);
 	   card.setCertificateId(certificateId);
 	   List<Card> cards = cardService.findByCondition(conditionMap, card);
 	   ManagerLog log = Utils.setLog(request, "CARD", "EXPORT", "充电卡列表导出");
        ExcelUtil eu = ExcelUtil.getInstance();
        String files = System.getProperty("user.dir");
        File fileChild = new File(files);
        File parentFile = fileChild.getParentFile();
        String path = parentFile.getPath()+File.separator+"excel"+File.separator+"cards"+Utils.dateChangeToNumber(new Date())+".xls";
        File file = new File(path);
        if(!file.exists()){
     	   if(!file.getParentFile().exists()){
     		   file.getParentFile().mkdirs();
     	   }
     	   try {
 			file.createNewFile();
 		   } catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 				log.setStatus(false);
 				managerLogService.save(log);
 		   }
        }
        Map<String, String> map = new HashMap<>();
        map.put("title", "充电卡管理列表");
        map.put("total", cards.size()+" 条");
        map.put("date", Utils.getDate());
        OutputStream os=null;
        try {
 			os = new FileOutputStream(file);
 		} catch (FileNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
			log.setStatus(false);
			managerLogService.save(log);
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
			log.setStatus(false);
			managerLogService.save(log);
 		}
        eu.exportObj2ExcelByTemplate(map, "/card-info-template.xls", os, cards, Card.class, true);
        FileSystemResource outfile = new FileSystemResource(path);  
        HttpHeaders headers = new HttpHeaders();  
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", outfile.getFilename()));  
        headers.add("Pragma", "no-cache");  
        headers.add("Expires", "0");  
		log.setStatus(true);
		managerLogService.save(log);
        try {
 		return ResponseEntity  
 		           .ok()  
 		           .headers(headers)  
 		           .contentLength(outfile.contentLength())  
 		           .contentType(MediaType.parseMediaType("application/vnd-ms-excel"))  
 		           .body(new InputStreamResource(outfile.getInputStream()));
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
			log.setStatus(false);
			managerLogService.save(log);
 		} 
        return null;
    }
    

}
