package com.starchenko.hotelmanagementapi.repository;

import com.starchenko.hotelmanagementapi.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {}