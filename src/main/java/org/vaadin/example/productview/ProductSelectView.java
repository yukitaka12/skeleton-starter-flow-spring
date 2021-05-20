package org.vaadin.example.productview;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@PreserveOnRefresh
@Route("products")
public class ProductSelectView extends VerticalLayout implements RouterLayout {

    private final ProductModel productModel;

    public ProductSelectView(@Autowired ProductModel model, @Autowired ProductService service) {
        productModel = model;
        
        final Select<Product> select = new Select<>();
        select.setLabel("Select a product");
        select.setItems(service.getProducts());
        select.setItemLabelGenerator(Product::getName);
        select.addValueChangeListener(event -> productModel.setSelectedProduct(event.getValue()));
        add(select);

        add(new RouterLink("Show Product Details", ProductDetailsView.class),
                new RouterLink("Show Product Stocks", ProductStockView.class));
    }

}
