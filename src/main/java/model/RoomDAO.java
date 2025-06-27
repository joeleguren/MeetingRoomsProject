package model;

import utils.DAOConstants;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Optional;

public class RoomDAO {

    /**
     * Elimina una sala de reuniones de la base de datos.
     * @param roomId El ID de la sala a eliminar.
     * @return true si se ha eliminado correctamente, false si no se pudo eliminar (por ejemplo, si la sala no existe).
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean deleteRoom(int roomId) throws SQLException {
        boolean isDeleted = false;
        String sqlDeleteRoom = "DELETE FROM rooms WHERE room_id = ?";

        try (Connection connection = DriverManager.getConnection(DAOConstants.JDBC_URL, DAOConstants.JDBC_USER, DAOConstants.JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDeleteRoom)) {

            preparedStatement.setInt(1, roomId);
            int rowsAffected = preparedStatement.executeUpdate();
            isDeleted = rowsAffected > 0; // Si se ha afectado alguna fila, la sala se ha eliminado correctamente
        }
        return isDeleted;
    }

    /**
     * Añade una sala de reuniones a la base de datos.
     * @param room La sala de reuniones que se quiere añadir.
     * @return true si se ha añadido correctamente, false si no se pudo añadir (por ejemplo, si la sala ya existe).
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean addRoom(Room room) throws SQLException {
        boolean isAdded = false;
        String sqlAddRoom = "INSERT INTO rooms (room_id, name, capacity, resources) VALUES (?, ?, ?, ?)";

        if (getRoomById(room.getRoomId()).isPresent()) { // Comprueba si la sala ya existe obteniendo la sala por su ID
            return false; // Si la sala ya existe, no se añade
        }

        try(Connection connection = DriverManager.getConnection(DAOConstants.JDBC_URL, DAOConstants.JDBC_USER, DAOConstants.JDBC_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlAddRoom)) {

            preparedStatement.setInt(1, room.getRoomId());
            preparedStatement.setString(2, room.getName());
            preparedStatement.setInt(3, room.getCapacity());
            preparedStatement.setString(4, room.getResources());

            int rowsAffected = preparedStatement.executeUpdate();
            isAdded = rowsAffected > 0; // Si se ha afectado alguna fila, la sala se ha añadido correctamente
        }
        return  isAdded;
    }

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
