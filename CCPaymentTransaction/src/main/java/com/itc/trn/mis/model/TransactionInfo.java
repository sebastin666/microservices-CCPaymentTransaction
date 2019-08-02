package com.itc.trn.mis.model;

import java.util.Date;

public class TransactionInfo {
	
	private String transId;
	private String transStatus;		
	private Date transTime;
		
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
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
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TransactionInfo["+transId+", "+", "+transStatus+", "+transTime+"]";
	}

}
