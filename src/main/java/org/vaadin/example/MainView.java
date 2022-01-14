package org.vaadin.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout{

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service The message service. Automatically injected Spring managed bean.
     */
    public MainView(@Autowired GreetService service) {

        // Use TextField for standard text input
        VerticalLayout List = new VerticalLayout();
        TextField textField = new TextField("名前:");
        textField.addThemeName("bordered");
        //TextField content = new TextField();

        TextArea textArea = new TextArea();
        textArea.setMaxLength(10000);
        textArea.setValueChangeMode(ValueChangeMode.EAGER);
        textArea.addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + 10000);
        });
        
        // Button click listeners can be defined as lambda expressions

        Button button = new Button("書き込む");
        button.addClickListener(click -> {
            if(service.greet(textField.getValue(),textArea.getValue())==null){
            Notification.show("名前か送信内容が入力されていません。");
            }


            else{
            List.add(new H3(service.greet(textField.getValue(),textArea.getValue())),new H3(textArea.getValue()));
            }
        });

        //Button button = new Button("書き込む",
                //e -> new H1(service.greet(textField.getValue(),content.getValue())));
                
        //Notification.show(service.greet(textField.getValue(),content.getValue())));

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button has a more prominent look.
        //button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        //button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        textArea.addClassName("centered-content");
        textField.addClassName("centered-content");

        add(
            new H1("Vaadin thread"),
            List,
            new HorizontalLayout(
                button
            ),
            textField,
            textArea
        );
    }
}
