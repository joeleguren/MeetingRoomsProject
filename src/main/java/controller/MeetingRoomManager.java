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

        // if (EmployeeDAO.getEmployeeByDNI(reservation.getDni()) == null) {
        //      return null;
        // }

        // if (roomDAO.getRoomById(roomId) == null || reservationDAO.canReservate(roomId, reservationDate, startTime, endTime)) {
        //     return null;
        // }
        return reservationDAO.addReservation(reservation);
    }

    private String generateReservationID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
