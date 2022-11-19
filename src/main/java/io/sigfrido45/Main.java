package io.sigfrido45;

import io.sigfrido45.payload.NodeValidator;
import io.sigfrido45.payload.TypeValidator;
import io.sigfrido45.tree.Node;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        System.getProperties()
                .setProperty("validation.error-messages", "messages_es.properties");
        System.getProperties()
                .setProperty("validation.attributes-messages", "attributes_es.properties");

        var payload = new HashMap<>() {{
            put("name", "123");
//            put("another", new HashMap<>() {{
//                put("name", "lmao");
//            }});
        }};


        var node = (Node.<Map>build())
                .addNode(
                        Node.<String>build()
                                .setValidation(
                                        TypeValidator.str("name")
                                                .required(true)
                                                .min(1)
                                )
                )
                .addNode(
                        Node.<Map>build()
                                .setValidation(TypeValidator.map("another").required(false))
                                .addNode(
                                        Node.<String>build()
                                                .setValidation(
                                                        TypeValidator.str("name")
                                                                .required(false)
                                                                .min(1)
                                                )
                                )
                );

        var nodeValidator = NodeValidator.validateNode(node, payload);
        System.out.println(nodeValidator.getErrors());
        System.out.println(nodeValidator.getValidated());
    }
}