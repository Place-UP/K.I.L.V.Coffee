package com.api.vendas.exceptions;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException() {
        super("Reservation not found.");
    }
}
