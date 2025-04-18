package com.example.VehicleRentalSystem;

import com.example.VehicleRentalSystem.dao.BookingDAO;
import com.example.VehicleRentalSystem.model.Booking;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class BookingController {

    @FXML private TextField customerIdField;
    @FXML private TextField vehicleIdField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;

    @FXML
    private void handleBooking() {
        if (customerIdField.getText().isEmpty() || vehicleIdField.getText().isEmpty()) {
            showAlert("Input Error", "Customer ID and Vehicle ID must not be empty.");
            return;
        }

        try {
            int customerId = Integer.parseInt(customerIdField.getText().trim());
            int vehicleId = Integer.parseInt(vehicleIdField.getText().trim());
            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();

            if (start == null || end == null) {
                showAlert("Date Error", "Please select both start and end dates.");
                return;
            }

            if (end.isBefore(start)) {
                showAlert("Date Error", "End date cannot be before start date.");
                return;
            }

            Booking booking = new Booking(0, customerId, vehicleId, start, end, "Booked");

            if (BookingDAO.insertBookings(booking)) {
                showAlert("Success", "Booking created successfully!");
                clearForm();
            } else {
                showAlert("Database Error", "Booking failed. Please try again.");
            }

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Customer ID and Vehicle ID must be numeric.");
        }
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/VehicleRentalSystem/employee_dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) customerIdField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Employee Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        customerIdField.clear();
        vehicleIdField.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
    }
}