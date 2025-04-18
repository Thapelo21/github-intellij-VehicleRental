module com.example.VehicleRentalSystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itext;
    requires java.desktop;

    opens com.example.VehicleRentalSystem to javafx.fxml;  // This allows FXMLLoader to access your package
    exports com.example.VehicleRentalSystem;
    exports com.example.VehicleRentalSystem.model;
    opens com.example.VehicleRentalSystem.model to javafx.fxml;
    exports com.example.VehicleRentalSystem.util;
    opens com.example.VehicleRentalSystem.util to javafx.fxml;  // This allows access to your package from other modules
}
