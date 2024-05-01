package com.example.vaiaonde.adapter;

public class TravelModel {
    private String destination;
    // Variáveis para a função calculateTotalGasolineCost
    private String totalGasolineCost;
    private String totalKm;
    private String averageKm;
    private String costPerLiter;
    private String totalVehicles;
    // Variáveis para a função calculateTotalAirfareCost
    private String totalAirfareCost;
    private String costPerPerson;
    private String totalTravelers;
    private String carRental;
    // Variáveis para a função calculateTotalMealsCost
    private String mealsPerDay;
    private String estimatedCost;
    private String tripDuration;
    private String totalMealsCost;
    // Variáveis para a função calculateTotalAccommodationCost
    private String averageCost;
    private String totalNights;
    private String totalRooms;
    private String totalAccommodationCost;


    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public void setTotalGasolineCost(String totalGasolineCost) {
        this.totalGasolineCost = totalGasolineCost;
    }

    public String getTotalGasolineCost() {
        return totalGasolineCost;
    }

    public void setTotalKm(String totalKm) {
        this.totalKm = totalKm;
    }

    public String getTotalKm() {
        return totalKm;
    }

    public void setAverageKm(String averageKm) {
        this.averageKm = averageKm;
    }

    public String getAverageKm() {
        return averageKm;
    }

    public void setCostPerLiter(String costPerLiter) {
        this.costPerLiter = costPerLiter;
    }

    public String getCostPerLiter() {
        return costPerLiter;
    }

    public void setTotalVehicles(String totalVehicles) {
        this.totalVehicles = totalVehicles;
    }

    public String getTotalVehicles() {
        return totalVehicles;
    }

    public void setTotalAirfareCost(String totalAirfareCost) {
        this.totalAirfareCost = totalAirfareCost;
    }

    public String getTotalAirfareCost() {
        return totalAirfareCost;
    }

    public void setCostPerPerson(String costPerPerson) {
        this.costPerPerson = costPerPerson;
    }

    public String getCostPerPerson() {
        return costPerPerson;
    }

    public void setTotalTravelers(String totalTravelers) {
        this.totalTravelers = totalTravelers;
    }

    public String getTotalTravelers() {
        return totalTravelers;
    }

    public void setCarRental(String carRental) {
        this.carRental = carRental;
    }

    public String getCarRental() {
        return carRental;
    }

    public void setMealsPerDay(String mealsPerDay) {
        this.mealsPerDay = mealsPerDay;
    }

    public String getMealsPerDay() {
        return mealsPerDay;
    }

    public void setEstimatedCost(String estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getEstimatedCost() {
        return estimatedCost;
    }

    public void setTripDuration(String tripDuration) {
        this.tripDuration = tripDuration;
    }

    public String getTripDuration() {
        return tripDuration;
    }

    public void setTotalMealsCost(String totalMealsCost) {
        this.totalMealsCost = totalMealsCost;
    }

    public String getTotalMealsCost() {
        return totalMealsCost;
    }

    public void setTotalAccommodationCost(String totalAccommodationCost) {
        this.totalAccommodationCost = totalAccommodationCost;
    }

    public String getTotalAccommodationCost() {
        return totalAccommodationCost;
    }

    // --- Cálculo para o custo total da gasolina ---
    public void calculateTotalGasolineCost() {
        double totalKm = Double.parseDouble(this.totalKm);
        double averageKm = Double.parseDouble(this.averageKm);
        double costPerLiter = Double.parseDouble(this.costPerLiter);
        double totalVehicles = Double.parseDouble(this.totalVehicles);

        double totalGasolineCost = ((totalKm / averageKm) * costPerLiter) / totalVehicles;

        this.totalGasolineCost = String.valueOf(totalGasolineCost);
    }

    // --- Cálculo para o custo total da passagem aérea ---
    public void calculateTotalAirfareCost() {
        double costPerPerson = Double.parseDouble(this.costPerPerson);
        double totalTravelers = Double.parseDouble(this.totalTravelers);
        double carRental = Double.parseDouble(this.carRental);

        double totalAirfareCost = (costPerPerson * totalTravelers) + carRental;

        this.totalAirfareCost = String.valueOf(totalAirfareCost);
    }

    // --- Cálculo para o custo total das refeições ---
    public void calculateTotalMealsCost() {
        double mealsPerDay = Double.parseDouble(this.mealsPerDay);
        double totalTravelers = Double.parseDouble(this.totalTravelers);
        double estimatedCost = Double.parseDouble(this.estimatedCost);
        double tripDuration = Double.parseDouble(this.tripDuration);

        double totalMealsCost = ((mealsPerDay * totalTravelers) * estimatedCost) * tripDuration;

        this.totalMealsCost = String.valueOf(totalMealsCost);
    }

    // --- Cálculo para o custo total da acomodação ---
    public void calculateTotalAccommodationCost() {
        double averageCost = Double.parseDouble(this.averageCost);
        double totalNights = Double.parseDouble(this.totalNights);
        double totalRooms = Double.parseDouble(this.totalRooms);

        double totalAccommodationCost = (averageCost * totalNights) * totalRooms;

        this.totalAccommodationCost = String.valueOf(totalAccommodationCost);
    }

}
