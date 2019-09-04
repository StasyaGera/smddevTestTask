package ru.sgera.smddevTestTask.repos;

import org.springframework.stereotype.Repository;
import ru.sgera.smddevTestTask.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private static EntityManagerFactory entityManagerFactory;

    public ProductRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "productPu" );
    }

    public void deleteAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Product").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Product save(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
        entityManager.close();
        return product;
    }

    public Optional<Product> findById(String id) {
        String query = "SELECT p FROM Product p WHERE p.id = :id";

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Product> result = entityManager
                .createQuery(query, Product.class)
                .setParameter("id", id)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();

        if (result.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(result.get(0));
        }
    }

    public List<String> findByNameLike(String name) {
        String query = "SELECT p.name FROM Product p WHERE p.name LIKE :name";
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<String> result = entityManager
                .createQuery(query, String.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }

    public List<String> findByParameter(String key, String value) {
        String query = "SELECT p.name FROM Product p JOIN p.parameters pp WHERE pp.key = :key AND pp.value = :value";
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<String> result = entityManager
                .createQuery(query, String.class)
                .setParameter("key", key)
                .setParameter("value", value)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        entityManagerFactory.close();
    }
}
