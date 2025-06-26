import controller.MeetingRoomManager;
import model.Reservation;
import model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

public class MeetingRoomsTest {

   MeetingRoomManager mrmanager;

   @BeforeEach
    public void before() {
       mrmanager = new MeetingRoomManager();
   }

    /**
     * Añadir una reserva valida
     */
   @Test
    public void testAddValidReservation() throws SQLException {
       Reservation reservation = new Reservation(5,
               "45678901D",
               Date.valueOf("2025-07-10").toLocalDate(),
               Time.valueOf("14:30:00").toLocalTime(),
               Time.valueOf("16:00:00").toLocalTime());

       // Comprobamos que la reserva se añade correctamente, devuelve el codigo de reserva
       assertNotNull(mrmanager.addReservation(reservation));
   }

    /**
     * Añadir una reserva no valida
     */
    @Test
    public void testAddInvalidReservation() throws SQLException {
        Reservation reservation = new Reservation(19,
                "47586920X",
                Date.valueOf("2024-07-10").toLocalDate(),
                Time.valueOf("10:00:00").toLocalTime(),
                Time.valueOf("10:30:00").toLocalTime());

        // Comprobamos que la reserva falla, devuelve null en vez del codigo de reserva
        assertNull(mrmanager.addReservation(reservation));
    }

    /**
     * Cancelar una reserva valida
     */
    @Test
    public void testCancelValidReservation() throws SQLException {
        Reservation reservation = new Reservation(5,
                "45678901D",
                Date.valueOf("2025-07-10").toLocalDate(),
                Time.valueOf("14:30:00").toLocalTime(),
                Time.valueOf("16:00:00").toLocalTime());

        String reservationId = mrmanager.addReservation(reservation);

        // Cancelamos la reserva y comprobamos que devuelve true, lo que indica que se ha cancelado correctamente
        assertTrue(mrmanager.cancelReservation(reservationId));
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

}