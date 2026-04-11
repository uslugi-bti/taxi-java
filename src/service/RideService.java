package service;

import model.Customer;
import model.Ride;

public class RideService {

    // Оригінальний метод (залишаємо)
    public void processRide(Customer customer, Ride ride) {
        double price = ride.calculatePrice();
        System.out.println("Customer: " + customer.getName());
        System.out.println("Distance: " + ride.getDistance());
        System.out.println("Price: " + price);
        System.out.println("-------------------");
    }

    // Новий метод для тестування за таблицями рішень
    public String getRideAction(Customer customer, Ride ride) {
        double distance = ride.getDistance();
        double price = ride.calculatePrice();

        boolean isLongDistance = distance > 50;
        boolean isExpensive = price > 300;
        boolean isEconomy = ride.getPricingStrategy().getClass().getSimpleName().equals("EconomyPricing");

        // Дії (Actions)
        if (isLongDistance && isExpensive && !isEconomy) {
            return "ПОТРІБНА_ЗНИЖКА";
        } else if (isLongDistance && isEconomy) {
            return "ЕКОНОМ_РЕКОМЕНДОВАНО";
        } else if (!isLongDistance && isExpensive) {
            return "ЗАДОРОГО_ДЛЯ_КОРОТКОЇ_ПОЇЗДКИ";
        } else if (isExpensive) {
            return "ВИСОКА_ЦІНА";
        } else {
            return "СТАНДАРТНА_ПОЇЗДКА";
        }
    }
}