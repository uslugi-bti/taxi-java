package test;

import model.Ride;
import org.junit.jupiter.api.Test;
import pricing.EconomyPricing;
import pricing.ComfortPricing;
import static org.junit.jupiter.api.Assertions.*;

public class RideEquivalenceTest {

    // ========== ТЕСТИ ДЛЯ ВІДСТАНІ (distance) ==========
    
    @Test
    public void testDistance_ValidEquivalenceClass_Positive() {
        // Допустимий клас: distance > 0
        Ride ride = new Ride(10, new EconomyPricing());
        assertEquals(10, ride.getDistance(), "Відстань повинна коректно встановлюватись");
    }

    @Test
    public void testDistance_InvalidEquivalenceClass_Zero() {
        // Недопустимий клас: distance = 0
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ride(0, new EconomyPricing());
        });
        assertEquals("Відстань має бути > 0", exception.getMessage());
    }

    @Test
    public void testDistance_InvalidEquivalenceClass_Negative() {
        // Недопустимий клас: distance < 0
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ride(-10, new EconomyPricing());
        });
        assertEquals("Відстань має бути > 0", exception.getMessage());
    }

    // ========== ТЕСТИ ДЛЯ СТРАТЕГІЇ ЦІНОУТВОРЕННЯ ==========

    @Test
    public void testPricingStrategy_ValidEconomy() {
        // Допустимий клас: EconomyPricing
        Ride ride = new Ride(10, new EconomyPricing());
        assertNotNull(ride.getPricingStrategy());
        assertEquals(50.0, ride.calculatePrice(), 0.01);
    }

    @Test
    public void testPricingStrategy_ValidComfort() {
        // Допустимий клас: ComfortPricing
        Ride ride = new Ride(10, new ComfortPricing());
        assertNotNull(ride.getPricingStrategy());
        assertEquals(100.0, ride.calculatePrice(), 0.01);
    }

    @Test
    public void testPricingStrategy_Invalid_Null() {
        // Недопустимий клас: null
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ride(10, null);
        });
        assertEquals("Стратегія ціноутворення не може бути null", exception.getMessage());
    }

    // ========== ТЕСТИ ДЛЯ ЦІНИ (різні класи еквівалентності) ==========

    @Test
    public void testPrice_ShortEconomy() {
        // Клас: коротка відстань (≤50 км) + Economy
        Ride ride = new Ride(10, new EconomyPricing());
        assertEquals(50.0, ride.calculatePrice(), 0.01);
    }

    @Test
    public void testPrice_LongEconomy() {
        // Клас: довга відстань (>50 км) + Economy
        Ride ride = new Ride(60, new EconomyPricing());
        assertEquals(300.0, ride.calculatePrice(), 0.01);
    }

    @Test
    public void testPrice_ShortComfort() {
        // Клас: коротка відстань (≤50 км) + Comfort
        Ride ride = new Ride(10, new ComfortPricing());
        assertEquals(100.0, ride.calculatePrice(), 0.01);
    }

    @Test
    public void testPrice_LongComfort() {
        // Клас: довга відстань (>50 км) + Comfort
        Ride ride = new Ride(60, new ComfortPricing());
        assertEquals(600.0, ride.calculatePrice(), 0.01);
    }
}