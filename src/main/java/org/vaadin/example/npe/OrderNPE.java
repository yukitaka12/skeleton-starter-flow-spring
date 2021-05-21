package org.vaadin.example.npe;

import java.util.Objects;

public class OrderNPE {
    private final long id;
    private final String name;

    public OrderNPE(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderNPE order = (OrderNPE) o;
        return id == order.id && Objects.equals(name, order.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
