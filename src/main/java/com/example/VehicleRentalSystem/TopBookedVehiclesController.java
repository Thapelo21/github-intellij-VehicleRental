package com.example.VehicleRentalSystem;

import com.example.VehicleRentalSystem.dao.BookingDAO;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public class TopBookedVehiclesController {

    @FXML private BarChart<String, Number> topVehiclesChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    @FXML
    public void initialize() {
        xAxis.setLabel("Vehicle Model");
        yAxis.setLabel("Number of Bookings");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Top Booked Vehicles");

        Map<String, Integer> topVehicles = BookingDAO.getTopBookedVehicles();
        for (Map.Entry<String, Integer> entry : topVehicles.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        topVehiclesChart.getData().add(series);
    }

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/VehicleRentalSystem/view_reports.fxml"));
            Stage stage = (Stage) topVehiclesChart.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Reports & Charts");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
