package model;

import utils.DAOConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
