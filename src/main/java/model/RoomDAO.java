package model;

import utils.DAOConstants;

import java.sql.*;
import java.util.LinkedHashSet;

public class RoomDAO {

    public boolean updateRoom(Room room) throws SQLException {
        boolean isUpdated = false;
        String sqlUpdateRoom = "UPDATE rooms SET name = ?, capacity = ?, resources = ? WHERE room_id = ?";

        try (Connection connection = DriverManager.getConnection(DAOConstants.JDBC_URL, DAOConstants.JDBC_USER, DAOConstants.JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateRoom)) {

            preparedStatement.setString(1, room.getName());
            preparedStatement.setInt(2, room.getCapacity());
            preparedStatement.setString(3, room.getResources());
            preparedStatement.setInt(4, room.getRoomId());

            int rowsAffected = preparedStatement.executeUpdate();
            isUpdated = rowsAffected > 0;
        }

        return isUpdated;
    }


    public LinkedHashSet<Room> getAllRooms() throws SQLException {
        LinkedHashSet<Room> rooms = new LinkedHashSet<>();
        String sqlGetAllRooms = "SELECT * FROM rooms";

        try (Connection connection = DriverManager.getConnection(DAOConstants.JDBC_URL, DAOConstants.JDBC_USER, DAOConstants.JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlGetAllRooms)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int roomId = rs.getInt("room_id");
                String name = rs.getString("name");
                int capacity = rs.getInt("capacity");
                String resources = rs.getString("resources");

                Room room = new Room(roomId, name, capacity, resources);
                rooms.add(room);
            }
        }
        return rooms;
    }

}
