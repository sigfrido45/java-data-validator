package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.Presence;

import java.util.Map;

public class MapTypeValidator extends AbstractTypeValidator<Map> implements Presence<Map> {

    public MapTypeValidator(String attrName) {
        super(attrName, Map.class);
    }

    @Override
    public AbstractTypeValidator<Map> required(boolean required) {
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
    public AbstractTypeValidator<Map> nullable(boolean nullable) {
        if (nullable) {
            continueValidating = false;
        }
        return this;
    }
}
