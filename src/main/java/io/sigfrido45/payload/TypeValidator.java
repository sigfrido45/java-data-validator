package io.sigfrido45.payload;

import io.sigfrido45.validation.IntTypeValidator;
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

  public static LongTypeValidator long_() {
    return new LongTypeValidator();
  }

  public static ListTypeValidator list(String attrName, Class<?> clazz) {
    return new ListTypeValidator(attrName, clazz);
  }

  public static ListTypeValidator list(Class<?> clazz) {
    return new ListTypeValidator(clazz);
  }

  public static IntTypeValidator int_(String attrName) {
    return new IntTypeValidator(attrName);
  }

  public static IntTypeValidator int_() {
    return new IntTypeValidator();
  }
}
