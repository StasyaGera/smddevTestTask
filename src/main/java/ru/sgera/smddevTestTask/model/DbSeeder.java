package ru.sgera.smddevTestTask.model;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.sgera.smddevTestTask.repos.ProductRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {
    private ProductRepository productRepo;

    public DbSeeder(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Product.Parameter> props1 = new ArrayList<>();
        props1.add(new Product.Parameter("prop1", "value1"));
        props1.add(new Product.Parameter("prop2", "value1"));
        Product product1 = new Product(
                "mobile phone",
                "description1",
                props1
        );

        List<Product.Parameter> props2 = new ArrayList<>();
        props2.add(new Product.Parameter("prop1", "value2"));
        props2.add(new Product.Parameter("prop2", "value1"));
        props2.add(new Product.Parameter("prop3", "value2"));
        Product product2 = new Product(
                "mobile phone",
                "description2",
                props2
        );

        List<Product.Parameter> props3 = new ArrayList<>();
        props3.add(new Product.Parameter("prop1", "value3"));
        props3.add(new Product.Parameter("prop2", "value3"));
        props3.add(new Product.Parameter("prop3", "value2"));
        Product product3 = new Product(
                "laptop",
                "description3",
                props3
        );

        List<Product.Parameter> props4 = new ArrayList<>();
        props4.add(new Product.Parameter("prop1", "value1"));
        props4.add(new Product.Parameter("prop2", "value1"));
        props4.add(new Product.Parameter("prop3", "value1"));
        Product product4 = new Product(
                "5d63e5660815ee34c046f6ff",
                "mobile phone",
                "description4",
                props4
        );

        productRepo.deleteAll();

        List<Product> products = Arrays.asList(product1, product2, product3, product4);
        productRepo.saveAll(products);
    }
}
