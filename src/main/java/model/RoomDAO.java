package model;

import utils.DAOConstants;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Optional;

public class RoomDAO {

    /**
     * Devuelve un Optional<Room> con la sala de reuniones que tiene el id indicado.
     * @param roomId El id de la sala que se quiere buscar.
     * @return Optional<Room> con la sala de reuniones si existe, o un Optional vacío si no existe.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public Optional<Room> getRoomById(int roomId) throws SQLException {
        Optional<Room> room = Optional.empty();
        String sqlGetRoom = "SELECT * FROM rooms WHERE room_id = ?";

        try (Connection connection = DriverManager.getConnection(DAOConstants.JDBC_URL, DAOConstants.JDBC_USER, DAOConstants.JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlGetRoom)) {

            preparedStatement.setInt(1, roomId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int capacity = rs.getInt("capacity");
                String resources = rs.getString("resources");

                room = Optional.of(new Room(roomId, name, capacity, resources));
            }
        }
        return room;
    }

    /**
     * Actualiza los datos de una sala de reuniones.
     * @param room La sala con los nuevos datos y con el id de sala que se quiere modificar.
     * @return true si se ha modificado correctamente, false si no se pudo modificar (por ejemplo, si la sala no existe o los datos no son correctos).
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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

    /**
     * Devuelve un conjunto de todas las salas de reuniones.
     * @return LinkedHashSet<Room> con todas las salas de reuniones.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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
