package org.example;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    // Custom query method to find a Product by productID and associated DataWarehouse's warehouseID
    Product findByProductIDAndDataWarehouse_WarehouseID(String productID, String warehouseID);
}
