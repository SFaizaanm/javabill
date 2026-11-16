package com.electricity.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BillCalculationResult {
    private int unitsConsumed;
    private BigDecimal energyCharge;
    private BigDecimal fixedCharge;
    private BigDecimal additionalCharges;
    private BigDecimal totalAmount;
    private final List<TariffSlabCharge> slabCharges = new ArrayList<>();
    private String tariffSummary;

    public int getUnitsConsumed() {
        return unitsConsumed;
    }

    public void setUnitsConsumed(int unitsConsumed) {
        this.unitsConsumed = unitsConsumed;
    }

    public BigDecimal getEnergyCharge() {
        return energyCharge;
    }

    public void setEnergyCharge(BigDecimal energyCharge) {
        this.energyCharge = energyCharge;
    }

    public BigDecimal getFixedCharge() {
        return fixedCharge;
    }

    public void setFixedCharge(BigDecimal fixedCharge) {
        this.fixedCharge = fixedCharge;
    }

    public BigDecimal getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(BigDecimal additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<TariffSlabCharge> getSlabCharges() {
        return slabCharges;
    }

    public String getTariffSummary() {
        return tariffSummary;
    }

    public void setTariffSummary(String tariffSummary) {
        this.tariffSummary = tariffSummary;
    }

    public static class TariffSlabCharge {
        private String slabName;
        private int units;
        private BigDecimal rate;
        private BigDecimal amount;

        public TariffSlabCharge(String slabName, int units, BigDecimal rate, BigDecimal amount) {
            this.slabName = slabName;
            this.units = units;
            this.rate = rate;
            this.amount = amount;
        }

        public String getSlabName() {
            return slabName;
        }

        public int getUnits() {
            return units;
        }

        public BigDecimal getRate() {
            return rate;
        }

        public BigDecimal getAmount() {
            return amount;
        }
    }
}
