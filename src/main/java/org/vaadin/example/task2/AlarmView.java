package org.vaadin.example.task2;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "alarms")
public class AlarmView extends VerticalLayout {

    private final AlarmController controller;

    public AlarmView(@Autowired AlarmController controller) {
        this.controller = controller;
        add(new Button("Logout", click ->
                UI.getCurrent().navigate(MainView.class)));
        add(new Hr());
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        controller.init(this);
    }

    public void addAlarm(Alarm alarm) {
        HorizontalLayout alarmContainer = new HorizontalLayout();
        alarmContainer.setWidthFull();
        Checkbox resolve = new Checkbox("Mark as Resolved", event ->
                alarmContainer.getElement().removeFromParent());
        alarmContainer.add(resolve);
        alarmContainer.add(new Span(alarm.getDescription()));
        alarmContainer.add(renderSeverity(alarm));
        add(alarmContainer);
    }

    public void onSubscribe() {
        getUI().ifPresent(ui ->
                ui.access(() ->
                        Notification.show("Subscribed to Alarm Service",
                                3000,
                                Notification.Position.MIDDLE)));
    }

    public void onUnsubscribe() {
        getUI().ifPresent(ui ->
                ui.access(() ->
                        Notification.show("Unsubscribed from Alarm Service",
                                3000,
                                Notification.Position.MIDDLE)));
    }

    private Span renderSeverity(Alarm alarm) {
        Alarm.Severity severity = alarm.getSeverity();
        String color;
        switch (severity) {
            case MINOR:
                color = "green";
                break;
            case MAJOR:
                color = "orange";
                break;
            case CRITICAL:
                color = "red";
                break;
            default:
                throw new IllegalStateException("Unknown alarm");
        }
        Span span = new Span(severity.name());
        span.getElement().getStyle().set("color", color);
        return span;
    }
}
