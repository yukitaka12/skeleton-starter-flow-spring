package org.vaadin.example.productview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.server.Command;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.RouteScopeOwner;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.stereotype.Component;

@SpringComponent
@RouteScope
@RouteScopeOwner(ProductSelectView.class)
public class ProductModel implements Serializable {

    private List<Command> listeners = new ArrayList<>(2);
    private Product selectedProduct;

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        notifyListeners();
    }

    public Registration addUpdateListener(Command listener) {
        listeners.add(listener);
        return () -> listeners.remove(listener);
    }

    private void notifyListeners() {
        listeners.forEach(Command::execute);
    }
}
