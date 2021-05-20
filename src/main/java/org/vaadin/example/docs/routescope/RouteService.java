package org.vaadin.example.docs.routescope;

import java.util.UUID;

import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.RouteScopeOwner;
import com.vaadin.flow.spring.annotation.SpringComponent;

@SpringComponent
@RouteScope
@RouteScopeOwner(ParentView.class)
public class RouteService {
    private String uid = UUID.randomUUID().toString();

    public String getText() {
        return "ui " + uid;
    }
}
