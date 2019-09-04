package ru.sgera.smddevTestTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public String create(@Valid @RequestBody Product product) {
        return productRepository.save(product).getId();
    }

    @GetMapping("/{id}")
    public Product getDetailsById(@PathVariable("id") String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @GetMapping("/search/{name}")
    public List<String> getByName(@PathVariable("name") String name) {
        return productRepository.findByNameLike(name);
    }

    @GetMapping("/search")
    public List<String> getByParams(@RequestParam Map<String, String> params) {
        Map.Entry<String, String> parameter = params.entrySet().stream().findAny().get();
        return productRepository.findByParameter(parameter.getKey(), parameter.getValue());
    }
}
