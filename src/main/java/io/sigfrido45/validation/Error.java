package io.sigfrido45.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {
    private String key;
    private String message;

    public Error(String message) {
        this.message = message;
    }
}
