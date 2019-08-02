package com.itc.trn.mis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.itc.trn.mis.model.PaymentInfo;
import com.itc.trn.mis.model.PaymentReceipt;
import com.itc.trn.mis.model.TransactionInfo;

@RestController
@RequestMapping("/credittransaction")
public class PaymentTransactionController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentTransactionController.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@PostMapping(value = "/paymerchant", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> payMerchant(@RequestBody PaymentInfo paymentInfo){
		try {
			logger.info("Payload::"+paymentInfo);
			//
			String approvalStatus = restTemplate.getForObject("http://CC-TransactionBankApproval/cardverification/transactionapproval/"+paymentInfo.getCardNo(), String.class);			
			logger.info("Entry-approvalStatus::"+approvalStatus);
			//			
			TransactionInfo transactionInfo = restTemplate.postForObject("http://CC-PaymentGateway/paymentgateway/moneytransfer", paymentInfo, TransactionInfo.class);
			logger.info("TransactionInfo==>"+transactionInfo);
			//
			PaymentReceipt paymentReceipt = restTemplate.postForObject("http://CC-PaymentReceipt/receipt/transreceipt", paymentInfo, PaymentReceipt.class);
			//
			paymentReceipt.setApprovalStatus(approvalStatus); 					// Update From Bank Approval Service
			paymentReceipt.setTransId(transactionInfo.getTransId());			// Update From Payment Gateway Service
			paymentReceipt.setTransStatus(transactionInfo.getTransStatus()); 	// Update From Payment Gateway Service
			paymentReceipt.setTransTime(transactionInfo.getTransTime()); 		// Update From Payment Gateway Service
			//			
			return new ResponseEntity<PaymentReceipt>(paymentReceipt, HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error while Merchant Payment: ", ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
