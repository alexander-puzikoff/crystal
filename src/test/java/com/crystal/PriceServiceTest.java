package com.crystal;

import com.crystal.common.Price;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by alex on 27.07.16.
 */
public class PriceServiceTest {
    private List<Price> oldPrices = null;
    private List<Price> newPrices = null;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

    @Before
    public void startUp(){
        oldPrices = new LinkedList<Price>();
        newPrices = new LinkedList<Price>();
    }

    @Test
    public void testAggregateSamePricesInside() throws Exception {
        oldPrices.add(new Price("122856", 1, 1, dateFormat.parse("01.01.2013 00:00:00"),
                dateFormat.parse("31.01.2013 23:59:59"), 11000));
        newPrices.add(new Price("122856", 1, 1, dateFormat.parse("05.01.2013 00:00:00"),
                dateFormat.parse("15.01.2013 23:59:59"), 11000));
        List<Price> exptectedPrices = new LinkedList<Price>();
        exptectedPrices.add(new Price("122856", 1, 1, dateFormat.parse("01.01.2013 00:00:00"),
                dateFormat.parse("31.01.2013 23:59:59"), 11000));
        PriceService priceService = new PriceService();
        List<Price> resultPrices = priceService.aggregatePrices(oldPrices, newPrices);
        Assert.assertThat(exptectedPrices, is(resultPrices));
    }

    @Test
    public void testAggregateWithNullInside() throws Exception {
        Exception expected = null;
        oldPrices.add(new Price("122856", 1, 1, null,
                dateFormat.parse("31.01.2013 23:59:59"), 11000));
        newPrices.add(new Price("122856", 1, 1, dateFormat.parse("05.01.2013 00:00:00"),
                dateFormat.parse("15.01.2013 23:59:59"), 11000));
        List<Price> exptectedPrices = new LinkedList<Price>();
        exptectedPrices.add(new Price("122856", 1, 1, dateFormat.parse("01.01.2013 00:00:00"),
                dateFormat.parse("31.01.2013 23:59:59"), 11000));
        PriceService priceService = new PriceService();
        try{
            List<Price> resultPrices = priceService.aggregatePrices(oldPrices, newPrices);
        }catch (Exception ex){
            expected = ex;
        }
        Assert.assertTrue(expected instanceof  NullPointerException);
    }

    @Test
    public void nullInputTest() throws ParseException {
        PriceService priceService = new PriceService();
        List<Price> resultPrices = priceService.aggregatePrices(null, newPrices);
        Assert.assertEquals(resultPrices, newPrices);
        newPrices.add(new Price("122856", 1, 1, dateFormat.parse("05.01.2013 00:00:00"),
                dateFormat.parse("15.01.2013 23:59:59"), 11000));
        resultPrices = priceService.aggregatePrices(newPrices, null);
        Assert.assertEquals(resultPrices, newPrices);
        Assert.assertNull(priceService.aggregatePrices(null, null));
    }

    @Test
    public void test1AggregatePrices() throws Exception {
        prepareForTest1();
        PriceService priceService = new PriceService();
        List<Price> resultPrices = priceService.aggregatePrices(oldPrices, newPrices);
        Assert.assertThat(resultsForTest1(), is(resultPrices));
    }

    @Test
    public void test2AggregatePrices() throws Exception {
        prepareForTest2();
        PriceService priceService = new PriceService();
        List<Price> resultPrices = priceService.aggregatePrices(oldPrices, newPrices);
        for (Price p : resultsForTest2())
            System.out.println(p);
        Assert.assertThat(resultsForTest2(), is(resultPrices));
    }


    private void prepareForTest1() throws ParseException {
        oldPrices.add(new Price("122856", 1, 1, dateFormat.parse("01.01.2013 00:00:00"),
                dateFormat.parse("31.01.2013 23:59:59"), 11000));
        oldPrices.add(new Price("122856", 2, 1, dateFormat.parse("10.01.2013 00:00:00"),
                dateFormat.parse("20.01.2013 23:59:59"), 99000));
        oldPrices.add(new Price("6654", 1, 2, dateFormat.parse("01.01.2013 00:00:00"),
                dateFormat.parse("31.01.2013 00:00:00"), 5000));
        newPrices.add(new Price("122856", 1, 1, dateFormat.parse("20.01.2013 00:00:00"),
                dateFormat.parse("20.02.2013 23:59:59"), 11000));
        newPrices.add(new Price("122856", 2, 1,
                dateFormat.parse("15.01.2013 00:00:00"), dateFormat.parse("25.01.2013 23:59:59"), 92000));
        newPrices.add(new Price("6654", 1, 2, dateFormat.parse("12.01.2013 00:00:00"),
                dateFormat.parse("13.01.2013 00:00:00"), 4000));
    }

