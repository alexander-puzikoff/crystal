package com.crystal.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author APuzikov
 * @version 1.0
 * @since <pre>07/26/2016</pre>
 */
public class PriceTest {
    private Price oldPrice;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        oldPrice = new Price();
        oldPrice.setProductCode("1234");
        oldPrice.setNumber(2);
        oldPrice.setDepart(1);
        oldPrice.setPriceInCents(342432);
        oldPrice.setStartsFrom(dateFormat.parse("2015-04-30 00:00:00"));
        oldPrice.setEndsAt(dateFormat.parse("2015-05-10 02:00:00"));
    }

    @Test
    public void testIsBefore() throws Exception {
        Price newPrice = new Price(oldPrice);
        newPrice.setStartsFrom(dateFormat.parse("2015-05-11 00:00:00"));
        newPrice.setEndsAt(dateFormat.parse("2015-05-12 01:00:00"));
        Assert.assertTrue(oldPrice.isBefore(newPrice));
        Assert.assertFalse(newPrice.isBefore(oldPrice));
    }

    @Test
    public void testIsAfter() throws Exception {
        Price newPrice = new Price(oldPrice);
        newPrice.setStartsFrom(dateFormat.parse("2015-05-11 00:00:00"));
        newPrice.setEndsAt(dateFormat.parse("2015-05-12 01:00:00"));
        Assert.assertTrue(newPrice.isAfter(oldPrice));
        Assert.assertFalse(oldPrice.isAfter(newPrice));
    }

    @Test
    public void testIsWhile() throws Exception {
        Price newPrice = new Price(oldPrice);
        newPrice.setStartsFrom(dateFormat.parse("2015-05-05 00:00:00"));
        newPrice.setEndsAt(dateFormat.parse("2015-05-05 15:00:00"));
        Assert.assertTrue(newPrice.isWhile(oldPrice));
    }

    @Test
    public void testIsWhileWithNull() throws Exception {
        Exception exception = null;
        Price newPrice = new Price(oldPrice);
        newPrice.setStartsFrom(null);
        newPrice.setEndsAt(dateFormat.parse("2015-05-05 15:00:00"));
        try{
            newPrice.isWhile(oldPrice);
        }catch (Exception ex){
            exception = ex;
        }
        Assert.assertTrue(exception instanceof NullPointerException);
    }

    @Test
    public void testIsNotWhile() throws Exception {
        Price newPrice = new Price(oldPrice);
        newPrice.setStartsFrom(dateFormat.parse("2015-05-05 00:00:00"));
        newPrice.setEndsAt(dateFormat.parse("2015-05-05 15:00:00"));
        Assert.assertFalse(oldPrice.isWhile(newPrice));
    }

    @Test
    public void testIsValidTimed() throws Exception {
        Assert.assertTrue(oldPrice.isValidTimed());
    }

    @Test
    public void testIsNotValidTimedWithEqualsDates() throws Exception {
        oldPrice.setEndsAt(oldPrice.getStartsFrom());
        Assert.assertFalse(oldPrice.isValidTimed());
    }

    @Test
    public void testIsNotValidTimedWithImpossibleDate() throws Exception {
        oldPrice.setStartsFrom(dateFormat.parse("2015-05-30 00:00:00")); // end 05-10
        Assert.assertFalse(oldPrice.isValidTimed());
    }

    @Test
    public void testIsInterceptsVice() throws Exception {
        Price newPrice = new Price(oldPrice);
        newPrice.setStartsFrom(dateFormat.parse("2015-05-05 00:00:00"));
        newPrice.setEndsAt(dateFormat.parse("2015-05-15 15:00:00"));
        Assert.assertTrue(newPrice.isIntercepts(oldPrice));
    }

    @Test
    public void testIsInterceptsVerca() throws Exception {
        Price newPrice = new Price(oldPrice);
        newPrice.setStartsFrom(dateFormat.parse("2015-05-05 00:00:00"));
        newPrice.setEndsAt(dateFormat.parse("2015-05-15 15:00:00"));
        Assert.assertTrue(oldPrice.isIntercepts(newPrice));
    }

    @Test
    public void testIsNotIntercepts() throws Exception {
        Price newPrice = new Price(oldPrice);
        newPrice.setStartsFrom(dateFormat.parse("2011-05-05 00:00:00"));
        newPrice.setEndsAt(dateFormat.parse("2011-05-15 15:00:00"));
        Assert.assertFalse(oldPrice.isIntercepts(newPrice));
    }

    @Test
    public void testIsInterceptsNeighbours() throws Exception {
        Price oldPrice = new Price("14", 3, 1, dateFormat.parse("2011-10-16 02:00:00"), dateFormat.parse("2011-11-16 02:00:00"), 149900);
        Price newPrice = new Price("14", 3, 1, dateFormat.parse("2011-11-16 02:00:00"),
                dateFormat.parse("2011-12-03 13:30:00"), 129900);
        Assert.assertTrue(newPrice.isIntercepts(oldPrice));
    }
}