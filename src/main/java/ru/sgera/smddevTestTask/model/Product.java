package ru.sgera.smddevTestTask.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Document(collection = "products")
public class Product {
    @Id
    private ObjectId id;

    @NotEmpty(message = "Please provide a name")
    private String name;

    @NotNull(message = "Please provide a description")
    private String description;

    @NotNull(message = "Please provide a list of parameters")
    private List<Parameter> parameters;

    public Product() {}

    public Product(String name, String description, List<Parameter> parameters) {
        this.name = name;
        this.description = description;
        this.parameters = parameters;
    }

    public Product(String id, String name, String description, List<Parameter> parameters) {
        this.id = new ObjectId(id);
        this.name = name;
        this.description = description;
        this.parameters = parameters;
    }

    public String getId() {
        return id.toString();
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

    public void putParameter(String key, String value) {
        parameters.add(new Parameter(key, value));
    }

    public static class Parameter {
        private String key;
        private String value;

        public Parameter(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Parameter) {
                Parameter o = (Parameter) obj;
                return Objects.equals(this.getKey(), o.getKey()) && Objects.equals(this.getValue(), o.getValue());
            } else {
                return false;
            }
        }
    }
}
