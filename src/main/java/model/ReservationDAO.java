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

        if (!isReservationValid(reservation)) return null;

        try (Connection conn = DriverManager.getConnection(DAOConstants.JDBC_URL, DAOConstants.JDBC_USER, DAOConstants.JDBC_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sqlAddReservation)) {
            ps.setS

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
