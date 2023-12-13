package model.Supplier;

//this class will get all of summary data from the supplier
public class SupplierSummary {

    Supplier subjectSupplier;
    double totalSales;
    double loyaltyScore;
    double averageSpendingPerCustomer;
    double top5SalesScore;

    public SupplierSummary(Supplier sr) {
        subjectSupplier = sr;
    }

    public Supplier getSubjectSupplier() {
        return subjectSupplier;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public double getLoyaltyScore() {
        return loyaltyScore;
    }

    public double getAverageSpendingPerCustomer() {
        return averageSpendingPerCustomer;
    }

    public double getTop5SalesScore() {
        return top5SalesScore;
    }

    // Loyalty score
    public double calculateLoyaltyScore(int totalCustomerCount) {
        int uniqueCustomers = subjectSupplier.calculateUniqueCustomerCount();
        // totalcustomer = 我設定的用戶總量
        return (double) uniqueCustomers / totalCustomerCount;
    }

    // Average spending per customer
    public double calculateAverageSpendingPerCustomer() {
        double totalSales = subjectSupplier.getTotalSales();
        // 先得到不重複的customer數
        int uniqueCustomers = subjectSupplier.calculateUniqueCustomerCount();
        if (uniqueCustomers == 0) {
            return 0;
        }
        // 再將totalsale/每個unique customer得到答案
        return (double) totalSales / uniqueCustomers;
    }

    // Top 5 Sales Score 計算前五名客戶的銷售總額
    public double calculateTop5SalesScore() {
        // 先得到前五名購買力的人
        double top5Sales = subjectSupplier.top5CustomerSalesTotal();
        // 得到供英商的總銷售額
        double totalSales = subjectSupplier.getTotalSales();
        if (totalSales == 0) {
            return 0;
        }
        // 看比例知道供應商的銷售狀況是否過度依賴top5customers
        return top5Sales / totalSales;
    }

    public void printSupplierSummary() {
        System.out.println("\nSupplier Name: " + subjectSupplier.name +
                "\nTotal Sales: " + subjectSupplier.getTotalSales()
                + "\nLoyalty Score: " + calculateLoyaltyScore(300) +
                "\nAverage Spend: " + calculateAverageSpendingPerCustomer()
                + "\nTop 5 Sale Score: " + calculateTop5SalesScore());
    }

}
