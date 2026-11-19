package com.ecommerce;

import java.sql.*;
import java.util.Scanner;

public class OrderDAO {

    // (a) Insert 3 records
    public void insertOrders(Scanner sc) {

        String sql = "INSERT INTO Orders VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 1; i <= 3; i++) {

                System.out.println("\nEnter Order ID:");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.println("Enter Customer Name:");
                String customer = sc.nextLine();

                System.out.println("Enter Product Name:");
                String product = sc.nextLine();

                System.out.println("Enter Quantity:");
                int quantity = sc.nextInt();

                System.out.println("Enter Unit Price:");
                double price = sc.nextDouble();
                sc.nextLine();

                System.out.println("Enter Status:");
                String status = sc.nextLine();

                ps.setInt(1, id);
                ps.setString(2, customer);
                ps.setString(3, product);
                ps.setInt(4, quantity);
                ps.setDouble(5, price);
                ps.setString(6, status);

                try {
                    ps.executeUpdate();
                    System.out.println("Order inserted successfully!");

                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Duplicate Order ID!");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    // (b) Display orders by status and quantity > 2
    public void displayOrdersByStatus(String status) {

        String sql = "SELECT * FROM Orders WHERE status = ? " +
                "AND quantity > 2 ORDER BY unit_price DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                Order o = new Order(
                        rs.getInt("order_id"),
                        rs.getString("customer_name"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("unit_price"),
                        rs.getString("status")
                );

                o.display();
            }

            if (!found) {
                System.out.println("No orders found.");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    // (c) Increase quantity
    public void increaseQuantity(int id, int additionalQty) {

        String sql = "UPDATE Orders SET quantity = quantity + ? " +
                "WHERE order_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, additionalQty);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Quantity updated successfully!");
            } else {
                System.out.println("Order does not exist.");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    // (d) Update status
    public void updateStatus(int id, String newStatus) {

        String[] validStatuses = {"Pending", "Shipped", "Delivered"};

        boolean valid = false;

        for (String s : validStatuses) {

            if (s.equalsIgnoreCase(newStatus)) {
                newStatus = s;
                valid = true;
                break;
            }
        }

        if (!valid) {
            System.out.println("Invalid status!");
            return;
        }

        String sql = "UPDATE Orders SET status = ? WHERE order_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                System.out.println("Status updated successfully!");

                String select = "SELECT * FROM Orders WHERE order_id = ?";

                try (PreparedStatement ps2 = con.prepareStatement(select)) {

                    ps2.setInt(1, id);

                    ResultSet rs = ps2.executeQuery();

                    if (rs.next()) {

                        Order o = new Order(
                                rs.getInt("order_id"),
                                rs.getString("customer_name"),
                                rs.getString("product_name"),
                                rs.getInt("quantity"),
                                rs.getDouble("unit_price"),
                                rs.getString("status")
                        );

                        o.display();
                    }
                }

            } else {
                System.out.println("Order does not exist.");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    // (e) Display total order value
    public void displayTotalOrderValue() {

        String sql = "SELECT SUM(quantity * unit_price) AS total FROM Orders";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                System.out.println("Total Order Value = " +
                        rs.getDouble("total"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    // (e) Delete only Cancelled orders
    public void deleteCancelledOrder(int id) {

        String sql = "DELETE FROM Orders WHERE order_id = ? " +
                "AND status = 'Cancelled'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Order deleted successfully!");
            } else {
                System.out.println("Cannot delete this order.");
                System.out.println("Order not found or status is not Cancelled.");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}