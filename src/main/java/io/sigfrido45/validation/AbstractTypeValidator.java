package io.sigfrido45.validation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractTypeValidator<T> {
  public static final String FIELD_PREFIX = "validation.field.";
  public static final String NULL_STR_VALUE = "null";
  protected ValueInfo valueInfo;
  protected T _value;
  protected List<String> errors;
  protected boolean continueValidating;
  protected String attrName;
  protected List<Supplier<String>> validationFunctions;
  protected List<Supplier<Mono<String>>> reactiveValidationFunctions;
  private Map<String, Object> context;
  private MessageGetter msgGetter;

  public AbstractTypeValidator(String attrName) {
    initContext();
    setAttrName(attrName);
    errors = new ArrayList<>();
    continueValidating = true;
    validationFunctions = new ArrayList<>();
    reactiveValidationFunctions = new ArrayList<>();
  }

  public AbstractTypeValidator() {
    initContext();
    setAttrName("");
    errors = new ArrayList<>();
    continueValidating = true;
    validationFunctions = new ArrayList<>();
    reactiveValidationFunctions = new ArrayList<>();
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

  public Mono<Void> reactiveValidate() {
    return Flux.fromIterable(reactiveValidationFunctions)
      .flatMap(Supplier::get)
      .takeUntil(str -> !str.equalsIgnoreCase(AbstractTypeValidator.NULL_STR_VALUE))
      .collectList()
      .map(errs -> {
        var filtered = errs.stream().filter(x -> !x.equalsIgnoreCase(AbstractTypeValidator.NULL_STR_VALUE)).toList();
        errors.addAll(filtered);
        return true;
      })
      .then();
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

  public AbstractTypeValidator<T> customAsync(Function<Map<String, Object>, Mono<String>> function) {
    reactiveValidationFunctions.add(
      () -> {
        if (continueValidating) {
          return function.apply(context);
        }
        return Mono.just(AbstractTypeValidator.NULL_STR_VALUE);
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
        return getMsg("validation.present", getAttr(FIELD_PREFIX + attrName));
      } else if (continueValidating && !present && !valueInfo.isPresent()) {
        continueValidating = false;
      }
      return null;
    };
  }

  protected Mono<String> presentAsyncValidationFunction(boolean present) {
    return Mono.fromCallable(() -> {
      if (continueValidating && present && !valueInfo.isPresent()) {
        return getMsg("validation.present", getAttr(FIELD_PREFIX + attrName));
      } else if (continueValidating && !present && !valueInfo.isPresent()) {
        continueValidating = false;
      }
      return NULL_STR_VALUE;
    });
  }

  protected Supplier<String> nullableValidationFunction(boolean wantNull) {
    return () -> {
      if (continueValidating && wantNull && isNull(valueInfo.getValue())) {
        continueValidating = false;
      } else if (continueValidating && !wantNull && isNull(valueInfo.getValue())) {
        return getMsg("validation.nullable", getAttr(FIELD_PREFIX + attrName));
      }
      return null;
    };
  }

  protected Mono<String> nullableAsyncValidationFunction(boolean wantNull) {
    return Mono.fromCallable(() -> {
      if (continueValidating && wantNull && isNull(valueInfo.getValue())) {
        continueValidating = false;
      } else if (continueValidating && !wantNull && isNull(valueInfo.getValue())) {
        return getMsg("validation.nullable", getAttr(FIELD_PREFIX + attrName));
      }
      return NULL_STR_VALUE;
    });
  }


  protected String validateCast(CastInfo<T> castedInfo) {
    if (castedInfo.isValid()) {
      _value = castedInfo.getCasted();
      return null;
    }
    return getMsg("validation.type", getAttr(FIELD_PREFIX + attrName));
  }

  protected boolean isNull(Object value) {
    var strVal = String.valueOf(value);
    return strVal.equalsIgnoreCase(NULL_STR_VALUE);
  }

  private void initContext() {
    context = new HashMap<>();
    context.put("validator", this);
  }
}
