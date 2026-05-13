package model;

import pricing.PricingStrategy;

public class Ride {
    private double distance;
    private PricingStrategy pricingStrategy;

    public Ride(double distance, PricingStrategy pricingStrategy) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Відстань має бути > 0");
        }
        if (pricingStrategy == null) {
            throw new IllegalArgumentException("Стратегія ціноутворення не може бути null");
        }
        this.distance = distance;
        this.pricingStrategy = pricingStrategy;
    }

    public double calculatePrice() {
        return pricingStrategy.calculate(distance);
    }

    public double getDistance() {
        return distance;
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }
}