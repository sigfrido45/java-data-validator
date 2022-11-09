package org.example.interfaces;

import org.example.asd.ValtType;

public interface Presence<T> {

    ValtType<T> required(boolean required);

    ValtType<T> nullable(boolean nullable);
}
