package model.Supplier;

import java.util.ArrayList;

import model.CustomerManagement.CustomerSummary;
import model.ProductManagement.ProductSummary;
import model.Supplier.SupplierSummary;

public class SupplierReport {

    ArrayList<SupplierSummary> suppliersummarylist; // many rows
    // String name;
    String sortingRule;

    public SupplierReport() {
        suppliersummarylist = new ArrayList<SupplierSummary>();

    }

    public ArrayList<SupplierSummary> getSupplierSummary() {
        return suppliersummarylist;
    }

    public void addSupplierSummary(SupplierSummary ss) {
        suppliersummarylist.add(ss);
    }
}
