package com.crystal.common;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by alex on 25.07.16.
 */
public class Price {

    private String productCode;
    private int number, depart;
    private Date startsFrom, endsAt;
    private long priceInCents;

    public boolean isBefore(Price otherPrice) {
        return this.getStartsFrom().before(otherPrice.getStartsFrom()) &&
                this.getEndsAt().before(otherPrice.getEndsAt());
    }

    public boolean isAfter(Price otherPrice) {
        return otherPrice.getStartsFrom().before(this.getStartsFrom()) &&
                otherPrice.getEndsAt().before(this.getEndsAt());
    }

    public boolean isWhile(Price otherPrice) {
        return this.getStartsFrom().after(otherPrice.getStartsFrom()) &&
                this.getEndsAt().before(otherPrice.getEndsAt());
    }


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDepart() {
        return depart;
    }

    public void setDepart(int depart) {
        this.depart = depart;
    }

    public Date getStartsFrom() {
        return startsFrom;
    }

    public void setStartsFrom(Date startsFrom) {
        this.startsFrom = startsFrom;
    }

    public Date getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Date endsAt) {
        this.endsAt = endsAt;
    }

    public long getPriceInCents() {
        return priceInCents;
    }

    public void setPriceInCents(long priceInCents) {
        this.priceInCents = priceInCents;
    }
}
