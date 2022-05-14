package com.asanpardakht.atmemulator.repository;

import com.asanpardakht.atmemulator.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByPan(String pan);
}
