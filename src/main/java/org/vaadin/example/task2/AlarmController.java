package org.vaadin.example.task2;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;

@SpringComponent
public class AlarmController {

    private final AlarmService service;
    private Registration registration;
    private AlarmView view;
    private boolean subscribed;

    public AlarmController(@Autowired AlarmService service) {
        this.service = service;
    }

    public void init(AlarmView view) {
        this.view = view;
        if (!subscribed) {
            registration = service.subscribe(this::onMessage);
            subscribed = true;
            view.onSubscribe();
        }
    }

    @PreDestroy
    public void cleanUp() {
        assert subscribed;
        assert registration != null;
        registration.remove();
        view.onUnsubscribe();
    }

    private void onMessage(Alarm alarm) {
        view.getUI().ifPresent(ui ->
                ui.access(() ->
                        view.addAlarm(alarm)));
    }
}
