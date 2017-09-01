package com.iycharge.server.admin.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iycharge.server.domain.entity.admin.LogType;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.dict.DictData;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
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
import com.iycharge.server.api.util.IDCreator;
import com.iycharge.server.domain.common.utils.ReflectField;
import com.iycharge.server.domain.common.utils.VeDate;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.AccountRechargeLog;
import com.iycharge.server.domain.entity.account.AccountRechargeMethod;
import com.iycharge.server.domain.entity.account.AccountStation;
import com.iycharge.server.domain.entity.account.AccountStatus;
import com.iycharge.server.domain.entity.account.Car;
import com.iycharge.server.domain.entity.account.CarAuditLog;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.OrderStatus;
import com.iycharge.server.domain.entity.order.RechargeRecord;
import com.iycharge.server.domain.entity.price.ParamTemplateQueryParam;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.utils.FileDownload;
import com.iycharge.server.domain.entity.utils.Utils;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelUtil;
import com.iycharge.server.domain.service.AccountRechargeLogService;
import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.domain.service.CardRechargeRecordService;
import com.iycharge.server.domain.service.CardService;
import com.iycharge.server.domain.service.ManagerLogService;
import com.iycharge.server.domain.service.OrderService;
import com.iycharge.server.domain.service.RechargeRecordService;
import com.iycharge.server.domain.service.StationService;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/admin/accounts/")
public class AccountController {
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CardService cardService;
    
    @Autowired
    private CardRechargeRecordService cardRechargeRecordService;
    
    @Autowired
    private Environment env;
    
