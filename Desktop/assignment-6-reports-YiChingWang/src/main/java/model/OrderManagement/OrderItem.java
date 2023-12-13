/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.OrderManagement;

import model.ProductManagement.Product;

/**
 *
 * @author kal bugrara
 */

public class OrderItem {

    Product selectedproduct;
    int actualPrice;
    int quantity;

    public OrderItem(Product p, int paidprice, int q) {
        selectedproduct = p;
        p.addOrderItem(this);
        quantity = q;
        this.actualPrice = paidprice;
    }

    // 計算單個訂單的總額
    public int getOrderItemTotal() {
        return actualPrice * quantity;
    }

    // 用target price計算單個商品的銷售總額
    public int getOrderItemTargetTotal() {
        return selectedproduct.getTargetPrice() * quantity;
    }

    // 根據賣家的利潤率相對於目標值是高於、低於還是等於，分別返回正值、負值或零。
    public int calculatePricePerformance() {
        return (actualPrice - selectedproduct.getTargetPrice()) * quantity;
    }

    public boolean isActualAboveTarget() {
        if (actualPrice > selectedproduct.getTargetPrice()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isActualBelowTarget() {
        if (actualPrice < selectedproduct.getTargetPrice()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isActualATTarget() {
        if (actualPrice == selectedproduct.getTargetPrice()) {
            return true;
        } else {
            return false;
        }

    }

    public Product getSelectedProduct() {
        return selectedproduct;
    }

    public int getActualPrice() {
        return actualPrice;

    }

    public int getQuantity() {
        return quantity;
    }

}