package com.crystal;

import com.crystal.common.Price;
import com.crystal.common.util.PriceComparator;

import java.util.*;

/**
 * Created by alex on 25.07.16.
 */

public class PriceService {

    // METHOD!
    public List<Price> aggregatePrices(List<Price> oldPrices, List<Price> newPrices) {
        List<Price> allPrices = new ArrayList<Price>();
        if (oldPrices != null && newPrices == null) {
            return oldPrices;
        }


        Map<String, SortedSet<Price>> products = convertPricesToMap(oldPrices);

        for (Price price : newPrices) {
            if (!products.containsKey(price.getProductCode())) {
                SortedSet<Price> currentPrices = new TreeSet<Price>(new PriceComparator());
                currentPrices.add(price);
                products.put(price.getProductCode(), currentPrices);
            } else {
                addNewPrice(products, price);
            }
        }


        return null;
    }

    private void addNewPrice(Map<String, SortedSet<Price>> products, Price newPrice) {
        for(products)
        if(newPrice)

    }

    private Map<String, SortedSet<Price>> convertPricesToMap(List<Price> prices) {
        Map<String, SortedSet<Price>> result = new HashMap<String, SortedSet<Price>>();

        for (Price price : prices) {
            if (!result.containsKey(price.getProductCode())) {
                SortedSet<Price> currentPrices = new TreeSet<Price>(new PriceComparator());
                currentPrices.add(price);
                result.put(price.getProductCode(), currentPrices);
            } else {
                Set<Price> currentPrices = result.get(price.getProductCode());
                currentPrices.add(price);
            }

        }
        return result;
    }

}
