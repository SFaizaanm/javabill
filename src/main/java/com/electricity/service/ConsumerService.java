package com.electricity.service;

import com.electricity.model.Consumer;
import com.electricity.repository.ConsumerRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConsumerService {

    private final ConsumerRepository consumerRepository;

    public ConsumerService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    public void saveConsumer(Consumer consumer) {
        if (consumer == null) {
            throw new IllegalArgumentException("Consumer details cannot be null.");
        }
        if (consumer.getConsumerId() == null || consumer.getConsumerId().isBlank()) {
            throw new IllegalArgumentException("Consumer ID is required.");
        }
        consumerRepository.save(consumer);
    }

    public List<Consumer> getAllConsumers() {
        return consumerRepository.findAll();
    }

    public Consumer getConsumerById(int id) {
        return consumerRepository.findById(id);
    }

    public Consumer getConsumerByConsumerId(String consumerId) {
        return consumerRepository.findByConsumerId(consumerId);
    }

    public void updateConsumer(Consumer consumer) {
        if (consumer == null || consumer.getId() == null) {
            throw new IllegalArgumentException("Consumer id is required for update.");
        }
        consumerRepository.update(consumer);
    }

    public void deleteConsumer(int id) {
        consumerRepository.deleteById(id);
    }

    public int getConsumerCount() {
        return consumerRepository.countConsumers();
    }

    public Map<String, Object> getDashboardData() {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("totalConsumers", consumerRepository.countConsumers());
        dashboard.put("totalBills", 0);
        dashboard.put("totalUnits", 0L);
        dashboard.put("totalAmount", 0.0);
        return dashboard;
    }
}
