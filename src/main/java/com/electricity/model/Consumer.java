package com.electricity.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Consumer {
    private Integer id;
    private String consumerId;
    private String name;
    private String address;
    private String mobileNumber;
    private String category;
    private LocalDateTime createdAt;

    public Consumer() {
    }

    public Consumer(String consumerId, String name, String address, String mobileNumber, String category) {
        this.consumerId = consumerId;
        this.name = name;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
