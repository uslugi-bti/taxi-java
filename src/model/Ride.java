package model;

import pricing.PricingStrategy;

public class Ride {
    private double distance;
    private PricingStrategy pricingStrategy;

    public Ride(double distance, PricingStrategy pricingStrategy) {
        this.distance = distance;
        this.pricingStrategy = pricingStrategy;
    }

    public double calculatePrice() {
        return pricingStrategy.calculate(distance);
    }

    public double getDistance() {
        return distance;
    }
}