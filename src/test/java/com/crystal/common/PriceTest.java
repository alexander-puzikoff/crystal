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
    }

    @Test
    public void testIsAfter() throws Exception {
        Price newPrice = new Price(oldPrice);
        newPrice.setStartsFrom(dateFormat.parse("2015-05-11 00:00:00"));
        newPrice.setEndsAt(dateFormat.parse("2015-05-12 01:00:00"));
        Assert.assertTrue(newPrice.isAfter(oldPrice));

    }

    @Test
    public void testIsWhile() throws Exception {
        Price newPrice = new Price(oldPrice);
        newPrice.setStartsFrom(dateFormat.parse("2015-05-05 00:00:00"));
        newPrice.setEndsAt(dateFormat.parse("2015-05-05 15:00:00"));
        Assert.assertTrue(newPrice.isWhile(oldPrice));
        Assert.assertTrue(!oldPrice.isWhile(newPrice));
    }

    @Test
    public void testIsValidTimed() throws Exception {
        Assert.assertTrue(oldPrice.isValidTimed());
    }

    @Test
    public void testIsNotValidTimed() throws Exception {
        oldPrice.setEndsAt(oldPrice.getStartsFrom());
        Assert.assertTrue(!oldPrice.isValidTimed());
        oldPrice.setStartsFrom(dateFormat.parse("2015-05-30 00:00:00"));
        Assert.assertTrue(!oldPrice.isValidTimed());
    }

    @Test
    public void testIsIntercepts() throws Exception {
        Price newPrice = new Price(oldPrice);
        newPrice.setStartsFrom(dateFormat.parse("2015-05-05 00:00:00"));
        newPrice.setEndsAt(dateFormat.parse("2015-05-15 15:00:00"));
        Assert.assertTrue(newPrice.isIntercepts(oldPrice) && oldPrice.isIntercepts(newPrice));
    }
}