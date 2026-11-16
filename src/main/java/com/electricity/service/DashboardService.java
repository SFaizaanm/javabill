package com.electricity.service;

import com.electricity.model.Bill;
import com.electricity.repository.BillRepository;
import com.electricity.repository.ConsumerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final ConsumerRepository consumerRepository;
    private final BillRepository billRepository;

    public DashboardService(ConsumerRepository consumerRepository, BillRepository billRepository) {
        this.consumerRepository = consumerRepository;
        this.billRepository = billRepository;
    }

    public Map<String, Object> getDashboardSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalConsumers", consumerRepository.countConsumers());
        summary.put("totalBills", billRepository.countBills());
        summary.put("totalUnitsConsumed", billRepository.totalUnitsConsumed());
        summary.put("totalBillingAmount", billRepository.totalBillingAmount());
        summary.put("recentBills", billRepository.findAll().stream().limit(5).toList());
        return summary;
    }
}
