package test;

import org.junit.jupiter.api.Test;
import model.Customer;
import model.Ride;
import pricing.EconomyPricing;
import pricing.ComfortPricing;
import service.RideService;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RideServiceFunctionalDiagramTest {

    private final RideService service = new RideService();
    private final Customer customer = new Customer("Тест");

    // Метод, що імітує логіку з RideService
    public String getRideAction(boolean isLongDistance, boolean isExpensive, boolean isEconomy) {
        if (isLongDistance && isExpensive && !isEconomy) {
            return "ПОТРІБНА_ЗНИЖКА";      // Наслідок 20
        } else if (isLongDistance && !isExpensive && isEconomy) {
            return "ЕКОНОМ_РЕКОМЕНДОВАНО"; // Наслідок 21
        } else if (!isLongDistance && isExpensive && !isEconomy) {
            return "ЗАДОРОГО_ДЛЯ_КОРОТКОЇ_ПОЇЗДКИ"; // Наслідок 22
        } else if (!isLongDistance && isExpensive && isEconomy) {
            return "ВИСОКА_ЦІНА";          // Наслідок 23
        } else {
            return "СТАНДАРТНА_ПОЇЗДКА";   // Наслідок 24
        }
    }

    @Test
    public void testLongDistanceExpensiveComfort() {
        // TC-1: LongDistance=1, Expensive=1, Economy=0
        String result = getRideAction(true, true, false);
        assertEquals("ПОТРІБНА_ЗНИЖКА", result, 
            "TC-1: Довга дорога поїздка комфорт-класу потребує знижки");
    }

    @Test
    public void testLongDistanceNotExpensiveEconomy() {
        // TC-2: LongDistance=1, Expensive=0, Economy=1
        String result = getRideAction(true, false, true);
        assertEquals("ЕКОНОМ_РЕКОМЕНДОВАНО", result, 
            "TC-2: Для довгої відстані рекомендується економ-клас");
    }

    @Test
    public void testShortDistanceExpensiveComfort() {
        // TC-3: LongDistance=0, Expensive=1, Economy=0
        String result = getRideAction(false, true, false);
        assertEquals("ЗАДОРОГО_ДЛЯ_КОРОТКОЇ_ПОЇЗДКИ", result, 
            "TC-3: Коротка дорога поїздка комфорт-класу - занадто дорого");
    }

    @Test
    public void testShortDistanceExpensiveEconomy() {
        // TC-4: LongDistance=0, Expensive=1, Economy=1
        String result = getRideAction(false, true, true);
        assertEquals("ВИСОКА_ЦІНА", result, 
            "TC-4: Висока ціна навіть для економ-класу");
    }

    @Test
    public void testShortDistanceNotExpensiveComfort() {
        // TC-5: LongDistance=0, Expensive=0, Economy=0
        String result = getRideAction(false, false, false);
        assertEquals("СТАНДАРТНА_ПОЇЗДКА", result, 
            "TC-5: Стандартна коротка поїздка комфорт-класу");
    }

    @Test
    public void testLongDistanceNotExpensiveComfort() {
        // TC-6: LongDistance=1, Expensive=0, Economy=0
        String result = getRideAction(true, false, false);
        assertEquals("СТАНДАРТНА_ПОЇЗДКА", result, 
            "TC-6: Стандартна довга поїздка комфорт-класу");
    }
}