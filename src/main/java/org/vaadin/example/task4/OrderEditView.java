package org.vaadin.example.task4;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route("edit/:orderID")
public class OrderEditView extends VerticalLayout implements BeforeEnterObserver {

    private final OrderEditPresenter presenter;
    private long orderID;
    private Button renameButton;
    private TextField editField;

    public OrderEditView(@Autowired OrderEditPresenter presenter) {
        this.presenter = presenter;
        renameButton = new Button("Edit");
        editField = new TextField("Enter a new name");
        add(editField, renameButton);
    }

    public void setName(String name) {
        editField.setValue(name);
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        orderID =
                event.getRouteParameters().getLong("orderID")
                        .orElseThrow(() ->
                                new IllegalArgumentException("Order ID required"));
        presenter.init(this, orderID);
        renameButton.addClickListener(click ->
                presenter.updateOrder(new Order(orderID, editField.getValue())));
    }

    public void showOrders() {
        getUI().ifPresent(ui -> ui.navigate(OrdersView.class));
    }
}
