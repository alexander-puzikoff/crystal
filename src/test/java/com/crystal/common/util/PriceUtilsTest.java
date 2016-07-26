package com.crystal.common.util;

import com.crystal.common.Price;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        oldPrices = new ArrayList<Price>();
        oldPrices.add(new Price("1234", 2, 1, dateFormat.parse("2015-04-30 00:00:00"), dateFormat.parse("2015-05-10 02:00:00"), 11232300));
        oldPrices.add(new Price("134", 2, 1, dateFormat.parse("2015-07-13 00:00:00"), dateFormat.parse("2015-07-16 02:00:00"), 90323));
        oldPrices.add(new Price("1234", 1, 1, dateFormat.parse("2015-12-10 00:00:00"), dateFormat.parse("2016-01-10 02:00:00"), 78323));
        oldPrices.add(new Price("64", 2, 3, dateFormat.parse("2015-06-30 00:20:00"), dateFormat.parse("2016-06-10 02:00:00"), 2323));
    }


    @Test
    public void testCorrectInterceptedPrices() throws Exception {

    }

    @Test
    public void testSplitPrices() throws Exception {

    }
}