package controller;

import model.Reservation;
import model.ReservationDAO;

import java.sql.SQLException;
import java.util.UUID;

public class MeetingRoomManager {
    private ReservationDAO reservationDAO;
    // private EmployeeDAO employeeDAO;
    // private RoomDAO roomDAO;

    public MeetingRoomManager() {
        this.reservationDAO = new ReservationDAO();
        // this.employeeDAO = new EmployeeDAO();
        // this.roomDAO = new RoomDAO();
    }

    public String addReservation(Reservation reservation) throws SQLException {

        reservation.setReservationId(generateReservationID());

        // Comprobamos si la reserva es valida
        // if (EmployeeDAO.getEmployeeByDNI(reservation.getDni()) == null) {
        //      return null;
        // }

        // Comprobmos si la sala existe y si se puede reservar
        // if (roomDAO.getRoomById(roomId) == null || reservationDAO.canReservate(roomId, reservationDate, startTime, endTime)) {
        //     return null;
        // }
        return reservationDAO.addReservation(reservation);
    }

    public boolean cancelReservation(String reservationId) throws SQLException {
        return reservationDAO.cancelReservation(reservationId);
    }

    private String generateReservationID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
