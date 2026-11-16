package com.electricity.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bill {
    private int id;
    private String consumerId;
    private String consumerName;
    private int previousReading;
    private int currentReading;
    private int unitsConsumed;
    private BigDecimal energyCharge;
    private BigDecimal fixedCharge;
    private BigDecimal additionalCharges;
    private BigDecimal totalAmount;
    private LocalDateTime billDate;

    public Bill() {
    }

    public Bill(String consumerId, String consumerName, int previousReading, int currentReading,
                int unitsConsumed, BigDecimal energyCharge, BigDecimal fixedCharge,
                BigDecimal additionalCharges, BigDecimal totalAmount, LocalDateTime billDate) {
        this.consumerId = consumerId;
        this.consumerName = consumerName;
        this.previousReading = previousReading;
        this.currentReading = currentReading;
        this.unitsConsumed = unitsConsumed;
        this.energyCharge = energyCharge;
        this.fixedCharge = fixedCharge;
        this.additionalCharges = additionalCharges;
        this.totalAmount = totalAmount;
        this.billDate = billDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public int getPreviousReading() {
        return previousReading;
    }

    public void setPreviousReading(int previousReading) {
        this.previousReading = previousReading;
    }

    public int getCurrentReading() {
        return currentReading;
    }

    public void setCurrentReading(int currentReading) {
        this.currentReading = currentReading;
    }

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

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }
}
