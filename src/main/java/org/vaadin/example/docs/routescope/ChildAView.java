package org.vaadin.example.docs.routescope;

import java.util.UUID;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.RouteScopeOwner;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "child-a", layout = ParentView.class)
public class ChildAView extends VerticalLayout {

    private final String uid = UUID.randomUUID().toString();

    public ChildAView(
            @Autowired @RouteScopeOwner(ParentView.class)
                    RouteService routeService) {
        add(new Div(new Text("Child-A id: " + uid)));
        add(new Div(new Text("Service-id: " + routeService.getText())));
    }
}
