package ru.sgera.smddevTestTask.model;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
public class Parameter {
    private String key;
    private String value;

    public Parameter() {
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter = (Parameter) o;
        return key.equals(parameter.key) &&
                value.equals(parameter.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
