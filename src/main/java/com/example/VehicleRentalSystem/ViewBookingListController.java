package com.example.VehicleRentalSystem;

import com.example.VehicleRentalSystem.dao.BookingDAO;
import com.example.VehicleRentalSystem.model.Booking;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ViewBookingListController {

    @FXML private TableView<Booking> bookingsTable;
    @FXML private TableColumn<Booking, Integer> idCol;
    @FXML private TableColumn<Booking, Integer> customerCol;
    @FXML private TableColumn<Booking, Integer> vehicleCol;
    @FXML private TableColumn<Booking, String> startCol;
    @FXML private TableColumn<Booking, String> endCol;
    @FXML private TableColumn<Booking, String> statusCol;

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        vehicleCol.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("rentalStart"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("rentalEnd"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("bookingStatus"));

        loadBookings();
    }

    private void loadBookings() {
        List<Booking> bookings = BookingDAO.getAllBookings();
        bookingsTable.getItems().setAll(bookings);
    }

    @FXML
    private void modifyBooking() {
        Booking selected = bookingsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/VehicleRentalSystem/edit_booking.fxml"));
                Parent root = loader.load();

                EditBookingController controller = loader.getController();
                controller.setBookingData(selected); // Pass selected booking data

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Edit Booking");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("No Selection", "Please select a booking to modify.");
        }
    }

    @FXML
    private void cancelBooking() {
        Booking selected = bookingsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean success = BookingDAO.cancelBooking(selected.getBookingId());
            if (success) {
                showAlert("Success", "Booking cancelled successfully.");
                loadBookings();
            } else {
                showAlert("Error", "Failed to cancel booking.");
            }
        } else {
            showAlert("No Selection", "Please select a booking to cancel.");
        }
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/VehicleRentalSystem/admin_dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) bookingsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Failed to return to dashboard.");
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
