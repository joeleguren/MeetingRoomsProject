package model;

import utils.DAOConstants;

import java.sql.*;
import java.time.LocalDate;

public class ReservationDAO {

    /**
     * Añade una reserva a la base de datos.
     * @param reservation La reserva a añadir.
     * @return El ID de la reserva añadida, o null si no se pudo añadir.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public String addReservation(Reservation reservation) throws SQLException {
        String reservationId = null;
        String sqlAddReservation = "INSERT INTO reservations (reservation_id, room_id, dni, reservation_date, start_time, end_time) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DAOConstants.JDBC_URL, DAOConstants.JDBC_USER, DAOConstants.JDBC_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sqlAddReservation)) {

            ps.setString(1, reservation.getReservationId());
            ps.setInt(2, reservation.getRoomId());
            ps.setString(3, reservation.getDni());
            ps.setDate(4, java.sql.Date.valueOf(reservation.getReservationDate()));
            ps.setTime(5, java.sql.Time.valueOf(reservation.getStartTime()));
            ps.setTime(6, java.sql.Time.valueOf(reservation.getEndTime()));

            if (ps.executeUpdate() > 0) {
                reservationId = reservation.getReservationId();
            }
        }
        return reservationId;
    }

    /**
     * Cancela una reserva de la base de datos.
     * @param reservationId El ID de la reserva a cancelar.
     * @return true si se ha cancelado correctamente, false si no se puede cancelar (por ejemplo, si la reserva no existe o es hoy).
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean cancelReservation(String reservationId) throws SQLException {
        boolean isCancelled = false;
        String sqlCancelReservation = "DELETE FROM reservations WHERE reservation_id = ?";

        // Si la reserva no se puede cancelar, porque no existe o porque queda menos de 24 horas devuelve false.
        if (!canCancelReservation(reservationId)) {
            return false;
        }

        try (Connection conn = DriverManager.getConnection(DAOConstants.JDBC_URL, DAOConstants.JDBC_USER, DAOConstants.JDBC_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sqlCancelReservation)) {

            ps.setString(1, reservationId);
            isCancelled = ps.executeUpdate() > 0;
        }
        return isCancelled;
    }


    /**
     * Comprueba si se puede cancelar una reserva.
     * @param reservationId La reserva a comprobar.
     * @return true si se puede cancelar, false si no se puede (por ejemplo la reserva es para hoy).
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private boolean canCancelReservation(String reservationId) throws SQLException {
        String sql = "SELECT reservation_date FROM reservations WHERE reservation_id = ?";

        try (Connection conn = DriverManager.getConnection(DAOConstants.JDBC_URL, DAOConstants.JDBC_USER, DAOConstants.JDBC_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reservationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LocalDate reservationDate = rs.getDate("reservation_date").toLocalDate();
                LocalDate now = LocalDate.now();
                // Solo se puede cancelar si la reserva es para mañana o más adelante
                return reservationDate.isAfter(now);
            }
        }
        return false;
    }

    /**
     * Comprueba si se puede reservar una sala.
     * @param reservation La reserva a comprobar.
     * @return true si se puede reservar, false si no se puede (por ejemplo, si ya hay una reserva en ese horario).
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private boolean canReservate(Reservation reservation) throws SQLException {
        boolean canReserve = true;

        // Comprobar que la fecha de reserva es futura y no es hoy
        if (!reservation.getReservationDate().isAfter(LocalDate.now())) {
            return false;
        }
        // Consulta SQL que comprueba que no hay reservas que se solapen con la nueva reserva
        String sqlCheckReservation = "SELECT * FROM reservations WHERE room_id = ? AND reservation_date = ? AND (start_time < ? AND end_time > ?)";

        try (Connection conn = DriverManager.getConnection(DAOConstants.JDBC_URL, DAOConstants.JDBC_USER, DAOConstants.JDBC_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sqlCheckReservation)) {

            ps.setInt(1, reservation.getRoomId());
            ps.setDate(2, java.sql.Date.valueOf(reservation.getReservationDate()));
            ps.setTime(3, java.sql.Time.valueOf(reservation.getEndTime()));
            ps.setTime(4, java.sql.Time.valueOf(reservation.getStartTime()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) { // Si devuelve algun registro, significa que hay una reserva que se solapa
                canReserve = false; // Como se solapa, no se puede reservar
            }
        }
        return canReserve;
    }
}