    private void prepareForTest2() throws ParseException {
        oldPrices = new LinkedList<Price>();
        newPrices = new LinkedList<Price>();
        oldPrices.add(new Price("1", 1, 2, dateFormat.parse("31.07.2016 00:00:00"),
                dateFormat.parse("07.08.2016 00:00:00"), 1200));
        oldPrices.add(new Price("1", 1, 2, dateFormat.parse("10.08.2016 04:00:00"),
                dateFormat.parse("16.08.2016 00:00:00"), 3000));
        oldPrices.add(new Price("1", 2, 2, dateFormat.parse("20.08.2016 00:00:00"),
                dateFormat.parse("26.08.2016 00:00:00"), 1600));
        oldPrices.add(new Price("1", 1, 2, dateFormat.parse("30.08.2016 00:00:00"),
                dateFormat.parse("06.09.2016 00:00:00"), 1800));
        oldPrices.add(new Price("2", 1, 1, dateFormat.parse("02.08.2016 00:00:00"),
                dateFormat.parse("09.08.2016 00:00:00"), 1000));
        oldPrices.add(new Price("2", 1, 1, dateFormat.parse("14.08.2016 00:00:00"),
                dateFormat.parse("28.08.2016 00:00:00"), 2800));
        oldPrices.add(new Price("3", 2, 2, dateFormat.parse("31.07.2016 00:00:00"),
                dateFormat.parse("13.08.2016 00:00:00"), 5000));
        oldPrices.add(new Price("3", 2, 2, dateFormat.parse("13.08.2016 00:00:00"),
                dateFormat.parse("26.08.2016 00:00:00"), 10000));
        newPrices.add(new Price("1", 2, 2, dateFormat.parse("31.07.2016 00:00:00"),
                dateFormat.parse("01.08.2016 00:00:00"), 300));
        newPrices.add(new Price("1", 1, 2, dateFormat.parse("05.08.2016 00:00:00"),
                dateFormat.parse("15.08.2016 00:00:00"), 600));
        newPrices.add(new Price("1", 1, 2, dateFormat.parse("18.08.2016 00:00:00"),
                dateFormat.parse("26.08.2016 00:00:00"), 3000));
        newPrices.add(new Price("2", 1, 1, dateFormat.parse("01.08.2016 00:00:00"),
                dateFormat.parse("05.08.2016 00:00:00"), 1500));
        newPrices.add(new Price("2", 1, 1, dateFormat.parse("08.08.2016 00:00:00"),
                dateFormat.parse("20.08.2016 00:00:00"), 2800));
        newPrices.add(new Price("3", 2, 1, dateFormat.parse("05.08.2016 00:00:00"),
                dateFormat.parse("12.08.2016 00:00:00"), 10000));
        newPrices.add(new Price("3", 2, 2, dateFormat.parse("19.08.2016 00:00:00"),
                dateFormat.parse("24.08.2016 00:00:00"), 1000));
        newPrices.add(new Price("4", 1, 2, dateFormat.parse("31.07.2016 00:00:00"),
                dateFormat.parse("22.08.2016 00:00:00"), 3500));
        newPrices.add(new Price("4", 1, 2, dateFormat.parse("25.08.2016 00:00:00"),
                dateFormat.parse("27.08.2016 00:00:00"), 2500));
    }

