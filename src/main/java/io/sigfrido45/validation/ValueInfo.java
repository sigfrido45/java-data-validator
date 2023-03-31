package io.sigfrido45.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValueInfo {
    private Object value;
    private boolean isPresent;
}
