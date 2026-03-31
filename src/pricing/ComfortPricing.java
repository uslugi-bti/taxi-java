package pricing;

public class ComfortPricing implements PricingStrategy {

    @Override
    public double calculate(double distance) {
        return distance * 10;
    }
}