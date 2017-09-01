package com.iycharge.server.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity.Summary;
import com.iycharge.server.domain.entity.account.Account;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "authorizations")
public class Authorization extends BaseEntity {
    private String uid;
    private String nickname;
    private String gender;
	private String avatar;
    private String token;
    private Integer expiresIn;
    private String refreshToken;
    private String provice;
    private String openid;
    private String city;
    private String country;
    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

    public String getProvice() {
		return provice;
	}

	public void setProvice(String provice) {
		this.provice = provice;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Enumerated(EnumType.STRING)
    @JsonView(Summary.class)
    private Provider provider;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.REFRESH, optional=false)
    private Account account = null;

    public Authorization() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public enum Provider {
        WEIBO("新浪微博"), QQ("腾讯QQ"), DOUBAN("豆瓣"), WECHAT("微信");

        private String title;

        Provider(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return this.title;
        }

        public static List<Provider> asList() {
            return Arrays.asList(WEIBO, QQ, DOUBAN, WECHAT);
        }
    }
}
