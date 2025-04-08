### Protocol: DEZSYS_GK81_WAREHOUSE_ORM



---

### **1. Introduction**

This protocol documents the steps taken to set up a Data Warehouse application using Spring Boot with MySQL as the database, integrating Object-Relational Mapping (ORM) concepts using JPA (Java Persistence API). This was done as part of a tutorial and exercise to implement a relational database model and solve issues encountered along the way.

---

### **2. Initial Setup**

I began the project by following the Spring Boot tutorial for accessing data with MySQL, which was straightforward, and documented each step carefully:

1. **Installing Prerequisites:**
   
   - **Gradle 8+** was installed (version 8.1).
   
   - **Java SDK 18** was used.
   
   - **MySQL** (Docker container setup) was configured, along with MySQL Workbench for management.
   
   - MySQL container was pulled and set up:
     
     ```bash
     docker run --name=mysql-container -e MYSQL_ROOT_PASSWORD=root -d mysql:latest
     ```

2. **Creating MySQL Database and Tables:** Once the MySQL Docker container was running, I accessed the MySQL shell with:
   
   ```bash
   mysqlsh root@localhost
   ```
   
   I created the database and ensured the connection was working:
   
   ```sql
   CREATE DATABASE example;
   USE example;
   ```

---

### **3. Setting Up Spring Boot with MySQL**

1. **Spring Boot Application Configuration:** I initialized the Spring Boot project using Gradle. In the `build.gradle` file, I added dependencies for Spring Data JPA and MySQL.
   
   ```groovy
   dependencies {
      implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
      implementation 'mysql:mysql-connector-java'
      implementation 'org.springframework.boot:spring-boot-starter-web'
   }
   ```

2. **`application.properties` Configuration:** I configured the MySQL connection in the `src/main/resources/application.properties` file.
   
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/example
   spring.datasource.username=root
   spring.datasource.password=root
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
   ```
   
   This step was quite straightforward, but I encountered a few connectivity issues, which were solved by ensuring that MySQL was running and the Docker container’s port 3306 was exposed correctly.

---

### **4. Entity Classes and Data Warehouse Model**

Next, I worked on setting up the entity classes for the Data Warehouse system. I modeled the `DataWarehouse` and `Product` entities as described in the problem statement.

1. **DataWarehouse Entity:**
   
   ```java
   @Entity
   public class DataWarehouse {
      @Id
      private Long id;
   
      @Column(unique = true)
      private String warehouseID;
      private String warehouseName;
      private String warehouseAddress;
      private String warehousePostalCode;
      private String warehouseCity;
      private String warehouseCountry;
   
      @OneToMany(mappedBy = "dataWarehouse", cascade = CascadeType.ALL)
      private List<Product> products;
   }
   ```

2. **Product Entity:**
   
   ```java
   @Entity
   public class Product {
      @Id
      private String productID;
      private String productName;
      private String productCategory;
      private int productQuantity;
      private String productUnit;
   
      @ManyToOne
      @JoinColumn(name = "warehouse_id")
      private DataWarehouse dataWarehouse;
   }
   ```
   
   - **Issue Encountered:** The relationship between `DataWarehouse` and `Product` wasn't behaving as expected at first. Initially, products were not being correctly saved to their respective warehouses.
   
   - **Solution:** Added the `@ManyToOne` annotation in the `Product` entity to specify that each product belongs to one data warehouse. Similarly, `@OneToMany` was used on the `DataWarehouse` entity to define the relationship.

---

### **5. Repository Layer**

Next, I created the repository interfaces for both entities. Spring Data JPA was used to auto-generate the CRUD operations.

```java
public interface DataWarehouseRepository extends JpaRepository<DataWarehouse, Long> {
    Optional<DataWarehouse> findByWarehouseID(String warehouseID);
}
```

```java
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByDataWarehouse(DataWarehouse dataWarehouse);
}
```

These repositories worked out of the box, but I made a minor change later to handle potential `null` values more gracefully by wrapping queries in `Optional` objects where necessary.

---

### **6. Writing Business Logic**

I wrote the business logic for adding products to a warehouse, updating them, and querying the database. For example:

```java
@Service
public class WarehouseService {

