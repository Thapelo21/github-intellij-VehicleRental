package com.example.VehicleRentalSystem;

public class Vehicle {
    private int vehicleId;
    private String brand;
    private String model;
    private String category;
    private double rentalPricePerDay;
    private String availabilityStatus;

    public Vehicle(int vehicleId, String brand, String model, String category, double rentalPricePerDay, String availabilityStatus) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.rentalPricePerDay = rentalPricePerDay;
        this.availabilityStatus = availabilityStatus;
    }

    // Getters
    public int getVehicleId() { return vehicleId; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public String getCategory() { return category; }
    public double getRentalPricePerDay() { return rentalPricePerDay; }
    public String getAvailabilityStatus() { return availabilityStatus; }

    // Setters
    public void setBrand(String brand) { this.brand = brand; }
    public void setModel(String model) { this.model = model; }
    public void setCategory(String category) { this.category = category; }
    public void setRentalPricePerDay(double rentalPricePerDay) { this.rentalPricePerDay = rentalPricePerDay; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }
}
