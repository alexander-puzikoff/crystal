package com.crystal;

import com.crystal.common.Price;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alex on 27.07.16.
 */
public class PriceServiceTest {
    List<Price> oldPrices = null;
    List<Price> newPrices = null;

    @Before
    public void startUp() throws ParseException {
        oldPrices = new LinkedList<Price>();
        newPrices = new LinkedList<Price>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        oldPrices.add(new Price("122856",
                1,
                1,
                dateFormat.parse("01.01.2013 00:00:00"),
                dateFormat.parse("31.01.2013 23:59:59"),
                11000
        ));
        oldPrices.add(new Price("122856",
                2,
                1,
                dateFormat.parse("10.01.2013 00:00:00"),
                dateFormat.parse("20.01.2013 23:59:59"),
                99000
        ));
        oldPrices.add(new Price("6654",
                1,
                2,
                dateFormat.parse("01.01.2013 00:00:00"),
                dateFormat.parse("31.01.2013 00:00:00"),
                5000
        ));
        newPrices.add(new Price("122856",
                1,
                1,
                dateFormat.parse("20.01.2013 00:00:00"),
                dateFormat.parse("20.02.2013 23:59:59"),
                11000
        ));
        newPrices.add(new Price("122856",
                2,
                1,
                dateFormat.parse("15.01.2013 00:00:00"),
                dateFormat.parse("25.01.2013 23:59:59"),
                92000
        ));
        newPrices.add(new Price("6654",
                1,
                2,
                dateFormat.parse("12.01.2013 00:00:00"),
                dateFormat.parse("13.01.2013 00:00:00"),
                4000
        ));
    }

    @Test
    public void testAggregatePrices() throws Exception {
        PriceService priceService = new PriceService();
        List<Price> resultPrices = priceService.aggregatePrices(oldPrices, newPrices);
        for (Price price : resultPrices)
            System.out.println(price);
    }
}