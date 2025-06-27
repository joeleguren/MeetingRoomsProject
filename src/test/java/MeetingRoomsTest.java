import controller.MeetingRoomManager;
import model.Employee;
import model.Reservation;
import model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeetingRoomsTest {

   MeetingRoomManager mrmanager;

   @BeforeEach
    public void before() {
       mrmanager = new MeetingRoomManager();
   }

    /**
     * Añadir una reserva válida
     */
   @Test
    public void testAddValidReservation() throws SQLException {
       Reservation reservation = new Reservation(5,
               "45678901D",
               Date.valueOf("2025-07-10").toLocalDate(),
               Time.valueOf("14:30:00").toLocalTime(),
               Time.valueOf("16:00:00").toLocalTime());

       // Comprobamos que la reserva se añade correctamente, el Optional debe estar presente
       assertTrue(mrmanager.addReservation(reservation).isPresent());
   }

    /**
     * Añadir una reserva no válida
     */
    @Test
    public void testAddInvalidReservation() throws SQLException {
        Reservation reservation = new Reservation(5,
                "47586920X",
                Date.valueOf("2025-07-10").toLocalDate(),
                Time.valueOf("15:50:00").toLocalTime(), // Aqui solapa con la anterior reserva
                Time.valueOf("17:00:00").toLocalTime());

        // Comprobamos que la reserva falla, el Optional debe estar vacío
        assertTrue(mrmanager.addReservation(reservation).isEmpty());
    }

    /**
     * Cancelar una reserva válida
     */
    @Test
    public void testCancelValidReservation() throws SQLException {
        Reservation reservation = new Reservation(7,
                "22334455L",
                Date.valueOf("2025-08-05").toLocalDate(),
                Time.valueOf("13:15:00").toLocalTime(),
                Time.valueOf("15:45:00").toLocalTime());

        Optional<String> reservationId = mrmanager.addReservation(reservation);

        if (reservationId.isEmpty()) {
            throw new SQLException("No se pudo añadir la reserva para cancelar");
        }

        // Cancelamos la reserva y comprobamos que devuelve true, lo que indica que se ha cancelado correctamente
        assertTrue(mrmanager.cancelReservation(reservationId.get()));
    }

    /**
     * Modificar una habitación de manera no válida
     */
    @Test
    public void testModifyRoom() throws SQLException {
        // Creamos una sala válida
        Room modifiedRoom1 = new Room(1, "Sala Aurora", 0, "Proyector, Pizarra blanca, WiFi");

        Room modifiedRoom2 = new Room(1, "", -2, "Proyector, Pizarra blanca, WiFi");

        // Comprobamos no se puede modificar la sala porque la capacidad es 0
        assertFalse(mrmanager.modifyRoom(modifiedRoom1));
        // Comprobamos no se puede modificar la sala porque el nombre está vacio y la capacidad es negativa
        assertFalse(mrmanager.modifyRoom(modifiedRoom2));
    }

    @Test
    public void testGetAllRooms() throws SQLException {
        // Obtenemos todas las salas
        LinkedHashSet<Room> rooms = mrmanager.getAllRooms();
        rooms.forEach(System.out::println);
        // Comprobamos que se obtienen todas las salas
        assertFalse(rooms.isEmpty());
    }

    @Test
    public void testGetAllEmployees() throws SQLException {
        // Obtenemos todas las salas
        TreeSet<Employee> employees = mrmanager.getAllEmployees();
        employees.forEach(System.out::println);
        // Comprobamos que se obtienen todas las salas
        assertFalse(employees.isEmpty());
    }

    @Test
    public void testDeleteEmployee() throws SQLException {
        // Creamos un empleado para eliminar
        String dni = "99887766Z"; // DNI válido con letra
        Employee newEmployee = new Employee(
                dni,
                "Marc Vilalta Soler",   // Nombre completo
                "mvilalta@empresa.com", // Email
                "IT"                   // Departamento
        );
        mrmanager.addEmployee(newEmployee); // Añadimos el empleado a la base de datos
        assertTrue(mrmanager.deleteEmployee(dni)); // Comprobamos que se elimina correctamente, no tiene reservas asociadas
    }

    @Test
    public void testDeleteValidRoom() throws SQLException {
        Room room = new Room(150, "Sala de Reuniones", 10, "Proyector, Pizarra blanca, WiFi");
        mrmanager.addRoom(room); // Añadimos una sala válida
        // Intentamos eliminar una sala sin reservas
        assertTrue(mrmanager.deleteRoom(150)); // Debería fallar porque tiene reservas
    }

    @Test
    public void testDeleteRoomWithReservations() throws SQLException {
        // Intentamos eliminar una sala con reservas
        assertFalse(mrmanager.deleteRoom(23)); // Debería fallar porque tiene reservas
    }

}