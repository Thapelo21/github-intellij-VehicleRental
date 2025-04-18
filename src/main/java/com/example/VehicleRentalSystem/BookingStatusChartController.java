package com.example.VehicleRentalSystem;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BookingStatusChartController {

    @FXML private BarChart<String, Number> statusChart;

    @FXML
    public void initialize() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Active", 10));
        series.getData().add(new XYChart.Data<>("Completed", 6));
        series.getData().add(new XYChart.Data<>("Cancelled", 3));

        statusChart.getData().add(series);
    }

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/VehicleRentalSystem/view_reports.fxml"));
            Stage stage = (Stage) statusChart.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
