package org.example.asd;

import lombok.Data;

@Data
public class ValueInfo<T> {

    private T data;
    private int index;
    private Object value;
}
