package org.example.interfaces;

import org.example.IntervalInfo;
import org.example.asd.ValtType;

public interface Interval<T> {

    ValtType<T> min(int min);

    ValtType<T> max(int max);
}
