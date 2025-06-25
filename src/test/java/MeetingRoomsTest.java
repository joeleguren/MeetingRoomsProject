import controller.MeetingRoomManager;
import model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MeetingRoomsTest {

   MeetingRoomManager mrmanager;

   @BeforeEach
    public void before() {
       mrmanager = new MeetingRoomManager();
   }

//    /**
//     * Añadir una reserva valida
//     */
//   @Test
//    public void testAddReservation() {
//       Reservation reservation = new Reservation("k9j2h5f8m3",
//               5,
//               "45678901D",
//               "2025-11-20");
//
//       String reservationId = mrmanager.addReservation(reservation);
//
//       // Comprobar que se ha añadido correctamente
//      assertThat(rese);
//   }

}