package org.vaadin.example.productview;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public Collection<Product> getProducts() {
        final Random random = new Random();
        return Arrays.asList(new Product("Hammer", "You can use it to hit things", random.nextInt(10000)),
                new Product("Swiss Army Knife", "The only thing you need", random.nextInt(1000)),
                new Product("T-shirt", "Black. One size fits all", random.nextInt(4000)));
    }
}
