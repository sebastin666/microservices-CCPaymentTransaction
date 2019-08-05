package com.itc.trn.mis.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.itc.trn.mis.model.PaymentInfo;
import com.itc.trn.mis.model.PaymentReceipt;
import com.itc.trn.mis.model.TransactionInfo;

@RestController
@RequestMapping("/credittransaction")
public class PaymentTransactionController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentTransactionController.class);

	@Autowired
	RestTemplate restTemplate;
	
	@Qualifier("restTemplate_TO")
	@Autowired
	RestTemplate restTemplate_TO;

	@Autowired
	WebClient.Builder webClientBuilder;

	@PostMapping(value = "/paymerchant", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> payMerchant(@RequestBody PaymentInfo paymentInfo) {
		try {
			logger.info("Payload::" + paymentInfo);			
			//
			PaymentReceipt paymentReceipt = callByRestTemplate(paymentInfo);
//			PaymentReceipt paymentReceipt = callByWebClient(paymentInfo);
			// Client side time out call
//			PaymentReceipt paymentReceipt = callByRestTemplate_TO(paymentInfo);
			//
			return new ResponseEntity<PaymentReceipt>(paymentReceipt, HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error while Merchant Payment: ", ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	private PaymentReceipt callByRestTemplate(PaymentInfo paymentInfo) {
		logger.info("************ callByRestTemplate *************");
		logger.info("paymentInfo.getCardNo===>::" + paymentInfo.getCardNo());
		String approvalStatus = restTemplate.getForObject(
				"http://CC-TransactionBankApproval/cardverification/transactionapproval/" + paymentInfo.getCardNo(),
				String.class);
		logger.info("Entry-approvalStatus::" + approvalStatus);
		//
		TransactionInfo transactionInfo = restTemplate.postForObject(
				"http://CC-PaymentGateway/paymentgateway/moneytransfer", paymentInfo, TransactionInfo.class);
		logger.info("TransactionInfo==>" + transactionInfo);
		//
		PaymentReceipt paymentReceipt = restTemplate.postForObject("http://CC-PaymentReceipt/receipt/transreceipt",
				paymentInfo, PaymentReceipt.class);
		//
		paymentReceipt.setApprovalStatus(approvalStatus); // Update From Bank Approval Service
		paymentReceipt.setTransId(transactionInfo.getTransId()); // Update From Payment Gateway Service
		paymentReceipt.setTransStatus(transactionInfo.getTransStatus()); // Update From Payment Gateway Service
		paymentReceipt.setTransTime(transactionInfo.getTransTime()); // Update From Payment Gateway Service
		//
		return paymentReceipt;
	}

	private PaymentReceipt callByWebClient(PaymentInfo paymentInfo) {
		logger.info("************ callByWebClient *************");
		//
		String approvalStatus = webClientBuilder.build().get()
				.uri("http://CC-TransactionBankApproval/cardverification/transactionapproval/" + paymentInfo.getCardNo())
				.retrieve()
				.bodyToMono(String.class)
				.block();
		logger.info("Entry-approvalStatus::" + approvalStatus);
		//
		TransactionInfo transactionInfo  = webClientBuilder.build().post()
				.uri("http://CC-PaymentGateway/paymentgateway/moneytransfer")
				.body(BodyInserters.fromObject(paymentInfo))
				.retrieve()
				.bodyToMono(TransactionInfo.class)
				.block();
		logger.info("TransactionInfo==>" + transactionInfo);
		//
		PaymentReceipt paymentReceipt = webClientBuilder.build().post()
				.uri("http://CC-PaymentReceipt/receipt/transreceipt")
				.body(BodyInserters.fromObject(paymentInfo))
				.retrieve()
				.bodyToMono(PaymentReceipt.class)
				.block();
		//
		paymentReceipt.setApprovalStatus(approvalStatus); // Update From Bank Approval Service
		paymentReceipt.setTransId(transactionInfo.getTransId()); // Update From Payment Gateway Service
		paymentReceipt.setTransStatus(transactionInfo.getTransStatus()); // Update From Payment Gateway Service
		paymentReceipt.setTransTime(transactionInfo.getTransTime()); // Update From Payment Gateway Service
		//
		return paymentReceipt;
	}
	
	private PaymentReceipt callByRestTemplate_TO(PaymentInfo paymentInfo) {
		logger.info("************ callByRestTemplate_TO *************");
		logger.info("Time Before Call===>::" + new Date());
		String approvalStatus = "REJ";
		try {
		 approvalStatus = restTemplate_TO.getForObject(
				"http://CC-TransactionBankApproval/cardverification/transactionapproval/" + paymentInfo.getCardNo(),
				String.class);
		}catch(Exception ex) {
			logger.info("Time out on the conection");
		}
		logger.info("Time After Call===>::" + new Date());
		logger.info("Entry-approvalStatus::" + approvalStatus);
		//
		TransactionInfo transactionInfo = restTemplate_TO.postForObject(
				"http://CC-PaymentGateway/paymentgateway/moneytransfer", paymentInfo, TransactionInfo.class);
		logger.info("TransactionInfo==>" + transactionInfo);
		//
		PaymentReceipt paymentReceipt = restTemplate_TO.postForObject("http://CC-PaymentReceipt/receipt/transreceipt",
				paymentInfo, PaymentReceipt.class);
		//
		paymentReceipt.setApprovalStatus(approvalStatus); // Update From Bank Approval Service
		paymentReceipt.setTransId(transactionInfo.getTransId()); // Update From Payment Gateway Service
		paymentReceipt.setTransStatus(transactionInfo.getTransStatus()); // Update From Payment Gateway Service
		paymentReceipt.setTransTime(transactionInfo.getTransTime()); // Update From Payment Gateway Service
		//
		return paymentReceipt;
	}

}
