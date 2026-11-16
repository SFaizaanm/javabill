package com.electricity.controller;

import com.electricity.model.Consumer;
import com.electricity.model.Bill;
import com.electricity.service.BillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final BillService billService;

    public HomeController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Bill> bills = billService.getAllBills();
        model.addAttribute("bills", bills);
        return "index";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("consumer", new Consumer());
        return "register";
    }

    @PostMapping("/register")
    public String registerConsumer(@ModelAttribute Consumer consumer) {
        billService.registerConsumer(consumer);
        return "redirect:/";
    }

    @GetMapping("/calculate")
    public String showCalculateForm(Model model) {
        model.addAttribute("consumers", billService.getAllConsumers());
        return "calculate";
    }

    @PostMapping("/calculate")
    public String calculateBill(@RequestParam String consumerId,
                               @RequestParam int previousReading,
                               @RequestParam int currentReading) {
        Consumer consumer = billService.getConsumerById(consumerId);
        if (consumer == null) {
            throw new IllegalArgumentException("Consumer not found.");
        }

        Bill bill = billService.generateBill(consumer, previousReading, currentReading);
        return "redirect:/bill/" + bill.getId();
    }

    @GetMapping("/bill/{id}")
    public String showBill(@PathVariable int id, Model model) {
        Bill bill = billService.getBillById(id);
        if (bill == null) {
            throw new IllegalArgumentException("Bill not found.");
        }
        model.addAttribute("bill", bill);
        return "bill";
    }
}
