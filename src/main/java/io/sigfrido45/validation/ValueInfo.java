package io.sigfrido45.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValueInfo<T> {
    private T value;
    private boolean isPresent;
}
