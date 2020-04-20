package com.example.micro_pago.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.micro_pago.domain.Transaction;

public interface TransactionDAO extends CrudRepository<Transaction, Integer>{

	
}