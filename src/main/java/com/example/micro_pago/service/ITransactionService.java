package com.example.micro_pago.service;

import java.util.Optional;

import com.example.micro_pago.domain.Transaction;



public interface ITransactionService {
	
	public Transaction save ( Transaction transaction);

	public Optional<Transaction> findById(Integer id);

}
