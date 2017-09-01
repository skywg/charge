package com.iycharge.server.api.security;


import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.service.AccountService;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.StringUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class OneTimePasswordTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "otp";
    private OneTimePasswordAuthenticator oneTimePasswordAuthenticator = new OneTimePasswordAuthenticator();

    private AccountService accountService;

    public OneTimePasswordTokenGranter(AccountService accountService,
                                       AuthorizationServerTokenServices tokenServices,
                                       ClientDetailsService clientDetailsService,
                                       OAuth2RequestFactory requestFactory) {
        this(accountService, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    protected OneTimePasswordTokenGranter(AccountService accountService,
                                          AuthorizationServerTokenServices tokenServices,
                                          ClientDetailsService clientDetailsService,
                                          OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.accountService = accountService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = tokenRequest.getRequestParameters();
        String phone = parameters.get("phone");
        String code = parameters.get("code");

        if (phone == null || !StringUtils.hasText(phone)) {
            throw new InvalidRequestException("The mobile phone must be supplied.");
        }

        if (code == null || !StringUtils.hasText(code)) {
            throw new InvalidRequestException("An OTP code must be supplied.");
        }

        Authentication userAuth;

        try {
            if (!oneTimePasswordAuthenticator.verifyCode(phone, Integer.parseInt(code), 2)) {
                throw new InvalidGrantException("Invalid TOTP code");
            }

            Account account = createOrUpdateAccountWithPhone(phone);

            userAuth = new UsernamePasswordAuthenticationToken(
                    account.getId(), null, AuthorityUtils.createAuthorityList("ROLE_USER"));
            ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        }
        catch (InvalidKeyException | NoSuchAlgorithmException | NumberFormatException e) {
            throw new InvalidGrantException("TOTP code verification failed", e);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

    private Account createOrUpdateAccountWithPhone(String phone) {
        Account account = accountService.findByPhone(phone);
        if (account == null) {
            account = new Account();
            account.setPhone(phone);
            return accountService.save(account);
        }
        return account;
    }
}
