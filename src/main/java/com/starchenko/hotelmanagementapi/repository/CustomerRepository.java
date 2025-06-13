package com.starchenko.hotelmanagementapi.repository;

import com.starchenko.hotelmanagementapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Customer findByEmail(String email);
}