    private List<Price> resultsForTest1() throws ParseException {
        List<Price> resultedPrices = new LinkedList<Price>();
        resultedPrices.add(new Price("6654", 1, 2, dateFormat.parse("01.01.2013 00:00:00"),
                dateFormat.parse("12.01.2013 00:00:00"), 5000));
        resultedPrices.add(new Price("6654", 1, 2, dateFormat.parse("12.01.2013 00:00:00"),
                dateFormat.parse("13.01.2013 00:00:00"), 4000));
        resultedPrices.add(new Price("6654", 1, 2, dateFormat.parse("13.01.2013 00:00:00"),
                dateFormat.parse("31.01.2013 00:00:00"), 5000));
        resultedPrices.add(new Price("122856", 1, 1, dateFormat.parse("01.01.2013 00:00:00"),
                dateFormat.parse("20.02.2013 23:59:59"), 11000));
        resultedPrices.add(new Price("122856", 2, 1, dateFormat.parse("10.01.2013 00:00:00"),
                dateFormat.parse("15.01.2013 00:00:00"), 99000));
        resultedPrices.add(new Price("122856", 2, 1, dateFormat.parse("15.01.2013 00:00:00"),
                dateFormat.parse("25.01.2013 23:59:59"), 92000));
        return resultedPrices;
    }

    private List<Price> resultsForTest2() throws ParseException {
        List<Price> resultedPrices = new LinkedList<Price>();

        resultedPrices.add(new Price("1", 1, 2, dateFormat.parse("31.07.2016 00:00:00"),
                dateFormat.parse("05.08.2016 00:00:00"), 1200));
        resultedPrices.add(new Price("1", 2, 2, dateFormat.parse("31.07.2016 00:00:00"),
                dateFormat.parse("01.08.2016 00:00:00"), 300));
        resultedPrices.add(new Price("1", 1, 2, dateFormat.parse("05.08.2016 00:00:00"),
                dateFormat.parse("15.08.2016 00:00:00"), 600));
        resultedPrices.add(new Price("1", 1, 2, dateFormat.parse("15.08.2016 00:00:00"),
                dateFormat.parse("16.08.2016 00:00:00"), 3000));
        resultedPrices.add(new Price("1", 1, 2, dateFormat.parse("18.08.2016 00:00:00"),
                dateFormat.parse("26.08.2016 00:00:00"), 3000));
        resultedPrices.add(new Price("1", 2, 2, dateFormat.parse("20.08.2016 00:00:00"),
                dateFormat.parse("26.08.2016 00:00:00"), 1600));
        resultedPrices.add(new Price("1", 1, 2, dateFormat.parse("30.08.2016 00:00:00"),
                dateFormat.parse("06.09.2016 00:00:00"), 1800));
        resultedPrices.add(new Price("2", 1, 1, dateFormat.parse("01.08.2016 00:00:00"),
                dateFormat.parse("05.08.2016 00:00:00"), 1500));
        resultedPrices.add(new Price("2", 1, 1, dateFormat.parse("05.08.2016 00:00:00"),
                dateFormat.parse("08.08.2016 00:00:00"), 1000));
        resultedPrices.add(new Price("2", 1, 1, dateFormat.parse("08.08.2016 00:00:00"),
                dateFormat.parse("28.08.2016 00:00:00"), 2800));
        resultedPrices.add(new Price("3", 2, 2, dateFormat.parse("31.07.2016 00:00:00"),
                dateFormat.parse("13.08.2016 00:00:00"), 5000));
        resultedPrices.add(new Price("3", 2, 1, dateFormat.parse("05.08.2016 00:00:00"),
                dateFormat.parse("12.08.2016 00:00:00"), 10000));
        resultedPrices.add(new Price("3", 2, 2, dateFormat.parse("13.08.2016 00:00:00"),
                dateFormat.parse("19.08.2016 00:00:00"), 10000));
        resultedPrices.add(new Price("3", 2, 2, dateFormat.parse("19.08.2016 00:00:00"),
                dateFormat.parse("24.08.2016 00:00:00"), 1000));
        resultedPrices.add(new Price("3", 2, 2, dateFormat.parse("24.08.2016 00:00:00"),
                dateFormat.parse("26.08.2016 00:00:00"), 10000));
        resultedPrices.add(new Price("4", 1, 2, dateFormat.parse("31.07.2016 00:00:00"),
                dateFormat.parse("22.08.2016 00:00:00"), 3500));
        resultedPrices.add(new Price("4", 1, 2, dateFormat.parse("25.08.2016 00:00:00"),
                dateFormat.parse("27.08.2016 00:00:00"), 2500));
        return resultedPrices;
    }
}