package com.itc.trn.mis.model;

import java.util.Date;

public class TransactionInfo {
	
	private String transStatus;		
	private Date transTime;
	
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	public Date getTransTime() {
		return transTime;
	}
	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}				

}
