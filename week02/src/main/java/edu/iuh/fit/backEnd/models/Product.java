package edu.iuh.fit.backEnd.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.iuh.fit.backEnd.enums.ProductStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
@NamedQueries({
        @NamedQuery(name = "Product.getAllProduct", query = "select p from Product p order by p.status desc"),
        @NamedQuery(name = "Product.findById", query = "select p from Product p where p.id = :id")
})
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "unit", length = 50)
    private String unit;

    @Column(name = "manufacturer_name")
    private String manufacturerName;

    @Column(name = "status", nullable = false)
    private ProductStatus status;


    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private Set<Productimage> productimages;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private Set<Productprice> productprices;
    public Product() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public Set<Productimage> getProductimages() {
        return productimages;
    }

    public void setProductimages(Set<Productimage> productimages) {
        this.productimages = productimages;
    }

    public Set<Productprice> getProductprices() {
        return productprices;
    }

    public void setProductprices(Set<Productprice> productprices) {
        this.productprices = productprices;
    }

    public Product(String name, String description, String unit, String manufacturerName, ProductStatus status) {
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.manufacturerName = manufacturerName;
        this.status = status;
    }

    public Product(Long id, String name, String description, String unit, String manufacturerName, ProductStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.manufacturerName = manufacturerName;
        this.status = status;
    }

    public Product(Long id, String name, String description, String unit, String manufacturerName, ProductStatus status, Set<Productimage> productimages, Set<Productprice> productprices) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.manufacturerName = manufacturerName;
        this.status = status;
        this.productimages = productimages;
        this.productprices = productprices;
    }
}