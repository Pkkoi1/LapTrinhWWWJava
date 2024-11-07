package edu.iuh.fit.backEnd.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Entity
@Table(name = "productprice")
public class Productprice implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProductpriceId id;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    private Product product;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "note")
    private String note;

    public ProductpriceId getId() {
        return id;
    }

    public void setId(ProductpriceId id) {
        this.id = id;
    }

//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Productprice(ProductpriceId id, Product product, Double price, String note) {
        this.id = id;
        this.product = product;
        this.price = price;
        this.note = note;
    }

    public Productprice() {
    }
}