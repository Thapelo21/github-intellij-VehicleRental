package com.example.VehicleRentalSystem;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VehiclePieChartController {

    @FXML private PieChart vehiclePieChart;

    @FXML
    public void initialize() {
        vehiclePieChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Available", 15),
                new PieChart.Data("Rented", 8)
        ));
        vehiclePieChart.setTitle("Availability Status");
    }

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/VehicleRentalSystem/view_reports.fxml"));
            Stage stage = (Stage) vehiclePieChart.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
