package com.example.VehicleRentalSystem.dao;

import com.example.VehicleRentalSystem.model.Vehicle;
import com.example.VehicleRentalSystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    // Method to fetch a list of available vehicles from the database
    public static List<Vehicle> getAvailableVehicles() {

        List<Vehicle> vehicles = new ArrayList<>();


        String sql = "SELECT * FROM vehicles WHERE availability_status = 1"; // fixed column name

        try (
                // Establishing connection to the database
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            // Iterating through the result set to create Vehicle objects
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("category"),
                        rs.getDouble("rental_price_per_day"),
                        rs.getInt("availability_status") == 1 ? "Available" : "Not Available"
                );

                vehicles.add(vehicle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
}