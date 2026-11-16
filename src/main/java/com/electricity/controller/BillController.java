package com.electricity.controller;

import com.electricity.model.Bill;
import com.electricity.service.BillingService;
import com.electricity.service.ConsumerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BillController {

    private final BillingService billingService;
    private final ConsumerService consumerService;

    public BillController(BillingService billingService, ConsumerService consumerService) {
        this.billingService = billingService;
        this.consumerService = consumerService;
    }

    @GetMapping("/bills")
    public String listBills(@RequestParam(required = false) String consumerId, Model model) {
        List<Bill> bills = billingService.getBillsByConsumerId(consumerId);
        model.addAttribute("bills", bills);
        model.addAttribute("consumerId", consumerId);
        model.addAttribute("title", "Billing History");
        return "bill-history";
    }

    @GetMapping("/bills/{id}")
    public String showBill(@PathVariable int id, Model model) {
        Bill bill = billingService.getBillById(id);
        if (bill == null) {
            throw new IllegalArgumentException("Bill not found.");
        }
        model.addAttribute("bill", bill);
        model.addAttribute("title", "Bill Details");
        return "bill-details";
    }
}
