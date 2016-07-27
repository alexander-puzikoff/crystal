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
        Assert.assertTrue(oldPrice.getEndsAt().equals(newPrice.getStartsFrom()));
        Assert.assertTrue(oldPrice.getStartsFrom().equals(oldSt));
        Price newPrice2 = new Price("14", 3, 1, dateFormat.parse("2011-11-13 13:30:00"), dateFormat.parse("2011-11-23 13:30:00"), 32900);
        PriceUtils.correctInterceptedPrices(newPrice, newPrice2);
        Assert.assertTrue(newPrice.getStartsFrom().equals(newPrice2.getEndsAt()));

    }

    @Test
    public void testUniteInterceptedPricesWithSamePrices() throws Exception {
        Date oldSt = dateFormat.parse("2011-11-13 23:30:00");
        long priceForProduct = 129900;
        Price oldPrice = new Price("14", 3, 1, oldSt, dateFormat.parse("2011-11-16 02:00:00"),
                priceForProduct);
        Price newPrice = new Price("14", 3, 1, dateFormat.parse("2011-11-14 13:30:00"),
                dateFormat.parse("2011-12-03 13:30:00"), priceForProduct);
        Price copyOld = new Price(oldPrice), copyNew = new Price(newPrice);
        PriceUtils.correctInterceptedPrices(oldPrice, newPrice);
        Assert.assertTrue(oldPrice.getStartsFrom().equals(copyOld.getStartsFrom()));
        Assert.assertTrue(oldPrice.getEndsAt().equals(copyNew.getEndsAt()));
        Assert.assertTrue(!newPrice.isValidTimed());
    }

    @Test
    public void testSplitDifferentPrices() throws Exception {
        Date oldSt = dateFormat.parse("2011-11-13 23:30:00");
        Price outsidePrice = new Price("14", 3, 1, oldSt, dateFormat.parse("2011-12-14 02:00:00"), 149900);
        Price insidePrice = new Price("14", 3, 1, dateFormat.parse("2011-11-14 13:30:00"), dateFormat.parse("2011-12-03 13:30:00"), 129900);
        Price copyInside = new Price(insidePrice), copyOutside = new Price(outsidePrice);
        Price resultedPrice = PriceUtils.splitDifferentPrices(insidePrice, outsidePrice);
        Assert.assertTrue(resultedPrice.getStartsFrom().equals(copyInside.getEndsAt()));
        Assert.assertTrue(resultedPrice.getEndsAt().equals(copyOutside.getEndsAt()));
        Assert.assertTrue(outsidePrice.getEndsAt().equals(copyInside.getStartsFrom()));
    }

    @Test
    public void testUnitePricesWithSamePrices() throws Exception {
        Date oldSt = dateFormat.parse("2011-11-13 23:30:00");
        long priceForProduct = 129900;
        Price outsidePrice = new Price("14", 3, 1, oldSt, dateFormat.parse("2011-12-14 02:00:00"),
                priceForProduct);
        Price insidePrice = new Price("14", 3, 1, dateFormat.parse("2011-11-14 13:30:00"),
                dateFormat.parse("2011-12-03 13:30:00"), priceForProduct);
        Price copyInside = new Price(insidePrice), copyOutside = new Price(outsidePrice);
        Price resultedPrice = PriceUtils.uniteSamePrices(insidePrice, outsidePrice);
        Assert.assertTrue(resultedPrice.getStartsFrom().equals(copyOutside.getStartsFrom()));
        Assert.assertTrue(resultedPrice.getEndsAt().equals(copyOutside.getEndsAt()));
        Assert.assertTrue(!insidePrice.isValidTimed());
        Assert.assertTrue(resultedPrice.equals(copyOutside));
    }
}