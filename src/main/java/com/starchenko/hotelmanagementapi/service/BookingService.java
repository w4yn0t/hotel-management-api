package com.starchenko.hotelmanagementapi.service;

import com.starchenko.hotelmanagementapi.model.Booking;
import com.starchenko.hotelmanagementapi.model.Customer;
import com.starchenko.hotelmanagementapi.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public List<Booking> getBookingsByCustomer(Customer customer) {
        return bookingRepository.findAllByCustomer(customer);
    }

    public List<Booking> getBookingsByCustomerEmail(String email) {
        return bookingRepository.findAllByCustomerEmail(email);
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, Booking booking) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found");
        }
        booking.setId(id);
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found");
        }
        bookingRepository.deleteById(id);
    }
}
