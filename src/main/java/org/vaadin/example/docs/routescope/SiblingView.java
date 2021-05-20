package org.vaadin.example.docs.routescope;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value = "sibling")
public class SiblingView extends VerticalLayout {

    public SiblingView() {
        add(new RouterLink("Open ParentView", ParentView.class),
                new RouterLink("Open Child-A", ChildAView.class),
                new RouterLink("Open Child-B", ChildBView.class));
    }
}
