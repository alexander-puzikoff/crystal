package com.crystal;

import com.crystal.common.Price;
import com.crystal.common.util.PriceUtils;

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
            if (products.containsKey(price.getProductCode())) {
                addNewPrice(products, price);
            } else {
                addPriceToMap(products, price);
            }
        }

        List<Price> resultPrices = new LinkedList<Price>();
        for (SortedSet<Price> prdcts : products.values()) {
            resultPrices.addAll(prdcts);
        }
        return resultPrices;
    }

    private void addPriceToMap(Map<String, SortedSet<Price>> products, Price price) {
        SortedSet<Price> currentPrices = new TreeSet<Price>();
        currentPrices.add(price);
        products.put(price.getProductCode(), currentPrices);
    }

    private void addNewPrice(Map<String, SortedSet<Price>> products, Price newPrice) {
        Price[] prices = new Price[products.get(newPrice.getProductCode()).size()];
        products.get(newPrice.getProductCode()).toArray(prices);
        SortedSet<Price> newPricesForProduct = new TreeSet<Price>();
        boolean dirty = false;
        for (Price currentPrice : prices) {
            if (currentPrice.getNumber() == newPrice.getNumber()) {
                dirty = newPrice.isIntercepts(currentPrice) && newPrice.isValidTimed();
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
        }
        Collections.addAll(newPricesForProduct, prices);
        newPricesForProduct.add(newPrice);
        for (Iterator<Price> iter = newPricesForProduct.iterator(); iter.hasNext(); ) {
            Price nextPrice = iter.next();
            if (!nextPrice.isValidTimed()) {
                iter.remove();
            }
        }
        products.put(newPrice.getProductCode(), newPricesForProduct);
    }


    private Map<String, SortedSet<Price>> convertPricesToMap(List<Price> prices) {
        Map<String, SortedSet<Price>> result = new HashMap<String, SortedSet<Price>>();

        for (Price price : prices) {
            if (!result.containsKey(price.getProductCode())) {
                addPriceToMap(result, price);
            } else {
                Set<Price> currentPrices = result.get(price.getProductCode());
                currentPrices.add(price);
            }

        }
        return result;
    }

}
