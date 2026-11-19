package com.ecommerce;

public class Order {

    int orderId;
    String customerName;
    String productName;
    int quantity;
    double unitPrice;
    String status;

    public Order(int orderId, String customerName, String productName,
                 int quantity, double unitPrice, String status) {

        this.orderId = orderId;
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.status = status;
    }

    public void display() {

        System.out.println("Order ID: " + orderId);
        System.out.println("Customer Name: " + customerName);
        System.out.println("Product Name: " + productName);
        System.out.println("Quantity: " + quantity);
        System.out.println("Unit Price: " + unitPrice);
        System.out.println("Status: " + status);

        System.out.println("---------------------------");
    }
}