    @Autowired
    private DataWarehouseRepository dataWarehouseRepository;

    @Autowired
    private ProductRepository productRepository;

    public Product addProductToWarehouse(Product product, String warehouseID) {
        DataWarehouse dataWarehouse = dataWarehouseRepository.findByWarehouseID(warehouseID)
            .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        product.setDataWarehouse(dataWarehouse);
        return productRepository.save(product);
    }
}
```

I faced some issues here because the `findByWarehouseID` method was occasionally returning more than one result, leading to a `NonUniqueResultException`. This was resolved by ensuring the database integrity of the warehouse IDs and by updating the method to use `Optional`.

---

### **7. Running the Application**

I ran the application using the following Gradle command:

```bash
gradle bootRun
```

The application started successfully, and I tested the `addProductToWarehouse` functionality by inserting a product into a data warehouse. Products were added, and I could retrieve them based on warehouseID.

---

### **8. Extending the Data Model**

I extended the data model to include more warehouses and products. I inserted two data warehouse records and ten product records as per the requirements.

```sql
INSERT INTO data_warehouse (warehouseID, warehouseName, warehouseAddress, warehousePostalCode, warehouseCity, warehouseCountry) 
VALUES ('001', 'Linz Bahnhof', 'Bahnhofsstrasse 27/9', 'Linz', 'Linz', 'Austria');

INSERT INTO product (productID, productName, productCategory, productQuantity, productUnit, warehouse_id) 
VALUES ('00-443175', 'Bio Orangensaft Sonne', 'Getraenk', 2500, 'Packung 1L', 1);
```

---

### **9. Problems and Fixes**

- **Problem:** MySQL connection error.
  
  - **Fix:** Ensured Docker container ports were correctly exposed and MySQL was running.

- **Problem:** `NonUniqueResultException` when querying by `warehouseID`.
  
  - **Fix:** Fixed the database integrity by ensuring `warehouseID` values were unique, and modified the query to return `Optional` to handle `null` cases.

- **Problem:** Products not linked to Data Warehouse correctly.
  
  - **Fix:** Correctly set up the `@ManyToOne` and `@OneToMany` annotations to define relationships.

---

### **10. Advanced Features (Extended Requirements)**

1. **Fetching Data by WarehouseID and ProductID:** I extended the `DataWarehouseRepository` and `ProductRepository` with methods to fetch products by warehouse and update warehouses.
   
   ```java
   public List<Product> findProductsByWarehouseID(String warehouseID);
   public Product findProductByWarehouseAndProductID(String warehouseID, String productID);
   ```

2. **Switching to PostgreSQL:** I also tested the application with PostgreSQL by changing the `application.properties` to point to a PostgreSQL database and adding the appropriate PostgreSQL dependency in `build.gradle`. The switch was smooth, with only minor configuration adjustments required.

---

### **11. Questions**

1. **What is ORM and how is JPA used?** ORM (Object Relational Mapping) is a programming technique for converting data between incompatible type systems. JPA (Java Persistence API) is used to manage the relational data in Java applications, mapping Java objects to database tables.

2. **What is the `application.properties` used for?** `application.properties` is used to configure the application's settings, such as database connections, server settings, logging, and other environment-specific properties.

3. **Which annotations are frequently used for entity types?**
   
   - `@Entity`: Marks a class as a persistent entity.
   
   - `@Id`: Marks the primary key of the entity.
   
   - `@OneToMany`, `@ManyToOne`, `@OneToOne`: Define relationships between entities.

4. **What methods do you need for CRUD operations?** The basic methods provided by Spring Data’s `CrudRepository` are:
   
   - `save()`
   
   - `findById()`
   
   - `findAll()`
   
   - `deleteById()`

---

### **12. Conclusion**

The project was successfully completed, and I was able to set up a Data Warehouse application using Spring Boot and MySQL (with an optional PostgreSQL setup). The ORM setup with JPA was implemented, and I handled the relationship between the `DataWarehouse` and `Product` entities. All requirements were met, and issues were resolved systematically throughout the process.
