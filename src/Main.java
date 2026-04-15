import model.Customer;
import model.Ride;
import pricing.EconomyPricing;
import pricing.ComfortPricing;
import service.RideService;
import test.ManualTest;

public class Main {
    public static void main(String[] args) {

        /*Customer customer = new Customer("Ivan");

        Ride ride1 = new Ride(10, new EconomyPricing());
        Ride ride2 = new Ride(10, new ComfortPricing());

        RideService service = new RideService();

        service.processRide(customer, ride1);
        service.processRide(customer, ride2);*/

        ManualTest.main(args);
    }
}