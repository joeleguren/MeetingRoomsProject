package controller;

import model.*;

import java.sql.SQLException;
import java.util.UUID;

public class MeetingRoomManager {
    private ReservationDAO reservationDAO;
    private RoomDAO roomDAO;
    private EmployeeDAO employeeDAO;


    public MeetingRoomManager() {
        this.reservationDAO = new ReservationDAO();
        this.roomDAO = new RoomDAO();
        this.employeeDAO = new EmployeeDAO();

    }

    /**
     * Modificar los datos de un nuevo empleado existente.
     * @param employee El empleado a modificar.
     * @return true si se ha modificado correctamente, false si no se pudo modificar (por ejemplo, si el empleado no existe o los datos no son correctos).
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean modifyEmployee(Employee employee) throws SQLException {
        // Validar los datos del empleado
        if (employee.getDni().isBlank() || employee.getName().isBlank() || employee.getEmail().isBlank() || !employee.getEmail().matches(EmployeeDAO.EMAIL_REGEX)) {
            return false; // Empleado no válido
        }
        return employeeDAO.updateEmployee(employee);
    }

    /**
     * Modifica una sala existente.
     * @param room La sala con los nuevos datos y con el id de sala que se quiere modificar.
     * @return true si se ha modificado correctamente, false si no se pudo modificar (por ejemplo, si la sala no existe o los datos no son correctos).
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean modifyRoom(Room room) throws SQLException {
        if (room.getRoomId() <= 0 && room.getCapacity() <= 0 || room.getName().isBlank()) {
            return false; // Sala no válida
        }
        // Actualizar la sala en la base de datos
        return roomDAO.updateRoom(room);
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


    private String generateReservationID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
