package com.crystal.common.util;

import com.crystal.common.Price;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * Created by alex on 27.07.16.
 */
public class PriceByDateComparatorTest {
    private SimpleDateFormat dateFormat;

    @Test
    public void testCompareIntercepted() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Price oldPrice = new Price("14", 3, 1, dateFormat.parse("2011-11-13 23:30:00"),
                dateFormat.parse("2011-11-16 02:00:00"), 149900);
        Price newPrice = new Price("14", 3, 1, dateFormat.parse("2011-11-14 13:30:00"),
                dateFormat.parse("2011-12-03 13:30:00"), 129900);
        Comparator priceComparator = new PriceByDateComparator();
        Assert.assertEquals(0, priceComparator.compare(oldPrice, newPrice));
    }

    @Test
    public void testCompareBefore() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Price oldPrice = new Price("14", 3, 1, dateFormat.parse("2011-11-13 23:30:00"),
                dateFormat.parse("2011-11-16 02:00:00"), 149900);
        Price newPrice = new Price("14", 3, 1, dateFormat.parse("2011-11-17 13:30:00"),
                dateFormat.parse("2011-12-03 13:30:00"), 129900);
        Comparator priceComparator = new PriceByDateComparator();
        Assert.assertEquals(-1, priceComparator.compare(oldPrice, newPrice));
    }

    @Test
    public void testCompareAfter() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Price oldPrice = new Price("14", 3, 1, dateFormat.parse("2011-11-13 23:30:00"),
                dateFormat.parse("2011-11-16 02:00:00"), 149900);
        Price newPrice = new Price("14", 3, 1, dateFormat.parse("2011-11-17 13:30:00"),
                dateFormat.parse("2011-12-03 13:30:00"), 129900);
        Comparator priceComparator = new PriceByDateComparator();
        Assert.assertEquals(1, priceComparator.compare(newPrice, oldPrice));
    }
}