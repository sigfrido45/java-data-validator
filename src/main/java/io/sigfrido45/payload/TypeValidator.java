package io.sigfrido45.payload;

import io.sigfrido45.validation.ListTypeValidator;
import io.sigfrido45.validation.LongTypeValidator;
import io.sigfrido45.validation.StringTypeValidator;

public class TypeValidator {

  public static StringTypeValidator str(String attrName) {
    return new StringTypeValidator(attrName);
  }


  public static LongTypeValidator long_(String attrName) {
    return new LongTypeValidator(attrName);
  }

  public static ListTypeValidator list(String attrName) {
    return new ListTypeValidator(attrName);
  }
}
