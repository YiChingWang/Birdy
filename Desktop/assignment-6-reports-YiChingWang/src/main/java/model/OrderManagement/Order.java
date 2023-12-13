/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.OrderManagement;

import java.util.ArrayList;

import model.CustomerManagement.CustomerProfile;
import model.MarketModel.MarketChannelAssignment;
import model.ProductManagement.Product;
import model.SalesManagement.SalesPersonProfile;

/**
 *
 * @author kal bugrara
 */

public class Order {

    ArrayList<OrderItem> orderitems;
    CustomerProfile customer;
    SalesPersonProfile salesperson;
    MarketChannelAssignment mca;
    String status;
    double totalPrice;

    public Order() {
    }

    public Order(CustomerProfile cp) {
        orderitems = new ArrayList<>();
        customer = cp;
        customer.addCustomerOrder(this);
        salesperson = null;
        status = "in process";
    }

    public Order(CustomerProfile cp, SalesPersonProfile ep) {
        orderitems = new ArrayList<>();
        customer = cp;
        salesperson = ep;
        customer.addCustomerOrder(this);
        salesperson.addSalesOrder(this);
    }

    public OrderItem newOrderItem(Product p, int actualprice, int q) {
        OrderItem oi = new OrderItem(p, actualprice, q);
        orderitems.add(oi);
        return oi;
    }

    // get order total use for loop
    public int getOrderTotal() {
        int sum = 0;
        for (OrderItem oi : orderitems) {
            sum = sum + oi.getOrderItemTotal();
        }
        return sum;
    }

    public int getOrderPricePerformance() {
        int sum = 0;
        for (OrderItem oi : orderitems) {
            sum = sum + oi.calculatePricePerformance(); // positive and negative values
        }
        return sum;
    }

    public int getNumberOfOrderItemsAboveTarget() {
        int sum = 0;
        for (OrderItem oi : orderitems) {
            if (oi.isActualAboveTarget() == true) {
                sum = sum + 1;
            }
        }
        return sum;
    }

    // sum all the item targets and compare to the total of the order
    public boolean isOrderAboveTotalTarget() {
        int sum = 0;
        for (OrderItem oi : orderitems) {
            sum = sum + oi.getOrderItemTargetTotal(); // product targets are added
        }
        if (getOrderTotal() > sum) {
            return true;
        } else {
            return false;
        }
    }

    public void CancelOrder() {
        status = "Cancelled";
    }

    public void Submit() {
        status = "Submitted";
    }

    public int getNumberOfItems() {
        return orderitems.size();
    }

    public String getCustomerId() {
        return customer.getCustomerId();
    }

    public CustomerProfile getCustomer() {
        return this.customer;
    }

    public double getOrderValue() {
        // Assuming there's a field in Order class that stores the total price
        return this.totalPrice;
    }
}
