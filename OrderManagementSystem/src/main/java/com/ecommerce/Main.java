package com.ecommerce;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        OrderDAO dao = new OrderDAO();

        int choice;

        do {

            System.out.println("\n===== ORDER MANAGEMENT SYSTEM =====");
            System.out.println("1. Insert 3 Orders");
            System.out.println("2. Display Orders by Status");
            System.out.println("3. Increase Quantity");
            System.out.println("4. Update Order Status");
            System.out.println("5. Display Total Value");
            System.out.println("6. Delete Cancelled Order");
            System.out.println("7. Exit");

            System.out.print("Enter Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    dao.insertOrders(sc);
                    break;

                case 2:
                    System.out.print("Enter Status: ");
                    String status = sc.nextLine();

                    dao.displayOrdersByStatus(status);
                    break;

                case 3:
                    System.out.print("Enter Order ID: ");
                    int id = sc.nextInt();

                    System.out.print("Enter Additional Quantity: ");
                    int qty = sc.nextInt();

                    dao.increaseQuantity(id, qty);
                    break;

                case 4:
                    System.out.print("Enter Order ID: ");
                    int orderId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter New Status: ");
                    String newStatus = sc.nextLine();

                    dao.updateStatus(orderId, newStatus);
                    break;

                case 5:
                    dao.displayTotalOrderValue();
                    break;

                case 6:
                    System.out.print("Enter Order ID: ");
                    int deleteId = sc.nextInt();

                    dao.deleteCancelledOrder(deleteId);
                    break;

                case 7:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 7);

        sc.close();
    }
}