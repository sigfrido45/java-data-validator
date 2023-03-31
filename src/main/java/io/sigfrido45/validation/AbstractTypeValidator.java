package io.sigfrido45.validation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractTypeValidator<T> {

  protected ValueInfo valueInfo;
  protected T _value;
  protected List<String> errors;
  protected boolean continueValidating;
  protected String attrName;
  protected List<Supplier<Error>> validationFunctions;
  private MessageFormat msgReader;

  public AbstractTypeValidator(String attrName) {
    this.attrName = attrName;
    errors = new ArrayList<>();
    continueValidating = true;
    validationFunctions = new ArrayList<>();
  }

  public String getAttrName() {
    return attrName;
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

  protected String getMsg(String code, String... args) {
//    return msgReader.format(code);
    return "code: " + code + " _args: " + Arrays.toString(args);
  }

  protected String getAttr(String attr) {
//    return msgReader.format(attr);
    return "attr:" + attr;
  }


}
