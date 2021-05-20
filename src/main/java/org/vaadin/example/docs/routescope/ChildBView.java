package org.vaadin.example.docs.routescope;

import java.util.UUID;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.RouteScopeOwner;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@RouteScope
@RouteScopeOwner(ParentView.class)
@Route(value = "child-b", layout = ParentView.class)
public class ChildBView extends VerticalLayout {

    private final String uid = UUID.randomUUID().toString();

    public ChildBView(
            @Autowired @RouteScopeOwner(ParentView.class)
                    RouteService routeService) {
        add(new Div(new Text("Child-B id: " + uid)));
        add(new Div(new Text("Service-id: " + routeService.getText())));
    }
}
