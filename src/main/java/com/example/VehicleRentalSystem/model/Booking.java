package com.example.VehicleRentalSystem.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Booking {

    private final IntegerProperty bookingId;
    private final IntegerProperty customerId;
    private final IntegerProperty vehicleId;
    private final ObjectProperty<LocalDate> rentalStart;
    private final ObjectProperty<LocalDate> rentalEnd;
    private final StringProperty bookingStatus;

    public Booking(int bookingId, int customerId, int vehicleId, LocalDate rentalStart, LocalDate rentalEnd, String bookingStatus) {
        this.bookingId = new SimpleIntegerProperty(bookingId);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.vehicleId = new SimpleIntegerProperty(vehicleId);
        this.rentalStart = new SimpleObjectProperty<>(rentalStart);
        this.rentalEnd = new SimpleObjectProperty<>(rentalEnd);
        this.bookingStatus = new SimpleStringProperty(bookingStatus);
    }

    // Property getters
    public IntegerProperty bookingIdProperty() { return bookingId; }
    public IntegerProperty customerIdProperty() { return customerId; }
    public IntegerProperty vehicleIdProperty() { return vehicleId; }
    public ObjectProperty<LocalDate> rentalStartProperty() { return rentalStart; }
    public ObjectProperty<LocalDate> rentalEndProperty() { return rentalEnd; }
    public StringProperty bookingStatusProperty() { return bookingStatus; }

    // Normal getters
    public int getBookingId() { return bookingId.get(); }
    public int getCustomerId() { return customerId.get(); }
    public int getVehicleId() { return vehicleId.get(); }
    public LocalDate getRentalStart() { return rentalStart.get(); }
    public LocalDate getRentalEnd() { return rentalEnd.get(); }
    public String getBookingStatus() { return bookingStatus.get(); }

    // Setters
    public void setBookingId(int id) { bookingId.set(id); }
    public void setCustomerId(int id) { customerId.set(id); }
    public void setVehicleId(int id) { vehicleId.set(id); }
    public void setRentalStart(LocalDate date) { rentalStart.set(date); }
    public void setRentalEnd(LocalDate date) { rentalEnd.set(date); }
    public void setBookingStatus(String status) { bookingStatus.set(status); }
}
