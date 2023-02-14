package io.sigfrido45.validation;

import io.sigfrido45.messages.ValidationMsgReader;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractTypeValidator<T> implements TypeValidator<T> {

    protected Object value;
    protected T _value;
    protected List<String> errors;
    protected boolean continueValidating;
    protected String attrName;
    protected List<Supplier<Error>> validationFunctions;
    private final ValidationMsgReader msgReader;

    public AbstractTypeValidator(String attrName, Class<T> clazz) {
        this.attrName = attrName;
        errors = new ArrayList<>();
        continueValidating = true;
        this.msgReader = new ValidationMsgReader(
                System.getProperty("validator.error-messages"),
                System.getProperty("validator.attributes-messages")
        );
        validationFunctions = new ArrayList<>() {{
            add(() -> validateCast(clazz));
        }};
    }

    public String getAttrName() {
        return attrName;
    }


    public void setValue(Object value) { //we dont save value we save value info
        this.value = value;
    }

    @Override
    public void validate() {
        for (Supplier<Error> validationFunction : validationFunctions) {
            var error = validationFunction.get();
            if (error != null) {
                errors.add(error.getMessage());
                break;
            }
        }
    }

    @Override
    public boolean isValid() {
        return errors.isEmpty();
    }

    @Override
    public List<String> errors() {
        return errors;
    }

    @Override
    public T validated() {
        return _value;
    }

    protected String getMsg(String code, String... args) {
        var msg = msgReader.getMsg(code);
        for (int i = 0; i < args.length; i++) {
            msg = msg.replace("{" + i + "}", args[i]);
        }
        return msg;
    }

    protected String getAttr(String attr) {
        return msgReader.getProperty(attr, attr);
    }

    private Error validateCast(Class<T> clazz) {
        if (value != null) {
            _value = getCasted(clazz, value);
            return _value != null ? null : new Error(
                    getMsg("validation.type", getAttr(attrName))
            );
        }
        return null;
    }

    private T getCasted(Class<T> clazz, Object value) {
        try {
            return clazz.cast(value);
        } catch (Exception E) {
            return null;
        }
    }
}
