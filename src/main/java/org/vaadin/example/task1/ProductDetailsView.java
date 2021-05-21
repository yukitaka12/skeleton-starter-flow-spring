package org.vaadin.example.task1;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

@Route(value = "details", layout = ProductSelectView.class)
public class ProductDetailsView extends VerticalLayout {

    private ProductModel model;
    private Registration registration;
    private Div detailsLabel;

    public ProductDetailsView(@Autowired ProductModel model) {
        this.model = model;
        detailsLabel = new Div();
        add(new H2("Details"), detailsLabel);
        onProductUpdate();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        registration = model.addUpdateListener(this::onProductUpdate);
    }

    private void onProductUpdate() {
        final Product selectedProduct = model.getSelectedProduct();
        if (selectedProduct != null) {
            detailsLabel.setText(selectedProduct.getName() + " - " +
                                 selectedProduct.getDetails());
        } else {
            detailsLabel.setText("Please select a product first");
        }
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        registration.remove();
    }
}
