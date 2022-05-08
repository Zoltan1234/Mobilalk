package com.example.android_ordering;

import java.util.List;

public class Order {
    private String id;
    private String name;
    private String email;
    private String address;
    private String description;
    private String orderDate;
    private List<Shoppingitem> orderList;
    private String sumprice;
    private String deliveryFee;

    public Order() { }

    public Order(String id, String name, String email, String address, String description, String orderDate, List<Shoppingitem> orderList, String sumprice, String deliveryFee) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.description = description;
        this.orderDate = orderDate;
        this.orderList = orderList;
        this.sumprice = sumprice;
        this.deliveryFee = deliveryFee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<Shoppingitem> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Shoppingitem> orderList) {
        this.orderList = orderList;
    }

    public String getSumprice() {
        return sumprice;
    }

    public void setSumprice(String sumprice) {
        this.sumprice = sumprice;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}
