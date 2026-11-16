package com.electricity.service;

import com.electricity.model.Bill;
import com.electricity.model.Consumer;
import com.electricity.repository.BillRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillService {

    private static final BigDecimal FIXED_CHARGE = new BigDecimal("150.00");
    private static final BigDecimal ADDITIONAL_CHARGES = new BigDecimal("65.00");

    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public void registerConsumer(Consumer consumer) {
        if (consumer == null) {
            throw new IllegalArgumentException("Consumer details are required.");
        }
        if (consumer.getConsumerId() == null || consumer.getConsumerId().trim().isEmpty()) {
            throw new IllegalArgumentException("Consumer ID is required.");
        }
        if (consumer.getName() == null || consumer.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Consumer name is required.");
        }
        billRepository.saveConsumer(consumer);
    }

    public List<Consumer> getAllConsumers() {
        return billRepository.findAllConsumers();
    }

    public Consumer getConsumerById(String consumerId) {
        return billRepository.findConsumerById(consumerId);
    }

    public Bill generateBill(Consumer consumer, int previousReading, int currentReading) {
        if (consumer == null) {
            throw new IllegalArgumentException("Consumer is required.");
        }
        if (previousReading < 0 || currentReading < 0) {
            throw new IllegalArgumentException("Meter readings cannot be negative.");
        }
        if (currentReading < previousReading) {
            throw new IllegalArgumentException("Current reading cannot be less than previous reading.");
        }

        int unitsConsumed = currentReading - previousReading;
        if (unitsConsumed <= 0) {
            throw new IllegalArgumentException("Total units consumed must be greater than zero.");
        }

        BigDecimal energyCharge = calculateEnergyCharge(unitsConsumed);
        BigDecimal totalAmount = energyCharge.add(FIXED_CHARGE).add(ADDITIONAL_CHARGES).setScale(2, RoundingMode.HALF_UP);

        Bill bill = new Bill(
                consumer.getConsumerId(),
                consumer.getName(),
                previousReading,
                currentReading,
                unitsConsumed,
                energyCharge,
                FIXED_CHARGE,
                ADDITIONAL_CHARGES,
                totalAmount,
                LocalDateTime.now());

        int newId = billRepository.saveBill(bill);
        bill.setId(newId);
        return bill;
    }

    public Bill getBillById(int id) {
        return billRepository.findBillById(id);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAllBills();
    }

    private BigDecimal calculateEnergyCharge(int unitsConsumed) {
        BigDecimal total = BigDecimal.ZERO;
        int remaining = unitsConsumed;

        int slab1 = Math.min(remaining, 50);
        total = total.add(new BigDecimal("3.50").multiply(BigDecimal.valueOf(slab1)));
        remaining -= slab1;

        if (remaining > 0) {
            int slab2 = Math.min(remaining, 100);
            total = total.add(new BigDecimal("4.50").multiply(BigDecimal.valueOf(slab2)));
            remaining -= slab2;
        }

        if (remaining > 0) {
            int slab3 = Math.min(remaining, 150);
            total = total.add(new BigDecimal("6.00").multiply(BigDecimal.valueOf(slab3)));
            remaining -= slab3;
        }

        if (remaining > 0) {
            total = total.add(new BigDecimal("8.00").multiply(BigDecimal.valueOf(remaining)));
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
