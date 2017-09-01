package com.iycharge.server.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.google.gson.JsonObject;
import com.iycharge.server.admin.controller.helper.PageWrapper;
import com.iycharge.server.admin.message.PushClient;
import com.iycharge.server.admin.message.SendCloudSmtp;
import com.iycharge.server.admin.message.SmsSend;
import com.iycharge.server.api.util.IDCreator;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.ManagerLog;
import com.iycharge.server.domain.entity.notification.MailTemplate;
import com.iycharge.server.domain.entity.notification.NoteTemplate;
import com.iycharge.server.domain.entity.notification.Notification;
import com.iycharge.server.domain.entity.notification.NotificationQueryParam;
import com.iycharge.server.domain.entity.notification.NotificationStatus;
import com.iycharge.server.domain.entity.notification.Notification_Sender;
import com.iycharge.server.domain.entity.notification.SendType;
import com.iycharge.server.domain.entity.notification.ShowNotify;
import com.iycharge.server.domain.entity.utils.Gender;
import com.iycharge.server.domain.entity.utils.Utils;
import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.domain.service.ManagerLogService;
import com.iycharge.server.domain.service.MessageService;
import com.iycharge.server.domain.service.NotifiSenderService;
import com.iycharge.server.domain.service.NotificationService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
 * 消息控制器
 * 
 * @author sxiao
 */
@Controller
@RequestMapping("/admin/notify")
public class NotificationController {


	
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private NotifiSenderService notifiSenderService;
	@Autowired
	private ManagerLogService managerLogService;
	private static NotificationQueryParam staticQueryParam = null;
	private static final String SMTP_URL = "http://api.sendcloud.net/apiv2/template/list";
	private static final String SMTP_APIUSER="qyjsyf_test_sDLlHx";
	private static final String SMTP_APIKEY="JdSwljfVq1OgB38n";
	private static final String URL_SMTP=SMTP_URL+"?apiUser="+SMTP_APIUSER+"&apiKey="+SMTP_APIKEY;
	private static final String SMS_URL = "http://www.sendcloud.net/smsapi/list";
	private static final String SMS_USER = "uway_123";
	private static final String SMS_KEY = "E3IxeW2CZT5LMPukrcJSn6IDyWEf74qd";
	private static final String SELECT_NAME="过滤条件";
	
	
	@ModelAttribute("sendTypes")
    public List<SendType> sendTypes() {
        return SendType.asList();
    }
	@ModelAttribute("notificationStatus")
    public List<NotificationStatus> notificationStatus() {
        return NotificationStatus.asList();
    }
	
