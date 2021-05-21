package org.vaadin.example.task1;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.stereotype.Service;

@Service
public class LocationService {
    public Collection<Location> getLocations() {
        return Arrays.asList(new Location("Turku"), new Location("Helsinki"));
    }
}
