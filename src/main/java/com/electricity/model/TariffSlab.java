package com.electricity.model;

import java.math.BigDecimal;

public class TariffSlab {
    private Integer id;
    private String slabName;
    private int lowerLimit;
    private int upperLimit;
    private BigDecimal rate;

    public TariffSlab() {
    }

    public TariffSlab(String slabName, int lowerLimit, int upperLimit, BigDecimal rate) {
        this.slabName = slabName;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.rate = rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSlabName() {
        return slabName;
    }

    public void setSlabName(String slabName) {
        this.slabName = slabName;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
