package io.sigfrido45.validation;

import io.sigfrido45.interfaces.Interval;
import io.sigfrido45.interfaces.Presence;

public class StringValidationAbstractType extends AbstractTypeValidation<String> implements Presence<String>, Interval<String> {


    public StringValidationAbstractType(String attrName) {
        super(attrName, String.class);
    }

    @Override
    public StringValidationAbstractType required(boolean required) {
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
    public StringValidationAbstractType nullable(boolean nullable) {
        if (nullable) {
            continueValidating = false;
        }
        return this;
    }

    @Override
    public StringValidationAbstractType min(int min) {
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
    public StringValidationAbstractType max(int max) {
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

}
