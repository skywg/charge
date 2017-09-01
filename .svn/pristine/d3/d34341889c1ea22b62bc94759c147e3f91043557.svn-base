package com.iycharge.server.domain.entity.utils;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity.Summary;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

/**
 * A value object abstraction of an email address.
 */
@Embeddable
public class EmailAddress {

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);
    
    @JsonView(Summary.class)
    @Column(name = "email")
    private String value;

    public EmailAddress(String emailAddress) {
        Assert.isTrue(isValid(emailAddress), "Invalid email address!");
        this.value = emailAddress;
    }

    protected EmailAddress() {
    }

    public static boolean isValid(String candidate) {
        return candidate != null && PATTERN.matcher(candidate).matches();
    }

    @Override
    public String toString() {
        return value;
    }
}
