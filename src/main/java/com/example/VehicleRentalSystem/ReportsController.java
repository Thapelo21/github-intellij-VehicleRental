package com.example.VehicleRentalSystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class ReportsController {

    @FXML private Button backBtn;

    private void openScene(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/VehicleRentalSystem/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CHARTS
    @FXML private void openRevenueChart() {
        openScene("revenue_chart.fxml", "Revenue Chart");
    }

    @FXML private void openVehiclePieChart() {
        openScene("vehicle_pie_chart.fxml", "Vehicle Availability Chart");
    }

    @FXML private void openBookingStatusChart() {
        openScene("booking_status_chart.fxml", "Booking Status Chart");
    }

    @FXML private void openTopBookedVehiclesChart() {
        openScene("top_booked_vehicles.fxml", "Top Booked Vehicles");
    }

    // TEXT REPORTS
    @FXML private void openAvailableVehiclesReport() {
        openScene("available_vehicles_report.fxml", "Available Vehicles Report");
    }

    @FXML private void openRentalHistoryReport() {
        openScene("rental_history_report.fxml", "Rental History Report");
    }

    @FXML
    private void goBackToDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/VehicleRentalSystem/admin_dashboard.fxml"));
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
