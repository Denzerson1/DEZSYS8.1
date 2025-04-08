package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create a new Product and associate it with a specific Warehouse
    @PostMapping("/{warehouseID}")
    public Product createProduct(@PathVariable long warehouseID, @RequestBody Product product) {
        return productService.save(product, warehouseID);
    }

    // Get a Product by productID and warehouseID
    @GetMapping("/{warehouseID}/{productID}")
    public Product getProduct(@PathVariable String warehouseID, @PathVariable String productID) {
        return productService.getProductByIDAndWarehouse(productID, warehouseID);
    }
}
