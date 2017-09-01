package com.iycharge.server.api.security;

import java.security.InvalidKeyException;
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

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.AccountStatus;
import com.iycharge.server.domain.entity.utils.Status;
import com.iycharge.server.domain.service.AccountService;

public class PasswordTokenGranter extends AbstractTokenGranter {

	private static final String GRANT_TYPE = "password";

	private AccountService accountService;

	public PasswordTokenGranter(AccountService accountService, AuthorizationServerTokenServices tokenServices,
			ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
		this(accountService, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
	}

	protected PasswordTokenGranter(AccountService accountService, AuthorizationServerTokenServices tokenServices,
			ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
		super(tokenServices, clientDetailsService, requestFactory, grantType);
		this.accountService = accountService;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

		Map<String, String> parameters = tokenRequest.getRequestParameters();
		String phone = parameters.get("phone");
		String password = parameters.get("password");
		String devicetoken = parameters.get("device_token");

		if (phone == null || !StringUtils.hasText(phone)) {
			throw new InvalidRequestException(Status.MISSING_PHONE.getErrorDescr());
		}

		if (password == null || !StringUtils.hasText(password)) {
			throw new InvalidRequestException(Status.MISSING_PASS.getErrorDescr());
		}
		if (devicetoken == null || !StringUtils.hasText(devicetoken)) {
			throw new InvalidRequestException(Status.SERVER_ERROR.getErrorDescr());
		}

		Authentication userAuth;

		try {
			Account account = createOrUpdateAccountWithPhone(phone);
			if (account == null) {
				throw new NullPointerException(Status.USER_NOT_EXIST.getErrorDescr());
				
			} else if (account.getStatus() == AccountStatus.FORBIDDEN ) {
			    throw new InvalidGrantException(Status.USER_INVALID.getErrorDescr());
			}
			
			if (!verifyPwd(phone, password)) {
				throw new InvalidGrantException(Status.LOGIN_FAILD.getErrorDescr());
				
			}
			account.setDeviceToken(devicetoken);
			accountService.save(account);
			userAuth = new UsernamePasswordAuthenticationToken(account.getId(), null,
					AuthorityUtils.createAuthorityList("ROLE_USER"));
			((AbstractAuthenticationToken) userAuth).setDetails(parameters);

		}  catch ( InvalidKeyException  e) {
            throw new InvalidGrantException(Status.SERVER_ERROR.getErrorDescr(), e);
        
		}

		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
		return new OAuth2Authentication(storedOAuth2Request, userAuth);
	}

	private Account createOrUpdateAccountWithPhone(String phone) 
			throws NullPointerException{
		Account account = accountService.findByPhone(phone);
		return account;
	}

	// 获得数据库密码并进行对比
	private boolean verifyPwd(String phone, String password) throws InvalidKeyException{
		Account account = accountService.findByPhone(phone);
		// 对原密码进加密对比
		String Md5Pwd = DigestUtils.md5DigestAsHex(password.getBytes());;
		String pwd = account.getPassword();
		if (pwd.equals(Md5Pwd)) {
			return true;
		}
		return false;
	}

}
