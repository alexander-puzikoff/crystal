package com.crystal;

import com.crystal.common.Price;
import com.crystal.common.util.PriceUtils;
import com.crystal.common.util.PriceByDateComparator;

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
                SortedSet<Price> currentPrices = new TreeSet<Price>(new PriceByDateComparator());
                currentPrices.add(price);
                products.put(price.getProductCode(), currentPrices);
            } else {
                addNewPrice(products, price);
            }
        }

        List<Price> resultPrices = new LinkedList<Price>();
        for (SortedSet<Price> prdcts : products.values()) {
            resultPrices.addAll(prdcts);
        }
        return resultPrices;
    }

    private void addNewPrice(Map<String, SortedSet<Price>> products, Price newPrice) {
        Price[] prices = new Price[products.get(newPrice.getProductCode()).size()];
        products.get(newPrice.getProductCode()).toArray(prices);
        SortedSet<Price> newPricesForProduct = new TreeSet<Price>(new PriceByDateComparator());
        boolean dirty = false;
        for (Price currentPrice : prices) {
            dirty = newPrice.isIntercepts(currentPrice);
            if (dirty) {
                PriceUtils.correctInterceptedPrices(currentPrice, newPrice);
                continue;
            }
            dirty = dirty || newPrice.isWhile(currentPrice) || currentPrice.isWhile(newPrice);
            if (dirty) {
                Price minedPrice = newPrice.getPriceInCents() ==
                        currentPrice.getPriceInCents() ? PriceUtils.uniteSamePrices(newPrice, currentPrice) :
                        PriceUtils.splitDifferentPrices(newPrice, currentPrice);
                newPricesForProduct.add(minedPrice);
            }
        }
        if (!dirty) {
            Collections.addAll(newPricesForProduct, prices);
            newPricesForProduct.add(newPrice);
            for (Iterator<Price> iter = newPricesForProduct.iterator(); iter.hasNext(); ) {
                Price nextPrice = iter.next();
                if (!nextPrice.isValidTimed()) {
                    iter.remove();
                }
            }
        }
        products.put(newPrice.getProductCode(), newPricesForProduct);
    }


    private Map<String, SortedSet<Price>> convertPricesToMap(List<Price> prices) {
        Map<String, SortedSet<Price>> result = new HashMap<String, SortedSet<Price>>();

        for (Price price : prices) {
            if (!result.containsKey(price.getProductCode())) {
                SortedSet<Price> currentPrices = new TreeSet<Price>(new PriceByDateComparator());
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
