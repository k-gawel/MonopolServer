package org.california.monopolserver.model.dto.transferable;

import java.util.Objects;

public class TransactionOperations {

    public String property;
    public boolean add;
    public boolean remove;

    public TransactionOperations(String property, boolean add, boolean remove) {
        this.property = property;
        this.add = add;
        this.remove = remove;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionOperations that = (TransactionOperations) o;
        return add == that.add &&
                remove == that.remove &&
                Objects.equals(property, that.property);
    }

    @Override
    public int hashCode() {
        return Objects.hash(property, add, remove);
    }

}
