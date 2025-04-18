package com.example.VehicleRentalSystem.model;

import javafx.beans.property.*;

public class Vehicle {

    private final IntegerProperty vehicleId;
    private final StringProperty brand;
    private final StringProperty model;
    private final StringProperty category;
    private final DoubleProperty rentalPricePerDay;
    private final StringProperty availability;

    public Vehicle(int vehicleId, String brand, String model, String category, double rentalPricePerDay, String availability) {
        this.vehicleId = new SimpleIntegerProperty(vehicleId);
        this.brand = new SimpleStringProperty(brand);
        this.model = new SimpleStringProperty(model);
        this.category = new SimpleStringProperty(category);
        this.rentalPricePerDay = new SimpleDoubleProperty(rentalPricePerDay);
        this.availability = new SimpleStringProperty(availability);
    }

    // Property Getters
    public IntegerProperty vehicleIdProperty() { return vehicleId; }
    public StringProperty brandProperty() { return brand; }
    public StringProperty modelProperty() { return model; }
    public StringProperty categoryProperty() { return category; }
    public DoubleProperty rentalPricePerDayProperty() { return rentalPricePerDay; }
    public StringProperty availabilityProperty() { return availability; }

    // Standard Getters
    public int getVehicleId() { return vehicleId.get(); }
    public String getBrand() { return brand.get(); }
    public String getModel() { return model.get(); }
    public String getCategory() { return category.get(); }
    public double getRentalPricePerDay() { return rentalPricePerDay.get(); }
    public String getAvailability() { return availability.get(); }

    // Setters
    public void setVehicleId(int id) { this.vehicleId.set(id); }
    public void setBrand(String brand) { this.brand.set(brand); }
    public void setModel(String model) { this.model.set(model); }
    public void setCategory(String category) { this.category.set(category); }
    public void setRentalPricePerDay(double price) { this.rentalPricePerDay.set(price); }
    public void setAvailability(String availability) { this.availability.set(availability); }
}
