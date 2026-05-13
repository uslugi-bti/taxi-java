package test;

import model.Ride;
import model.Customer;
import pricing.EconomyPricing;
import pricing.ComfortPricing;
import service.RideService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoundaryValueTest {

    private final RideService service = new RideService();
    private final Customer customer = new Customer("Тест");

    // ========== ТЕСТУВАННЯ НИЖНЬОЇ МЕЖІ (0 км) ==========

    @Test
    void testBoundary_BelowZero() {
        // Значення до межі: -0.1 (недопустиме)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ride(-0.1, new EconomyPricing());
        });
        assertEquals("Відстань має бути > 0", exception.getMessage());
    }

    @Test
    void testBoundary_AtZero() {
        // Значення на межі: 0.0 (недопустиме)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ride(0.0, new EconomyPricing());
        });
        assertEquals("Відстань має бути > 0", exception.getMessage());
    }

    @Test
    void testBoundary_AboveZero() {
        // Значення після межі: 0.1 (допустиме)
        Ride ride = new Ride(0.1, new EconomyPricing());
        assertNotNull(ride);
        assertEquals(0.1, ride.getDistance());
    }

    // ========== ТЕСТУВАННЯ МЕЖІ ПОДІЛУ (50 км) ==========

    @Test
    void testBoundary_BelowFifty() {
        // Значення до межі: 49.9 км (коротка відстань)
        Ride ride = new Ride(49.9, new EconomyPricing());
        String action = service.getRideAction(customer, ride);
        assertFalse(action.equals("ЕКОНОМ_РЕКОМЕНДОВАНО") && 
                     action.equals("ПОТРІБНА_ЗНИЖКА"), 
                     "49.9 км має вважатись короткою відстанню");
    }

    @Test
    void testBoundary_AtFifty() {
        // Значення на межі: 50.0 км (коротка відстань, тому що умова >50)
        Ride ride = new Ride(50.0, new EconomyPricing());
        String action = service.getRideAction(customer, ride);
        assertFalse(action.equals("ЕКОНОМ_РЕКОМЕНДОВАНО") && 
                     action.equals("ПОТРІБНА_ЗНИЖКА"),
                     "50.0 км має вважатись короткою відстанню (умова >50)");
    }

    @Test
    void testBoundary_AboveFifty() {
        // Значення після межі: 50.1 км (довга відстань)
        Ride ride = new Ride(50.1, new EconomyPricing());
        String action = service.getRideAction(customer, ride);
        // Для довгої економ-поїздки очікуємо ЕКОНОМ_РЕКОМЕНДОВАНО
        assertEquals("ЕКОНОМ_РЕКОМЕНДОВАНО", action);
    }

    // ========== ДОДАТКОВІ ТЕСТИ ДЛЯ РІЗНИХ СТРАТЕГІЙ НА МЕЖІ 50 КМ ==========

    @Test
    void testBoundary_AtFifty_Comfort() {
        // 50 км на комфорті: ціна = 500 > 300 → Expensive=true, LongDistance=false
        Ride ride = new Ride(50.0, new ComfortPricing());
        String action = service.getRideAction(customer, ride);
        assertEquals("ЗАДОРОГО_ДЛЯ_КОРОТКОЇ_ПОЇЗДКИ", action);
    }

    @Test
    void testBoundary_AtFifty_Economy() {
        // 50 км на економі: ціна = 250 ≤ 300 → Expensive=false, LongDistance=false
        Ride ride = new Ride(50.0, new EconomyPricing());
        String action = service.getRideAction(customer, ride);
        assertEquals("СТАНДАРТНА_ПОЇЗДКА", action);
    }

    @Test
    void testBoundary_AboveFifty_Economy() {
        // 51 км на економі: ціна = 255 ≤ 300 → Expensive=false, LongDistance=true
        Ride ride = new Ride(51.0, new EconomyPricing());
        String action = service.getRideAction(customer, ride);
        assertEquals("ЕКОНОМ_РЕКОМЕНДОВАНО", action);
    }
}