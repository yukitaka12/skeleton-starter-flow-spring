package org.vaadin.example;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean. Use the @PWA
 * annotation make the application installable on phones, tablets and some
 * desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@NpmPackage(value = "lit-element", version = "2.3.1")
@JsModule("./vaadin-devmode-gizmo.ts")
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service
     *            The message service. Automatically injected Spring managed
     *            bean.
     */
    public MainView(@Autowired GreetService service) {
        ComboBox<String> typeField = new ComboBox<>("Type");
        typeField.setItems(Arrays.asList("information", "warning"));
        typeField.setValue("information");
        add(typeField);

        TextField messageField = new TextField("Message");
        messageField.setTitle("Main notification contents");
        messageField.setValue("Test notification");
        add(messageField);

        TextArea detailsField = new TextArea("Details");
        add(detailsField);

        TextField linkField = new TextField("Link URL");
        linkField.setTitle("Optional link from notification");
        add(linkField);

        TextField persistenceIdField = new TextField("Persistence ID");
        persistenceIdField.setTitle("Notifications with persistence ID allows permanent dismissal (setting saved in localstorage)");
        add(persistenceIdField);

        Button showNotification = new Button("Show notification", e -> {
            String type = "'" + typeField.getValue() + "'";
            String message = jsString(messageField.getValue());
            String details = detailsField.isEmpty() ? "null"
                    : jsString(detailsField.getValue());
            String link = linkField.isEmpty() ? "null"
                    : jsString(linkField.getValue());
            String persistenceId = persistenceIdField.isEmpty() ? "null"
                    : jsString(persistenceIdField.getValue());
            getUI().get().getPage()
                    .executeJs(String.format(
                            "window.Gizmo.showNotification(%s, %s, %s, %s, %s)",
                            type, message, details, link, persistenceId));
        });
        add(showNotification);

        TextField splashMessageField = new TextField("Splash message");
        splashMessageField.setTitle("Message shown directly on gizmo instead of in separate bubble");
        splashMessageField.setValue("Test splash message");
        add(splashMessageField);

        Button showSplashMessage = new Button("Show splash message", e -> {
            getUI().get().getPage().executeJs(
                    String.format("window.Gizmo.showSplashMessage(%s)",
                            jsString(splashMessageField.getValue())));
        });
        add(showSplashMessage);

        add(new HorizontalLayout(new Button("Close Java channel",
                e -> getUI().get().getPage().executeJs(
                        "window.Vaadin.Flow.devModeGizmo.javaConnection.webSocket.close()")),
                new Button("Error Java channel",
                        e -> getUI().get().getPage().executeJs(
                                "window.Vaadin.Flow.devModeGizmo.javaConnection.webSocket.onerror('Faked error on Java channel');"
                                        + "window.Vaadin.Flow.devModeGizmo.javaConnection.webSocket.close()"))));

        add(new HorizontalLayout(new Button("Close frontend channel",
                e -> getUI().get().getPage().executeJs(
                        "window.Vaadin.Flow.devModeGizmo.frontendConnection.webSocket.close()")),
                new Button("Error frontend channel",
                        e -> getUI().get().getPage().executeJs(
                                "window.Vaadin.Flow.devModeGizmo.frontendConnection.webSocket.onerror('Faked error on frontend channel');"
                                        + "window.Vaadin.Flow.devModeGizmo.frontendConnection.webSocket.close()"))));
    }

    private static String jsString(String s) {
        return "'" + s.replace("'", "\\'").replace("\n", "\\n") + "'";
    }

    protected void onAttach(AttachEvent e) {
        getUI().get().getPage().executeJs(
                "[...document.getElementsByTagName('vaadin-devmode-gizmo')].forEach(elm => elm.remove());"
                        + "let gizmo = document.createElement('vaadin-devmode-gizmo');"
                        + "gizmo.setAttribute('url', './vaadinServlet');"
                        + "gizmo.setAttribute('springbootlivereloadport','35729');"
                        + "gizmo.setAttribute('backend','SPRING_BOOT_DEVTOOLS');"
                        + "document.body.appendChild(gizmo);"
                        + "window.Vaadin.Flow.devModeGizmo = gizmo;");
    }

}
