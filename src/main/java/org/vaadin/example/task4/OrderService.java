package org.vaadin.example.task4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class OrderService {

    private final List<Order> orders = new ArrayList<>(
            Arrays.asList(
                    new Order(0, "KNARREVIK, Yöpöytä 37x28 cm"),
                    new Order(1, "HEMNES, Sivupöytä 46x35 cm"),
                    new Order(2, "ASKVOLL, Lipasto, 2 laatikkoa 41x48 cm"))
    );

    public Collection<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    public Order getById(long id) {
        return getOrders().stream().filter(order -> id == order.getId())
                .findFirst().orElseThrow(() ->
                        new RuntimeException("Order " + id + " not found"));
    }

    public void updateOrCreate(Order order) {
        int index = orders.indexOf(order);
        if (index > -1) {
            orders.remove(index);
            orders.add(index, order);
        } else {
            orders.add(order);
        }
    }
}
