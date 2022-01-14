package org.vaadin.example;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;

@Service
public class GreetService implements Serializable {
    double n=0;
    int number;

    public String greet(String name,String contents) {
        Date dateObj = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String display = format.format(dateObj);
        //LocalDateTime nowDateTime = LocalDateTime.now();
        //DateTimeFormatter java8Format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //String java8Disp = nowDateTime.format( java8Format );
        if (name == null || name.isEmpty() || contents == null || contents.isEmpty()) {
            return null;
        } else {
            n += 0.5;
            int number = (int) n;
            
            return number + " 名前 : " + name + " : " + display;
        }
    }

}
