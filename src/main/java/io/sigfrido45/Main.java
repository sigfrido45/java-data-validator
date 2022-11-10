package io.sigfrido45;

import io.sigfrido45.payload.NodeValidator;
import io.sigfrido45.payload.TypeValidator;
import io.sigfrido45.tree.Node;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        var payload = new HashMap<>() {{
            put("name", "123");
            put("another", new HashMap<>() {{
                put("name", "lmao");
            }});
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
                                .setValidation(TypeValidator.map("another"))
                                .addNode(
                                        Node.<String>build()
                                                .setValidation(
                                                        TypeValidator.str("name")
                                                                .required(true)
                                                                .min(1)
                                                )
                                )
                );

        var nodeValidator = NodeValidator.validateNode(node, payload);

        System.out.println(nodeValidator.getErrors());
        System.out.println(nodeValidator.getValidated());
    }


}