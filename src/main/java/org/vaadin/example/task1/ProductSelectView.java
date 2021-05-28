package org.vaadin.example.task1;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

@ParentLayout(MainView.class)
public class ProductSelectView extends VerticalLayout implements RouterLayout {

    public ProductSelectView(@Autowired ProductModel productModel,
                             @Autowired LocationModel locationModel,
                             @Autowired ProductService service) {
        Select<Product> select = new Select<>();
        select.setLabel("Select a product");
        select.setItemLabelGenerator(Product::getName);
        select.addValueChangeListener(event -> productModel.setSelectedProduct(event.getValue()));
        Location selectedLocation = locationModel.getSelectedLocation();
        select.setItems(service.getProductsByLocation(selectedLocation));
        add(new H3("Step 2: Select a product to order"));
        add(select);
        add(new Span("Location: " + (selectedLocation != null ?
                selectedLocation.getCity() : "All")));

        add(new RouterLink("Show Product Details", ProductDetailsView.class),
                new RouterLink("Show Product Stocks", ProductStockView.class),
                new RouterLink("Select product from scratch", LocationSelectView.class));
    }
}
