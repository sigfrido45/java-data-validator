package io.sigfrido45.validation;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractTypeValidator<T> {

  protected ValueInfo valueInfo;
  protected T _value;
  protected List<String> errors;
  protected boolean continueValidating;
  protected String attrName;
  protected List<Supplier<String>> validationFunctions;
  private Map<String, Object> context;
  private MessageGetter msgGetter;

  public AbstractTypeValidator(String attrName) {
    initContext();
    setAttrName(attrName);
    errors = new ArrayList<>();
    continueValidating = true;
    validationFunctions = new ArrayList<>();
  }

  public AbstractTypeValidator() {
    initContext();
    setAttrName("");
    errors = new ArrayList<>();
    continueValidating = true;
    validationFunctions = new ArrayList<>();
  }

  public String getAttrName() {
    return attrName;
  }

  public void setAttrName(String attrName) {
    this.attrName = attrName;
  }

  public void setValueInfo(ValueInfo valueInfo) {
    this.valueInfo = valueInfo;
  }

  public void setMsgGetter(MessageGetter msgFormat) {
    this.msgGetter = msgFormat;
  }

  public void validate() {
    for (Supplier<String> validationFunction : validationFunctions) {
      var error = validationFunction.get();
      if (error != null) {
        errors.add(error);
        break;
      }
    }
  }

  public boolean isValid() {
    return errors.isEmpty();
  }

  public List<String> errors() {
    return errors;
  }

  public T validated() {
    return _value;
  }

  public AbstractTypeValidator<T> custom(Function<Map<String, Object>, String> function) {
    validationFunctions.add(
      () -> {
        if (continueValidating) {
          return function.apply(context);
        }
        return null;
      }
    );
    return this;
  }

  public void mergeAdditionalContext(Map<String, Object> additionalContext) {
    context.putAll(additionalContext);
  }

  protected String getMsg(String code, String... args) {
    return msgGetter.getMessage(code, args);
  }

  protected String getAttr(String code) {
    return msgGetter.getMessage(code);
  }

  protected MessageGetter getMsgGetter() {
    return msgGetter;
  }

  protected Supplier<String> presentValidationFunction(boolean present) {
    return () -> {
      if (continueValidating && present && !valueInfo.isPresent()) {
        return getMsg("validation.present", getMsg(attrName));
      } else if (continueValidating && !present && !valueInfo.isPresent()) {
        continueValidating = false;
      }
      return null;
    };
  }

  protected Supplier<String> nullableValidationFunction(boolean wantNull) {
    return () -> {
      if (continueValidating && wantNull && Objects.isNull(valueInfo.getValue())) {
        continueValidating = false;
      } else if (continueValidating && !wantNull && Objects.isNull(valueInfo.getValue())) {
        return getMsg("validation.nullable", getMsg(attrName));
      }
      return null;
    };
  }

  private void initContext() {
    context = new HashMap<>();
    context.put("validator", this);
  }
}
