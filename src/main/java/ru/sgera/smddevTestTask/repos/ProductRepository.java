package ru.sgera.smddevTestTask.repos;

import org.springframework.stereotype.Repository;
import ru.sgera.smddevTestTask.model.Parameter;
import ru.sgera.smddevTestTask.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {
    private static EntityManagerFactory entityManagerFactory;

    public ProductRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("productPu");
    }

    private <R> R useEntityManager(Function<? super EntityManager, R> function) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        R result = function.apply(entityManager);
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }

    public void deleteAll() {
        useEntityManager(em -> em.createNativeQuery("db.products.remove({})").executeUpdate());
    }

    public Product save(Product product) {
        return useEntityManager(em -> {
            em.persist(product);
            return product;
        });
    }

    public Optional<Product> findById(String id) {
        return Optional.ofNullable(useEntityManager(em -> em.find(Product.class, id)));

//        String query = "SELECT p FROM Product p WHERE p.id = :id";
//        List<Product> result = useEntityManager(em -> em.createQuery(query, Product.class)
//                .setParameter("id", id)
//                .getResultList());
//
//        if (result.isEmpty()) {
//            return Optional.empty();
//        } else {
//            return Optional.of(result.get(0));
//        }
    }

    @SuppressWarnings("unchecked")
    public List<String> findByNameLike(String name) {
        // case-sensitive JP-QL query
//        String query = "SELECT p.name FROM Product p WHERE p.name LIKE :name";
//        return useEntityManager(em -> em.createQuery(query, String.class)
//                .setParameter("name", "%" + name + "%")
//                .getResultList());

        // case-insensitive native mongodb query
        String query = "{'name': {$regex : \"" + name.toLowerCase() + "\", $options: 'i'}}";
        List<Product> res = useEntityManager(em -> em.createNativeQuery(query, Product.class).getResultList());
        return res.stream().map(Product::getName).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<String> findByParameter(String key, String value) {
        // native mongodb query
        String query = "{'parameters': {$elemMatch: {'key': \"" + key + "\", 'value': \"" + value + "\"}}}";
        List<Product> res = useEntityManager(em -> em.createNativeQuery(query, Product.class).getResultList());
        return res.stream().map(Product::getName).collect(Collectors.toList());

        // not working properly for unknown reason
//        String query = "SELECT p.name FROM Product p JOIN p.parameters pp WHERE pp.key = :key AND pp.value = :value";
//        return useEntityManager(em -> em.createQuery(query, String.class)
//                .setParameter("key", key)
//                .setParameter("value", value)
//                .getResultList());
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        entityManagerFactory.close();
    }
}
