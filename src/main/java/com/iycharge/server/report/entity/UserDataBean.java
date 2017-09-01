package com.iycharge.server.report.entity;

public class UserDataBean {
    
    private String time;
    /**
     * 个人
     */
    private int grnum;
    /**
     * 企业
     */
    private int qynum;
    private int total;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getGrnum() {
		return grnum;
	}
	public void setGrnum(int grnum) {
		this.grnum = grnum;
	}
	public int getQynum() {
		return qynum;
	}
	public void setQynum(int qynum) {
		this.qynum = qynum;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
    
    
}
