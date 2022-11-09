package org.example.asd;

import org.example.interfaces.Complex;
import org.example.interfaces.Interval;
import org.example.interfaces.Presence;

public class StrVal extends ValtType<String> implements Presence<String>, Interval<String>, Complex<String> {


    public StrVal(String attrName) {
        super(attrName, String.class);
    }

    @Override
    public StrVal required(boolean required) {
        validationFunctions.add(
                () -> {
                    if (_value == null)
                        return new Error("null");
                    return null;
                }
        );
        return this;
    }

    @Override
    public StrVal nullable(boolean nullable) {
        if (nullable) {
            continueValidating = false;
        }
        return this;
    }

    @Override
    public StrVal min(int min) {
        if (continueValidating) {
            validationFunctions.add(
                    () -> {
                        if (_value.length() <= min)
                            return new Error("min validation");
                        return null;
                    }
            );
        }
        return this;
    }

    @Override
    public StrVal max(int max) {
        if (continueValidating) {
            validationFunctions.add(
                    () -> {
                        if (_value.length() >= max)
                            return new Error("max validation");
                        return null;
                    }
            );
        }
        return this;
    }

    @Override
    public StrVal add(ValtType<String> another) {
        if (continueValidating) {
            validationFunctions.add(
                    () -> {
                        another.validate();
                        if (another.isValid())
                            return null;
                        return another.errors();
                    }
            );
        }
        return this;
    }
}
