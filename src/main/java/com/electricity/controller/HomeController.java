package com.electricity.controller;

import com.electricity.exception.ValidationException;
import com.electricity.model.Bill;
import com.electricity.model.Consumer;
import com.electricity.service.BillingService;
import com.electricity.service.ConsumerService;
import com.electricity.service.DashboardService;
import com.electricity.service.TariffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    private final ConsumerService consumerService;
    private final BillingService billingService;
    private final TariffService tariffService;
    private final DashboardService dashboardService;

    public HomeController(ConsumerService consumerService, BillingService billingService,
                          TariffService tariffService, DashboardService dashboardService) {
        this.consumerService = consumerService;
        this.billingService = billingService;
        this.tariffService = tariffService;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Electricity Bill Calculator");
        model.addAttribute("summary", dashboardService.getDashboardSummary());
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("summary", dashboardService.getDashboardSummary());
        model.addAttribute("title", "Admin Dashboard");
        return "dashboard";
    }

    @GetMapping("/tariff")
    public String tariffs(Model model) {
        model.addAttribute("tariffs", tariffService.getSlabs());
        model.addAttribute("title", "Tariff Slabs");
        return "tariff";
    }

    @GetMapping("/calculate")
    public String calculateForm(Model model) {
        model.addAttribute("title", "Calculate Electricity Bill");
        return "billing-form";
    }

    @PostMapping("/calculate")
    public String calculateBill(@RequestParam String consumerId,
                                @RequestParam int previousReading,
                                @RequestParam int currentReading,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        try {
            Consumer consumer = consumerService.getConsumerByConsumerId(consumerId.trim());
            if (consumer == null) {
                throw new ValidationException("Consumer ID not found. Please register the consumer first.");
            }
            var result = tariffService.calculateBill(previousReading, currentReading);
            model.addAttribute("consumer", consumer);
            model.addAttribute("result", result);
            model.addAttribute("previousReading", previousReading);
            model.addAttribute("currentReading", currentReading);
            model.addAttribute("title", "Bill Calculation Result");
            return "bill-calculation";
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/calculate";
        }
    }

    @PostMapping("/bills/generate")
    public String generateBill(@RequestParam String consumerId,
                               @RequestParam int previousReading,
                               @RequestParam int currentReading,
                               RedirectAttributes redirectAttributes) {
        try {
            Consumer consumer = consumerService.getConsumerByConsumerId(consumerId.trim());
            if (consumer == null) {
                throw new ValidationException("Consumer ID not found.");
            }
            Bill generatedBill = billingService.generateBill(consumer, previousReading, currentReading);
            redirectAttributes.addFlashAttribute("successMessage", "Bill generated successfully.");
            return "redirect:/bills/" + generatedBill.getId();
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/calculate";
        }
    }
}
