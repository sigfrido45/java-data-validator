package io.sigfrido45.validation;

import lombok.Data;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractTypeValidator<T> {

  protected ValueInfo valueInfo;
  protected T _value;
  protected List<String> errors;
  protected boolean continueValidating;
  protected String attrName;
  protected List<Supplier<Error>> validationFunctions;
  private MessageFormat msgReader;

  private Map<String, Object> context;

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

  public void setMsgReader(MessageFormat msgFormat) {
    this.msgReader = msgFormat;
  }


  public void validate() {
    for (Supplier<Error> validationFunction : validationFunctions) {
      var error = validationFunction.get();
      if (error != null) {
        errors.add(error.getMessage());
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


  public AbstractTypeValidator<T> custom(Function<Map<String, Object>, Error> function) {
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

  protected String getMsg(String code, String... args) {
//    return msgReader.format(code);
    return "code: " + code + " _args: " + Arrays.toString(args);
  }

  protected String getAttr(String attr) {
//    return msgReader.format(attr);
    return "attr:" + attr;
  }

  protected Supplier<Error> presentValidationFunction(boolean present) {
    return () -> {
      if (continueValidating && present && !valueInfo.isPresent()) {
        return new Error("validation.presence");
      } else if (continueValidating && !present && !valueInfo.isPresent()) {
        continueValidating = false;
      }
      return null;
    };
  }

  protected Supplier<Error> nullableValidationFunction(boolean wantNull) {
    return () -> {
      if (continueValidating && wantNull && Objects.isNull(valueInfo.getValue())) {
        continueValidating = false;
      } else if (continueValidating && !wantNull && Objects.isNull(valueInfo.getValue())) {
        return new Error("validation.nullable");
      }
      return null;
    };
  }

  private void initContext() {
    context = new HashMap<>();
    context.put("val", this);
  }

}
