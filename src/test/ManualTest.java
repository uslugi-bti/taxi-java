package test;

import model.Customer;
import model.Ride;
import pricing.EconomyPricing;
import pricing.ComfortPricing;
import service.RideService;

public class ManualTest {
    public static void main(String[] args) {
        RideService service = new RideService();
        Customer customer = new Customer("Тест");

        System.out.println("=== ТЕСТУВАННЯ ЗА ТАБЛИЦЕЮ РІШЕНЬ ===\n");

        // Тест 1: Стандартна коротка економ
        Ride ride1 = new Ride(10, new EconomyPricing());
        String action1 = service.getRideAction(customer, ride1);
        System.out.println("Тест 1 (коротка/економ): " + action1);
        System.out.println("  Очікувано: СТАНДАРТНА_ПОЇЗДКА -> " + (action1.equals("СТАНДАРТНА_ПОЇЗДКА") ? "✅" : "❌"));

        // Тест 2: Стандартна коротка комфорт
        Ride ride2 = new Ride(10, new ComfortPricing());
        String action2 = service.getRideAction(customer, ride2);
        System.out.println("Тест 2 (коротка/комфорт): " + action2);
        System.out.println("  Очікувано: СТАНДАРТНА_ПОЇЗДКА -> " + (action2.equals("СТАНДАРТНА_ПОЇЗДКА") ? "✅" : "❌"));

        // Тест 3: Дорога коротка
        Ride ride3 = new Ride(40, new ComfortPricing()); // 40 * 10 = 400 > 300
        String action3 = service.getRideAction(customer, ride3);
        System.out.println("Тест 3 (дорога/коротка): " + action3);
        System.out.println("  Очікувано: ЗАДОРОГО_ДЛЯ_КОРОТКОЇ_ПОЇЗДКИ -> " + (action3.equals("ЗАДОРОГО_ДЛЯ_КОРОТКОЇ_ПОЇЗДКИ") ? "✅" : "❌"));

        // Тест 4: Довга економ
        Ride ride4 = new Ride(60, new EconomyPricing()); // 60 * 5 = 300 (не >300)
        String action4 = service.getRideAction(customer, ride4);
        System.out.println("Тест 4 (довга/економ): " + action4);
        System.out.println("  Очікувано: ЕКОНОМ_РЕКОМЕНДОВАНО -> " + (action4.equals("ЕКОНОМ_РЕКОМЕНДОВАНО") ? "✅" : "❌"));

        // Тест 5: Довга дорога
        Ride ride5 = new Ride(60, new ComfortPricing()); // 60 * 10 = 600 > 300
        String action5 = service.getRideAction(customer, ride5);
        System.out.println("Тест 5 (довга/дорога): " + action5);
        System.out.println("  Очікувано: ПОТРІБНА_ЗНИЖКА -> " + (action5.equals("ПОТРІБНА_ЗНИЖКА") ? "✅" : "❌"));

        System.out.println("\n=== ТЕСТУВАННЯ ЗАВЕРШЕНО ===");
    }
}