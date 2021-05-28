package org.vaadin.example.task1;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

@Route(value = "location", layout = MainView.class)
public class LocationSelectView extends VerticalLayout implements RouterLayout {

    public LocationSelectView(@Autowired LocationModel locationModel,
                              @Autowired LocationService locationService) {
        Select<Location> locationSelect = new Select<>();
        locationSelect.setItems(locationService.getLocations());
        locationSelect.setLabel("Select a location");
        locationSelect.addValueChangeListener(event ->
                locationModel.setSelectedLocation(event.getValue()));
        add(new H3("Step 1: Select a location to order product from"));
        add(locationSelect);
        add(new RouterLink("Select a product", ProductDetailsView.class));
    }
}
