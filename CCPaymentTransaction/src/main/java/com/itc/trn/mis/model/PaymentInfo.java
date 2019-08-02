package com.itc.trn.mis.model;

public class PaymentInfo {

	public PaymentInfo() {	
	}
	
	private String cardNo;
	private String merchnatId;
	private String amountSpent;
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getMerchnatId() {
		return merchnatId;
	}
	public void setMerchnatId(String merchnatId) {
		this.merchnatId = merchnatId;
	}
	public String getAmountSpent() {
		return amountSpent;
	}
	public void setAmountSpent(String amountSpent) {
		this.amountSpent = amountSpent;
	}
		
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "PaymentInfo ["+cardNo+", "+merchnatId+", "+amountSpent+"]";		
	}
}
