package org.example.asd;

import org.example.interfaces.Presence;

import java.util.Map;

public class MapVal extends ValtType<Map> implements Presence<Map> {

    public MapVal(String attrName) {
        super(attrName, Map.class);
    }

    @Override
    public ValtType<Map> required(boolean required) {
        validationFunctions.add(
                () -> {
                    if (_value == null)
                        return new Error("null");
                    return null;
                }
        );
        return this;
    }

    @Override
    public ValtType<Map> nullable(boolean nullable) {
        if (nullable) {
            continueValidating = false;
        }
        return this;
    }
}
