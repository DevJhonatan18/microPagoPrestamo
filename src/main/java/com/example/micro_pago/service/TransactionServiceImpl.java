package com.example.micro_pago.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.micro_pago.dao.TransactionDAO;
import com.example.micro_pago.domain.Transaction;


@Service
public class TransactionServiceImpl implements ITransactionService{
 
	 @Autowired
	 private TransactionDAO transactionDAO;

	@Override
	public Transaction save(Transaction transaction) {
		return transactionDAO.save(transaction);
	}

	@Override
	public Optional<Transaction> findById(Integer id) {
		// TODO Auto-generated method stub
		return transactionDAO.findById(id);
	}
	 
	 
	 
	
}
