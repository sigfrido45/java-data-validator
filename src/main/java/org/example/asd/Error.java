package org.example.asd;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class Error {
    private String message;

    public List<String> asList() {
        return Collections.singletonList(message);
    }
}
