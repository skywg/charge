package com.iycharge.server.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.admin.cache.StationStatisticTask.RStationData;
import com.iycharge.server.api.response.QQGetUserinfoResponse;
import com.iycharge.server.api.response.WeiboGetTokenInfoResponse;
import com.iycharge.server.api.response.WeixinGetUserinfoResponse;
import com.iycharge.server.ccu.util.Utility;
import com.iycharge.server.domain.entity.Authorization;
import com.iycharge.server.domain.entity.Authorization.Provider;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.Preference;
import com.iycharge.server.domain.entity.account.favorite.Favorite;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.OrderStatus;
import com.iycharge.server.domain.entity.order.RechargeRecord;
import com.iycharge.server.domain.entity.utils.RedisUtil;
import com.iycharge.server.domain.entity.utils.Status;
import com.iycharge.server.domain.service.AccountService;
import com.iycharge.server.domain.service.FavoriteService;
import com.iycharge.server.domain.service.OrderService;
import com.iycharge.server.domain.service.PreferenceService;
import com.iycharge.server.domain.service.RechargeRecordService;
import com.iycharge.server.domain.service.StationService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/account")
public class AccountRestController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private StationService stationService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RechargeRecordService rechargeRecordService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private PreferenceService preferenceService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    /**
     * 根据用id查询用户信息
     * @param accountId         用户ID
     * @return
     */
    @RequestMapping("/{accountId}")
    @JsonView(BaseEntity.Summary.class)
    public Account getAccount(@PathVariable("accountId") Long accountId) {
        return accountService.findById(accountId);
    }
    
    /**
     * 查询当前用户的信息
     * @param principal     
     * @return
     */
    @RequestMapping("")
    @JsonView(BaseEntity.Private.class)
    public Account getCurrentAccount(Principal principal) {
        return accountService.findById(Long.valueOf(principal.getName()));
    }
    
    /**
     * 更新用户信息
     * @param request       需要更新的信息体
     * @param principal     请求更新信息的用户
     * @return
     */
    @Transactional
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @JsonView(BaseEntity.Summary.class)
    public ResponseEntity<?> updateAccount(@RequestBody Account request, Principal principal) {
        Account account = accountService.findById(Long.valueOf(principal.getName()));
        account.setAvatar(request.getAvatar());
        account.setRealName(request.getRealName());
        account.setNickname(request.getNickname());
        account.setEmail(request.getEmail());
        account.setGender(request.getGender());
        account.setBirth(request.getBirth());
        account.setDeviceToken(request.getDeviceToken());
        account.setProvince(request.getProvince());
        account.setCity(request.getCity());
        account.setArea(request.getArea());
        account.setDetailAddress(request.getDetailAddress());
        // Update this account to database.
        accountService.save(account);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    
    /**
     * 添加收藏
     * @param principal    收藏用户    
     * @param objectId     电站ID
     * @return
     */
    @Transactional
    @RequestMapping(value = "/like", method = RequestMethod.PUT)
    @JsonView(BaseEntity.Private.class)
    public String like(Principal principal, @RequestParam("objectId") Long objectId) {  
        Favorite favorite = favoriteService.findAccountFavorite(Long.valueOf(principal.getName()), objectId);
        if(favorite == null) {
            favorite = new Favorite();
            favorite.setStation(EntityUtil.getStation(objectId));
            favorite.setAccount(accountService.findById(Long.valueOf(principal.getName())));      
            favoriteService.save(favorite);
        }
        return "success";
    }
    
    /**
     * 取消收藏
     * @param principal     取消收藏用户    
     * @param objectId      电站id
     * @return
     */
    @Transactional
    @RequestMapping(value = "/like", method = RequestMethod.DELETE)
    @JsonView(BaseEntity.Private.class)
    public String unlike(Principal principal, @RequestParam("objectId") Long objectId) {
        favoriteService.deleteAccountFavorite(Long.valueOf(principal.getName()), objectId);
        return "success";
    }
    
    /**
     * 查询用户充电记录
     * @param pageable      
     * @param principal     查询用户    
     * @return
     */
    @RequestMapping("/rechargeRecords")
    @JsonView(BaseEntity.Summary.class)
    public Map<String, Object> getCurrentAccountRechargeRecords(@PageableDefault(size = 10, direction=Sort.Direction.DESC, sort={"updatedAt"}) Pageable pageable, Principal principal) {
        Page<RechargeRecord> result = rechargeRecordService.findByAccountAndStatus(accountService.findById(Long.valueOf(principal.getName())), OrderStatus.PAID, pageable);
        
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", result.getNumber());
        map.put("totalPage"  , result.getTotalPages());
        map.put("itemCount"  , result.getContent().size());
               
        JSONArray records = new JSONArray(); 
        if(result.getContent().size() > 0) { 
            for(RechargeRecord record : result.getContent()) {
                JSONObject item = new JSONObject();
                item.put("id", record.getId());
                item.put("money", record.getMoney());
                item.put("status", record.getStatus().getTitle());
                item.put("paidFrom", EntityUtil.getDictTile(CategoryConstant.PAID_FROM, record.getPaidFrom()));
                if(record.getPaidFrom().equals("ALIPAY")) {
                    item.put("updatedAt", Utility.formatDate(record.getAlipayPaymentTime(), "yyyy-MM-dd HH:mm:ss"));
                } else if(record.getPaidFrom().equals("WEBCHART")) {
                    item.put("updatedAt", Utility.formatDate(record.getWeixinalipayPaymentTime(), "yyyy-MM-dd HH:mm:ss"));
                } else {
                    item.put("updatedAt", Utility.formatDate(record.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss"));
                }
                records.add(item);
            }
        }
        
        map.put("items"      , records);
        
        
        return map;
    }
    
    /**
     * 查询用户收藏站点
     * @param principal        查询用户
     * @return
     */
    @RequestMapping("/favorites")
    @JsonView(BaseEntity.Summary.class)
    public List<Favorite> getCurrentAccountFavorites(Principal principal) {
        List<Favorite> favorites = accountService.findById(Long.valueOf(principal.getName())).getFavorites();
        if(favorites != null && favorites.size() > 0) {
            Set<String> keys = new HashSet<>();
            //过滤掉已删除的站点
            for(Iterator<Favorite> iter = favorites.iterator(); iter.hasNext(); ) {
                Favorite favorite = iter.next();
                if(favorite.getStation() != null && favorite.getStation().getDelStatus().equals("del")) {
                    iter.remove();
                } else {
                    keys.add(RedisUtil.PREFIX_STATION + favorite.getStation().getId());
                }
            }
            
            if(keys.size() > 0) {
                Collection<Object> datas = redisUtil.get(keys);
                if(datas != null && datas.size() > 0) {
                    Map<Long, RStationData> rstationDataMap = new HashMap<>();
                    for(Object obj : datas) {
                        rstationDataMap.put(((RStationData)obj).getStationId(), (RStationData)obj);
                    }
                    for(Favorite favorite : favorites) {
                        if(rstationDataMap.containsKey(favorite.getStation().getId())) {
                            favorite.getStation().setTotalCount(
                                    rstationDataMap.get(favorite.getStation().getId()).getTotalNum()
                            );
                            favorite.getStation().setIdleCount(
                                    rstationDataMap.get(favorite.getStation().getId()).getIdleNum()
                            );
                        }
                    }
                }
            }
        }
        return favorites;
    }
    
    /**
     * 修改密码
     * @param principal      
     * @param oldpasswd     旧密码
     * @param newpasswd     新密码
     * @param confirmpass   确认密码
     * @return
     */
    @Transactional
    @RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
    @JsonView(BaseEntity.Private.class)
    public ResponseEntity<?> modifyPassword(Principal principal, 
            @RequestParam("oldpass") String oldpasswd,
            @RequestParam("newpass") String newpasswd,
            @RequestParam("confirmpass") String confirmpass) {
        
        String md5pass = DigestUtils.md5DigestAsHex(oldpasswd.getBytes());
        //查询用户信息，判断数据中的用户密码和传入的用户密码(oldpass)是否一致
        Account account = accountService.findById(Long.parseLong(principal.getName()));
        if(!account.getPassword().equals(md5pass)){
            return new ResponseEntity<>(Status.CHECK_PASS.asMap(), HttpStatus.valueOf(Status.CHECK_PASS.getHttpErroCode()));
        }
        //判断新密码与确认密码是否一致
        if(!newpasswd.equals(confirmpass)) {
            return new ResponseEntity<>(Status.CONFIRM_PASS.asMap(), HttpStatus.valueOf(Status.CONFIRM_PASS.getHttpErroCode())); 
        }
        
        //以上两步都成功，修改用户密码
        account.setPassword(DigestUtils.md5DigestAsHex(newpasswd.getBytes()));
        accountService.save(account);
        
        return new ResponseEntity<>(Status.OK.asMap(), HttpStatus.OK);
    }
    
    /**
     * 查询用户充电记录
     * @param pageable      
     * @param principal     查询用户    
     * @return
     */
    @RequestMapping("/orders")
    @JsonView(BaseEntity.Summary.class)
    public Map<String, Object> getCurrentAccountOrder(@PageableDefault(size = 30, direction=Sort.Direction.DESC, sort={"startAt"}) Pageable pageable, Principal principal) {
        Page<Order> result = orderService.findByAccount(accountService.findById(Long.valueOf(principal.getName())), pageable);
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", result.getNumber());
        map.put("totalPage"  , result.getTotalPages());
        map.put("itemCount"  , result.getContent().size());
        map.put("items"      , result.getContent());
        
        return map;
    }
    
    /**
     * 提交偏好设置
     * @param preference
     * @param principal
     * @return
     */
    @Transactional
    @RequestMapping(value="/addPrefrence")
    @JsonView(BaseEntity.Private.class)
    public String addPrefrence(@RequestParam("carBrand")String carBrand,
    						   @RequestParam("homeAddress")String homeAddress,
    						   @RequestParam("compnayAddress")String compnayAddress,
    						   Principal principal){
    	Account account = accountService.findById(Long.parseLong(principal.getName()));
    	Preference preference2 =account.getPreference();
    	if (preference2==null) {
			preference2 =new Preference();
		}
    	preference2.setCarBrand(carBrand);
    	preference2.setCompnayAddress(compnayAddress);
    	preference2.setHomeAddress(homeAddress);
    	preference2.setAccount(account);
    	preferenceService.save(preference2);
    	return "success";
    }
    
    /**
     * 查看偏好设置
     * @param principal
     * @return
     */
    @RequestMapping(value="/checkPerfrence",method = RequestMethod.GET)
    @JsonView(BaseEntity.Private.class)
    public Preference checkPrefrence(Principal principal){
    	Account account = accountService.findById(Long.parseLong(principal.getName()));
    	return account.getPreference();
    }
    /**
     * 查看三方绑定情况
     * @param principal
     * @return
     */
    @RequestMapping(value="/checkBind",method = RequestMethod.GET)
    @JsonView(BaseEntity.Private.class)
    public List<Authorization> checkBind(Principal principal){
    	Account account = accountService.findById(Long.parseLong(principal.getName()));
    	
    	List<Authorization> authorizations = account.getAuthorizations();
    	
    	return authorizations;
    }
    /**
     * 绑定
     * @param principal
     * @return
     */
    @RequestMapping(value="/addBind",method = RequestMethod.GET)
    @JsonView(BaseEntity.Private.class)
    public String addBind(Principal principal,
    				@RequestParam("provider")String provider,
    				@RequestParam("uid")String uid,
    				@RequestParam("openid") String openid,
    				@RequestParam("token") String token,
    				@RequestParam("appid") String appid){
    	Account account = accountService.findById(Long.parseLong(principal.getName()));
    	Authorization auth = accountService.findAuthorization(Provider.valueOf(provider), uid);
    	Provider providers =null;
    if (auth==null) {
    	 if (provider.equals("QQ")) {
    		 providers =Provider.valueOf(provider);
    		 QQGetUserinfoResponse tokenInfoResponse =getQQTokenInfo(token, openid, appid);
    		 	auth =  new Authorization();
    	    	auth.setProvider(providers);
    	    	auth.setUid(uid);
    	    	auth.setOpenid(openid);
    	    	if (tokenInfoResponse.getGender().equals("男")) {
                	auth.setGender("男");
    			}else {
    				auth.setGender("女");
    			}
                auth.setAvatar(tokenInfoResponse.getFigureurl_qq_2());
                auth.setNickname(tokenInfoResponse.getNickname());
                auth.setCity(tokenInfoResponse.getCity());
                auth.setCountry(tokenInfoResponse.getCountry());
                auth.setProvice(tokenInfoResponse.getProvince());
                accountService.addAuthorization(account, auth);
                return "success";
		}else if (provider.equals("WECHAT")) {
			 providers =Provider.valueOf(provider);
			 WeixinGetUserinfoResponse tokenInfoResponse =getWeixinTokenInfo(token, openid);
			 auth =  new Authorization();
			 auth.setProvider(providers);
 	    	 auth.setUid(uid);
 	    	 
			 if (tokenInfoResponse.getSex().equals("1")) {
					auth.setGender("男");
				}else {
					auth.setGender("女");
				}
	            auth.setAvatar(tokenInfoResponse.getHeadimgurl());
	            auth.setNickname(tokenInfoResponse.getNickname());
	            auth.setOpenid(tokenInfoResponse.getOpenid());
	            auth.setCity(tokenInfoResponse.getCity());
	            auth.setCountry(tokenInfoResponse.getCountry());
	            auth.setProvice(tokenInfoResponse.getProvice());
	            accountService.addAuthorization(account, auth);
	            return "success";
		}else if (provider.equals("WEIBO")) {
			 providers =Provider.valueOf(provider);
			 WeiboGetTokenInfoResponse tokenInfoResponse=getWeiboTokenInfo(token, uid);
			 auth =  new Authorization();
			 auth.setProvider(providers);
 	    	 auth.setUid(uid);
 	    	 auth.setOpenid(openid);
			 if (tokenInfoResponse.getGender().equals("m")) {
	            	auth.setGender("男");
			 }else {
					auth.setGender("女");
			 }
	         auth.setAvatar(tokenInfoResponse.getProfile_image_url());
	         auth.setNickname(tokenInfoResponse.getScreen_name());
	         auth.setCity(tokenInfoResponse.getCity());
	         auth.setCountry(tokenInfoResponse.getCountry());
	         auth.setProvice(tokenInfoResponse.getProvice());
	         accountService.addAuthorization(account, auth);
	         return "success";
		}
      }
		  Account  account2 =  auth.getAccount();
		  accountService.updateAuthorization(account, auth);
  		  accountService.del(account2.getId());
  		  return "success";
	  
    	
    	
    	
    }
    
    /**
     * 解绑
     * @param principal
     * @return
     */
    @RequestMapping(value="/deleteBind",method = RequestMethod.GET)
    @Transactional
    @JsonView(BaseEntity.Private.class)
    public String deleteBind(Principal principal,@RequestParam("provider")String provider){
    	Account account = accountService.findById(Long.parseLong(principal.getName()));
    	List<Authorization> authorizations = account.getAuthorizations();
    	if (provider.equals("QQ")) {
			provider ="腾讯QQ";
		}else if (provider.equals("WECHAT")) {
			provider="微信";
		}else if(provider.equals("WEIBO")){
			provider="新浪微博";
		}
    	for (Authorization authorization : authorizations) {
			if (authorization.getProvider().getTitle().equals(provider) ) {
				accountService.delete(authorization);
			}
		}
    	return "success";
    }
    /*
     * 微信获取第三方资料
     */
    private WeixinGetUserinfoResponse getWeixinTokenInfo(String token,String openid) {
        WeixinGetUserinfoResponse response = null;
        RestTemplate restTemplate = geTemplate();
        MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
        mvm.add("access_token", token);
        mvm.add("openid", openid);

        try {
            response = restTemplate.postForObject("https://api.weixin.qq.com/sns/userinfo", mvm, WeixinGetUserinfoResponse.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return response;
    }
    /*
     * QQ获取第三方资料
     */
    private QQGetUserinfoResponse getQQTokenInfo(String token,String openid,String appid) {
        QQGetUserinfoResponse response = null;
        RestTemplate restTemplate = geTemplate();
       // MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
        Map<String, String> mvm =new HashMap<>();
        mvm.put("access_token", token);
        mvm.put("openid", openid);
        mvm.put("oauth_consumer_key", appid);

        try {
        	response=restTemplate.getForObject("https://graph.qq.com/user/get_user_info?oauth_consumer_key="+appid+"&access_token="+token+"&openid="+openid, QQGetUserinfoResponse.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return response;
    }
    /*
     * 获取微博第三方资料
     */
    private WeiboGetTokenInfoResponse getWeiboTokenInfo(String token,String uid) {
        WeiboGetTokenInfoResponse response = null;
        RestTemplate restTemplate = geTemplate();
        MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
        mvm.add("access_token", token);
        mvm.add("uid", uid);

        try {
            response = restTemplate.postForObject("https://api.weibo.com/2/users/show.json", mvm, WeiboGetTokenInfoResponse.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return response;
    }
    private RestTemplate geTemplate(){
    	RestTemplate restTemplate = new RestTemplate();

        final List<HttpMessageConverter<?>> listHttpMessageConverters = new ArrayList<HttpMessageConverter<?>>();
        listHttpMessageConverters.add(new FormHttpMessageConverter());

        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.TEXT_HTML));
        listHttpMessageConverters.add(jackson2HttpMessageConverter);

        restTemplate.setMessageConverters(listHttpMessageConverters);
        return restTemplate;
    }
}
