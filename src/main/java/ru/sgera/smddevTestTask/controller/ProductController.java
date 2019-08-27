package ru.sgera.smddevTestTask.controller;

import org.springframework.web.bind.annotation.*;
import ru.sgera.smddevTestTask.exceptions.ProductNotFoundException;
import ru.sgera.smddevTestTask.model.Product;
import ru.sgera.smddevTestTask.repos.ProductRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductRepository productRepo;

    public ProductController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @PostMapping
    public void create(@Valid @RequestBody Product product) {
        productRepo.save(product);
    }

    @GetMapping("/{id}")
    public Product getDetailsById(@PathVariable("id") String id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @GetMapping("/search/{name}")
    public List<String> getByName(@PathVariable("name") String name) {
        return extractNames(productRepo.findByNameLike(name));
    }

    @GetMapping("/search")
    public List<String> getByParams(@RequestParam Map<String, String> params) {
        return extractNames(productRepo.findByParameters(params));
    }

    private List<String> extractNames(List<Product> products) {
        return products.stream().map(Product::getName).collect(Collectors.toList());
    }
}
