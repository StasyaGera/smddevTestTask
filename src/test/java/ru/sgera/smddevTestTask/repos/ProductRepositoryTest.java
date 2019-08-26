package ru.sgera.smddevTestTask.repos;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sgera.smddevTestTask.model.Product;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepo;

    private String customId = "5d63e5660815ee34c046f6ff";

    @Before
    public void clearDB() {
        productRepo.deleteAll();
    }

    @Test
    public void findByNameLike() {
        List<Product> retrieved;

        addProduct("iPhone X 128GB");
        addProduct("Laptop HP Model 18f7gw236");
        addProduct(customId, "iPhone XR 256GB");

        // check that method returns right amount of products
        retrieved = productRepo.findByNameLike("iPhone");
        assertEquals(2, retrieved.size());
        retrieved = productRepo.findByNameLike("ipHOne");
        assertEquals(2, retrieved.size());
        retrieved = productRepo.findByNameLike("HP");
        assertEquals(1, retrieved.size());
        retrieved = productRepo.findByNameLike("2");
        assertEquals(3, retrieved.size());

        // check that method returns a correct product (by Id)
        retrieved = productRepo.findByNameLike("iPhone XR");
        assertEquals(1, retrieved.size());
        assertEquals(customId, retrieved.get(0).getId());
    }

    @Test
    public void findByParameters() {
        List<Product> retrieved;
        Map<String, String> params = new HashMap<>();

        Product.Parameter p1v1 = new Product.Parameter("p1", "v1");
        Product.Parameter p2v1 = new Product.Parameter("p2", "v1");
        Product.Parameter p2v2 = new Product.Parameter("p2", "v2");
        Product.Parameter p3v1 = new Product.Parameter("p3", "v1");

        addProduct().getId();
        addProduct(customId, p1v1, p2v1).getId();
        addProduct(p1v1, p2v2).getId();
        String threeParamsId = addProduct(p1v1, p2v2, p3v1).getId();

        params.put(p1v1.getKey(), p1v1.getValue());
        retrieved = productRepo.findByParameters(params);
        assertEquals(3, retrieved.size());
        params.clear();

        params.put(p2v1.getKey(), p2v1.getValue());
        retrieved = productRepo.findByParameters(params);
        assertEquals(1, retrieved.size());
        params.clear();

        params.put(p1v1.getKey(), p1v1.getValue());
        params.put(p2v2.getKey(), p2v2.getValue());
        retrieved = productRepo.findByParameters(params);
        assertEquals(2, retrieved.size());
        params.clear();

        params.put(p1v1.getKey(), p1v1.getValue());
        params.put(p3v1.getKey(), p3v1.getValue());
        retrieved = productRepo.findByParameters(params);
        assertEquals(1, retrieved.size());
        assertEquals(threeParamsId, retrieved.get(0).getId());
        params.clear();

        params.put(p2v1.getKey(), p2v1.getValue());
        retrieved = productRepo.findByParameters(params);
        assertEquals(1, retrieved.size());
        assertEquals(customId, retrieved.get(0).getId());
        params.clear();
    }

    private Product addProduct(String name) {
        return productRepo.save(new Product(name, "", new ArrayList<>()));
    }

    private Product addProduct(String id, String name) {
        return productRepo.save(new Product(id, name, "", new ArrayList<>()));
    }

    private Product addProduct(Product.Parameter... params) {
        return productRepo.save(new Product("product", "", Arrays.asList(params)));
    }

    private Product addProduct(String id, Product.Parameter... params) {
        return productRepo.save(new Product(id, "product", "", Arrays.asList(params)));
    }
}
