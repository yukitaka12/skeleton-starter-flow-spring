package org.vaadin.example.task2;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.annotation.ApplicationScope;

import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;

@SpringComponent
@ApplicationScope
public class AlarmService {

    private final AtomicInteger counter = new AtomicInteger(0);
    private final Random random = new Random();

    private final Collection<SerializableConsumer<Alarm>> listeners =
            Collections.synchronizedSet(new HashSet<>());

    @Scheduled(fixedRate = 5000)
    private void publish() {
        String description = "Alarm " + counter.getAndIncrement();
        listeners.forEach(listener -> {
            Alarm.Severity severity = Alarm.Severity.values()[random.nextInt(3)];
            Alarm alarm = new Alarm(severity, description);
            listener.accept(alarm);
        });
    }

    public Registration subscribe(SerializableConsumer<Alarm> listener) {
        listeners.add(listener);
        return () -> listeners.remove(listener);
    }
}
