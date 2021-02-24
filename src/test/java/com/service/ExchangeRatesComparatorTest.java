package com.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ExchangeRatesComparatorTest {
    HashMap<String, Double> curr;
    HashMap<String, Double> yesterday;
    ExchangeRatesComparator erc;

    private void createMaps(double rubCurr, double rubYesterd, double eurCurr, double eurYesterd)
    {
        curr = new HashMap<>();
        yesterday = new HashMap<>();
        curr.put("RUB", rubCurr);
        curr.put("EUR", eurCurr);
        yesterday.put("RUB", rubYesterd);
        yesterday.put("EUR", eurYesterd);
    }

//    @BeforeEach
//    public void setUp()
//    {
//        createMaps(74.2, 73.5, 0.87654, 0.84567);
//        erc = new ExchangeRatesComparator(curr, yesterday, "EUR", "RUB");
//
//    }

    @Test
    public void compareTest()
    {
        createMaps(74.2, 73.5, 0.87654, 0.84567);
        erc = new ExchangeRatesComparator(curr, yesterday, "EUR", "RUB");
        assertEquals("broken", erc.compare());
    }


    @Test
    public void compareTest2()
    {
        createMaps(70.2, 73.5, 0.87654, 0.95657);
        erc = new ExchangeRatesComparator(curr, yesterday, "EUR", "RUB");
        assertEquals("rich", erc.compare());
    }

    //чисто теоретический случай, мб что-то произошло на внешнем сервере, главное не выпадем с эксепшном
    @Test
    public void compareTest3()
    {
        createMaps(70.2, 73.5, 0, 0);
        erc = new ExchangeRatesComparator(curr, yesterday, "EUR", "RUB");
        assertEquals("rich", erc.compare());
    }


}
