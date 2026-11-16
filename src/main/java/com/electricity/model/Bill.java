package com.electricity.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bill {
    private Integer id;
    private String billNumber;
    private String consumerId;
    private String consumerName;
    private LocalDateTime billingDate;
    private int previousReading;
    private int currentReading;
    private int unitsConsumed;
    private BigDecimal energyCharge;
    private BigDecimal fixedCharge;
    private BigDecimal additionalCharges;
    private BigDecimal totalAmount;
    private String tariffSummary;

    public Bill() {
    }

    public Bill(String billNumber, String consumerId, String consumerName, LocalDateTime billingDate,
               int previousReading, int currentReading, int unitsConsumed, BigDecimal energyCharge,
               BigDecimal fixedCharge, BigDecimal additionalCharges, BigDecimal totalAmount,
               String tariffSummary) {
        this.billNumber = billNumber;
        this.consumerId = consumerId;
        this.consumerName = consumerName;
        this.billingDate = billingDate;
        this.previousReading = previousReading;
        this.currentReading = currentReading;
        this.unitsConsumed = unitsConsumed;
        this.energyCharge = energyCharge;
        this.fixedCharge = fixedCharge;
        this.additionalCharges = additionalCharges;
        this.totalAmount = totalAmount;
        this.tariffSummary = tariffSummary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
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

    public LocalDateTime getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDateTime billingDate) {
        this.billingDate = billingDate;
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

    public String getTariffSummary() {
        return tariffSummary;
    }

    public void setTariffSummary(String tariffSummary) {
        this.tariffSummary = tariffSummary;
    }
}
