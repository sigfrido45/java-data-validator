package io.sigfrido45.validation;

public interface MessageGetter {
  String getMessage(String code, String ...args);
  String getMessage(String code);
}
