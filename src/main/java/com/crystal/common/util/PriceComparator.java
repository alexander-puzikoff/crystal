package com.crystal.common.util;

import com.crystal.common.Price;

import java.util.Comparator;

/**
 * Created by alex on 25.07.16.
 */
public class PriceComparator implements Comparator<Price> {

    public int compare(Price o1, Price o2) {
        if (o1.isBefore(o2)) {
            return -1;

        }
        if (o1.isAfter(o2)) {
            return 1;
        }
        return 0;
    }


}
