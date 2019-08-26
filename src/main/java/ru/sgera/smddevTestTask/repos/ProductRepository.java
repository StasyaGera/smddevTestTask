package ru.sgera.smddevTestTask.repos;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.sgera.smddevTestTask.model.Product;
import ru.sgera.smddevTestTask.model.QProduct;

import java.util.List;
import java.util.Map;

public interface ProductRepository extends MongoRepository<Product, String>, QuerydslPredicateExecutor<Product> {
    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<Product> findByNameLike(String name);

    default List<Product> findByParameters(Map<String, String> params) {
        QProduct qProduct = new QProduct("product");
        BooleanExpression filterByParams = qProduct.parameters.isNotEmpty();
        for (Map.Entry<String, String> kv: params.entrySet()) {
            filterByParams = filterByParams.and(qProduct.parameters.contains(new Product.Parameter(kv.getKey(), kv.getValue())));
        }
        return (List<Product>) findAll(filterByParams);
    }

//    @Query(value = "{'parameters': {$elemMatch: {'key': ?0, 'value': ?1}}}")
//    List<Product> findByParameter(String key, String value);
}
