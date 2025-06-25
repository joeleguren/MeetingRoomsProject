package model;

import utils.DAOConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationDAO {

    public String addReservation(Reservation reservation) throws SQLException {
        String reservationId = null;
        String sqlAddReservation = "INSERT INTO reservations (reservationId, roomId, dni, reservationDate, startTime, endTime) VALUES (?, ?, ?, ?, ?, ?)";

        // Aixo es gestionara desde el controller, no desde el DAO
        // if (!isReservationValid(reservation)) return null;

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

    private boolean isReservationValid(Reservation reservation) {

        boolean isValid = false;

        if (reservation != null && Employee) {

        }

        return isValid;
    }

}
