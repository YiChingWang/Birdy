/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author kal bugrara
 */

public class ProductsReport {

    public ArrayList<ProductSummary> productsummarylist;
    String sortingRule;

    public ProductsReport(String sortingRule) {
        productsummarylist = new ArrayList<ProductSummary>();
        this.sortingRule = sortingRule;
    }

    public void addProductSummary(ProductSummary ps) {
        productsummarylist.add(ps);

        ProductSummaryComparator comparator = new ProductSummaryComparator(sortingRule);
        Collections.sort(productsummarylist, comparator);
    }

    public ProductSummary getTopProductAboveTarget() {
        ProductSummary currentproduct = null;

        for (ProductSummary ps : productsummarylist) {
            if (currentproduct == null) {
                currentproduct = ps;
            } else if (ps.getNumberAboveTarget() > currentproduct.getNumberAboveTarget()) {
                currentproduct = ps;
            }
        }
        return currentproduct;
    }

    public ArrayList<ProductSummary> getProductsAlwaysAboveTarget() {
        ArrayList<ProductSummary> productsalwaysabovetarget = new ArrayList<>(); // temp array list

        for (ProductSummary ps : productsummarylist) {
            if (ps.isProductAlwaysAboveTarget() == true) {
                productsalwaysabovetarget.add(ps);
            }
        }
        return productsalwaysabovetarget;
    }

    public void printProductReport() {
        System.out.println("Product Performace Report");
        System.out.println("Below are product name, actual sales and number of sales above target.");
        for (ProductSummary ps : productsummarylist) {
            int index = productsummarylist.indexOf(ps);
            System.out.print((index + 1) + " ");
            ps.printProductSummary();
        }
    }

}
