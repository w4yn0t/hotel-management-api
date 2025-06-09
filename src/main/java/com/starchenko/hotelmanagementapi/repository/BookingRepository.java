package com.starchenko.hotelmanagementapi.repository;

import com.starchenko.hotelmanagementapi.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {}