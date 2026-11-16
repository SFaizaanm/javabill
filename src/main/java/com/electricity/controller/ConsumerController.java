package com.electricity.controller;

import com.electricity.exception.ValidationException;
import com.electricity.model.Consumer;
import com.electricity.service.ConsumerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/consumers")
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @GetMapping
    public String listConsumers(Model model) {
        List<Consumer> consumers = consumerService.getAllConsumers();
        model.addAttribute("consumers", consumers);
        model.addAttribute("title", "Consumer Management");
        return "consumers";
    }

    @GetMapping("/new")
    public String newConsumerForm(Model model) {
        model.addAttribute("consumer", new Consumer());
        model.addAttribute("title", "Register Consumer");
        return "consumer-form";
    }

    @PostMapping("/save")
    public String saveConsumer(@ModelAttribute Consumer consumer, RedirectAttributes redirectAttributes) {
        try {
            consumerService.saveConsumer(consumer);
            redirectAttributes.addFlashAttribute("successMessage", "Consumer registered successfully.");
            return "redirect:/consumers";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/consumers/new";
        }
    }

    @GetMapping("/edit/{id}")
    public String editConsumer(@PathVariable int id, Model model) {
        Consumer consumer = consumerService.getConsumerById(id);
        model.addAttribute("consumer", consumer);
        model.addAttribute("title", "Edit Consumer");
        return "consumer-form";
    }

    @PostMapping("/update/{id}")
    public String updateConsumer(@PathVariable int id,
                                @ModelAttribute Consumer consumer,
                                RedirectAttributes redirectAttributes) {
        try {
            consumer.setId(id);
            consumerService.updateConsumer(consumer);
            redirectAttributes.addFlashAttribute("successMessage", "Consumer updated successfully.");
            return "redirect:/consumers";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/consumers/edit/" + id;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteConsumer(@PathVariable int id, RedirectAttributes redirectAttributes) {
        consumerService.deleteConsumer(id);
        redirectAttributes.addFlashAttribute("successMessage", "Consumer deleted successfully.");
        return "redirect:/consumers";
    }
}
