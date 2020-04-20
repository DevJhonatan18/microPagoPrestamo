package com.example.micro_pago.controller;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.micro_pago.domain.Transaction;
import com.example.micro_pago.producer.PagoPrestamoEventProducer;
import com.example.micro_pago.service.ITransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class PaymentEventsController {
	
	private Logger log = LoggerFactory.getLogger(PaymentEventsController.class);
	
	@Autowired
	PagoPrestamoEventProducer pagoEventProducer;
	
	@Autowired
	private ITransactionService transactionService;  
		
	
	@PostMapping("/v1/paymentEvent")
	public ResponseEntity<Transaction> postLibraryEvent(@RequestBody  Transaction paymentEvent) throws JsonProcessingException{
		
		Transaction tranSql = transactionService.save(paymentEvent);
		
		log.info("antes sendDepositEvent_Approach3 ");
		
		pagoEventProducer.sendPaymentEvent_Approach3(paymentEvent);
		
		
		log.info("despues sendDepositEvent_Approach3 ");	
		
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(paymentEvent);
	}
	
	

}
