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

        // Comprobamos si el empleado existe
        // if (EmployeeDAO.getEmployeeByDNI(reservation.getDni()) == null) {
        //      return null;
        // }

        // Comprobamos si la sala existe y si se puede reservar
        // if (roomDAO.getRoomById(roomId) == null || reservationDAO.canReservate(roomId, reservationDate, startTime, endTime)) {
        //     return null;
        // }
        return reservationDAO.addReservation(reservation);
    }

    /**
     * Cancela una reserva.
     * @param reservationId El ID de la reserva a cancelar.
     * @return true si se ha cancelado correctamente, false si no se puede cancelar (por ejemplo, si la reserva no existe o es hoy).
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean cancelReservation(String reservationId) throws SQLException {
        return reservationDAO.cancelReservation(reservationId);
    }

    private String generateReservationID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
