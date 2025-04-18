package com.example.VehicleRentalSystem;

import com.example.VehicleRentalSystem.dao.BookingDAO;
import com.example.VehicleRentalSystem.model.Booking;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.util.List;

public class RentalHistoryReportController {

    @FXML private TableView<Booking> historyTable;
    @FXML private TableColumn<Booking, Integer> bookingIdCol;
    @FXML private TableColumn<Booking, Integer> customerIdCol;
    @FXML private TableColumn<Booking, Integer> vehicleIdCol;
    @FXML private TableColumn<Booking, String> startCol;
    @FXML private TableColumn<Booking, String> endCol;
    @FXML private TableColumn<Booking, String> statusCol;

    @FXML
    public void initialize() {
        bookingIdCol.setCellValueFactory(data -> data.getValue().bookingIdProperty().asObject());
        customerIdCol.setCellValueFactory(data -> data.getValue().customerIdProperty().asObject());
        vehicleIdCol.setCellValueFactory(data -> data.getValue().vehicleIdProperty().asObject());

        startCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRentalStart().toString()));
        endCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRentalEnd().toString()));

        statusCol.setCellValueFactory(data -> data.getValue().bookingStatusProperty());

        List<Booking> history = BookingDAO.getRentalHistory();
        historyTable.setItems(FXCollections.observableArrayList(history));
    }

    @FXML
    private void exportToCSV() {
        try (PrintWriter writer = new PrintWriter("rental_history_report.csv")) {
            writer.println("Booking ID,Customer ID,Vehicle ID,Start Date,End Date,Status");
            for (Booking b : historyTable.getItems()) {
                writer.printf("%d,%d,%d,%s,%s,%s%n",
                        b.getBookingId(), b.getCustomerId(), b.getVehicleId(),
                        b.getRentalStart(), b.getRentalEnd(), b.getBookingStatus());
            }
            showAlert("Success", "Exported to CSV successfully!");
        } catch (Exception e) {
            showAlert("Error", "Failed to export CSV: " + e.getMessage());
        }
    }

    @FXML
    private void exportToPDF() {
        try (PrintWriter writer = new PrintWriter("rental_history_report.pdf")) {
            writer.println("Rental History Report\n");
            for (Booking b : historyTable.getItems()) {
                writer.printf("Booking ID: %d\nCustomer ID: %d\nVehicle ID: %d\nStart: %s\nEnd: %s\nStatus: %s\n\n",
                        b.getBookingId(), b.getCustomerId(), b.getVehicleId(),
                        b.getRentalStart(), b.getRentalEnd(), b.getBookingStatus());
            }
            showAlert("Success", "Exported to PDF successfully!");
        } catch (Exception e) {
            showAlert("Error", "Failed to export PDF: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/VehicleRentalSystem/view_reports.fxml"));
            Stage stage = (Stage) historyTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
