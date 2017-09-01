package com.iycharge.server.api.security;
/**
 * 绑定三方用户并登入
 */
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.iycharge.server.domain.entity.Authorization;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.AccountStatus;
import com.iycharge.server.domain.service.AccountService;

public class BindTokenGranter extends AbstractTokenGranter{
	    private static final String GRANT_TYPE = "bind";
	    private OneTimePasswordAuthenticator oneTimePasswordAuthenticator = new OneTimePasswordAuthenticator();
	    private final List<String> fields = Arrays.asList("phone","password","code","provider","uid");
	    private AccountService accountService;
	    public BindTokenGranter(AccountService accountService,
	                                     AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
	        this(accountService, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
	    }
	    protected BindTokenGranter(AccountService accountService, AuthorizationServerTokenServices tokenServices,
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

	        Authentication userAuth;

	        try {
	        	if (!oneTimePasswordAuthenticator.verifyCode(parameters.get("phone"), Integer.parseInt(parameters.get("code")), 2)) {
	                throw new InvalidGrantException("Invalid TOTP code");
	            }
	        	Account account = createOrUpdateAccountWithParams(provider, parameters);
	            userAuth = new UsernamePasswordAuthenticationToken(
	                    account.getId(), null, AuthorityUtils.createAuthorityList("ROLE_USER"));
	            ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
	           } catch (InvalidKeyException | NoSuchAlgorithmException | NumberFormatException e) {
	               throw new InvalidGrantException("TOTP code verification failed", e);
	           }

	        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
	        return new OAuth2Authentication(storedOAuth2Request, userAuth);
	    }
	    //绑定手机号
	    private  Account  createOrUpdateAccountWithParams(Authorization.Provider provider, Map<String, String> parameters){
	    	String passwd = DigestUtils.md5DigestAsHex(parameters.get("password").getBytes());
	    	Account account1=accountService.findByPhone(parameters.get("phone"));
	    	Authorization auth = accountService.findAuthorization(provider, parameters.get("uid"));
	    	//判断该手机号用户是否存在
	    	if (account1!=null) {
	    		Account account =auth.getAccount();
	    		long id  =account.getId();
	        	accountService.updateAuthorization(account1, auth);
	    		accountService.del(id);
	        	return account1;
			}
	    	Account account =auth.getAccount();
	    	account.setPhone(parameters.get("phone"));
	    	account.setPassword(passwd);
	    	account.setStatus(AccountStatus.NORMAL);
	    	account.setNickname(account.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
	    	return accountService.save(account);	
	    }
}
