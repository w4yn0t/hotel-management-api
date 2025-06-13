package com.starchenko.hotelmanagementapi.repository;

import com.starchenko.hotelmanagementapi.model.Booking;
import com.starchenko.hotelmanagementapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByCustomer(Customer customer);
    List<Booking> findAllByCustomerEmail(String email);
}