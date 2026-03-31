package pricing;

public class EconomyPricing implements PricingStrategy {

    @Override
    public double calculate(double distance) {
        return distance * 5;
    }
}