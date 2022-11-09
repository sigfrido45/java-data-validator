package org.example.asd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class ValtType<T> implements ValtCt<T> {

    protected Object value;
    protected T _value;
    protected Map<Object, Object> errors;
    protected boolean isValid;
    protected boolean continueValidating;
    protected String attrName;
    protected List<Supplier<Object>> validationFunctions;

    public ValtType(String attrName, Class<T> clazz) {
        this.attrName = attrName;
        errors = new HashMap<>();
        isValid = false;
        continueValidating = true;
        validationFunctions = new ArrayList<>() {{
            add(() -> validateCast(clazz));
        }};
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public void validate() {
        for (Supplier<Object> validationFunction : validationFunctions) {
            var error = validationFunction.get();
            if (error != null) {
                if (error instanceof Error)
                    errors.put(attrName, ((Error) error).asList());
                if (error instanceof Map)
                    errors.put(attrName, error);
                break;
            }
        }
    }

    @Override
    public boolean isValid() {
        return errors.isEmpty();
    }

    @Override
    public Map<Object, Object> errors() {
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
