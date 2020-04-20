package com.example.micro_pago.producer;

import com.example.micro_pago.domain.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Component
public class PagoPrestamoEventProducer {
	
	String topic = "loan-transaction-events";
	private Logger log = LoggerFactory.getLogger(PagoPrestamoEventProducer.class);
	
	@Autowired
    KafkaTemplate<Integer,String> kafkaTemplate;
	
	@Autowired
    ObjectMapper objectMapper;
	
	public void sendPaymentEvent(Transaction paymentEvent) throws JsonProcessingException {

		Integer key = paymentEvent.getId();
		String value = objectMapper.writeValueAsString(paymentEvent);

	
		
		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.sendDefault(key, value);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
			@Override
			public void onFailure(Throwable ex) {
				handleFailure(key, value, ex);
			}

			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				handleSuccess(key, value, result);
			}
		});

	}
	

	private void handleFailure(Integer key, String value, Throwable ex) {
			log.error("Error Sending the Message and the exception is {}", ex.getMessage());
			try {
				throw ex;
			} catch (Throwable throwable) {
				log.error("Error in OnFailure: {}", throwable.getMessage());
			}
	
	}

	private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
		log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value,
				result.getRecordMetadata().partition());
	}
	
	public SendResult<Integer, String> sendPaymentEventSynchronous(Transaction paymentEvent)
			throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

		Integer key = paymentEvent.getId();
		String value = objectMapper.writeValueAsString(paymentEvent);
		SendResult<Integer, String> sendResult = null;
		try {
			sendResult = kafkaTemplate.sendDefault(key, value).get(1, TimeUnit.SECONDS);
		} catch (ExecutionException | InterruptedException e) {
			log.error("ExecutionException/InterruptedException Sending the Message and the exception is {}",
					e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Exception Sending the Message and the exception is {}", e.getMessage());
			throw e;
		}

		return sendResult;

	}
	
	private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {

		// agregando header
		List<Header> recordHeaders = List.of(new RecordHeader("payment-event-source", "scanner".getBytes()));
		//List<Header> recordHeaders = (List<Header>) (new RecordHeader("deposit-event-source", "scanner".getBytes()));

		// return new ProducerRecord<>(topic, null, key, value, null);
		return new ProducerRecord<>(topic, null, key, value, recordHeaders);
	}
	
	public void sendPaymentEvent_Approach2(Transaction paymentEvent) throws JsonProcessingException {

		Integer key = paymentEvent.getId();
		String value = objectMapper.writeValueAsString(paymentEvent);

		ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, topic);

		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);

		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
			@Override
			public void onFailure(Throwable ex) {
				handleFailure(key, value, ex);
			}

			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				handleSuccess(key, value, result);
			}
		});
	}
	
	public ListenableFuture<SendResult<Integer,String>> sendPaymentEvent_Approach3(Transaction paymentEvent) throws JsonProcessingException {

		Integer key = paymentEvent.getId();
        String value = objectMapper.writeValueAsString(paymentEvent);

        ProducerRecord<Integer,String> producerRecord = buildProducerRecord(key, value, topic);

        ListenableFuture<SendResult<Integer,String>> listenableFuture =  kafkaTemplate.send(producerRecord);

        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }






        });

        return listenableFuture;
    }
	
	

	
	

}
