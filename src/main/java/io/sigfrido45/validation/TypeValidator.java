package io.sigfrido45.validation;

import java.util.List;

public interface TypeValidator<T> {
    boolean isValid();
    List<String> errors();
    T validated();
    void validate();
}
