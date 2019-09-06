package ru.sgera.smddevTestTask.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @Type(type = "objectid")
    @GenericGenerator(
            name = "assigned-identity",
            strategy = "ru.sgera.smddevTestTask.model.AssignedIdentityGenerator")
    @GeneratedValue(
            generator = "assigned-identity",
            strategy = GenerationType.IDENTITY)
    private String id;

    @Version
    private Integer version;

    @NotEmpty(message = "Please provide a name")
    private String name;

    @NotNull(message = "Please provide a description")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @NotNull(message = "Please provide a list of parameters")
    private List<Parameter> parameters;

    public Product() {
    }

    public Product(String name, String description, List<Parameter> parameters) {
        this.name = name;
        this.description = description;
        this.parameters = parameters;
    }

    public Product(String name, String description, Parameter... parameters) {
        this.name = name;
        this.description = description;
        this.parameters = Arrays.asList(parameters);
    }

    public Product(String id, String name, String description, List<Parameter> parameters) {
        this(name, description, parameters);
        this.id = id;
    }

    public Product(String id, String name, String description, Parameter... parameters) {
        this(name, description, parameters);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }
}
