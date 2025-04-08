// Updated DataWarehouseService.java
package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataWarehouseService {

    @Autowired
    private DataWarehouseRepository dataWarehouseRepository;

    public DataWarehouse save(DataWarehouse dataWarehouse) {
        return dataWarehouseRepository.save(dataWarehouse);
    }

    public DataWarehouse getDataWarehouseByID(long warehouseID) {
        return dataWarehouseRepository.findById(warehouseID);
    }

    public DataWarehouse getDataWarehouseById(Long id) {
        Optional<DataWarehouse> dataWarehouse = dataWarehouseRepository.findById(id);
        return dataWarehouse.orElse(null);
    }

    public List<DataWarehouse> getAllDataWarehouses() {
        return dataWarehouseRepository.findAll();
    }

    // Extended: Update a warehouse by warehouseID
    public DataWarehouse updateWarehouseByWarehouseID(long warehouseID, DataWarehouse updatedWarehouse) {
        DataWarehouse existing = dataWarehouseRepository.findById(warehouseID);
        if (existing != null) {
            existing.setWarehouseName(updatedWarehouse.getWarehouseName());
            existing.setWarehouseAddress(updatedWarehouse.getWarehouseAddress());
            existing.setWarehousePostalCode(updatedWarehouse.getWarehousePostalCode());
            existing.setWarehouseCity(updatedWarehouse.getWarehouseCity());
            existing.setWarehouseCountry(updatedWarehouse.getWarehouseCountry());
            existing.setTimestamp(updatedWarehouse.getTimestamp());
            return dataWarehouseRepository.save(existing);
        }
        return null;
    }

    // Extended: Get all data for a warehouseID
    public List<DataWarehouse> getAllByWarehouseID(String warehouseID) {
        return dataWarehouseRepository.findAllByWarehouseID(warehouseID);
    }
}
