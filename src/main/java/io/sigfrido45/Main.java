package io.sigfrido45;

import io.sigfrido45.tree.Node;
import io.sigfrido45.tree.NodeValidator;
import io.sigfrido45.tree.Validation;

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
                                        Validation.strValidator("name")
                                                .required(true)
                                                .min(1)
                                )
                )
                .addNode(
                        Node.<Map>build()
                                .setValidation(Validation.mapValidator("another"))
                                .addNode(
                                        Node.<String>build()
                                                .setValidation(
                                                        Validation.strValidator("name")
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