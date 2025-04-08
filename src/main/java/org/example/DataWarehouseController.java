// Updated DataWarehouseController.java
package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/datawarehouse")
public class DataWarehouseController {

    @Autowired
    private DataWarehouseService dataWarehouseService;

    @PostMapping("/add")
    public DataWarehouse addDataWarehouse(@RequestBody DataWarehouse dataWarehouse) {
        return dataWarehouseService.save(dataWarehouse);
    }

    @GetMapping("/get/{id}")
    public DataWarehouse getDataWarehouseByID(@PathVariable Long id) {
        return dataWarehouseService.getDataWarehouseById(id);
    }

    @GetMapping
    public List<DataWarehouse> getAllDataWarehouses() {
        return dataWarehouseService.getAllDataWarehouses();
    }

    // Extended: Get all by warehouseID
    @GetMapping("/all/{warehouseID}")
    public List<DataWarehouse> getAllByWarehouseID(@PathVariable String warehouseID) {
        return dataWarehouseService.getAllByWarehouseID(warehouseID);
    }

    // Extended: Update a warehouse by warehouseID
    @PutMapping("/update/{warehouseID}")
    public DataWarehouse updateWarehouse(@PathVariable long warehouseID, @RequestBody DataWarehouse updatedWarehouse) {
        return dataWarehouseService.updateWarehouseByWarehouseID(warehouseID, updatedWarehouse);
    }
}