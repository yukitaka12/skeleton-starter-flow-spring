package org.vaadin.example.productview;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "stock", layout = ProductSelectView.class)
public class ProductStockView extends VerticalLayout {

    private ProductModel model;
    private Registration registration;
    private Div stockLabel;

    public ProductStockView(@Autowired ProductModel model) {
        this.model = model;
        stockLabel = new Div();
        add(new H2("Stock"), stockLabel);
        onProductUpdate();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        registration = model.addUpdateListener(this::onProductUpdate);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        registration.remove();
    }

    private void onProductUpdate() {
        final Product selectedProduct = model.getSelectedProduct();
        if (selectedProduct != null) {
            stockLabel.setText("Available items: " + selectedProduct.getQuantity());
        } else {
            stockLabel.setText("Please select a product first");
        }
    }
}
