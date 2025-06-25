package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private String reservationId;
    private int roomId;
    private String dni;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;


    public Reservation(String reservationId, int roomId, String dni, LocalDate reservationDate, LocalTime startTime, LocalTime endTime) {
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.dni = dni;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Reservation(Reservation reservation) {
        this.reservationId = reservation.reservationId;
        this.roomId = reservation.roomId;
        this.dni = reservation.dni;
        this.reservationDate = reservation.reservationDate;
        this.startTime = reservation.startTime;
        this.endTime = reservation.endTime;
    }


    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Reservation that)) return false;
        return roomId == that.roomId && Objects.equals(reservationId, that.reservationId) && Objects.equals(dni, that.dni) && Objects.equals(reservationDate, that.reservationDate) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, roomId, dni, reservationDate, startTime, endTime);
    }
}
