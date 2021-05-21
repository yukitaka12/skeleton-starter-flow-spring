package org.vaadin.example.task4;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.spring.annotation.SpringComponent;

@SpringComponent
//@ViewScope
public class OrderEditPresenter {

    private OrderEditView view;
    private final OrderService orderService;

    public OrderEditPresenter(@Autowired OrderService orderService) {
        this.orderService = orderService;
    }

    void init(OrderEditView view, long orderId) {
        this.view = view;
        Order order = orderService.getById(orderId);
        view.setName(order.getName());
    }

    public void updateOrder(Order order) {
        orderService.updateOrCreate(order);
        view.showOrders();
    }
}