    @Autowired
    private RechargeRecordService rechargeRecordService;
    @Autowired
    private AccountRechargeLogService accountRechargeLogService;
    @Autowired
    private ManagerLogService managerLogService;
    @Autowired
    private StationService stationService;
    @ModelAttribute("genders")
    public List<DictData> types() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.GENDER);
        return lst != null?lst:(new ArrayList<DictData>());
    }
    
    @ModelAttribute("AccountStatusS")
    public List<DictData> Atypes() {
    	List<DictData> lst = EntityUtil.getDictDatas(CategoryConstant.ACCOUNT_STATUS);
        return lst != null?lst:(new ArrayList<DictData>());
    }
    @ModelAttribute("accountTypes")
    public List<DictData> accountTypes(){
    	return EntityUtil.getDictDatas(CategoryConstant.ACCOUNT_TYPE);
    }
    @ModelAttribute("paidFroms")
    public List<DictData> paidFroms(){
    	return EntityUtil.getDictDatas(CategoryConstant.PAID_FROM);
    }
    
    @RequestMapping("/")
    public String index(HttpServletRequest request,Account account,@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable, Model model) {
    	HttpSession session = request.getSession();
   	 	session.removeAttribute("sessionAccount");
   	 	PageWrapper<Account> page = new PageWrapper<>(accountService.findAll(pageable), "/admin/accounts/");
   	 	
   	 	List<Account> list =page.getContent();
        List<DictData> dic = EntityUtil.getDictDatas(CategoryConstant.ACCOUNT_TYPE);
        if(list!=null&&list.size()>0){
        	for(Account ac:list){
        		if("正常".equals(ac.getStatus().getTitle())){
        			ac.setTransientStatus("禁用");
        		}else if("违规".equals(ac.getStatus().getTitle())){
        			ac.setTransientStatus("启用");
        		}
        		ac.setTransientDate(VeDate.getStringDate(ac.getCreatedAt()));
        		for(DictData dictData :dic){
        			if(dictData.getDictValue().equals(ac.getAccountType())){
        				ac.setAccountType(dictData.getDescr());
        				break;
        			}
        		}
        	}
        }
        
        model.addAttribute("accountValue", account);
        model.addAttribute("page", page);
        return "admin/account/index";
    }
    @RequestMapping("/add")
    public String add(Model model) {
    	List<Map<String,String>> allStationInfos = getStationInfo();
        model.addAttribute("account", new Account());
        model.addAttribute("allStationInfos", allStationInfos);
        return "admin/account/add";
    }
    @RequestMapping("managers")
    public String managers() {
        return "admin/account/managers";
    }
    
    @RequestMapping("/del/{accountId}")
    public String del(@PathVariable("accountId") Long chargerId, Model model,HttpServletRequest request) {
    	Account entity = accountService.findById(chargerId);
    	entity.setDelstatus("del");
    	ManagerLog log = Utils.setLog(request, "ACCOUNT", "DELETE", "用户被删除-名称:"+entity.getRealName());
    	if(null!=entity){
    		log.setStatus(true);
    		accountService.save(entity);
    		managerLogService.save(log);
    	}else{
    		
    	}
    	return "redirect:/admin/accounts/search";
    }
    
    @RequestMapping("/changeStatus/{id}/{transientStatus}")
    public String transientStatus(HttpServletRequest request ,@PathVariable("id") Long id ,@PathVariable("transientStatus") String  transientStatus, Model model) {
    	Account entity = accountService.findById(id);
    	HttpSession session = request.getSession();
    	Manager manager = (Manager)session.getAttribute("user");
    	String remark = (String)request.getParameter("remark");
    	AccountRechargeLog  accountRechargeLog = new  AccountRechargeLog();
        accountRechargeLog.setAccount(entity);
        accountRechargeLog.setManager(manager);
        accountRechargeLog.setLogTime(new Date());
        accountRechargeLog.setRemark(remark);
        accountRechargeLog.setStatus(true);
        accountRechargeLogService.save(accountRechargeLog);
        ManagerLog log = null;
    	if(null!=entity){
    		if("启用".equals(transientStatus)){
    			log = Utils.setLog(request, "ACCOUNT", "ENABLE", "修改会员状态-启用:"+entity.getRealName());
    			entity.setStatus(AccountStatus.NORMAL);
    		}else if("禁用".equals(transientStatus)){
    			log = Utils.setLog(request, "ACCOUNT", "DISABLE", "修改会员状态-禁用:"+entity.getRealName());
    			entity.setStatus(AccountStatus.FORBIDDEN);
    		}
    		 accountService.save(entity);
    		 log.setStatus(true);
    		 managerLogService.save(log);
    	}
    	return "redirect:/admin/accounts/search";
    }
    @RequestMapping("/debit/{accountId}")
    public String debit(@PathVariable("accountId") Long accountId, Model model) {
    	Account account = accountService.findById(accountId);
    	account.setTransientDate(VeDate.dateToStr(new Date()));
        model.addAttribute("account", account);
        return "admin/account/debit";
    }
    @RequestMapping("/edit/{accountId}")
    public String editform(@PathVariable("accountId") Long accountId, Model model) {
    	List<Map<String,String>> allStationInfos = getStationInfo();
    	Account account = accountService.findById(accountId);
    	List<Station> hasStations = account.getStations();
    	String stationNames = "";
        String stationIds  = "";
        for(int i = 0 ; i < hasStations.size() ; i++){
        	if(i==(hasStations.size()-1)){
        		stationNames+=hasStations.get(i).getName();
        		stationIds+=hasStations.get(i).getId().toString();
        		break;
        	}
        	stationNames+=hasStations.get(i).getName()+",";
        	stationIds+=hasStations.get(i).getId().toString()+",";
        }
        model.addAttribute("stationNames",stationNames);
        model.addAttribute("stationIds",stationIds);
		model.addAttribute("allStationInfos", allStationInfos);
    	model.addAttribute("editflag", "edit");
    	model.addAttribute("account",account);
        return "admin/account/edit";
    }
    @RequestMapping("/{accountId}")
    public String edit(@PathVariable("accountId") Long accountId, Model model) {
    	List<Map<String,String>> allStationInfos = getStationInfo();
    	Account account = accountService.findById(accountId);
    	List<Station> hasStations = account.getStations();
    	String stationNames = "";
        for(int i = 0 ; i < hasStations.size() ; i++){
        	if(i==(hasStations.size()-1)){
        		stationNames+=hasStations.get(i).getName();
        		break;
        	}
        	stationNames+=hasStations.get(i).getName()+",";
        }
        model.addAttribute("stationNames", stationNames);
        model.addAttribute("account", accountService.findById(accountId));
        return "admin/account/check";
    }
    @RequestMapping(value = "/accoutAdd", method = RequestMethod.POST)
    public String save(Model model,@ModelAttribute Account account,BindingResult result, RedirectAttributes redirectAttributes,HttpServletRequest request,String stationIds) {
    	String[] sids = stationIds!=null?stationIds.split(","):new String[]{};
        List<Station> selectStations = new ArrayList<>();
    	for(String sid : sids){
    		Station station = stationService.findById(Long.parseLong(sid));
    			selectStations.add(station);
    	}
        account.setStations(selectStations);
    	if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("failed", "添加新用户时发生错误，请检查后重新尝试。");
            return "redirect:/admin/accounts/add";
        }
        Account check =  accountService.findByPhone(account.getPhone());
        if(check!=null){
        	List<Map<String,String>> allStationInfos = getStationInfo();
        	String[] stationNames = new String[selectStations.size()];
        	for(int i = 0 ; i < selectStations.size() ; i++){
        		stationNames[i] = selectStations.get(i).getName();
        	}
        	 model.addAttribute("allStationInfos", allStationInfos);
        	 model.addAttribute("account", account);
        	 model.addAttribute("stationNames", stationNames);
        	 model.addAttribute("stationIds", stationIds);
        	 model.addAttribute("failed", "该手机号码已存在，请检查后重新尝试。");
        	 return "admin/account/add";
        }
        account.setAccountType("COMPANY");
        account.setPassword(DigestUtils.md5DigestAsHex(account.getPhone().getBytes()));
        accountService.save(account);
        ManagerLog log = Utils.setLog(request, "ACCOUNT", "ADD", "新增会员:"+account.getRealName());
        log.setStatus(true);
        managerLogService.save(log);
        redirectAttributes.addFlashAttribute("success", MessageFormat.format("电桩 {0} 已经成功添加。", account.getNickname()));
        return "redirect:/admin/accounts/search";
    }
    
    @RequestMapping(value = "/accoutDebit/{accountId}", method = RequestMethod.POST)
    public String accoutDebit(HttpSession session,@PathVariable("accountId") Long accountId,@ModelAttribute Account form,BindingResult result, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("failed", "线下充值时发生错误，请检查后重新尝试。");
            return "admin/account/debit";
        }
        String params = null;
        ManagerLog log = Utils.setLog(request, "ACCOUNT", "RECHARGE", params);
        Manager manager = (Manager)session.getAttribute("user");
        Account account = accountService.findById(accountId);
        String paidFrom = null;
        if(account!=null){
        	account.setMoney(account.getMoney().add(form.getPayMoney()));
        	Account acc = accountService.save(account);
            if(acc!=null){
            	RechargeRecord rechargeRecord = new RechargeRecord();
            	if("2".equals(form.getPayType())){
            		rechargeRecord.setPaidFrom("OFFLINE");
            		paidFrom = "线下转账";
            	}if("1".equals(form.getPayType())){
            		rechargeRecord.setPaidFrom("CASH");
            		paidFrom = "现金支付";
            	}
            	rechargeRecord.setTradeNo(IDCreator.generator(IDCreator.BUS_COMPANY_RECHARGE));
            	rechargeRecord.setStatus(OrderStatus.PAID);
                rechargeRecord.setMoney(form.getPayMoney());
                rechargeRecord.setAccount(account);
                Date date = new Date();
                rechargeRecord.setUpdatedAt(date);
                rechargeRecordService.save(rechargeRecord);
                AccountRechargeLog  accountRechargeLog = new  AccountRechargeLog();
                accountRechargeLog.setAccount(acc);
                accountRechargeLog.setManager(manager);
                accountRechargeLog.setLogTime(new Date());
                accountRechargeLog.setVoucher(form.getSrc());
                accountRechargeLog.setRechargeMethod(AccountRechargeMethod.OFFLINE);
                accountRechargeLog.setMoney(form.getPayMoney());	
                accountRechargeLog.setRemark(form.getRemark());
                accountRechargeLog.setStatus(true);
                accountRechargeLog.setUpdatedAt(date);
                accountRechargeLogService.save(accountRechargeLog);
                //设置公共日志
                params = "会员充值:"+account.getRealName()+",充值金额:"+ form.getPayMoney()+",充值方式:"+paidFrom+",交易号:"+IDCreator.generator(IDCreator.BUS_COMPANY_RECHARGE);
                log.setStatus(true);
                log.setParams(params);
                managerLogService.save(log);
            }
        }
        redirectAttributes.addFlashAttribute("success", MessageFormat.format("充值 {0} 已经成功添加。", account.getNickname()));
        return "redirect:/admin/accounts/search";
    }
    
    @RequestMapping(value = "/accountEdit/{accountId}", method = RequestMethod.POST)
    public String edit(Model model,@PathVariable("accountId") Long accountId,@ModelAttribute Account account,BindingResult result, RedirectAttributes redirectAttributes,HttpServletRequest request,String district,String stationIds) {
    	String[] sids = stationIds!=null?stationIds.split(","):new String[]{};
          List<Station> selectStations = new ArrayList<>();
          for(String sid : sids){
      		Station station = stationService.findById(Long.parseLong(sid));
      			selectStations.add(station);
      	}
          account.setStations(selectStations);
    	if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("failed", "编辑新用户时发生错误，请检查后重新尝试。");
            return "admin/account/edit";
        }
    	account.setArea(district);
    	String[] areas  = district!=null?district.split(","):new String[]{""};
    	String[] provinces = account.getProvince()!=null?account.getProvince().split(","):new String[]{""};
    	String[] citys = account.getCity()!=null?account.getCity().split(","):new String[]{""};
    	account.setArea(areas[0]);
    	account.setProvince(provinces[0]);
    	account.setCity(citys[0]);
        Account accountnew  = accountService.findById(accountId);
        ManagerLog log = Utils.setLog(request, "ACCOUNT", "EDIT", "编辑会员:"+accountnew.getRealName());
        List<Account> list = accountService.findAll();
        int i=0;
        if(list!=null&&list.size()>0){
        	for(Account ac:list){
        		if(ac.getPhone()!=null&&ac.getPhone().equals(account.getPhone())){
        			if(ac.getId()!=accountId){
        				i++;
        			}
        			
        		}
        	}
        }
        if(i>0){
        	List<Map<String, String>> allStationInfos = getStationInfo();
        	String[] stationNames = new String[selectStations.size()];
        	for(int j = 0 ; j < selectStations.size() ; j++){
        		stationNames[j] = selectStations.get(j).getName();
        	}
        	accountnew.setPhone(account.getPhone());
        	model.addAttribute("allStationInfos", allStationInfos);
			model.addAttribute("account", accountnew);
			model.addAttribute("stationIds", stationIds);
			model.addAttribute("stationNames", stationNames);
    		model.addAttribute("failed", "该手机号码已存在，请检查后重新尝试");
    		return "admin/account/edit";
        }
        /*if(i>0){
        	if(i==1){
        		if(phone.equals(account.getPhone())){
        			
        		}else{
        			List<Map<String, String>> allStationInfos = getStationInfo();
        			model.addAttribute("allStationInfos", allStationInfos);
        			model.addAttribute("account", accountnew);
            		model.addAttribute("failed", "手机号码不能重复！");
            		return "admin/account/edit";
        		}
        	}else{
        		if(accountnew.getPhone()!=null&&!accountnew.getPhone().equals(account.getPhone())){
        			List<Map<String, String>> allStationInfos = getStationInfo();
        			model.addAttribute("allStationInfos", allStationInfos);
        			model.addAttribute("account", accountnew);
            		model.addAttribute("failed", "手机号码不能重复！");
            		return "admin/account/edit";
        		}
        	}
        }*/
        accountnew.setBirth(account.getBirth());
        accountnew.setEmail(account.getEmail());
        accountnew.setLevel(account.getLevel());
        accountnew.setMoney(account.getMoney());
        accountnew.setNickname(account.getNickname());
        accountnew.setPhone(account.getPhone());
        accountnew.setCredit(account.getCredit());
        accountnew.setRealName(account.getRealName());
        accountnew.setGender(account.getGender());
        accountnew.setStatus(account.getStatus());
        accountnew.setNote(account.getNote());
        accountnew.setAvatar(account.getAvatar());
        accountnew.setProvince(account.getProvince());
        accountnew.setCity(account.getCity());
        accountnew.setArea(account.getArea());
        accountnew.setDetailAddress(account.getDetailAddress());
        accountnew.setStations(selectStations);
        accountService.save(accountnew);
        log.setStatus(true);
        managerLogService.save(log);
        redirectAttributes.addFlashAttribute("success", MessageFormat.format("会员 {0} 已经成功添加。", account.getNickname()));
        return "redirect:/admin/accounts/search";
    }
    @RequestMapping("/edit_ajax")
    @ResponseBody
    public Map<String,String> edit_ajax(Account form,HttpServletRequest request){
    	Long id = form.getId();
    	Account account = accountService.findById(id);
    	account.setRealName(form.getRealName());
    	account.setEmail(form.getEmail());
    	account.setNickname(form.getNickname());
    	account.setPhone(form.getPhone());
    	Map<String,String> data = new HashMap<>();
    	ManagerLog log = Utils.setLog(request, "ACCOUNT", "EDIT", "用户信息更新-名称:"+account.getRealName());
    	try{
    		account = accountService.save(account);
    	}catch(Exception e){
    		data.put("errMsg", "更新信息失败:手机号已存在");
    		log.setStatus(false);
    		managerLogService.save(log);
    		return data;
    	}
    	log.setStatus(true);
    	managerLogService.save(log);
    	data.put("sucMsg", "更新成功!");
    	data.put("realName", form.getRealName()!=null?form.getRealName():"");
    	data.put("nickname", form.getNickname()!=null?form.getNickname():"");
    	data.put("email", form.getEmail()!=null?form.getEmail():"");
    	data.put("phone", form.getPhone()!=null?form.getPhone():"");
    	return data;
    	
    }
    @RequestMapping("/search")
    public String search(HttpServletRequest request,Model model, @ModelAttribute Account account,
    		@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
    	HttpSession session = request.getSession();
        if("1".equals(account.getFlag())){
        	session.setAttribute("sessionAccount", account);
        }else{
        	Account ch = (Account)session.getAttribute("sessionAccount");
       	 if(ch!=null){
       		account = ch;
       	 }
        }    
    	String field[] = {"realName","phone","accountType","delstatus","province","city","area"};
    	String key="";
    	for(String fieldName:field){
			Object o = ReflectField.getFieldValueByName(fieldName, account);
			if(o!=null&&!"".equals(o)){
				key+=fieldName+"="+o.toString()+"&";
			}
		}
    	if(!StringUtils.isEmpty(key)){
    		key = key.substring(0,key.length()-1);
    	}
        PageWrapper<Account> page = new PageWrapper<>(accountService.findAllSearch(field,account,pageable), "/admin/accounts/search?"+key);
        List<Account> list =page.getContent();
        List<DictData> dic = EntityUtil.getDictDatas(CategoryConstant.ACCOUNT_TYPE);
        if(list!=null&&list.size()>0){
        	for(Account ac:list){
        		if("正常".equals(ac.getStatus().getTitle())){
        			ac.setTransientStatus("禁用");
        		}else if("违规".equals(ac.getStatus().getTitle())){
        			ac.setTransientStatus("启用");
        		}
        		ac.setTransientDate(VeDate.getStringDate(ac.getCreatedAt()));
        		for(DictData dictData :dic){
        			if(dictData.getDictValue().equals(ac.getAccountType())){
        				ac.setAccountType(dictData.getDescr());
        				break;
        			}
        		}
        	}
        }
        model.addAttribute("page", page);
        model.addAttribute("accountValue", account);
        return "admin/account/index";
    }
    /**
     * 交易记录自定义时间查询
     * @param model
     * @param req
     * @param order
     * @param pageable
     * @return
     */
   /* @RequestMapping(value="/searchDeal/{id}", method = RequestMethod.GET)
    public String search(Model model,HttpServletRequest req,
    		@ModelAttribute Order order,@PathVariable("id") Long id,
    		@PageableDefault(sort = "id", direction = Sort.Direction.DESC,size=10)Pageable pageable) {
    	   String[] fields=new String[]{"account","createdAt"};
		    	Account	account=accountService.findById(id);
	    		PageWrapper<Order> page = new PageWrapper<>(orderService.findAll(fields,order, pageable),"/admin/accounts/searchDeal/"+id);
	    		model.addAttribute("page", page);
	    		model.addAttribute("account", account);
		    	return "admin/account/deal";
    }*/
    
    @RequestMapping(value="/searchDeal/{id}", method = RequestMethod.GET)
    public String search(Model model,
    		@ModelAttribute Order order,@PathVariable("id") Long id,
    		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size=15)Pageable pageable) {
		    	Account	account=accountService.findById(id);
		    	if(order.getStartAt()!=null){
	    			order.setFormstartAt(VeDate.dateToStr(order.getStartAt()));
	    		}
	    		if(order.getEndAt()!=null){
	    			order.setFormendAt(VeDate.dateToStr(order.getEndAt()));
	    		}
	    		String key ="startAt="+order.getFormstartAt()+"&endAt="+order.getFormendAt();
		    	PageWrapper<Order> page = new PageWrapper<Order>(orderService.findAll(order, pageable),"/admin/accounts/searchDeal/"+id+"?"+key);
	    		model.addAttribute("page", page);
	    		model.addAttribute("account", account);
		    	return "admin/account/deal";
    }
    /**
     * 交易记录固定时间的查询
     * @param model
     * @param req
     * @param pageable
     * @return
     */
    
    @RequestMapping(value="/searchTime/{id}/{days}", method = RequestMethod.GET)
    public String searchTime(Model model,
    		@PathVariable("id") Long id,
    		@PathVariable("days") Long days,
    		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size=15)Pageable pageable
    		) {
    	model.addAttribute("account", accountService.findById(id));
		PageWrapper<Order> page = 
				new PageWrapper<>(orderService.searchTime(days, id, pageable),"/admin/accounts/searchTime/"+id+"/"+days);
		model.addAttribute("page", page);
		model.addAttribute("order",new Order());
    	return "admin/account/deal";
    	
    }
	
   /**
    * 充值记录自定义时间查询
    * @param model
    * @param req
    * @param redirectAttributes
    * @param pageable
    * @return
    * @throws ParseException
    */
    @RequestMapping(value="/searchge/{id}")
    public String searchge(Model model,@ModelAttribute RechargeRecord rechargeRecord,@PathVariable("id") Long id,
    		@PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC, size=15)Pageable pageable
    		) { 
		    	if(rechargeRecord.getStartAt()!=null){
		    		rechargeRecord.setFormStartAt(VeDate.dateToStr(rechargeRecord.getStartAt()));
				}
				if(rechargeRecord.getEndAt()!=null){
					rechargeRecord.setFormEndAt(VeDate.dateToStr(rechargeRecord.getEndAt()));
				}
				String key ="account.id="+id+"&startAt="+rechargeRecord.getFormStartAt()+"&endAt="+rechargeRecord.getFormEndAt();
    	       String[] fields={"updatedAt","account"};
	    		PageWrapper<RechargeRecord> page = 
	    				new PageWrapper<>(rechargeRecordService.findAll(fields,rechargeRecord, pageable),"/admin/accounts/searchge/"+id+"?"+key);
	    		List<RechargeRecord> list = page.getContent();
	        	if(list!=null&&list.size()>0){
	        		for(RechargeRecord re:list){
	        			List<AccountRechargeLog> accountRechargeLog = accountRechargeLogService.findByUpdatedAt(re.getUpdatedAt());
	        			if(accountRechargeLog!=null&&accountRechargeLog.size()>0){
	        				AccountRechargeLog ac = accountRechargeLog.get(0);
	        				String src = ac.getVoucher();
	            			re.setUri(src);
	        			}
	        		}
	        	}
	    		model.addAttribute("page", page);
	    		model.addAttribute("account", accountService.findById(id));
	    		model.addAttribute("rechargeRecord",rechargeRecord);
		    	return "admin/account/recharge";
    }
    
    
    
    
    
    /**
     * 充值记录固定时间的查询
     */
    @RequestMapping(value="/searchCharge/{id}/{days}")
    public String searchCharge(Model model,@PathVariable("id") Long id,@PathVariable("days") Long days,
    		@PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC, value=15)Pageable pageable)  {
		PageWrapper<RechargeRecord> page = new PageWrapper<>(rechargeRecordService.searchTime(days, id, pageable),"/admin/accounts/searchCharge/"+id+"/"+days);
		List<RechargeRecord> list = page.getContent();
    	if(list!=null&&list.size()>0){
    		for(RechargeRecord re:list){
    			List<AccountRechargeLog> accountRechargeLog = accountRechargeLogService.findByUpdatedAt(re.getUpdatedAt());
    			if(accountRechargeLog!=null&&accountRechargeLog.size()>0){
    				AccountRechargeLog ac = accountRechargeLog.get(0);
    				String src = ac.getVoucher();
        			re.setUri(src);
    			}
    		}
    	}
		model.addAttribute("page", page);
		model.addAttribute("account", accountService.findById(id));
		model.addAttribute("rechargeRecord", new RechargeRecord());
    	return "admin/account/recharge";
    	
    }
    
   
    /**
     * 查看车辆信息
     * @param model
     * @param account
     * @return
     */
    @RequestMapping("/checkCar/{id}")
    public String checkCar(Model model, @PathVariable("id") Long id) {
    	Car car=accountService.findById(id).getCar();
    	model.addAttribute("account", accountService.findById(id));
    	if(car==null){
    		model.addAttribute("car", null);
    	}else if(car!=null){
    		List<CarAuditLog> audits=car.getCarAuditLogList();
        	model.addAttribute("audit",audits);
        	model.addAttribute("car", car);
    	}
    	
    	return "admin/account/car";
    	
    }
    
    /**
     * 查看交易信息
     * @param model
     * @param account
     * @return
     */
    @RequestMapping("/checkDeal/{id}")
    public String checkOrders(Model model,@PathVariable("id") Long id,
    		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
    	PageWrapper<Order> page =
    			new PageWrapper<>(orderService.findByAccount(accountService.findById(id), pageable), "/admin/accounts/checkDeal/"+id);
		model.addAttribute("page", page);
		model.addAttribute("account", accountService.findById(id));
		model.addAttribute("order", new Order());
    	return "admin/account/deal";
    }
    /**
     * 充值记录
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/checkCharge/{id}")
    public String checkCharge(Model model,
    		@PathVariable("id") Long id, 
    		@PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
    	PageWrapper<RechargeRecord> page = 
    			new PageWrapper<>(rechargeRecordService.findByAccount(accountService.findById(id), pageable),"/admin/accounts/checkCharge/"+id);
    	List<RechargeRecord> list = page.getContent();
    	if(list!=null&&list.size()>0){
    		for(RechargeRecord re:list){
    			List<AccountRechargeLog> accountRechargeLog = accountRechargeLogService.findByUpdatedAt(re.getUpdatedAt());
    			if(accountRechargeLog!=null&&accountRechargeLog.size()>0){
    				AccountRechargeLog ac = accountRechargeLog.get(0);
    				String src = ac.getVoucher();
        			re.setUri(src);
    			}
    		}
    	}
    	model.addAttribute("account", accountService.findById(id));
    	model.addAttribute("method","charge");
    	model.addAttribute("rechargeRecord",new RechargeRecord());
    	model.addAttribute("page",page);
    	return "admin/account/recharge";
    }
   
    /**
     * 查看储值卡信息
     * @param model
     * @param account
     * @return
     */
    @RequestMapping("/checkCard/{id}")
    public String checkCard(@PathVariable("id")  long id, Model model,
            @RequestParam(name="cardNo", defaultValue="") String cardNo,
    		@Qualifier("CardRechargeRecord") @PageableDefault(sort = "tradeTime", direction = Sort.Direction.DESC, size=15) Pageable recordPage,
    		@Qualifier("Order") @PageableDefault(sort = "startAt", direction = Sort.Direction.DESC, size=15) Pageable orderPage) {
        Account account=accountService.findById(id);
        model.addAttribute("account",account);
    	List<Card> cards=account.getCards();
    	
        if(cards.isEmpty()) {
            model.addAttribute("cards",null);
            model.addAttribute("card",null);
            model.addAttribute("rechargeRecordPage", new PageWrapper<>(cardRechargeRecordService.findByCard(null,recordPage),"/admin/accounts/rechargeRecordSearch"));
            model.addAttribute("chargeRecordPage", new PageWrapper<>(orderService.findByCard(null, orderPage),"/admin/accounts/chargeRecordSearch"));
        }else  if(cards!=null){
        	 Card card = cards.get(0);
        	 if(cardNo != null && !"".equals(cardNo)) {
        	     for(Card cardItem  : cards) {
        	         if(cardItem.getCardNo().equals(cardNo)) {
        	             card = cardItem;
        	             break;
        	         }
        	     }
        	 }
        	 model.addAttribute("timeInterval",7);
        	 model.addAttribute("cards",cards);
        	 model.addAttribute("card",card);
        	 model.addAttribute("certificateTypes", EntityUtil.getDictDatas(CategoryConstant.CERTIFICATE_TYPE));
             model.addAttribute("cardTypes", EntityUtil.getDictDatas(CategoryConstant.CARD_TYPE));
             model.addAttribute("rechargeRecordPage",new PageWrapper<>(cardRechargeRecordService.findByCard(card,recordPage),"/admin/accounts/rechargeRecordSearch"));
             model.addAttribute("chargeRecordPage",new PageWrapper<>(orderService.findByCard(card, orderPage),"/admin/accounts/chargeRecordSearch"));
        }
    	return "admin/account/card";
    }
    /**
     * 查看储值卡详情
     * @param model
     * @param id
     * @param pageable
     * @return
     */
   @RequestMapping(value="/checkCardDetails" ,method=RequestMethod.GET)
    public ModelAndView checkDetailed(@RequestParam("cardNo")  String cardNo, 
    		@Qualifier("CardRechargeRecord") @PageableDefault(sort = "tradeNo", direction = Sort.Direction.DESC, size=15) Pageable recordPage,
    		@Qualifier("Order") @PageableDefault(sort = "orderId", direction = Sort.Direction.DESC, size=15) Pageable orderPage) {
    	ModelAndView view=new ModelAndView("admin/account/cardDetails");
    	Card card=cardService.findByCardNo(cardNo);
    	view.addObject("card", card);
    	return view;
    }
   //excel 导出
   @RequestMapping(value="/excle_output")
   public ResponseEntity<InputStreamResource> output(String realName,String phone,HttpServletRequest request){
	   Map<String,String> conditionMap = new HashMap<>();
	   conditionMap.put("realName", realName);
	   conditionMap.put("phone", phone);
	   Account account = new Account();
	   account.setRealName(realName);
	   account.setPhone(phone);
	   List<Account> accounts = accountService.findByCondition(conditionMap, account);
       ExcelUtil eu = ExcelUtil.getInstance();
       String files = System.getProperty("user.dir");
       File fileChild = new File(files);
       File parentFile = fileChild.getParentFile();
       String path = parentFile.getPath()+File.separator+"excel"+File.separator+"accounts"+Utils.dateChangeToNumber(new Date())+".xls";
       File file = new File(path);
       if(!file.exists()){
    	   file.getParentFile().mkdirs();
    	   try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
       Map<String, String> map = new HashMap<>();
       map.put("title", "用户列表");
       map.put("total", accounts.size()+" 条");
       map.put("date", Utils.getDate());
       ManagerLog log = Utils.setLog(request, "ACCOUNT", "EXPORT", "会员列表导出");
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
       eu.exportObj2ExcelByTemplate(map, "/account-info-template.xls", os, accounts, Account.class, true);
       FileSystemResource outfile = new FileSystemResource(path);  
       HttpHeaders headers = new HttpHeaders();  
       headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
       headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", outfile.getFilename()));  
       headers.add("Pragma", "no-cache");  
       headers.add("Expires", "0");  
       try {
	   log.setStatus(true);
	   managerLogService.save(log);
		return ResponseEntity  
		           .ok()  
		           .headers(headers)  
		           .contentLength(outfile.contentLength())  
		           .contentType(MediaType.parseMediaType("application/vnd-ms-excel"))  
		           .body(new InputStreamResource(outfile.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		   log.setStatus(false);
		   managerLogService.save(log);
			e.printStackTrace();
		}
       return null;
   }
  
   	@RequestMapping("/findListAllStation")
	@ResponseBody
	public List<Map<String,String>>  findListAllStation(String province1, String city1 , String district1,String codeAndName) {
		/*String []fields = {"city","district","province","delStatus","code","name"};*/
		String []fields = {"city","district","province","delStatus"};
		/*if("1".equals(station.getStationType())){
			station.setName(station.getCodeAndName());
		}else if("2".equals(station.getStationType())){
			station.setCode(station.getCodeAndName());
		}*/
		Station station = new Station();
		station.setProvince(province1);
		station.setCity(city1);
		station.setDistrict(district1);
		station.setCodeAndName(codeAndName);
		station.setName(codeAndName);
		station.setCode(codeAndName);
		List<Station> list = stationService.findSearch(fields, station);
		List<Map<String,String>> allStationInfos = new ArrayList<>();
		if(list.size()>0&&list!=null){
			for(Station stat :list){
				Map<String,String> map = new HashMap<>();
				map.put("id", stat.getId().toString());
				map.put("name", stat.getName());
				map.put("code", stat.getCode());
				map.put("province", stat.getProvince());
				map.put("city", stat.getCity());
				map.put("district", stat.getDistrict());
				map.put("address", stat.getAddress());
				map.put("oper", stat.getOperator()!=null?stat.getOperator().getName():"");
				allStationInfos.add(map);
			}
		}
		return allStationInfos;
	}
   private List<Map<String,String>> getStationInfo(){
	   Collection<Station> allStations =  EntityUtil.getAllStations();
		List<Map<String,String>> allStationInfos = new ArrayList<>();
		for(Station stat : allStations){
			Map<String,String> map = new HashMap<>();
			map.put("id", stat.getId().toString());
			map.put("name", stat.getName());
			map.put("code", stat.getCode());
			map.put("province", stat.getProvince());
			map.put("city", stat.getCity());
			map.put("district", stat.getDistrict());
			map.put("address", stat.getAddress());
			map.put("oper", stat.getOperator()!=null?stat.getOperator().getName():"");
			allStationInfos.add(map);
		}
		return allStationInfos;
   }
   
}
