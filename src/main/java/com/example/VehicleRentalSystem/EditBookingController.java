package com.example.VehicleRentalSystem;

import com.example.VehicleRentalSystem.dao.BookingDAO;
import com.example.VehicleRentalSystem.model.Booking;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EditBookingController {

    private Booking currentBooking;

    @FXML private TextField customerIdField;
    @FXML private TextField vehicleIdField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;

    public void setBookingData(Booking booking) {
        this.currentBooking = booking;

        customerIdField.setText(String.valueOf(booking.getCustomerId()));
        vehicleIdField.setText(String.valueOf(booking.getVehicleId()));
        startDatePicker.setValue(booking.getRentalStart());
        endDatePicker.setValue(booking.getRentalEnd());
    }

    @FXML
    private void handleUpdate() {
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        if (start == null || end == null) {
            showAlert("Validation Error", "Both start and end dates must be selected.");
            return;
        }

        if (end.isBefore(start)) {
            showAlert("Validation Error", "End date cannot be before start date.");
            return;
        }

        currentBooking.setRentalStart(start);
        currentBooking.setRentalEnd(end);

        if (BookingDAO.updateBookings(currentBooking)) {
            showAlert("Success", "Booking updated successfully.");
            goBack();
        } else {
            showAlert("Error", "Failed to update booking.");
        }
    }

    @FXML
    private void handleCancel() {
        goBack();
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/VehicleRentalSystem/ParentRoot/view_bookings.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) customerIdField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("View Bookings");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not load the bookings view.");
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
