package com.starchenko.hotelmanagementapi.repository;

import com.starchenko.hotelmanagementapi.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByIsAvailableTrue();

}