package org.vaadin.example.npe;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.LoggerFactory;

import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.RouteScopeOwner;
import com.vaadin.flow.spring.annotation.SpringComponent;

@RouteScope
@RouteScopeOwner(MainViewNPE.class)
@SpringComponent
public class OrderServiceNPE {
    private Collection<OrderNPE> cache;

    public Collection<OrderNPE> getOrders() {
        if (cache == null) {
            cache = loadOrders();
        }
        return Collections.unmodifiableCollection(cache);
    }

    private Collection<OrderNPE> loadOrders() {
        LoggerFactory.getLogger(OrderServiceNPE.class).info("Orders loaded");
        return Arrays.asList(new OrderNPE(0, "Order-1"),
                new OrderNPE(1, "Order-2"), new OrderNPE(2, "Order-3"));
    }
}
