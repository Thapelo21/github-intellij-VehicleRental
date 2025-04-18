package com.example.VehicleRentalSystem;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RevenueChartController {

    @FXML private BarChart<String, Number> revenueChart;

    @FXML
    public void initialize() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Revenue");

        series.getData().add(new XYChart.Data<>("Jan", 4200));
        series.getData().add(new XYChart.Data<>("Feb", 6100));
        series.getData().add(new XYChart.Data<>("Mar", 5800));
        series.getData().add(new XYChart.Data<>("Apr", 6900));

        revenueChart.getData().add(series);
    }

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/VehicleRentalSystem/view_reports.fxml"));
            Stage stage = (Stage) revenueChart.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