	/**
	 * 查询
	 */
	@RequestMapping("/")
    public String searchAll(Model model,@PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC, size=15) Pageable pageable,String flag) {	    			
		 PageWrapper<Notification> page = null;
		 if(flag!=null&&staticQueryParam!=null){
			 page = new PageWrapper<>(notificationService.findAllSearch(staticQueryParam, pageable),
						"/admin/notify/search");
			 model.addAttribute("queryParam", staticQueryParam); 
		 }else{
			 model.addAttribute("queryParam", new NotificationQueryParam()); 
			 page = new PageWrapper<>(notificationService.findAll(pageable), "/admin/notify/");
			 staticQueryParam = null;
		 }
		 model.addAttribute("page", page); 
         return "admin/news/index";
    }
	/**
	 * 条件查询
	 */
	@RequestMapping(value="/search")
	public String search(NotificationQueryParam queryParam,Model model, @ModelAttribute Notification notification,
			@PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC, size=15) Pageable pageable
			) {
		PageWrapper<Notification> page = new PageWrapper<>(notificationService.findAllSearch(queryParam, pageable),
				"/admin/notify/search");
			model.addAttribute("page", page);	
			model.addAttribute("queryParam",queryParam);
			staticQueryParam = queryParam;
		return "admin/news/index";
	}
	@RequestMapping(value="/search_ajax")
	public String search_ajax(Model model,String phone,String sex , String age,String level,
			@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=15) Pageable pageable) {
		String fields[]={phone,sex,age,level};
		Account account = new Account();
		account.setPhone(phone);
		account.setPhone(sex);
		account.setPhone(age);
		account.setPhone(level);
		PageWrapper<Account> page = new PageWrapper<>(accountService.findAllSearch(fields, account, pageable),
				"/admin/notify/search_ajax");
		return "admin/news/index";
	}
	/**
	 *新增 
	 */
	@RequestMapping(value="/add")
	public String add(Model model,HttpSession session) {
		Notification notification = new Notification();
    	Manager manager = (Manager)session.getAttribute("user");
    	String temp_sms = null;
    	String temp_smtp = null;
    	try {
    		temp_sms = getList("SMS");
        	temp_smtp = getList("SMTP");
		} catch (Exception e) {
			return "admin/news/error/error2";
		}
    	
    	List<NoteTemplate> temp_sms_list = (List<NoteTemplate>) jsonStrToList(temp_sms, "SMS");
    	List<MailTemplate> temp_smtp_list = (List<MailTemplate>) jsonStrToList(temp_smtp, "SMTP");
		notification.setEditor(manager.getLoginName());
    	model.addAttribute("editTime",new Date());
		model.addAttribute("notification",notification);
    	model.addAttribute("temp_sms",temp_sms_list);
		model.addAttribute("temp_smtp",temp_smtp_list);
		return "admin/news/add";
	}
	/**
	 * 增加消息 
	 */
    @Transactional
    @RequestMapping(value="/addnotify", method=RequestMethod.POST) 
    public String addnotify(@ModelAttribute Notification notification,
    		HttpSession session,String actionCode,Long id,HttpServletRequest request){
    	
    	if(id!=null){
    		//消息更新
    		ManagerLog log = Utils.setLog(request, "MESSAGE", "EDIT", "编辑消息:"+id+"号");
    		log.setStatus(true);
    		
    		Notification saved = notificationService.findById(id);
    		saved = setBean(saved, notification);
    		saved = setSenders(saved);
    		Manager manager = (Manager)session.getAttribute("user");
    		saved.setEditor(manager.getRealname());
    		saved = notificationService.save(saved);
    		managerLogService.save(log);
        	if(actionCode!=null&&actionCode.equals("send")){
        		return "redirect:/admin/notify/send/"+saved.getId()+"?sendObj="+saved.getTagets();    
        	}
        	return "redirect:/admin/notify/?flag=true";   
    	}
    	ManagerLog log = Utils.setLog(request, "MESSAGE", "ADD", "添加新消息");
    	notification = setSenders(notification);
    	notification.setCreatedAt(new Date());
    	Manager manager = (Manager)session.getAttribute("user");
    	notification.setEditor(manager.getRealname());
    	notification.setNotificationStatus(NotificationStatus.EDIT);
    	notification.setTagNumber(IDCreator.generator(IDCreator.BUS_COMPANY_RECHARGE));
    	notification = notificationService.save(notification);
    	log.setStatus(true);
    	managerLogService.save(log);
    	if(actionCode!=null&&actionCode.equals("send")){
    		return "redirect:/admin/notify/send/"+notification.getId()+"?sendObj="+notification.getTagets();
    	}
    	return "redirect:/admin/notify/?flag=true";
    }  
	
	/**
	 *查看 
	 */
	@RequestMapping(value="/check/{id}")
	public String check(Model model,  @PathVariable("id") Long id) {
		Notification notification = notificationService.findById(id);
		List<Notification_Sender> nss = notifiSenderService.findByNotification(notification);
		List<ShowNotify> sns = setShowNotify(notification, nss);
		model.addAttribute("notification", notification);
		model.addAttribute("sns", sns);
		return "admin/news/check";
	}
	/**
	 * 编辑
	 */
	@RequestMapping(value="/edit/{id}")
	public String edit(Model model,  @PathVariable("id") Long id) {
		Notification notification = notificationService.findById(id);
		String temp_sms = null;
    	String temp_smtp = null;
    	try {
    		temp_sms = getList("SMS");
        	temp_smtp = getList("SMTP");
		} catch (Exception e) {
			return "admin/news/error/error2";
		}
		JSONObject jasonObject_sms = JSONObject.fromObject(temp_sms);
		JSONObject jasonObject_smtp = JSONObject.fromObject(temp_smtp);
    	List<NoteTemplate> temp_sms_list = (List<NoteTemplate>) jsonStrToList(temp_sms, "SMS");
    	List<MailTemplate> temp_smtp_list = (List<MailTemplate>) jsonStrToList(temp_smtp, "SMTP");
    	model.addAttribute("temp_sms",temp_sms_list);
		model.addAttribute("temp_smtp",temp_smtp_list);
		model.addAttribute("notification",notification);
		model.addAttribute("sendObj",notification.getTagets());
		model.addAttribute("sType",notification.getSendType().name());
		model.addAttribute("edit","编辑消息");
		return "admin/news/add";
	}
	/**
	 * 更新
	 */
	@Transactional
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") Long id,
    		@Valid Notification form,String actionCode,String sendObj) {
    	Notification notification = notificationService.findById(id);
    	notification = setSenders(notification);
    	notification = notificationService.save(notification);
    	if(actionCode!=null&&actionCode.equals("send")){
    		return "redirect:/admin/notify/send/"+notification.getId()+"?sendObj="+notification.getTagets();    
    	}
    	return "redirect:/admin/notify/?flag=true";        	        
    }
	/**
	 *发送 
	 */

	@RequestMapping(value="/send/{id}")
	public String send(Model model ,@PathVariable("id") Long id ,HttpServletRequest request) {
		Notification notification = notificationService.findById(id);		
		String sendType = notification.getSendType().name();
		notification.setSendAt(new Date());
		notification.setNotificationStatus(NotificationStatus.SENDED);
		ManagerLog log = Utils.setLog(request, "MESSAGE", "EDIT", "发送消息-消息id："+id);
		/*if("WEIXIN".equals(sendType)){
			pushByWeixin(notification);
		}*/
		if("MAIL".equals(sendType)){
			pushByMail(notification);
		}else if("NOTE".equals(sendType)){
			pushByNote(notification);
		}else if("APPMSG".equals(sendType)){
			pushByAppmsg(notification,"message");
		}else if("APPNOT".equals(sendType)){
			pushByAppmsg(notification,"notification");
		}
		log.setStatus(true);
		notificationService.save(notification);
		managerLogService.save(log);
		return "redirect:/admin/notify/?flag=true";
	}
	
	@RequestMapping(value="/sendOne/{id}")
	public String sendOne(Model model ,@PathVariable("id") Long id ) {
		Notification_Sender ns = notifiSenderService.findById(id);
		Notification sendOne = new Notification();
		Notification notification = ns.getNotification();
		setBean(sendOne, notification);
		Account account = ns.getAccount();
		List<Account> accounts = new ArrayList<>(); 
		accounts.add(account);
		String sendType = notification.getSendType().name();
		sendOne.setNotificationStatus(NotificationStatus.SENDED);
		sendOne.setSenders(accounts);
		ns.setStatus(true);
		/*if("WEIXIN".equals(sendType)){
			pushByWeixin(notification);
		}*/
		if("MAIL".equals(sendType)){
			pushByMail(sendOne);
		}else if("NOTE".equals(sendType)){
			pushByNote(sendOne);
		}else if("APPMSG".equals(sendType)){
			pushByAppmsg(sendOne,"message");
		}else if("APPNOT".equals(sendType)){
			pushByAppmsg(sendOne,"notification");
		}
		notifiSenderService.save(ns);
		return "redirect:/admin/notify/?flag=true";
	}
	
	//处理前端邮件模板选择的ajax请求
	@RequestMapping("/ajax_mail_content")
	public void ajax_mail_content(String invokeName,HttpServletResponse response){
		String url = "http://api.sendcloud.net/apiv2/template/get?apiUser="+SMTP_APIUSER+"&apiKey="+SMTP_APIKEY+"&invokeName="+invokeName;
		RestTemplate restTemplate = new RestTemplate();
		JSONObject ret = restTemplate.getForObject(url, JSONObject.class);
		Map<String,String> jsonMap = new HashMap<>();
		JSONObject need = (JSONObject)ret.getJSONObject("info").getJSONObject("data");
		jsonMap.put("subject", need.getString("subject"));
		String html = need.getString("html");
		jsonMap.put("html", html);
		String data = JSONArray.fromObject(jsonMap).toString();
		PrintWriter out = null;
		try {
			 out = response.getWriter();
			 out.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
		
	}
	//处理前端邮件搜索的ajax请求
	@RequestMapping("/ajax_search")
	public void ajax_search(String phone,HttpServletResponse response){
		Account account = accountService.findByPhone(phone);
		String data = "";
		PrintWriter out = null;
		if(account!=null){
			data=phone;
		}
		try {
			out = response.getWriter();
			out.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
		
	}
	//使用app通知推送消息
	private void pushByAppmsg(Notification notification,String showType) {
		PushClient pushClient = new PushClient();
		String sendObj = notification.getTagets();
		new Thread(new Runnable() {
			public void run() {
				try {
					String obj = "";
					if(sendObj.equals("过滤条件:(所有人)")){
						obj = "broadcast";
					}else{
						obj = "listcast";
					}
					Notification android_not = new Notification();
					Notification ios_not = new Notification();
					List<Account> androids = new ArrayList<>();
					List<Account> ioss = new ArrayList<>();		
					if(notification.getSenders()!=null){
						List<Account> all = notification.getSenders(); 
						for(int i = 0 ; i < all.size() ; i++){
							if(all.get(i).getDeviceToken()!=null&&all.get(i).getDeviceToken().length()==44){
								androids.add(all.get(i));
							}else if(all.get(i).getDeviceToken()!=null&&all.get(i).getDeviceToken().length()==64){
								ioss.add(all.get(i));
							}
						}
						setBean(android_not, notification);
						android_not.setSenders(androids);
						setBean(ios_not, notification);
						ios_not.setSenders(ioss);
					}
					if(android_not.getSenders().size()>0){
						pushClient.send(android_not, obj, "android", "notification");
					}
					if(ios_not.getSenders().size()>0){
						pushClient.send(ios_not, obj, "ios", "notification");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}).start();
		
	}
	//群发短信
	private void pushByNote(Notification notification) {
		SmsSend sms = new SmsSend();
		String templateId = notification.getTempid();
		new Thread(new Runnable() {
			@Override
			public synchronized void run() {
				List<Account> accounts = notification.getSenders();
				int groups  = accounts.size() / 100;
				String phones = "";
				//短信每次最多发送100条
				if(groups < 1){
					for(Account account : accounts){
						if(account.getPhone()!=null&&!account.getPhone().trim().equals("")){
							phones += account.getPhone()+",";
						}
					}
					phones = phones.substring(0,phones.lastIndexOf(","));
					try {
						sms.sendSMS(phones, templateId);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}else{
					for(int i = 0 ; i < groups ; i ++){
						for(int j = i*100 ; j < (i+1)*100 ; j++){
							if(accounts.get(j).getPhone()!=null&&!accounts.get(j).getPhone().trim().equals("")){
								phones += accounts.get(j).getPhone()+",";
							}
						}
						try {
							phones = phones.substring(0,phones.lastIndexOf(",")-1);
							sms.sendSMS(phones, templateId);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
						} finally{
							phones = "";
						}
					}
					for(int i=groups*100;i<accounts.size();i++){
						if(accounts.get(i).getPhone()!=null&&!accounts.get(i).getPhone().trim().equals("")){
							phones += accounts.get(i).getPhone()+",";
						}
						phones = phones.substring(0,phones.lastIndexOf(",")-1);
						try {
							sms.sendSMS(phones, templateId);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally{
							phones = "";
						}
					}
				}
			}
		}).start();
	}
	//使用邮件
	private void pushByMail(Notification notification) {
		SendCloudSmtp smtp = new SendCloudSmtp();		
		if(notification.getTempName()!=null&&!notification.getTempName().trim().equals("")){
			new Thread(new Runnable() {
				@Override
				public synchronized  void run() {
					List<Account> accounts = notification.getSenders();
					int groups  = accounts.size() / 100;
					try {
						if(groups<1){
							smtp.send_template(notification,accounts);
						}else{
							for(int i= 0 ; i < groups ; i ++){
								List<Account> sendAccounts = new ArrayList<>();
								for(int j = i*100;j<(i+1)*100 ; j++){
									sendAccounts.add(accounts.get(j));
								}
								smtp.send_template(notification,sendAccounts);
							}
							List<Account> sendAccounts = new ArrayList<>();
							for(int i=groups*100;i<accounts.size();i++){
								sendAccounts.add(accounts.get(i));
							}
							if(sendAccounts.size()>0){
								smtp.send_template(notification,sendAccounts);
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}).start();
		}else{
			new Thread(new Runnable() {
				@Override
				public synchronized  void run() {
					List<Account> accounts = notification.getSenders();
					int groups  = accounts.size() / 100;
					String rcpt_to = "";
					//邮件每次最多发送100条
					if(groups < 1){
						for(Account account : accounts){
							if(account.getEmail()!=null){
								rcpt_to += account.getEmail()+";";
							}
						}
						rcpt_to = rcpt_to.substring(0,rcpt_to.lastIndexOf(";"));
						try {
							smtp.send_common(notification, rcpt_to);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}else{
						for(int i = 0 ; i < groups ; i ++){
							for(int j = i*100 ; j < (i+1)*100 ; j++){
								if(accounts.get(j).getEmail()!=null){
									rcpt_to += accounts.get(j).getEmail()+";";
								}
							}
							try {
								rcpt_to = rcpt_to.substring(0,rcpt_to.lastIndexOf(";"));
								smtp.send_common(notification, rcpt_to);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally{
								rcpt_to = "";
							}
						}
						for(int i=groups*100;i<accounts.size();i++){
							if(accounts.get(i).getEmail()!=null){
								rcpt_to += accounts.get(i).getEmail()+";";
							}
							rcpt_to = rcpt_to.substring(0,rcpt_to.lastIndexOf(";"));
						}
						try {
							if(rcpt_to.length()>0){
								smtp.send_common(notification, rcpt_to);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally{
							rcpt_to = "";
						}
					};
				}
			}).start();
		}
	}
	
	private Notification setSenders(Notification notification){
		String sendObj = notification.getTagets();
		if(sendObj.startsWith(SELECT_NAME)){
			sendObj = sendObj.substring(sendObj.indexOf(":"));
			sendObj = sendObj.substring(sendObj.indexOf("(")+1, sendObj.lastIndexOf(")"));
			if(sendObj.startsWith("所有人")){
				List<Account> lst = accountService.findAll();	
    			notification.setSenders(lst);
    			
			}else{
				String temp[] = sendObj.split(",");
				Map<String , String> queryMap = new HashMap<>();
				Account account = new Account();
				for(int i = 0 ; i < temp.length ; i++){
					String queryKey[] = temp[i].split(":");
					if(queryKey[1].equals("不限")){
						queryKey[1]=null;
					}if(queryKey[0].equals("gender")&&queryKey[1]!=null&&!("不限").equals(queryKey[1])){
						if(queryKey[1].equals("FEMALE")){
							account.setGender(Gender.FEMALE);
						}else{
							account.setGender(Gender.MALE);
						}
						
					}
					queryMap.put(queryKey[0], queryKey[1]);
				}
				List<Account> accounts = accountService.findByConditionMap(queryMap, account);
				notification.setSenders(accounts);
			}
		}else{
			String phones[] = sendObj.split(",");
			List<Account> accounts = new ArrayList<>();
			for(int i = 0 ; i < phones.length ; i++){
				Account account = accountService.findByPhone(phones[i]);
				accounts.add(account);
			}
			notification.setSenders(accounts);
		}
		return notification;
	}
	
	private String getList(String tempType){
		String allTemp = "";
		if(tempType.equals("SMS")){
			String url=SMS_URL;
			String isVerifyStr="1";
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("smsUser", SMS_USER);
	        params.put("isVerifyStr", isVerifyStr);
	        // 对参数进行排序
	        Map<String, String> sortedMap = new TreeMap<String, String>(new Comparator<String>() {
	            public int compare(String arg0, String arg1) {
	                // 忽略大小写
	                return arg0.compareToIgnoreCase(arg1); 
	            }
	        });
	        sortedMap.putAll(params);
	        // 计算签名
	        StringBuilder sb = new StringBuilder();
	        sb.append(SMS_KEY).append("&");
	        for (String s : sortedMap.keySet()) {
	            sb.append(String.format("%s=%s&", s, sortedMap.get(s)));
	        }
	        sb.append(SMS_KEY);
	        String sig = DigestUtils.md5Hex(sb.toString());
	        // 将所有参数和签名添加到post请求参数数组里
	        List<NameValuePair> postparams = new ArrayList<NameValuePair>();
	        for (String s : sortedMap.keySet()) {
	            postparams.add(new BasicNameValuePair(s, sortedMap.get(s)));
	        }
	        postparams.add(new BasicNameValuePair("signature", sig));
	        HttpPost httpPost = new HttpPost(url);
	        try {
	            httpPost.setEntity(new UrlEncodedFormEntity(postparams, "utf8"));
	            CloseableHttpClient httpClient;
	            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(100000).build();
	            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
	            HttpResponse response = httpClient.execute(httpPost);
	            HttpEntity entity = response.getEntity();
	            allTemp = EntityUtils.toString(entity);
	            System.out.println(EntityUtils.toString(entity));
	            EntityUtils.consume(entity);
	        } catch (Exception e) {
	            System.out.println(e.toString());
	        } finally {
	            httpPost.releaseConnection();
	        }
	        return allTemp;
		}
		if(tempType.equals("SMTP")){
			String templateStat = "1";
			String url = URL_SMTP+"&templateStat="+templateStat;
	    	RestTemplate restTemplate = new RestTemplate();
	    	allTemp = restTemplate.getForObject(url, String.class);
			return allTemp;
		}
		return null;
	}
	
	private List<?> jsonStrToList(String jsonStr,String Type){	
		List list = new ArrayList<>();
		if(Type.equals("SMS")){
			JSONObject jasonObject_sms = JSONObject.fromObject(jsonStr);
			JSONArray temp_sms_list = (JSONArray)((JSONObject) jasonObject_sms.get("info")).get("list");;
			if(temp_sms_list!=null){
				for(int i = 0 ; i < temp_sms_list.size(); i++){
					JSONObject json = temp_sms_list.getJSONObject(i);
					list.add((NoteTemplate)json.toBean(json,NoteTemplate.class));
				}
			}
		}
		if(Type.equals("SMTP")){
			JSONObject jasonObject_smtp = JSONObject.fromObject(jsonStr);
			JSONArray temp_smtp_list = (JSONArray)((JSONObject) jasonObject_smtp.get("info")).get("dataList");
			if(temp_smtp_list!=null){
				for(int i = 0 ; i < temp_smtp_list.size(); i++){
					JSONObject obj = temp_smtp_list.getJSONObject(i);
					list.add((MailTemplate)obj.toBean(obj, MailTemplate.class));
				}
			}
		}
		return list;
	}
	private List<ShowNotify> setShowNotify(Notification notification,List<Notification_Sender> ns){
		List<ShowNotify> sns = new ArrayList<>();
		List<Account> accounts = notification.getSenders();
		for(int i = 0 ; i < accounts.size() ; i++){
			if(notification.getSendAt()!=null){
				String sendAt = notification.getSendAt().toString();
				sendAt = sendAt.substring(0, sendAt.lastIndexOf("."));
				ShowNotify sn = new ShowNotify(accounts.get(i).getPhone(),ns.get(i).getId(),notification.getSendType().getTitle(),ns.get(i).getStatus(),sendAt);
				sns.add(sn);
			}
			
			
		}
		return sns;
	}
	private  String delHTMLTag(String htmlStr){ 
	         String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
	         String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
	         String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
	         
	         Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
	         Matcher m_script=p_script.matcher(htmlStr); 
	         htmlStr=m_script.replaceAll(""); //过滤script标签 
	         
	         Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
	         Matcher m_style=p_style.matcher(htmlStr); 
	         htmlStr=m_style.replaceAll(""); //过滤style标签 
	         
	         Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
	         Matcher m_html=p_html.matcher(htmlStr); 
	         htmlStr=m_html.replaceAll(""); //过滤html标签 

	        return htmlStr.trim(); //返回文本字符串 
	     } 
	private Notification setBean(Notification notification , Notification from){
		notification.setAction(from.getAction());
		notification.setContent(from.getContent());
		notification.setEditor(from.getEditor());
		notification.setNotificationStatus(NotificationStatus.EDIT);
		notification.setSendType(from.getSendType());
		notification.setTagets(from.getTagets());
		notification.setTempid(from.getTempid());
		notification.setTempName(from.getTempName());
		notification.setTicker(from.getTicker());
		notification.setTitle(from.getTitle());
		notification.setUrl(from.getUrl());
		notification.setVoice(from.getVoice());
		return notification;
	}
}
