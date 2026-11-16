package com.electricity.service;

import com.electricity.exception.ValidationException;
import com.electricity.model.Consumer;
import com.electricity.model.Bill;
import com.electricity.model.BillCalculationResult;
import com.electricity.model.TariffSlab;
import com.electricity.repository.ConsumerRepository;
import com.electricity.repository.TariffSlabRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TariffService {

    private final TariffSlabRepository tariffSlabRepository;

    public TariffService(TariffSlabRepository tariffSlabRepository) {
        this.tariffSlabRepository = tariffSlabRepository;
    }

    public List<TariffSlab> getSlabs() {
        List<TariffSlab> slabs = tariffSlabRepository.findAll();
        if (slabs.isEmpty()) {
            return defaultSlabs();
        }
        return slabs;
    }

    public BillCalculationResult calculateBill(int previousReading, int currentReading) {
        if (previousReading < 0 || currentReading < 0) {
            throw new ValidationException("Meter readings cannot be negative.");
        }
        if (currentReading < previousReading) {
            throw new ValidationException("Current meter reading cannot be less than previous meter reading.");
        }

        int unitsConsumed = currentReading - previousReading;
        if (unitsConsumed <= 0) {
            throw new ValidationException("Consumed units must be greater than zero.");
        }

        List<TariffSlab> slabs = getSlabs();
        BillCalculationResult result = new BillCalculationResult();
        result.setUnitsConsumed(unitsConsumed);

        BigDecimal totalEnergyCharge = BigDecimal.ZERO;
        BigDecimal fixedCharge = new BigDecimal("150.00");
        BigDecimal additionalCharges = new BigDecimal("65.00");
        StringBuilder summary = new StringBuilder();
        int remaining = unitsConsumed;

        for (TariffSlab slab : slabs) {
            int start = slab.getLowerLimit();
            int end = slab.getUpperLimit();
            int applicableUnits = calculateUnitsForSlab(remaining, start, end);

            if (applicableUnits > 0) {
                BigDecimal amount = slab.getRate().multiply(BigDecimal.valueOf(applicableUnits)).setScale(2, RoundingMode.HALF_UP);
                result.getSlabCharges().add(new BillCalculationResult.TariffSlabCharge(
                        slab.getSlabName(), applicableUnits, slab.getRate(), amount));
                totalEnergyCharge = totalEnergyCharge.add(amount);
                summary.append(slab.getSlabName())
                        .append(" (")
                        .append(applicableUnits)
                        .append(" units @ ")
                        .append(slab.getRate())
                        .append(" = ₹")
                        .append(amount)
                        .append("; ");
                remaining -= applicableUnits;
                if (remaining <= 0) {
                    break;
                }
            }
        }

        BigDecimal totalAmount = totalEnergyCharge.add(fixedCharge).add(additionalCharges).setScale(2, RoundingMode.HALF_UP);
        result.setEnergyCharge(totalEnergyCharge.setScale(2, RoundingMode.HALF_UP));
        result.setFixedCharge(fixedCharge);
        result.setAdditionalCharges(additionalCharges);
        result.setTotalAmount(totalAmount);
        result.setTariffSummary(summary.toString());
        return result;
    }

    public List<TariffSlab> defaultSlabs() {
        List<TariffSlab> defaultSlabs = new ArrayList<>();
        defaultSlabs.add(new TariffSlab("0-50 Units", 0, 50, new BigDecimal("3.50")));
        defaultSlabs.add(new TariffSlab("51-150 Units", 51, 150, new BigDecimal("4.50")));
        defaultSlabs.add(new TariffSlab("151-300 Units", 151, 300, new BigDecimal("6.00")));
        defaultSlabs.add(new TariffSlab("301+ Units", 301, 100000, new BigDecimal("8.00")));
        return defaultSlabs;
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

    private int calculateUnitsForSlab(int remainingUnits, int start, int end) {
        if (remainingUnits <= 0) {
            return 0;
        }
        if (start == 0 && end == 0) {
            return 0;
        }

        int slabSize = end - start + 1;
        if (slabSize <= 0) {
            return 0;
        }

        int result = Math.min(remainingUnits, slabSize);
        if (start == 301) {
            result = remainingUnits;
        }
        return result;
    }
}
