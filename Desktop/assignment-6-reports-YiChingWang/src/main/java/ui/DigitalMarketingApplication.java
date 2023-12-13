/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import model.Business.Business;
import model.Business.ConfigureABusiness;
import model.Supplier.SupplierDirectory;
import model.Supplier.SupplierReport;
import model.Supplier.SupplierSummary;

/**
 *
 * @author kal bugrara
 * 
 */

public class DigitalMarketingApplication {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {

        // 1. Populate the model +

        Business business = ConfigureABusiness.createABusiness("NEU", 50,
                30, 50, 300, 3, 10);
        SupplierDirectory sd = business.getSupplierDirectory();
        SupplierReport tmp = sd.generatSupplierReport();

        for (SupplierSummary ss : tmp.getSupplierSummary()) {
            ss.printSupplierSummary();
        }
    }
}
