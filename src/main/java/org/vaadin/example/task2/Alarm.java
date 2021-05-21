package org.vaadin.example.task2;

import java.io.Serializable;

public class Alarm implements Serializable {
    private Severity severity;
    private String description;
    private boolean resolved;

    public Alarm(Severity severity, String description) {
        this.severity = severity;
        this.description = description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public enum Severity { MINOR, MAJOR, CRITICAL }
}
