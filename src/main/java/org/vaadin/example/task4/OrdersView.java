package org.vaadin.example.task4;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

@Route("task4")
@RouteAlias("orders")
public class OrdersView extends VerticalLayout implements RouterLayout {

    private final OrderService orderService;

    public OrdersView(@Autowired OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        orderService.getOrders().forEach(order -> {
            Span name = new Span(order.getName());
            RouterLink editLink = new RouterLink("edit", OrderEditView.class,
                    new RouteParameters("orderID",
                            String.valueOf(order.getId())));
            add(new HorizontalLayout(name, editLink));
        });
    }
}
