package com.example.VehicleRentalSystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class EmployeeDashboardController {

    @FXML private Button makeBookingBtn;
    @FXML private Button handlePaymentsBtn;
    @FXML private Button viewBookingsBtn;
    @FXML private Button logoutBtn;

    @FXML
    private void initialize() {
        if (makeBookingBtn != null && handlePaymentsBtn != null && logoutBtn != null && viewBookingsBtn != null) {
            makeBookingBtn.setOnAction(event -> switchScene("/com/example/VehicleRentalSystem/ParentRoot/make_bookings.fxml"));
            handlePaymentsBtn.setOnAction(event -> switchScene("/com/example/VehicleRentalSystem/payments.fxml"));
            viewBookingsBtn.setOnAction(event -> switchScene("/com/example/VehicleRentalSystem/ParentRoot/view_bookings.fxml"
            ));
            logoutBtn.setOnAction(event -> switchScene("/com/example/VehicleRentalSystem/login.fxml"));
        } else {
            System.err.println("Error: Buttons are not properly initialized.");
        }
    }

    private void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath)); // ✅ use the parameter
            Parent root = loader.load();

            if (root != null) {
                Stage stage = (Stage) makeBookingBtn.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Vehicle Rental System");
                stage.show();
            } else {
                System.err.println(" Failed to load FXML: " + fxmlPath);
            }

        } catch (IOException e) {
            System.err.println(" Error loading FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }


}
