package com.crystal.common.util;

import com.crystal.common.Price;

import java.util.Comparator;

/**
 * Created by alex on 25.07.16.
 */
public class PriceComparator implements Comparator<Price> {

    public int compare(Price o1, Price o2) {
        if (o1.getStartsFrom().before(o2.getStartsFrom()) &&
                o1.getEndsAt().before(o2.getEndsAt())) {
            return -1;

        }
        if (o2.getStartsFrom().before(o1.getStartsFrom()) &&
                o2.getEndsAt().before(o1.getEndsAt())) {
            return 1;
        }
        return 0;
    }


}
