package model;

import java.util.Objects;

public class Room {
    private int roomId;
    private String name;
    private int capacity;
    private String resources;

    public Room(int roomId, String name, int capacity, String resources) {
        this.roomId = roomId;
        this.name = name;
        this.capacity = capacity;
        this.resources = resources;
    }

    public Room(Room room) {
        this.roomId = room.roomId;
        this.name = room.name;
        this.capacity = room.capacity;
        this.resources = room.resources;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Room room)) return false;
        return roomId == room.roomId && capacity == room.capacity && Objects.equals(name, room.name) && Objects.equals(resources, room.resources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, name, capacity, resources);
    }

    @Override
    public String toString() {
        return "Sala " + roomId +
                " [ Nombre: " + name + ". Con capacidad para " +
                capacity + " personas" +
                ". Recursos: '" + resources + '\'' +
                " ]";
    }
}
