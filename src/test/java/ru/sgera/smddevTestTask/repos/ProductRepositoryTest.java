package ru.sgera.smddevTestTask.repos;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sgera.smddevTestTask.model.Parameter;
import ru.sgera.smddevTestTask.model.Product;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepo;

//    private String customId = "5d63e5660815ee34c046f6ff";

    @Before
    public void clearDB() {
        productRepo.deleteAll();
    }

    @Test
    public void findByNameLike() {
        List<String> retrieved;

        addProduct("iPhone X 128GB");
        addProduct("Laptop HP Model 18f7gw236", new Parameter("key", "value"));
        addProduct("iPhone XR 256GB");

        // check that method returns right amount of products
        retrieved = productRepo.findByNameLike("iPhone");
        assertEquals(2, retrieved.size());
        retrieved = productRepo.findByNameLike("ipHOne");
        assertEquals(2, retrieved.size());
        retrieved = productRepo.findByNameLike("HP");
        assertEquals(1, retrieved.size());
        retrieved = productRepo.findByNameLike("2");
        assertEquals(3, retrieved.size());

        // check that method returns a correct product (by name)
        retrieved = productRepo.findByNameLike("iPhone XR");
        assertEquals(1, retrieved.size());
        assertEquals("iPhone XR 256GB", retrieved.get(0));
    }

    @Test
    public void findByParameters() {
        List<String> retrieved;

        Parameter p1v1 = new Parameter("p1", "v1");
        Parameter p2v1 = new Parameter("p2", "v1");
        Parameter p2v2 = new Parameter("p2", "v2");
        Parameter p3v1 = new Parameter("p3", "v1");

        addProduct("no params");
        addProduct("p1v1 and p2v1", p1v1, p2v1);
        addProduct("p1v1 and p2v2", p1v1, p2v2);
        addProduct("three params", p1v1, p2v2, p3v1);

        retrieved = productRepo.findByParameter(p1v1.getKey(), p1v1.getValue());
        assertEquals(3, retrieved.size());

        retrieved = productRepo.findByParameter(p2v2.getKey(), p2v2.getValue());
        assertEquals(2, retrieved.size());

        retrieved = productRepo.findByParameter(p3v1.getKey(), p3v1.getValue());
        assertEquals(1, retrieved.size());

        retrieved = productRepo.findByParameter(p2v1.getKey(), p2v1.getValue());
        assertEquals(1, retrieved.size());
        assertEquals("p1v1 and p2v1", retrieved.get(0));
    }

    private Product addProduct(String name, Parameter... params) {
        return productRepo.save(new Product(name, "", params));
    }

//    private Product addProduct(String id, String name, Parameter... params) {
//        return productRepo.save(new Product(id, name, "", params));
//    }
}
