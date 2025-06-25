package controller;

import model.ReservationDAO;

public class MeetingRoomManager {
    private ReservationDAO reservationDAO;

    public MeetingRoomManager() {
        this.reservationDAO = new ReservationDAO();
    }
}
