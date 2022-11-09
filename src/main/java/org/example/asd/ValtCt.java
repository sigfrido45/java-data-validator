package org.example.asd;

import java.util.Map;

public interface ValtCt<T> {
    boolean isValid();
    Map<Object, Object> errors();
    T validated();
    void validate();
}
