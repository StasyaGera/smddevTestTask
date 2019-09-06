package ru.sgera.smddevTestTask.model;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class AssignedIdentityGenerator extends IdentityGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        if (obj instanceof Product) {
            Product p = (Product) obj;
            Serializable id = p.getId();
            if (id != null) {
                return id;
            }
        }
        return super.generate(s, obj);
    }
}
