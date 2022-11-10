package io.sigfrido45.tree;

import io.sigfrido45.validation.StringValidationAbstractType;
import io.sigfrido45.validation.MapValidationAbstractType;

public class Validation {

    public static StringValidationAbstractType strValidator(String attrName) {
        return new StringValidationAbstractType(attrName);
    }

    public static MapValidationAbstractType mapValidator(String attrName) {
        return new MapValidationAbstractType(attrName);
    }
}
