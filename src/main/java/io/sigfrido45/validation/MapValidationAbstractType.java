package io.sigfrido45.validation;

import io.sigfrido45.interfaces.Presence;

import java.util.Map;

public class MapValidationAbstractType extends AbstractTypeValidation<Map> implements Presence<Map> {

    public MapValidationAbstractType(String attrName) {
        super(attrName, Map.class);
    }

    @Override
    public AbstractTypeValidation<Map> required(boolean required) {
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
    public AbstractTypeValidation<Map> nullable(boolean nullable) {
        if (nullable) {
            continueValidating = false;
        }
        return this;
    }
}
