package com.itc.trn.mis.model;

import java.util.Date;

public class PaymentReceipt {

	private String transId;
	private String receiptNo;		// Receipt Service
	private String cardNo;			// Input Pay load
	private String amountSpent;		// Input Pay load
	private String merchantId;		// Input Pay load
	private String approvalStatus;	// Bank Approval Service
	private String transStatus;		// Payment Gateway Service
	private Date transTime;			// Payment Gateway Service
	 	
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getAmountSpent() {
		return amountSpent;
	}
	public void setAmountSpent(String amountSpent) {
		this.amountSpent = amountSpent;
	}	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
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
		
}
