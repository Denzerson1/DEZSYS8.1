package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DataWarehouseRepository dataWarehouseRepository;
    public Product save(Product product, long id) {
        DataWarehouse warehouseOpt = dataWarehouseRepository.findById(id);
        System.out.println( warehouseOpt.toString() );
        warehouseOpt.addProduct(product);
        dataWarehouseRepository.save(warehouseOpt);
        return product;
    }

    // Get a Product by productID and warehouseID
    public Product getProductByIDAndWarehouse(String productID, String warehouseID) {
        return productRepository.findByProductIDAndDataWarehouse_WarehouseID(productID, warehouseID);
    }
}
