package com.starchenko.hotelmanagementapi.controller;

import com.starchenko.hotelmanagementapi.model.Booking;
import com.starchenko.hotelmanagementapi.model.Customer;
import com.starchenko.hotelmanagementapi.model.Room;
import com.starchenko.hotelmanagementapi.model.User;
import com.starchenko.hotelmanagementapi.repository.UserRepository;
import com.starchenko.hotelmanagementapi.service.BookingService;
import com.starchenko.hotelmanagementapi.service.CustomerService;
import com.starchenko.hotelmanagementapi.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class WebController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String ADMIN_PASSWORD = "admin123";

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        if (ADMIN_PASSWORD.equals(user.getPassword())) {
            user.setRole("ADMIN");
        } else {
            user.setRole("USER");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin() {
        return "redirect:/home";
    }

    @GetMapping("/admin/customers")
    public String viewCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "admin/customers/customers";
    }

    @GetMapping("/admin/customers/add")
    public String addCustomerPage(Model model) {
        model.addAttribute("customer", new Customer());
        return "admin/customers/add-customer";
    }

    @PostMapping("/admin/customers/add")
    public String addCustomer(@ModelAttribute Customer customer) {
        try {
            customerService.createCustomer(customer);
            return "redirect:/admin/customers?success";
        } catch (Exception e) {
            return "redirect:/admin/customers?error";
        }
    }

    @GetMapping("/admin/customers/edit/{id}")
    public String editCustomerPage(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "admin/customers/edit-customer";
    }

    @PostMapping("/admin/customers/edit/{id}")
    public String editCustomer(@PathVariable Long id,
                               @ModelAttribute Customer customer) {
        try {
            customerService.updateCustomer(id, customer);
            return "redirect:/admin/customers?success";
        } catch (Exception e) {
            return "redirect:/admin/customers?error";
        }
    }

    @PostMapping("/admin/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return "redirect:/admin/customers?success";
        } catch (Exception e) {
            return "redirect:/admin/customers?error";
        }
    }

    @GetMapping("/admin/bookings")
    public String viewBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "admin/bookings/bookings";
    }

    @GetMapping("/admin/bookings/details/{id}")
    public String viewBookingDetails(@PathVariable Long id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        return "admin/bookings/booking-details";
    }

    @GetMapping("/admin/bookings/add")
    public String addBookingPage(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("rooms", roomService.getAllRooms());
        return "admin/bookings/add-booking";
    }

    @PostMapping("/admin/bookings/add")
    public String addBooking(@ModelAttribute Booking booking,
                             @RequestParam Long customerId,
                             @RequestParam Long roomId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            Room room = roomService.getRoomById(roomId);
            booking.setCustomer(customer);
            booking.setRoom(room);
            bookingService.createBooking(booking);
            return "redirect:/admin/bookings?success";
        } catch (Exception e) {
            return "redirect:/admin/bookings?error";
        }
    }

    @GetMapping("/admin/bookings/edit/{id}")
    public String editBookingPage(@PathVariable Long id, Model model) {
        model.addAttribute("booking", bookingService.getBookingById(id));
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("rooms", roomService.getAllRooms());
        return "admin/bookings/edit-booking";
    }

    @PostMapping("/admin/bookings/edit/{id}")
    public String editBooking(@PathVariable Long id,
                              @RequestParam Long customerId,
                              @RequestParam Long roomId,
                              @ModelAttribute Booking booking) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            Room room = roomService.getRoomById(roomId);
            booking.setCustomer(customer);
            booking.setRoom(room);
            bookingService.updateBooking(id, booking);
            return "redirect:/admin/bookings?success";
        } catch (Exception e) {
            return "redirect:/admin/bookings?error";
        }
    }

    @PostMapping("/admin/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        try {
            bookingService.deleteBooking(id);
            return "redirect:/admin/bookings?success";
        } catch (Exception e) {
            return "redirect:/admin/bookings?error";
        }
    }

    @GetMapping("/admin/rooms")
    public String viewRooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "admin/rooms/rooms";
    }

    @GetMapping("/admin/rooms/add")
    public String addRoomPage(Model model) {
        model.addAttribute("room", new Room());
        return "admin/rooms/add-room";
    }

    @PostMapping("/admin/rooms/add")
    public String addRoom(@ModelAttribute Room room) {
        try {
            roomService.createRoom(room);
            return "redirect:/admin/rooms?success";
        } catch (Exception e) {
            return "redirect:/admin/rooms?error";
        }
    }

    @GetMapping("/admin/rooms/edit/{id}")
    public String editRoomPage(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.getRoomById(id));
        return "admin/rooms/edit-room";
    }

    @PostMapping("/admin/rooms/edit/{id}")
    public String editRoom(@PathVariable Long id, @ModelAttribute Room room) {
        try {
            roomService.updateRoom(id, room);
            return "redirect:/admin/rooms?success";
        } catch (Exception e) {
            return "redirect:/admin/rooms?error";
        }
    }

    @PostMapping("/admin/rooms/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        try {
            roomService.deleteRoom(id);
            return "redirect:/admin/rooms?success";
        } catch (Exception e) {
            return "redirect:/admin/rooms?error";
        }
    }

    @GetMapping("/customer/book-room")
    public String bookRoomPage(Model model) {
        model.addAttribute("rooms", roomService.getAvailableRooms());
        model.addAttribute("customer", new Customer());
        return "customer/book-room";
    }

    @PostMapping("/customer/book-room")
    public String processCustomerBooking(@ModelAttribute Customer customer,
                                         @RequestParam Long roomId,
                                         @RequestParam LocalDate checkInDate,
                                         @RequestParam LocalDate checkOutDate) {
        try {
            Room room = roomService.getRoomById(roomId);
            customer = customerService.createCustomer(customer);
            Booking booking = new Booking();
            booking.setCustomer(customer);
            booking.setRoom(room);
            booking.setCheckInDate(checkInDate);
            booking.setCheckOutDate(checkOutDate);
            booking.setStatus("Очікується");
            bookingService.createBooking(booking);
            return "redirect:/customer/my-bookings?success";
        } catch (Exception e) {
            return "redirect:/customer/my-bookings?error";
        }
    }

    @GetMapping("/customer/my-bookings")
    public String viewMyBookings(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Booking> bookings = bookingService.getBookingsByCustomerEmail(email);
        model.addAttribute("bookings", bookings);
        return "customer/my-bookings";
    }

    @GetMapping("/customer/my-bookings/details/{id}")
    public String viewCustomerBookingDetails(@PathVariable Long id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        return "customer/booking-details";
    }
}