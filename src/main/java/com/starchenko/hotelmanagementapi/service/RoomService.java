package com.starchenko.hotelmanagementapi.service;

import com.starchenko.hotelmanagementapi.model.Room;
import com.starchenko.hotelmanagementapi.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByIsAvailableTrue();
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public Room updateRoom(Long id, Room room) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        room.setId(id);
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        roomRepository.deleteById(id);
    }
}
