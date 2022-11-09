package org.example;

import org.example.asd.Validation;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        var payload = new HashMap<>() {{
            put("name", "123");
            put("another", new HashMap<>() {{
                put("name", "lmao");
            }});
        }};

        var validator = Validation.stringValidator(1, "name");
        validator.required(true).min(3).max(12).validate();
        if (validator.isValid()) {
            System.out.println("is valid");
        } else {
            System.out.println("errors " + String.valueOf(validator.errors()));
        }
    }


}