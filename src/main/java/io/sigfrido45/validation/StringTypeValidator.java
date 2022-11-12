package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.Interval;
import io.sigfrido45.validation.actions.Presence;

public class StringTypeValidator extends AbstractTypeValidator<String> implements Presence<String>, Interval<String> {


    public StringTypeValidator(String attrName) {
        super(attrName, String.class);
    }

    @Override
    public StringTypeValidator required(boolean required) {
        validationFunctions.add(
                () -> {
                    if (_value == null)
                        return new Error(
                                getMsg("validation.required", getAttr(attrName))
                        );
                    return null;
                }
        );
        return this;
    }

    @Override
    public StringTypeValidator nullable(boolean nullable) {
        if (nullable) {
            continueValidating = false;
        }
        return this;
    }

    @Override
    public StringTypeValidator min(int min) {
        if (continueValidating) {
            validationFunctions.add(
                    () -> {
                        if (_value.length() <= min)
                            return new Error(
                                    getMsg("validation.str.min", getAttr(attrName), String.valueOf(min))
                            );
                        return null;
                    }
            );
        }
        return this;
    }

    @Override
    public StringTypeValidator max(int max) {
        if (continueValidating) {
            validationFunctions.add(
                    () -> {
                        if (_value.length() >= max)
                            return new Error(
                                    getMsg("validation.str.max", getAttr(attrName), String.valueOf(max))
                            );
                        return null;
                    }
            );
        }
        return this;
    }

}
