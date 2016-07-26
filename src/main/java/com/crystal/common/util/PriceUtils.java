package com.crystal.common.util;

import com.crystal.common.Price;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author APuzikov
 * @version 1.0
 * @since <pre>07/26/2016</pre>
 */
public class PriceUtils {
    public static List<Price> correctInterceptedPrices(Price currentPrice, Price newPrice) {
        if (currentPrice.getPriceInCents() == newPrice.getPriceInCents()) {
            if (currentPrice.getStartsFrom().after(newPrice.getStartsFrom())) {
                currentPrice.setStartsFrom(newPrice.getStartsFrom());
            }
            if (currentPrice.getEndsAt().before(newPrice.getEndsAt())) {
                currentPrice.setEndsAt(newPrice.getEndsAt());
            }
            return Arrays.asList(currentPrice);
        } else {
            if (currentPrice.getEndsAt().after(newPrice.getStartsFrom())) {
                currentPrice.setEndsAt(newPrice.getStartsFrom());
            }
            if (currentPrice.getStartsFrom().before(newPrice.getEndsAt())) {
                currentPrice.setStartsFrom(newPrice.getEndsAt());
            }
            return Arrays.asList(currentPrice, newPrice);
        }


    }

    public static Price splitPrices(Price newPrice, Price currentPrice) {
        if (currentPrice.getPriceInCents() == newPrice.getPriceInCents()) {
            return currentPrice.isWhile(newPrice) ? newPrice : currentPrice;
        } else {
            Price newNewPrice = null;
            if (currentPrice.isWhile(newPrice)) {
                newNewPrice = new Price(newPrice);
                newPrice.setEndsAt(currentPrice.getStartsFrom());
                newNewPrice.setStartsFrom(currentPrice.getEndsAt());
            } else {
                newNewPrice = new Price(currentPrice);
                currentPrice.setEndsAt(newPrice.getStartsFrom());
                newNewPrice.setStartsFrom(newPrice.getEndsAt());
            }
            return newNewPrice;
        }
    }
}
