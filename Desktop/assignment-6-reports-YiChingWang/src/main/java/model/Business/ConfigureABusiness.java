/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import java.util.List;
import java.util.Random;
import com.github.javafaker.Faker;
import model.CustomerManagement.CustomerDirectory;
import model.CustomerManagement.CustomerProfile;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.Order;
import model.OrderManagement.OrderItem;
import model.Personnel.Person;
import model.Personnel.PersonDirectory;
import model.ProductManagement.Product;
import model.ProductManagement.ProductCatalog;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;

/**
 *
 * @author kal bugrara
 */

public class ConfigureABusiness {

    static int upperPriceLimit = 50;
    static int lowerPriceLimit = 10;
    static int range = 5;
    static int productMaxQuantity = 5;

    // new one to load business ensure that can fulfill demand about hw
    public static Business createABusiness(String name, int supplierCount, int supplierWithProduct, int productCount,
            int customerCount, int orderCount, int itemCount) {

        Business business = new Business(name);

        loadSuppliers(business, supplierCount);

        loadProductsToRandomSuppliers(business, supplierWithProduct, productCount);

        loadCustomers(business, customerCount);

        loadOrdersToEveryCustomers(business, customerCount, orderCount, itemCount);

        return business;
    }

    // original one
    public static Business createABusinessAndLoadALotOfData(String name, int supplierCount, int productCount,
            int customerCount, int orderCount, int itemCount) {

        Business business = new Business(name);

        // Add Suppliers +
        loadSuppliers(business, supplierCount);

        // Add Products +
        loadProducts(business, productCount);

        // Add Customers
        loadCustomers(business, customerCount);

        // Add Order
        loadOrders(business, orderCount, itemCount);

        return business;
    }

    // use faker to create name for suppliers
    public static void loadSuppliers(Business b, int supplierCount) {
        Faker faker = new Faker();

        SupplierDirectory supplierDirectory = b.getSupplierDirectory();

        for (int index = 1; index <= supplierCount; index++) {
            supplierDirectory.newSupplier(faker.company().name());
        }
    }

    // create productCount
    static void loadProducts(Business b, int productCount) {
        SupplierDirectory supplierDirectory = b.getSupplierDirectory(); // return supplier

        for (Supplier supplier : supplierDirectory.getSuplierList()) {

            int randomProductNumber = getRandom(1, productCount);
            ProductCatalog productCatalog = supplier.getProductCatalog();

            for (int index = 1; index <= randomProductNumber; index++) {

                String productName = "Product #" + index + " from " + supplier.getName();
                int randomFloor = getRandom(lowerPriceLimit, lowerPriceLimit + range);
                int randomCeiling = getRandom(upperPriceLimit - range, upperPriceLimit);
                int randomTarget = getRandom(randomFloor, randomCeiling);

                productCatalog.newProduct(productName, randomFloor, randomCeiling, randomTarget);
            }
        }
    }

    // create productCount for every random supplier
    public static void loadProductsToRandomSuppliers(Business b, int supplierWithProduct, int productCount) {
        // return supplier
        SupplierDirectory supplierDirectory = b.getSupplierDirectory();

        for (int i = 0; i < supplierWithProduct; i++) {
            Supplier randomSupplier = supplierDirectory.pickRandomSupplier();
            ProductCatalog productCatalog = randomSupplier.getProductCatalog();

            for (int index = 1; index <= productCount; index++) {

                String productName = "Product #" + index + " (from " + randomSupplier.getName() + ")";
                int randomFloor = getRandom(lowerPriceLimit, lowerPriceLimit + range);
                int randomCeiling = getRandom(upperPriceLimit - range, upperPriceLimit);
                int randomTarget = getRandom(randomFloor, randomCeiling);

                productCatalog.newProduct(productName, randomFloor, randomCeiling, randomTarget);
            }
        }
    }

    static int getRandom(int lower, int upper) {
        Random r = new Random();
        // nextInt(n) will return a number from zero to 'n'. Therefore e.g. if I want
        // numbers from 10 to 15
        // I will have result = 10 + nextInt(5)
        int randomInt = lower + r.nextInt(upper - lower);
        return randomInt;
    }

