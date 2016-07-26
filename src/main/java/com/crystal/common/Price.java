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

    public Price() {
    }

    public Price(String productCode, int number, int depart, Date startsFrom, Date endsAt, long priceInCents) {
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
        this.startsFrom = startsFrom;
        this.endsAt = endsAt;
        this.priceInCents = priceInCents;
    }

    public Price(Price newPrice) {
        this.setStartsFrom(newPrice.getStartsFrom());
        this.setEndsAt(newPrice.getEndsAt());
        this.setDepart(newPrice.getDepart());
        this.setNumber(newPrice.getNumber());
        this.setPriceInCents(newPrice.getPriceInCents());
        this.setProductCode(newPrice.getProductCode());
    }

    public boolean isBefore(Price otherPrice) {
        return this.getStartsFrom().before(otherPrice.getStartsFrom()) &&
                this.getEndsAt().before(otherPrice.getStartsFrom());
    }

    public boolean isAfter(Price otherPrice) {
        return otherPrice.getStartsFrom().before(this.getStartsFrom()) &&
                otherPrice.getEndsAt().before(this.getStartsFrom());
    }

    public boolean isWhile(Price otherPrice) {
        return this.getStartsFrom().after(otherPrice.getStartsFrom()) &&
                this.getEndsAt().before(otherPrice.getEndsAt());
    }

    public boolean isValidTimed() {
        return this.getEndsAt().after(this.getStartsFrom()) && this.getEndsAt().compareTo(this.getStartsFrom()) != 0;
    }

    public boolean isIntercepts(Price otherPrice) {
        return (this.getStartsFrom().before(otherPrice.getStartsFrom()) &&
                this.getEndsAt().before(otherPrice.getEndsAt()) &&
                this.getEndsAt().after(otherPrice.getStartsFrom())) ||
                (this.getStartsFrom().after(otherPrice.getStartsFrom()) &&
                        this.getStartsFrom().before(otherPrice.getEndsAt()) &&
                        this.getEndsAt().after(otherPrice.getEndsAt()));
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
