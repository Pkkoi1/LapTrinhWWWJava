package edu.iuh.fit.backEnd.repositories;

import edu.iuh.fit.backEnd.enums.ProductStatus;
import edu.iuh.fit.backEnd.models.Product;
import edu.iuh.fit.backEnd.models.Productimage;
import edu.iuh.fit.backEnd.models.Productprice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ProductRepository {
    private EntityManager em;
    private EntityTransaction et;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public ProductRepository() {
        em = Persistence.createEntityManagerFactory("MariaBD").createEntityManager();
        et = em.getTransaction();
    }

    public void insertProduct(Product product) {
        try {
            et.begin();
            em.persist(product);
            et.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            et.rollback();
        }
    }

    public void setStatus(Product product, ProductStatus status) {
        product.setStatus(status);
    }

    public void updateStatus(Long id, ProductStatus status) {
        TypedQuery<Product> query = em.createNamedQuery("Product.findById", Product.class)
                .setParameter("id", id);
        Product product1 = query.getSingleResult();
        product1.setStatus(status);
        try {
            et.begin();
            em.merge(product1);
            et.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            et.rollback();
        }
    }

    public boolean updateProduct(Product product) {
        try {
            et.begin();
            em.merge(product);
            et.commit();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            et.rollback();
            return false;
        }
    }

    public List<Product> getAllProduct() {
        return em.createNamedQuery("Product.getAllProduct", Product.class)
                .getResultList();
    }

    public Optional<Product> findById(long id) {
        return Optional.ofNullable(em.find(Product.class, id));
    }

    public List<Productimage> getProductImages(long productId) {
        return em.createNamedQuery("Productimage.findByProduct_Id", Productimage.class)
                .setParameter("id", productId)
                .getResultList();
    }

    public List<Productprice> getProductByPrice(long productId) {
        String query = "SELECT p FROM Productprice p WHERE p.product.id = :productId";
        return em.createQuery(query, Productprice.class)
                .setParameter("productId", productId)
                .getResultList();
    }

    public void close() {
        em.close();
    }
}
