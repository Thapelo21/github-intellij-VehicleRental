package com.example.VehicleRentalSystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;

public class AdminDashboardController {

    @FXML private Button manageVehiclesBtn;
    @FXML private Button manageCustomersBtn;
    @FXML private Button viewReportsBtn;
    @FXML private Button handlePaymentsBtn;
    @FXML private Button logoutBtn;

    @FXML
    private void initialize() {
        manageVehiclesBtn.setOnAction(e -> loadScene(e, "/com/example/VehicleRentalSystem/manage_vehicles.fxml"));
        manageCustomersBtn.setOnAction(e -> loadScene(e, "/com/example/VehicleRentalSystem/manage_customer.fxml"));
        viewReportsBtn.setOnAction(e -> loadScene(e, "/com/example/VehicleRentalSystem/view_reports.fxml"));
        handlePaymentsBtn.setOnAction(e -> loadScene(e, "/com/example/VehicleRentalSystem/view_payments.fxml"));
        logoutBtn.setOnAction(e -> loadScene(e, "/com/example/VehicleRentalSystem/login.fxml"));
    }

    private void loadScene(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getTitleFromPath(String path) {
        String name = path.substring(path.lastIndexOf('/') + 1).replace(".fxml", "");
        return name.replace("_", " ").toUpperCase();
    }
}
