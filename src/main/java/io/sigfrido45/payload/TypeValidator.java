package io.sigfrido45.payload;

import io.sigfrido45.validation.*;

public class TypeValidator {

  public static StringTypeValidator str(String attrName) {
    return new StringTypeValidator(attrName);
  }

  public static StringTypeValidator str() {
    return new StringTypeValidator();
  }

  public static StringReactiveTypeValidator strReactive(String attrName) {
    return new StringReactiveTypeValidator(attrName);
  }

  public static StringReactiveTypeValidator strReactive() {
    return new StringReactiveTypeValidator();
  }


  public static LongTypeValidator long_(String attrName) {
    return new LongTypeValidator(attrName);
  }

  public static LongTypeValidator long_() {
    return new LongTypeValidator();
  }


  public static LongReactiveValidator longReactive(String attrName) {
    return new LongReactiveValidator(attrName);
  }

  public static LongReactiveValidator longReactive() {
    return new LongReactiveValidator();
  }


  public static ListTypeValidator list(String attrName) {
    return new ListTypeValidator(attrName);
  }

  public static ListTypeValidator list() {
    return new ListTypeValidator();
  }

  public static ListReactiveValidator listReactive(String attrName) {
    return new ListReactiveValidator(attrName);
  }

  public static ListReactiveValidator listReactive() {
    return new ListReactiveValidator();
  }

  public static IntTypeValidator int_(String attrName) {
    return new IntTypeValidator(attrName);
  }

  public static IntTypeValidator int_() {
    return new IntTypeValidator();
  }

  public static IntReactiveTypeValidator intReactive(String attrName) {
    return new IntReactiveTypeValidator(attrName);
  }

  public static IntReactiveTypeValidator intReactive() {
    return new IntReactiveTypeValidator();
  }

  public static BooleanTypeValidator bool(String attrName) {
    return new BooleanTypeValidator(attrName);
  }

  public static BooleanTypeValidator bool() {
    return new BooleanTypeValidator();
  }

  public static BooleanReactiveTypeValidator boolReactive(String attrName) {
    return new BooleanReactiveTypeValidator(attrName);
  }

  public static BooleanReactiveTypeValidator boolReactive() {
    return new BooleanReactiveTypeValidator();
  }
}
