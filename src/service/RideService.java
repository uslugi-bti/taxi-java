package service;

import model.Customer;
import model.Ride;

public class RideService {

    public void processRide(Customer customer, Ride ride) {
        double price = ride.calculatePrice();

        System.out.println("Customer: " + customer.getName());
        System.out.println("Distance: " + ride.getDistance());
        System.out.println("Price: " + price);
        System.out.println("-------------------");
    }
}