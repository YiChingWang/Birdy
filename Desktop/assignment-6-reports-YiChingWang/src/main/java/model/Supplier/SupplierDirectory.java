/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 *
 * @author kal bugrara
 */

public class SupplierDirectory {
    ArrayList<Supplier> suppliers;

    public SupplierDirectory() {
        suppliers = new ArrayList<>();
    }

    public Supplier newSupplier(String n) {
        Supplier supplier = new Supplier(n);
        suppliers.add(supplier);
        return supplier;
    }

    public Supplier findSupplier(String id) {

        for (Supplier supplier : suppliers) {

            if (supplier.getName().equals(id))
                return supplier;
        }
        return null;
    }

    public SupplierReport generatSupplierReport() {

        SupplierReport supplierreport = new SupplierReport();

        for (Supplier sr : suppliers) {
            SupplierSummary ss = new SupplierSummary(sr);
            supplierreport.addSupplierSummary(ss);
        }
        return supplierreport;
    }

    public ArrayList<Supplier> getSuplierList() {
        return suppliers;
    }

    public Supplier pickRandomSupplier() {
        if (suppliers.size() == 0)
            return null;
        Random r = new Random();
        int randomIndex = r.nextInt(suppliers.size());
        return suppliers.get(randomIndex);
    }

    // create a new method
    public Supplier pickRandomSupplierWithProduct() {
        if (suppliers.size() == 0)
            return null;

        List<Supplier> supplierWithProduct = new ArrayList<Supplier>();
        for (Supplier supplier : this.suppliers) {
            if (supplier.getProductCatalog().getProductList().size() != 0) {
                supplierWithProduct.add(supplier);
            }
        }
        Random r = new Random();
        int randomIndex = r.nextInt(supplierWithProduct.size());
        return supplierWithProduct.get(randomIndex);
    }

    public void printShortInfo() {
        System.out.println("Checking what's inside the supplier directory.");
        System.out.println("There are " + suppliers.size() + " suppliers.");
        for (Supplier s : suppliers) {
            s.printShortInfo();
        }
    }

}