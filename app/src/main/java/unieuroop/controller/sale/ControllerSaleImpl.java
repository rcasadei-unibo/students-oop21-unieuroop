package unieuroop.controller.sale;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import unieuroop.controller.invoice.InvoicesFactory;
import unieuroop.controller.serialization.Files;
import unieuroop.controller.serialization.Serialization;
import unieuroop.model.department.Department;
import unieuroop.model.person.Client;
import unieuroop.model.product.Product;
import unieuroop.model.sale.Sale;
import unieuroop.model.sale.SaleImpl;
import unieuroop.model.shop.Shop;

public final class ControllerSaleImpl extends Thread implements ControllerSale {
    private final Map<Department, Map<Product, Integer>> reservedProductsMap = new HashMap<>();
    private final Shop shop;

    public ControllerSaleImpl(final Shop shop) {
        this.shop = shop;
    }

    @Override
    public int getQuantityOf(final Product product, final Department department) {
        final int quantity = department.getAllProducts().get(product);
        return this.reservedProductsMap.containsKey(department) && this.reservedProductsMap.get(department).containsKey(product)
                 ? this.reservedProductsMap.get(department).get(product) - quantity : quantity;

    }

    @Override
    public void reserveProducts(final Department departmentInput, final Map<Product, Integer> products) {
        this.reservedProductsMap.merge(departmentInput, products, 
                (olderMap, newerMap) -> {
                    olderMap.putAll(newerMap); return olderMap;
                    });
    }

    @Override
    public void closeSale(final Optional<Client> client) {
        if (!this.reservedProductsMap.isEmpty()) {
            for (final var entry : this.reservedProductsMap.entrySet()) {
                final Department department = this.shop.getDepartments().stream()
                        .filter((d) -> d.equals(entry.getKey())).findFirst().get();
                department.takeProductFromDepartment(entry.getValue());
            }
            final Map<Product, Integer> products = this.reservedProductsMap.entrySet().stream().map((entry) -> entry.getValue())
                        .flatMap((m) -> m.entrySet().stream())
                        .map((entry) -> entry.getKey())
                        .distinct()
                        .collect(Collectors.toMap((product) -> product, (product) -> this.totalQuantityProduct(product)));
            final Sale sale = new SaleImpl(LocalDate.now(), products, client);
            this.shop.addSale(sale);
            try {
                InvoicesFactory.createInvoice(sale, "/home/fabio/Desktop/prova.pdf");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.run();
            this.reservedProductsMap.clear();
        }
    }



    @Override
    public void clearReservedProducts() {
        this.reservedProductsMap.clear();
    }

    private int totalQuantityProduct(final Product product) {
        return this.reservedProductsMap.entrySet().stream()
                .flatMapToInt((entry) -> entry.getValue().entrySet().stream()
                        .filter((e) -> e.getKey().equals(product))
                        .mapToInt((e) -> e.getValue()))
                .sum();
    }
    @Override
    public Map<Product, Integer> getReservedProducts() {
        return Map.copyOf(this.reservedProductsMap.entrySet().stream()
                    .flatMap((entry) -> entry.getValue().entrySet().stream())
                    .map((entry) -> entry.getKey())
                    .distinct()
                    .collect(Collectors.toMap((product) -> product, (product) -> this.totalQuantityProduct(product))));
    }

    @Override
    public boolean isNotReserved() {
        return this.reservedProductsMap.isEmpty();
    }

    private void serializaSale() throws IOException {
        Serialization.<Set<Sale>>serialize(Files.SALES.getPath(), this.shop.getSales());
        Serialization.<Set<Department>>serialize(Files.DEPARTMENTS.getPath(), this.shop.getDepartments());
    }

    @Override
    public void run() {
        try {
            this.serializaSale();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
