/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Supplier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import model.CustomerManagement.CustomerProfile;
import model.OrderManagement.Order;
import model.ProductManagement.Product;
import model.ProductManagement.ProductCatalog;
import model.ProductManagement.ProductSummary;
import model.ProductManagement.ProductsReport;

/**
 *
 * @author kal bugrara
 */

public class Supplier {
    String name;
    ProductCatalog productcatalog;
    ProductsReport productsreport;
    ArrayList<String> customerIds;
    ArrayList<Order> orders;

    public Supplier(String n) {
        name = n;
        productcatalog = new ProductCatalog("software");
        customerIds = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public ProductsReport prepareProductsReport() {
        productsreport = productcatalog.generatProductPerformanceReport("");
        return productsreport;
    }

    public ArrayList<ProductSummary> getProductsAlwaysAboveTarget() {

        if (productsreport == null)
            productsreport = prepareProductsReport();

        return productsreport.getProductsAlwaysAboveTarget();

    }

    public String getName() {
        return name;
    }

    public ProductCatalog getProductCatalog() {
        return productcatalog;
    }

    public void printShortInfo() {
        System.out.println("Checking supplier " + name);
        productcatalog.printShortInfo();
    }

    // create a product list
    public ArrayList<Product> getProductList() {
        return this.productcatalog.getProductList();
    }

    // link order to supplier!!
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    // Method to add a customer who picked a product
    public void addCustomer(String customerId) {
        customerIds.add(customerId);
    }

    // Method to count the number of different customers
    public int getNumberOfDifferentCustomers() {
        Set<String> uniqueCustomers = new HashSet<>(customerIds);
        return uniqueCustomers.size();
    }

    // culculate the total sales to top 5 Customers
    public double top5CustomerSalesTotal() {
        // Map to store the total sales per customer
        Map<String, Double> salesPerCustomer = new HashMap<>();

        // Accumulate the total sales per customer
        for (Order order : orders) {
            String customerId = order.getCustomerId();
            double orderAmount = order.getOrderTotal(); // Assuming getOrderValue() returns the monetary value
            salesPerCustomer.put(customerId, salesPerCustomer.getOrDefault(customerId, 0.0) + orderAmount);
        }

        // Using a priority queue to sort customers by sales
        PriorityQueue<Double> salesQueue = new PriorityQueue<>(Comparator.reverseOrder());

        // Add all sales values to the priority queue
        salesQueue.addAll(salesPerCustomer.values());

        // Calculate the sum of top 5 sales
        double top5Sales = 0.0;
        for (int i = 0; i < 5 && !salesQueue.isEmpty(); i++) {
            top5Sales += salesQueue.poll();
        }
        return top5Sales;
    }

    // get totalSales
    public double getTotalSales() {
        double totalSales = 0;
        ProductsReport report = prepareProductsReport();

        // Using traditional for loop
        for (int i = 0; i < report.productsummarylist.size(); i++) {
            totalSales += report.productsummarylist.get(i).getSalesRevenues();
        }
        return totalSales;
    }

    // use hashset to ensure that customer not be calculate twice or more
    public int calculateUniqueCustomerCount() {
        HashSet<CustomerProfile> uniqueCustomers = new HashSet<>();
        for (Order order : orders) {
            uniqueCustomers.add(order.getCustomer());
        }
        return uniqueCustomers.size();
    }

    @Override
    public String toString() {
        return name;
    }

}
