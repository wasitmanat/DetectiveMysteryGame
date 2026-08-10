package com.detective.factory;

import com.detective.model.Room;

public class RoomFactory {
    public static Room createRoom(String name, String description) {
        return new Room(name, description);
    }
}