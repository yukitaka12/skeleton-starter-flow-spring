package org.vaadin.example.docs.routescope;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.RouteScopeOwner;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@RoutePrefix("parent")
public class ParentView extends VerticalLayout
        implements RouterLayout {

    public ParentView(
            @Autowired @RouteScopeOwner(ParentView.class)
                    RouteService routeService) {
        add(new Span("Parent view:" + routeService.getText()),
                new RouterLink("Open Child-A", ChildAView.class),
                new RouterLink("Open Child-B", ChildBView.class),
                new RouterLink("Open Sibling", SiblingView.class));
    }
}
