package io.sigfrido45.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractTypeValidator<T> implements TypeValidator<T> {

    protected Object value;
    protected T _value;
    protected List<String> errors;
    protected boolean isValid;
    protected boolean continueValidating;
    protected String attrName;
    protected List<Supplier<Error>> validationFunctions;

    public AbstractTypeValidator(String attrName, Class<T> clazz) {
        this.attrName = attrName;
        errors = new ArrayList<>();
        isValid = false;
        continueValidating = true;
        validationFunctions = new ArrayList<>() {{
            add(() -> validateCast(clazz));
        }};
    }

    public String getAttrName() {
        return attrName;
    }

    public void setValue(Object value) {
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


    private Error validateCast(Class<T> clazz) {
        if (value != null) {
            _value = getCasted(clazz, value);
            return _value != null ? null : new Error("casted error");
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
