package com.electricity.service;

import com.electricity.exception.ValidationException;
import com.electricity.model.BillCalculationResult;
import com.electricity.model.TariffSlab;
import com.electricity.repository.TariffSlabRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TariffService {

    private static final BigDecimal FIXED_CHARGE = new BigDecimal("150.00");
    private static final BigDecimal ADDITIONAL_CHARGES = new BigDecimal("65.00");

    private final TariffSlabRepository tariffSlabRepository;

    public TariffService(TariffSlabRepository tariffSlabRepository) {
        this.tariffSlabRepository = tariffSlabRepository;
    }

    public List<TariffSlab> getSlabs() {
        List<TariffSlab> slabs = tariffSlabRepository.findAll();
        return slabs.isEmpty() ? defaultSlabs() : slabs;
    }

    public BillCalculationResult calculateBill(int previousReading, int currentReading) {
        if (previousReading < 0 || currentReading < 0) {
            throw new ValidationException("Meter readings cannot be negative.");
        }
        if (currentReading < previousReading) {
            throw new ValidationException("Current meter reading cannot be less than previous meter reading.");
        }

        int unitsConsumed = currentReading - previousReading;
        List<TariffSlab> slabs = getSlabs();
        BillCalculationResult result = new BillCalculationResult();
        result.setUnitsConsumed(unitsConsumed);

        BigDecimal energyCharge = BigDecimal.ZERO;
        int remaining = unitsConsumed;
        StringBuilder summary = new StringBuilder();

        for (TariffSlab slab : slabs) {
            if (remaining == 0) break;
            int capacity = slab.getLowerLimit() == 0
                    ? slab.getUpperLimit()
                    : slab.getUpperLimit() - slab.getLowerLimit() + 1;
            if (capacity <= 0 || slab.getRate() == null || slab.getRate().signum() < 0) {
                throw new ValidationException("Tariff slab configuration is invalid.");
            }

            int slabUnits = Math.min(remaining, capacity);
            BigDecimal slabAmount = slab.getRate()
                    .multiply(BigDecimal.valueOf(slabUnits))
                    .setScale(2, RoundingMode.HALF_UP);
            result.getSlabCharges().add(new BillCalculationResult.TariffSlabCharge(
                    slab.getSlabName(), slabUnits, slab.getRate(), slabAmount));
            energyCharge = energyCharge.add(slabAmount);
            remaining -= slabUnits;

            if (summary.length() > 0) summary.append("; ");
            summary.append(slab.getSlabName()).append(" (").append(slabUnits)
                    .append(" units @ ₹").append(slab.getRate())
                    .append(" = ₹").append(slabAmount).append(")");
        }

        if (remaining > 0) {
            throw new ValidationException("Tariff slabs do not cover the full consumption range.");
        }

        BigDecimal total = energyCharge.add(FIXED_CHARGE).add(ADDITIONAL_CHARGES)
                .setScale(2, RoundingMode.HALF_UP);
        result.setEnergyCharge(energyCharge.setScale(2, RoundingMode.HALF_UP));
        result.setFixedCharge(FIXED_CHARGE);
        result.setAdditionalCharges(ADDITIONAL_CHARGES);
        result.setTotalAmount(total);
        result.setTariffSummary(summary.toString());
        return result;
    }

    public List<TariffSlab> defaultSlabs() {
        List<TariffSlab> slabs = new ArrayList<>();
        slabs.add(new TariffSlab("0-50 Units", 0, 50, new BigDecimal("3.50")));
        slabs.add(new TariffSlab("51-150 Units", 51, 150, new BigDecimal("4.50")));
        slabs.add(new TariffSlab("151-300 Units", 151, 300, new BigDecimal("6.00")));
        slabs.add(new TariffSlab("301+ Units", 301, Integer.MAX_VALUE, new BigDecimal("8.00")));
        return slabs;
    }

    public Map<String, Object> buildSummary(int previousReading, int currentReading) {
        BillCalculationResult result = calculateBill(previousReading, currentReading);
        Map<String, Object> summary = new HashMap<>();
        summary.put("unitsConsumed", result.getUnitsConsumed());
        summary.put("energyCharge", result.getEnergyCharge());
        summary.put("fixedCharge", result.getFixedCharge());
        summary.put("additionalCharges", result.getAdditionalCharges());
        summary.put("totalAmount", result.getTotalAmount());
        summary.put("slabCharges", result.getSlabCharges());
        return summary;
    }
}
