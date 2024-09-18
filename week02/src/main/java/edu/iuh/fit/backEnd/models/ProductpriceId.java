package edu.iuh.fit.backEnd.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class ProductpriceId implements Serializable {
    private static final long serialVersionUID = -8378126279744778838L;
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "price_date_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date priceDateTime;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getPriceDateTime() {
        return priceDateTime;
    }

    public void setPriceDateTime(Date priceDateTime) {
        this.priceDateTime = priceDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductpriceId entity = (ProductpriceId) o;
        return Objects.equals(this.priceDateTime, entity.priceDateTime) &&
                Objects.equals(this.productId, entity.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priceDateTime, productId);
    }

}