    // 創造所有的customer
    static void loadCustomers(Business b, int customerCount) {
        CustomerDirectory customerDirectory = b.getCustomerDirectory();
        PersonDirectory personDirectory = b.getPersonDirectory();

        Faker faker = new Faker();

        for (int index = 1; index <= customerCount; index++) {
            Person newPerson = personDirectory.newPerson(faker.name().fullName());
            customerDirectory.newCustomerProfile(newPerson);
        }
    }

    // 原本的load order裡一個customer只有一個訂單
    static void loadOrders(Business b, int orderCount, int itemCount) {

        // 把order連到getMasterOrderList
        MasterOrderList mol = b.getMasterOrderList();

        // pick a random customer (reach to customer directory)
        CustomerDirectory cd = b.getCustomerDirectory();
        SupplierDirectory sd = b.getSupplierDirectory();

        for (int index = 0; index < orderCount; index++) {

            CustomerProfile randomCustomer = cd.pickRandomCustomer();
            if (randomCustomer == null) {
                System.out.println("Cannot generate orders. No customers in the customer directory.");
                return;
            }

            // create an order for that customer
            Order randomOrder = mol.newOrder(randomCustomer);

            // add order itemsssssssssss
            // -- pick a supplier first (randomly)
            // -- pick a product (randomly)
            // -- actual price, quantity

            int randomItemCount = getRandom(1, itemCount);
            for (int itemIndex = 0; itemIndex < randomItemCount; itemIndex++) {

                Supplier randomSupplier = sd.pickRandomSupplier();
                if (randomSupplier == null) {
                    System.out.println("Cannot generate orders. No supplier in the supplier directory.");
                    return;
                }
                ProductCatalog pc = randomSupplier.getProductCatalog();
                Product randomProduct = pc.pickRandomProduct();
                if (randomProduct == null) {
                    System.out.println("Cannot generate orders. No products in the product catalog.");
                    return;
                }

                int randomPrice = getRandom(randomProduct.getFloorPrice(), randomProduct.getCeilingPrice());
                int randomQuantity = getRandom(1, productMaxQuantity);
            }
        }
        // Make sure order items are connected to the order

    }

    // 創新的方法讓新的method可以讓customer有1-3個order
    public static void loadOrdersToEveryCustomers(Business b, int customerCount, int orderCount, int itemCount) {

        MasterOrderList mol = b.getMasterOrderList();
        CustomerDirectory cd = b.getCustomerDirectory();
        SupplierDirectory sd = b.getSupplierDirectory();

        // Get all customers
        List<CustomerProfile> allCustomers = cd.getAllCustomers();

        // get all customer
        for (CustomerProfile everyCustomer : allCustomers) {

            int randomOrderCount = getRandom(1, orderCount);

            for (int orderIndex = 0; orderIndex < randomOrderCount; orderIndex++) {
                // create an order for that customer
                Order randomOrder = mol.newOrder(everyCustomer);

                int randomItemCount = getRandom(1, itemCount);

                for (int itemIndex = 0; itemIndex < randomItemCount; itemIndex++) {

                    Supplier randomSupplier = sd.pickRandomSupplierWithProduct();
                    if (randomSupplier == null) {
                        System.out.println("No supplier has any products.");
                        return;
                    }

                    ProductCatalog productCatalog = randomSupplier.getProductCatalog();
                    Product randomProduct = productCatalog.pickRandomProduct();
                    if (randomProduct == null) {
                        System.out.println("Cannot generate orders. No products in the product catalog.");
                        return;
                    }

                    int randomPrice = getRandom(randomProduct.getFloorPrice(), randomProduct.getCeilingPrice());
                    int randomQuantity = getRandom(1, productMaxQuantity);// 1-itemcount之間

                    OrderItem oi = randomOrder.newOrderItem(randomProduct, randomPrice, randomQuantity);
                    randomProduct.addOrderItem(oi);
                    randomSupplier.addOrder(randomOrder);
                }
            }
        }
    }
}
