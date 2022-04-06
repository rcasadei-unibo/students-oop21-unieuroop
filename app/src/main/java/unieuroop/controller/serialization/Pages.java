package unieuroop.controller.serialization;

public enum Pages {
    /**
     * @param 
     */
    DASHBOARD("Dashboard.fxml"),
    /**
     * @param
     */
    STOCK("/pages/Stock/StockView.fxml"),
    /**
     * @param
     */
    STOCK_SET_SEARCH_FILTER("/pages/Stock/StockSetFilterView.fxml"),
    /**
     * @param
     */
    STOCK_BUY_PRODUCTS("/pages/Stock/StockBuyProductsView.fxml"),
    /**
     * @param
     */
    STOCK_LABEL_FOR_STOCK("/pages/Stock/StockLabelForStockView.fxml"),
    /**
     * @param
     */
    SALES("/pages/Sale/MainSale.fxml"),
    /**
     * @param
     */
    LABEL_PRODUCT_SALE("/pages/Sale/labelProduct.fxml"),
    /**
     * @param
     */
    DEPARTMENTS("/pages/Department/DepartmentsView.fxml"),
    /**
     * @param
     */
    DEPARTMENTS_EDIT_PRODUCTS("/pages/Department/DepartmentsEditProductsView.fxml"),
    /**
     * @param
     */
    CLIENTS("/pages/Client/clientView.fxml"),
    /**
     * @param
     */
    STAFF("/pages/Staff/staffView.fxml"),
    /**
     * @param file directory of balance's chart
     */
    BALANCE("/pages/Balance/SpentEarnedChart.fxml"),
    /**
     * @param
     */
    MAIN_CATEGORIES_SOLD("/pages/CategoriesSold/MainCategoriesSold.fxml"),
    /**
     * @param
     */
    TABLE_CATEGORIES_SOLD("/pages/CategoriesSold/tableCategoriesSold.fxml"),
    /**
     * @param
     */
    MAIN_DATE_SOLD("/pages/DateSold/MainDateChart.fxml"),
    /**
     * @param
     */
    CHOSE_CLIENT("/pages/Client/ChoseClient.fxml");

    private final String fileName;

    Pages(final String fileName) {
        this.fileName = fileName;
    }
    /**
     * 
     * @return the path of the file 
     */
    public String getPath() {
        return this.fileName;
    }
}
