package com.iycharge.server.api.security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.MvcNamespaceHandler;

import com.iycharge.server.api.response.QQGetUserinfoResponse;
import com.iycharge.server.api.response.WeiboGetTokenInfoResponse;
import com.iycharge.server.api.response.WeixinGetUserinfoResponse;
import com.iycharge.server.domain.entity.Authorization;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.AccountStatus;
import com.iycharge.server.domain.entity.utils.Gender;
import com.iycharge.server.domain.exception.BindException;
import com.iycharge.server.domain.service.AccountService;
/**
 * 三方授权登入
 * @author daisi
 *
 */
public class SocialNetworkTokenGranter extends AbstractTokenGranter {
    private static final String GRANT_TYPE = "social";
    private final List<String> fields = Arrays.asList("provider","uid","openid","token","appid","device_token");
    private AccountService accountService;
    public SocialNetworkTokenGranter(AccountService accountService,
                                     AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        this(accountService, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    protected SocialNetworkTokenGranter(AccountService accountService, AuthorizationServerTokenServices tokenServices,
                                        ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.accountService = accountService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());

        // Validate request parameters
        for (String field : fields) {
            if (parameters.get(field) == null || !StringUtils.hasText(parameters.get(field))) {
                throw new InvalidRequestException("The field " + field + " must be supplied.");
            }
        }
        Authorization.Provider provider = Authorization.Provider.valueOf(parameters.get("provider").toUpperCase());
        WeixinGetUserinfoResponse tokenInfoResponse=null;
        if (provider.equals(Authorization.Provider.WECHAT)) {
             tokenInfoResponse = getWeixinTokenInfo(parameters.get("token"),parameters.get("uid"));
            if (tokenInfoResponse.getOpenid().equals("")&&tokenInfoResponse.getOpenid()==null) {
                throw new InvalidRequestException("Invalid access token");
            }
        }
        QQGetUserinfoResponse tokenInfoResponse1=null;
        if (provider.equals(Authorization.Provider.QQ)) {
             tokenInfoResponse1 = getQQTokenInfo(parameters.get("token"),parameters.get("uid"),parameters.get("appid"));
            if (tokenInfoResponse1.getRet().equals("")&&tokenInfoResponse1.getRet()==null) {
                throw new InvalidRequestException("Invalid access token");
            }
        }
        WeiboGetTokenInfoResponse tokenInfoResponse2=null;
        if (provider.equals(Authorization.Provider.WEIBO)) {
             tokenInfoResponse2 = getWeiboTokenInfo(parameters.get("token"),parameters.get("uid"));
            if (tokenInfoResponse2.getId().equals("")&&tokenInfoResponse2.getId()==null) {
                throw new InvalidRequestException("Invalid access token");
            }
        }
        Authentication userAuth;
        Account account =null;
        try {
        	if (provider.equals(Authorization.Provider.WECHAT)) {
        		 account = createOrUpdateAccountWithParams(provider, parameters,tokenInfoResponse);
            }
        	if (provider.equals(Authorization.Provider.QQ)) {
        		account = createOrUpdateAccountWithParams1(provider, parameters,tokenInfoResponse1);
			}
        	if (provider.equals(Authorization.Provider.WEIBO)) {
        		account = createOrUpdateAccountWithParams2(provider, parameters,tokenInfoResponse2);
			}
           //判断三方登入是否有绑定会员用户
           if (account.getPhone() == null || account.getPhone().length() <= 0) {
        	   throw new BindException("unbind_phone");
		   }
			  account.setDeviceToken(parameters.get("device_token"));
			  accountService.save(account);
		   
            userAuth = new UsernamePasswordAuthenticationToken(
            		account.getId(), null, AuthorityUtils.createAuthorityList("ROLE_USER"));
            ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
           } catch (InvalidRequestException e) {
        	   	throw new InvalidGrantException("Could not authenticate user", e);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
    private Account createOrUpdateAccountWithParams(Authorization.Provider provider, Map<String, String> parameters,WeixinGetUserinfoResponse tokenInfoResponse) {
        Authorization auth = accountService.findAuthorization(provider, parameters.get("uid"));
        if (auth == null) {
            Account account = new Account();
            account.setStatus(AccountStatus.FORBIDDEN);
	    	account.setAccountType("PERSON");
            account.setNickname(tokenInfoResponse.getNickname());
            account.setAvatar(tokenInfoResponse.getHeadimgurl());
            account.setDeviceToken(parameters.get("device_token"));
            if (tokenInfoResponse.getSex().equals("1")) {
				account.setGender(Gender.MALE);
			}else {
				account.setGender(Gender.FEMALE);
			}
            account = accountService.save(account);
            auth = new Authorization();
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
            auth.setProvider(provider);
            auth.setUid(parameters.get("uid"));
            auth.setToken(parameters.get("token"));
            auth = accountService.addAuthorization(account, auth);
        }
        return auth.getAccount();
    }
    private Account createOrUpdateAccountWithParams1(Authorization.Provider provider, Map<String, String> parameters,QQGetUserinfoResponse tokenInfoResponse) {
        Authorization auth = accountService.findAuthorization(provider, parameters.get("uid"));
        if (auth == null) {
            Account account = new Account();
	    	account.setAccountType("PERSON");
            account.setStatus(AccountStatus.FORBIDDEN);
            account.setNickname(tokenInfoResponse.getNickname());
            account.setAvatar(tokenInfoResponse.getFigureurl_qq_2());
            if (tokenInfoResponse.getGender().equals("男")) {
				account.setGender(Gender.MALE);
			}else {
				account.setGender(Gender.FEMALE);
			}
            account = accountService.save(account);
            auth = new Authorization();
            if (tokenInfoResponse.getGender().equals("男")) {
            	auth.setGender("男");
			}else {
				auth.setGender("女");
			}
            auth.setOpenid(parameters.get("openid"));
            auth.setAvatar(tokenInfoResponse.getFigureurl_qq_2());
            auth.setNickname(tokenInfoResponse.getNickname());
            auth.setCity(tokenInfoResponse.getCity());
            auth.setCountry(tokenInfoResponse.getCountry());
            auth.setProvice(tokenInfoResponse.getProvince());
            auth.setProvider(provider);
            auth.setUid(parameters.get("uid"));
            auth.setToken(parameters.get("token"));
            auth = accountService.addAuthorization(account, auth);
        }
        return auth.getAccount();
    }
    private Account createOrUpdateAccountWithParams2(Authorization.Provider provider, Map<String, String> parameters,WeiboGetTokenInfoResponse tokenInfoResponse) {
        Authorization auth = accountService.findAuthorization(provider, parameters.get("uid"));
        if (auth == null) {
            Account account = new Account();
            account.setStatus(AccountStatus.FORBIDDEN);
	    	account.setAccountType("PERSON");
            account.setNickname(tokenInfoResponse.getScreen_name());
            account.setAvatar(tokenInfoResponse.getProfile_image_url());
            if (tokenInfoResponse.getGender().equals("m")) {
				account.setGender(Gender.MALE);
			}else {
				account.setGender(Gender.FEMALE);
			}
            account = accountService.save(account);
            auth = new Authorization();
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
            auth.setProvider(provider);
            auth.setUid(parameters.get("uid"));
            auth.setToken(parameters.get("token"));
            auth = accountService.addAuthorization(account, auth);
        }
        return auth.getAccount();
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
