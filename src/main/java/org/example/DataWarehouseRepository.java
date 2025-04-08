// Updated DataWarehouseRepository.java
package org.example;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DataWarehouseRepository extends JpaRepository<DataWarehouse, Long> {
    DataWarehouse findById(long id);

    List<DataWarehouse> findAllByWarehouseID(String warehouseID);
}