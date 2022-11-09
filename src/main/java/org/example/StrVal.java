package org.example;

import org.example.interfaces.Complex;
import org.example.interfaces.Interval;
import org.example.interfaces.Presence;

import java.util.Map;

public class StrVal extends ValtType<String> implements Presence<String>, Interval<String>, Complex<String> {


    public StrVal(Object value, String attrName, Class<String> clazz) {
        super(value, attrName, clazz);
    }

    @Override
    public ValtType<String> required(boolean required) {
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
    public ValtType<String> nullable(boolean nullable) {
        if (nullable) {
            continueValidating = false;
        }
        return this;
    }


    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Map<Object, Object> errors() {
        return null;
    }

    @Override
    public String validated() {
        return null;
    }

    @Override
    public void validate() {

    }

    @Override
    public ValtType<String> min(int min) {
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
    public ValtType<String> max(int max) {
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
    public ValtType<String> add(ValtType<String> another) {
        if (continueValidating) {
            validationFunctions.add(
                    () -> {
                        another.validate();
                        if(another.isValid())
                            return null;
                        return another.errors();
                    }
            );
        }
        return this;
    }
}
