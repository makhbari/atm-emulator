package com.asanpardakht.atmemulator.repository;

import com.asanpardakht.atmemulator.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
