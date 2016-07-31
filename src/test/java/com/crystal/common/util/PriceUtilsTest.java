package com.crystal.common.util;

import com.crystal.common.Price;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author APuzikov
 * @version 1.0
 * @since <pre>07/26/2016</pre>
 */
public class PriceUtilsTest {
    List<Price> oldPrices = null;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        oldPrices = new ArrayList<Price>();
        oldPrices.add(new Price("1234", 2, 1, dateFormat.parse("2015-04-30 00:00:00"), dateFormat.parse("2015-05-10 02:00:00"), 11232300));
        oldPrices.add(new Price("134", 2, 1, dateFormat.parse("2015-07-13 00:00:00"), dateFormat.parse("2015-07-16 02:00:00"), 90323));
        oldPrices.add(new Price("1234", 1, 1, dateFormat.parse("2015-12-10 00:00:00"), dateFormat.parse("2016-01-10 02:00:00"), 78323));
        oldPrices.add(new Price("64", 2, 3, dateFormat.parse("2015-06-30 00:20:00"), dateFormat.parse("2016-06-10 02:00:00"), 2323));
    }


    @Test
    public void testCorrectInterceptedPrices() throws Exception {
        Date oldSt = dateFormat.parse("2011-11-13 23:30:00");
        Price oldPrice = new Price("14", 3, 1, oldSt, dateFormat.parse("2011-11-16 02:00:00"), 149900);
        Price newPrice = new Price("14", 3, 1, dateFormat.parse("2011-11-14 13:30:00"),
                dateFormat.parse("2011-12-03 13:30:00"), 129900);
        PriceUtils.correctInterceptedPrices(oldPrice, newPrice);
        Assert.assertTrue("Old early price didn't update its end", oldPrice.getEndsAt().equals(newPrice.getStartsFrom()));
        Assert.assertTrue("Old early price didn't save its start", oldPrice.getStartsFrom().equals(oldSt));

    }

    @Test
    public void testCorrectInterceptedPricesInverted() throws Exception {
        Price oldPrice = new Price("14", 3, 1, dateFormat.parse("2011-11-14 13:30:00"),
                dateFormat.parse("2011-12-03 13:30:00"), 129900);
        Price newPrice = new Price("14", 3, 1, dateFormat.parse("2011-11-13 13:30:00"), dateFormat.parse("2011-11-23 13:30:00"), 32900);
        PriceUtils.correctInterceptedPrices(oldPrice, newPrice);
        Assert.assertTrue("Old price didn't update its start", oldPrice.getStartsFrom().equals(newPrice.getEndsAt()));
    }

    @Test
    public void testNeighboursWithSamePrice() throws Exception {
        Price[] neighbours = prepareNeighboursWithSamePrice();
        Date startsFrom = neighbours[0].getStartsFrom();
        Price newPriceCopy = new Price(neighbours[1]);
        PriceUtils.correctInterceptedPrices(neighbours[0], neighbours[1]);
        Assert.assertTrue("2st price is valid", !neighbours[1].isValidTimed());
        Assert.assertEquals("Early price didn't update its end", neighbours[0].getEndsAt(), newPriceCopy.getEndsAt());
        Assert.assertEquals("Early price didn't save its start", neighbours[0].getStartsFrom(), startsFrom);
    }

    @Test
    public void testNeighboursWithSamePriceInverted() throws Exception {
        Price[] neighbours = prepareNeighboursWithSamePrice();
        Date endsAt = neighbours[1].getEndsAt();
        Price oldPriceCopy = new Price(neighbours[0]);
        PriceUtils.correctInterceptedPrices(neighbours[1], neighbours[0]);
        Assert.assertTrue("2st price is valid", !neighbours[0].isValidTimed());
        Assert.assertEquals("Late price didn't update start time", neighbours[1].getStartsFrom(), oldPriceCopy.getStartsFrom());
        Assert.assertEquals("Late price didn't saved end time", neighbours[1].getEndsAt(), endsAt);
    }


    @Test
    public void testUniteInterceptedWithSamePrices() throws Exception {
        Date oldSt = dateFormat.parse("2011-11-13 23:30:00");
        long priceForProduct = 129900;
        Price oldPrice = new Price("14", 3, 1, oldSt, dateFormat.parse("2011-11-16 02:00:00"),
                priceForProduct);
        Price newPrice = new Price("14", 3, 1, dateFormat.parse("2011-11-14 13:30:00"),
                dateFormat.parse("2011-12-03 13:30:00"), priceForProduct);
        Price copyOld = new Price(oldPrice), copyNew = new Price(newPrice);
        PriceUtils.correctInterceptedPrices(oldPrice, newPrice);
        Assert.assertTrue("1st price aint saved it's start value", oldPrice.getStartsFrom().equals(copyOld.getStartsFrom()));
        Assert.assertTrue("1st price and prolonged to 2nd", oldPrice.getEndsAt().equals(copyNew.getEndsAt()));
    }

    @Test
    public void testUniteInterceptedWithSamePricesNewIvalid() throws Exception {
        Date oldSt = dateFormat.parse("2011-11-13 23:30:00");
        long priceForProduct = 129900;
        Price oldPrice = new Price("14", 3, 1, oldSt, dateFormat.parse("2011-11-16 02:00:00"),
                priceForProduct);
        Price newPrice = new Price("14", 3, 1, dateFormat.parse("2011-11-14 13:30:00"),
                dateFormat.parse("2011-12-03 13:30:00"), priceForProduct);
        PriceUtils.correctInterceptedPrices(oldPrice, newPrice);
        Assert.assertTrue(!newPrice.isValidTimed());
    }

    @Test
    public void testSplitDifferentPrices() throws Exception {
        Date oldSt = dateFormat.parse("2011-11-13 23:30:00");
        Price outsidePrice = new Price("14", 3, 1, oldSt, dateFormat.parse("2011-12-14 02:00:00"), 149900);
        Price insidePrice = new Price("14", 3, 1, dateFormat.parse("2011-11-14 13:30:00"), dateFormat.parse("2011-12-03 13:30:00"), 129900);
        Price copyInside = new Price(insidePrice), copyOutside = new Price(outsidePrice);
        Price resultedPrice = PriceUtils.splitDifferentPrices(insidePrice, outsidePrice);
        Assert.assertTrue("3rd price ain't  after 2nd", resultedPrice.getStartsFrom().equals(copyInside.getEndsAt()));
        Assert.assertTrue("3rd price ain't stops where it should", resultedPrice.getEndsAt().equals(copyOutside.getEndsAt()));
        Assert.assertTrue("2nd price ain't after 1rst", outsidePrice.getEndsAt().equals(copyInside.getStartsFrom()));
        Assert.assertEquals("2nd price ain't saved inner price", copyInside.getPriceInCents(), insidePrice.getPriceInCents());
    }

    @Test
    public void testUniteWithSamePricesInsideInvalid() throws Exception {
        Date oldSt = dateFormat.parse("2011-11-13 23:30:00");
        long priceForProduct = 129900;
        Price outsidePrice = new Price("14", 3, 1, oldSt, dateFormat.parse("2011-12-14 02:00:00"),
                priceForProduct);
        Price insidePrice = new Price("14", 3, 1, dateFormat.parse("2011-11-14 13:30:00"),
                dateFormat.parse("2011-12-03 13:30:00"), priceForProduct);
        PriceUtils.uniteSamePrices(insidePrice, outsidePrice);
        Assert.assertTrue(!insidePrice.isValidTimed());
    }


    @Test
    public void testUniteWithSamePrices() throws Exception {
        Date oldSt = dateFormat.parse("2011-11-13 23:30:00");
        long priceForProduct = 129900;
        Price outsidePrice = new Price("14", 3, 1, oldSt, dateFormat.parse("2011-12-14 02:00:00"),
                priceForProduct);
        Price insidePrice = new Price("14", 3, 1, dateFormat.parse("2011-11-14 13:30:00"),
                dateFormat.parse("2011-12-03 13:30:00"), priceForProduct);
        Price copyOutside = new Price(outsidePrice);
        PriceUtils.uniteSamePrices(insidePrice, outsidePrice);
        Assert.assertTrue("Inside price wasn't annihilated", !insidePrice.isValidTimed());
        Assert.assertTrue("Outside price changed it's value", outsidePrice.equals(copyOutside));
    }


    private Price[] prepareNeighboursWithSamePrice() throws ParseException {
        Price oldPrice = new Price("14", 3, 1, dateFormat.parse("2011-10-16 02:00:00"), dateFormat.parse("2011-11-16 02:00:00"), 129900);
        Price newPrice = new Price("14", 3, 1, dateFormat.parse("2011-11-16 02:00:00"),
                dateFormat.parse("2011-12-03 13:30:00"), 129900);
        return new Price[]{oldPrice, newPrice};
    }

}