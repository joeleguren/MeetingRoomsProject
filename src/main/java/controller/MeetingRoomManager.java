package controller;

import model.*;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.TreeSet;
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

    public Optional<Employee> getEmployeeByDni(String dni) throws SQLException {
        // Obtener un empleado por su DNI
        return employeeDAO.getEmployeeByDni(dni);
    }

    public Optional<Room> getRoomById(int roomId) throws SQLException {
        // Obtener una sala de reuniones por su ID
        return roomDAO.getRoomById(roomId);
    }

    /**
     * Elimina un empleado de la base de datos.
     * @param dni DNI del empleado que se quiere eliminar.
     * @return true si se ha eliminado correctamente, false si no se pudo eliminar (por ejemplo, si el empleado tiene reservas activas).
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean deleteEmployee(String dni) throws SQLException {
        // Comprobamos si el empleado tiene alguna reserva asociada
        if (reservationDAO.employeeHasReservations(dni)) {
            return false;
        }
        // Eliminar el empleado de la base de datos
        return employeeDAO.deleteEmployee(dni);
    }

    /**
     * Añade un nuevo empleado a la base de datos.
     * @param employee El empleado que se quiere añadir.
     * @return true si se ha añadido correctamente, false si no se pudo añadir (por ejemplo, si los datos no son correctos).
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean addEmployee(Employee employee) throws SQLException {
        // Validar los datos del empleado
        if (!isValidEmployee(employee)) {
            return false; // Empleado no válido
        }
        // Añadir el empleado a la base de datos una vez validado
        return employeeDAO.addEmployee(employee);
    }

    /**
     * Elimina una sala de reuniones de la base de datos.
     * @param roomId El ID de la sala a eliminar.
     * @return true si se ha eliminado correctamente, false si no se pudo eliminar (por ejemplo, si la sala tiene reservas).
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean deleteRoom(int roomId) throws SQLException {
        if (reservationDAO.roomHasReservations(roomId)) {
            return false; // No se puede eliminar la sala si tiene reservas
        }
        // Eliminar la sala de reuniones de la base de datos
        return roomDAO.deleteRoom(roomId);
    }

    /**
     * Añade una nueva sala de reuniones a la base de datos.
     * @param room La sala de reuniones que se quiere añadir.
     * @return true si se ha añadido correctamente, false si no se pudo añadir (por ejemplo, si los datos no son correctos o la sala ya existe).
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean addRoom(Room room) throws SQLException {
        // Validar los datos de la sala
        if (!isValidRoom(room)) {
            return false; // Sala no válida
        }
        // Añadir la sala a la base de datos una vez validada
        return roomDAO.addRoom(room);
    }

    /**
     * Devuelve un TreeSet con todos los empleados de la base de datos, ordenados por departamento y luego por nombre.
     * @return Un TreeSet de empleados.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public TreeSet<Employee> getAllEmployees() throws SQLException {
        return employeeDAO.getAllEmployees();
    }

    /**
     * Obtiene todas las salas de reuniones.
     * @return Un conjunto de salas.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public LinkedHashSet<Room> getAllRooms() throws SQLException {
        return roomDAO.getAllRooms();
    }

    /**
     * Modificar los datos de un nuevo empleado existente.
     * @param employee El empleado a modificar.
     * @return true si se ha modificado correctamente, false si no se pudo modificar (por ejemplo, si el empleado no existe o los datos no son correctos).
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean modifyEmployee(Employee employee) throws SQLException {
        // Validar los datos del empleado
        if (!isValidEmployee(employee)) {
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
        if (!isValidRoom(room)) {
            return false; // Sala no válida
        }
        // Actualizar la sala en la base de datos una vez validada
        return roomDAO.updateRoom(room);
    }

    /**
     * Comprueba si una sala es válida.
     * @param room sala a comprobar.
     * @return true si la sala es válida, false en caso contrario.
     */
    private static boolean isValidRoom(Room room) {
        return room != null && room.getRoomId() > 0 // Comprobamos que el ID de la sala es positivo
                && room.getCapacity() > 0 // Comprobamos que la capacidad de la sala es positiva
                && !room.getName().isBlank(); // Comprobamos que el nombre de la sala no esté vacío
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

    /**
     * Añade una nueva reserva.
     * @param reservation La reserva a añadir.
     * @return Un Optional que contiene el ID de la reserva si se ha añadido correctamente, o un Optional vacío si no se puede reservar.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public Optional<String> addReservation(Reservation reservation) throws SQLException {
        reservation.setReservationId(generateReservationID()); // Generar un ID único para la reserva

        if (!canBeReserved(reservation)) {
            return Optional.empty(); // La reserva no se puede realizar
        }
        return reservationDAO.addReservation(reservation);
    }


    /**
     * Mira si un empleado es válido para ser añadido a la base de datos.
     * @param employee El empleado a comprobar.
     * @return true si el empleado es válido, false en caso contrario.
     */
    private boolean isValidEmployee(Employee employee) {
        // Comprobamos que el empleado no sea nulo y que sus campos no estén vacíos, además de que el email sea válido cumpliendo el Regex.
        return employee != null && !employee.getDni().isBlank() && !employee.getName().isBlank()
                && !employee.getEmail().isBlank() && employee.getEmail().matches(EmployeeDAO.EMAIL_REGEX);
    }

    /**
     * Devuelve true si la reserva puede ser realizada, es decir, si el empleado existe, la sala existe y se puede reservar.
     * @param reservation La reserva a comprobar.
     * @return true si la reserva puede ser realizada, false en caso contrario.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private boolean canBeReserved(Reservation reservation) throws SQLException {
        return employeeDAO.getEmployeeByDni(reservation.getDni()).isPresent() // Comprobamos que el empleado existe
                && roomDAO.getRoomById(reservation.getRoomId()).isPresent() // Comprobamos que la sala existe
                && reservationDAO.canReservate(reservation); // Comprobamos que la reserva se puede realizar
    }

    /**
     * Genera un ID único para una reserva.
     * @return Un String que representa el ID de la reserva.
     */
    private String generateReservationID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
