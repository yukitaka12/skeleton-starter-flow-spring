package org.vaadin.example.task1;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

@Route("task1")
@RouteAlias("location")
public class MainView extends VerticalLayout implements RouterLayout {

    public MainView(@Autowired LocationModel locationModel,
                    @Autowired LocationService locationService) {
        Select<Location> locationSelect = new Select<>();
        locationSelect.setItems(locationService.getLocations());
        locationSelect.setLabel("Select a location");
        locationSelect.addValueChangeListener(event ->
                locationModel.setSelectedLocation(event.getValue()));
        add(locationSelect);
        add(new RouterLink("Select a product", ProductDetailsView.class));
    }
}
