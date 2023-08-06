package ru.objectsfill.annotation_processor;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing parameters for generating code.
 */
public class Params {

    private final Map<String, String> parameters = new HashMap<>();

    /**
     * Sets the value for the interface name parameter.
     *
     * @param val the value to set
     * @return the Params object for method chaining
     */
    public Params setInterfaceName(String val) {
        String interfaceName = "#interfaceName";
        parameters.put(interfaceName, val);
        return this;
    }

    /**
     * Sets the value for the import interface parameter.
     *
     * @param val the value to set
     * @return the Params object for method chaining
     */
    public Params setImportInterface(String val) {
        String importInterface = "#importInterface";
        parameters.put(importInterface, val);
        return this;
    }

    /**
     * Sets the value for the interface extension parameter.
     *
     * @param val the value to set
     * @return the Params object for method chaining
     */
    public Params setInterfaceExtension(String val) {
        String interfaceExtension = "#interfaceExtension";
        parameters.put(interfaceExtension, val);
        return this;
    }

    /**
     * Sets the value for the interface implement path parameter.
     *
     * @param val the value to set
     * @return the Params object for method chaining
     */
    public Params setInterfaceImplementPath(String val) {
        String interfaceImplementPath = "#interfaceImplementPath";
        parameters.put(interfaceImplementPath, val);
        return this;
    }

    /**
     * Retrieves the parameters as a map.
     *
     * @return the map of parameters
     */
    public Map<String, String> getParameters() {
        return this.parameters;
    }

}
