package com.iycharge.server.api.response;

public class WeixinGetUserinfoResponse {
	private String nickname;
	private String sex;
	private String headimgurl;
	private String openid ;
	private String city;
	private String provice;
	private String country;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvice() {
		return provice;
	}
	public void setProvice(String provice) {
		this.provice = provice;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	@Override
	public String toString() {
		return "WeixinGetUserinfoResponse [nickname=" + nickname + ", sex=" + sex + ", headimgurl=" + headimgurl
				+ ", openid=" + openid + "]";
	}

	
	
	
}
