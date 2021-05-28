package org.vaadin.example.task1;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.router.RouterLayout;

@RoutePrefix("product")
public class MainView extends VerticalLayout implements RouterLayout {

    public MainView() {
        add(new H2("Product selection wizard"));
    }
}
