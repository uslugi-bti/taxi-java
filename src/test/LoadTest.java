package test;

import model.Customer;
import model.Ride;
import pricing.EconomyPricing;
import pricing.ComfortPricing;
import service.RideService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadTest {

    @Test
    void testRideServiceLoad() throws InterruptedException {
        // Параметри навантаження
        int numberOfThreads = 100;      // 100 одночасних користувачів
        int ridesPerThread = 1000;      // 1000 поїздок від кожного
        
        // Підготовка
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        RideService service = new RideService();
        Customer customer = new Customer("Тест");
        
        AtomicLong totalRides = new AtomicLong(0);
        
        System.out.println("=== НАВАНТАЖУВАЛЬНЕ ТЕСТУВАННЯ RIDE SERVICE ===");
        System.out.println("Кількість потоків: " + numberOfThreads);
        System.out.println("Поїздок на потік: " + ridesPerThread);
        System.out.println("Загальна кількість: " + (numberOfThreads * ridesPerThread));
        System.out.println("Запуск...\n");
        
        long startTime = System.currentTimeMillis();
        
        // Запуск потоків
        for (int i = 0; i < numberOfThreads; i++) {
            final int threadId = i;
            executor.execute(() -> {
                for (int j = 0; j < ridesPerThread; j++) {
                    // Чергуємо типи поїздок для різноманітності
                    Ride ride;
                    if (j % 2 == 0) {
                        ride = new Ride(10 + (j % 100), new EconomyPricing());
                    } else {
                        ride = new Ride(10 + (j % 100), new ComfortPricing());
                    }
                    
                    // Виконуємо основні операції
                    double price = ride.calculatePrice();
                    String action = service.getRideAction(customer, ride);
                    
                    totalRides.incrementAndGet();
                }
                latch.countDown();
            });
        }
        
        // Очікуємо завершення всіх потоків
        latch.await();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        executor.shutdown();
        
        // Виведення результатів
        System.out.println("=== РЕЗУЛЬТАТИ ===");
        System.out.println("Оброблено поїздок: " + totalRides.get());
        System.out.println("Витрачено часу: " + totalTime + " мс");
        System.out.println("Середній час на одну поїздку: " + (totalTime * 1000.0 / totalRides.get()) + " мкс");
        System.out.println("Продуктивність: " + (totalRides.get() * 1000.0 / totalTime) + " поїздок/сек");
        
        // Перевірка критерію успішності
        assertTrue(totalTime < 2000, "Система працює надто повільно! Час: " + totalTime + " мс");
    }
}