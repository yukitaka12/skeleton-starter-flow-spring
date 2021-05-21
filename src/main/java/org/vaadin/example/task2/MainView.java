package org.vaadin.example.task2;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route("task2")
@RouteAlias("login")
public class MainView extends VerticalLayout {
    public MainView() {
        Button login = new Button("Login",
                event -> UI.getCurrent().navigate(AlarmView.class));

        add(new Span("Login to monitor the alarms"));
        add(login);
    }
}
