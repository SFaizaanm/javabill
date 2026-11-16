package com.electricity.service;

import com.electricity.exception.ValidationException;
import com.electricity.model.Bill;
import com.electricity.model.BillCalculationResult;
import com.electricity.model.Consumer;
import com.electricity.repository.BillRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillingService {

    private final BillRepository billRepository;
    private final TariffService tariffService;

    public BillingService(BillRepository billRepository, TariffService tariffService) {
        this.billRepository = billRepository;
        this.tariffService = tariffService;
    }

    public Bill generateBill(Consumer consumer, int previousReading, int currentReading) {
        if (consumer == null) {
            throw new ValidationException("Consumer details are required.");
        }
        if (consumer.getConsumerId() == null || consumer.getConsumerId().isBlank()) {
            throw new ValidationException("Consumer ID is required.");
        }

        BillCalculationResult calculation = tariffService.calculateBill(previousReading, currentReading);
        String billNumber = "EB" + System.currentTimeMillis();

        Bill bill = new Bill();
        bill.setBillNumber(billNumber);
        bill.setConsumerId(consumer.getConsumerId());
        bill.setConsumerName(consumer.getName());
        bill.setBillingDate(LocalDateTime.now());
        bill.setPreviousReading(previousReading);
        bill.setCurrentReading(currentReading);
        bill.setUnitsConsumed(calculation.getUnitsConsumed());
        bill.setEnergyCharge(calculation.getEnergyCharge());
        bill.setFixedCharge(calculation.getFixedCharge());
        bill.setAdditionalCharges(calculation.getAdditionalCharges());
        bill.setTotalAmount(calculation.getTotalAmount());
        bill.setTariffSummary(calculation.getTariffSummary());

        return billRepository.save(bill);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public List<Bill> getBillsByConsumerId(String consumerId) {
        if (consumerId == null || consumerId.isBlank()) {
            return billRepository.findAll();
        }
        return billRepository.findByConsumerId(consumerId.trim());
    }

    public Bill getBillById(int id) {
        return billRepository.findById(id);
    }

    public BigDecimal getTotalBillingAmount() {
        return billRepository.totalBillingAmount();
    }

    public int getTotalBillsGenerated() {
        return billRepository.countBills();
    }

    public BigDecimal getTotalUnitsConsumed() {
        return billRepository.totalUnitsConsumed();
    }
}
