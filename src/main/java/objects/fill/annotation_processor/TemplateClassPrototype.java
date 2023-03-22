package objects.fill.annotation_processor;

import java.util.HashMap;
import java.util.Map;

public final class TemplateClassPrototype {

    private static final String INTERFACE_NAME = "#interfaceName";
    private static final String IMPORT_INTERFACE = "#interfaceName";
    private static final String INTERFACE_EXTENSION = "#interfaceName";
    private static final String INTERFACE_IMPLEMENT_PATH = "#interfaceName";

    private TemplateClassPrototype() {
        throw new IllegalStateException("Utility class");
    }

    private static Map<String, String> getBoxTypeClassCreationParameters() {
        Map<String, String> templateReplaceParameters = new HashMap<>();

        templateReplaceParameters.put(INTERFACE_NAME, "FillBoxType");
        templateReplaceParameters.put(IMPORT_INTERFACE, "objects.fill.types.box_type.FillBoxType;");
        templateReplaceParameters.put(INTERFACE_EXTENSION, "BoxTypeContainerService");
        templateReplaceParameters.put(INTERFACE_IMPLEMENT_PATH, "import objects.fill.service.interfaces.BoxTypeContainerService;");

        return templateReplaceParameters;
    }

    private static Map<String, String> getObjectTypeClassCreationParameters() {
        Map<String, String> templateReplaceParameters = new HashMap<>();

        templateReplaceParameters.put(INTERFACE_NAME, "FillObjectType");
        templateReplaceParameters.put(IMPORT_INTERFACE, "objects.fill.types.object_type.FillObjectType;");
        templateReplaceParameters.put(INTERFACE_EXTENSION, "ObjectTypeContainerService");
        templateReplaceParameters.put(INTERFACE_IMPLEMENT_PATH, "import objects.fill.service.interfaces.ObjectTypeContainerService;");

        return templateReplaceParameters;
    }

    private static Map<String, String> getCollectionTypeClassCreationParameters() {
        Map<String, String> templateReplaceParameters = new HashMap<>();

        templateReplaceParameters.put(INTERFACE_NAME, "FillCollectionType");
        templateReplaceParameters.put(IMPORT_INTERFACE, "objects.fill.types.collection_type.FillCollectionType;");
        templateReplaceParameters.put(INTERFACE_EXTENSION, "CollectionTypeContainerService");
        templateReplaceParameters.put(INTERFACE_IMPLEMENT_PATH, "import objects.fill.service.interfaces.CollectionTypeContainerService;");

        return templateReplaceParameters;
    }

    public static Map<String, String> selectPrototype(String annotationName) {
        return switch (annotationName) {
            case "BoxType" -> getBoxTypeClassCreationParameters();
            case "CollectionType" -> getCollectionTypeClassCreationParameters();
            case "ObjectType" -> getObjectTypeClassCreationParameters();
            default -> throw new RuntimeException();
        };
    }


}
