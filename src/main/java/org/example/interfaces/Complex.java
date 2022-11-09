package org.example.interfaces;

import org.example.ValtType;

public interface Complex<T> {

    ValtType<T> add(ValtType<T> another);
}
