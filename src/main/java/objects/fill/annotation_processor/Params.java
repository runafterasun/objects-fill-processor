package objects.fill.annotation_processor;

import java.util.HashMap;
import java.util.Map;

public class Params {

    private final Map<String, String> parameters = new HashMap<>();

    public Params setInterfaceName(String val) {
        String interfaceName = "#interfaceName";
        parameters.put(interfaceName, val);
        return this;
    }

    public Params setImportInterface(String val) {
        String importInterface = "#importInterface";
        parameters.put(importInterface, val);
        return this;
    }

    public Params setInterfaceExtension(String val) {
        String interfaceExtension = "#interfaceExtension";
        parameters.put(interfaceExtension, val);
        return this;
    }

    public Params setInterfaceImplementPath(String val) {
        String interfaceImplementPath = "#interfaceImplementPath";
        parameters.put(interfaceImplementPath, val);
        return this;
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

}
