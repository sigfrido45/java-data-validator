package io.sigfrido45.payload;

import io.sigfrido45.validation.StringTypeValidator;
import io.sigfrido45.validation.MapTypeValidator;

public class TypeValidator {

    public static StringTypeValidator str(String attrName) {
        return new StringTypeValidator(attrName);
    }

    public static MapTypeValidator map(String attrName) {
        return new MapTypeValidator(attrName);
    }
}
