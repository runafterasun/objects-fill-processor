package objects.fill.annotation_processor;

import java.util.HashMap;
import java.util.Map;

public final class TemplateClassPrototype {

    private static Map<String, String> getBoxTypeClassCreationParameters() {
        Map<String, String> templateReplaceParameters = new HashMap<>();

        templateReplaceParameters.put("#interfaceName", "FillBoxType");
        templateReplaceParameters.put("#importInterface", "objects.fill.types.box_type.FillBoxType;");
        templateReplaceParameters.put("#interfaceExtension", "BoxTypeContainerService");
        templateReplaceParameters.put("#interfaceImplementPath", "import objects.fill.service.interfaces.BoxTypeContainerService;");

        return templateReplaceParameters;
    }

    private static Map<String, String> getObjectTypeClassCreationParameters() {
        Map<String, String> templateReplaceParameters = new HashMap<>();

        templateReplaceParameters.put("#interfaceName", "FillObjectType");
        templateReplaceParameters.put("#importInterface", "objects.fill.types.object_type.FillObjectType;");
        templateReplaceParameters.put("#interfaceExtension", "ObjectTypeContainerService");
        templateReplaceParameters.put("#interfaceImplementPath", "import objects.fill.service.interfaces.ObjectTypeContainerService;");

        return templateReplaceParameters;
    }

    private static Map<String, String> getCollectionTypeClassCreationParameters() {
        Map<String, String> templateReplaceParameters = new HashMap<>();

        templateReplaceParameters.put("#interfaceName", "FillCollectionType");
        templateReplaceParameters.put("#importInterface", "objects.fill.types.collection_type.FillCollectionType;");
        templateReplaceParameters.put("#interfaceExtension", "CollectionTypeContainerService");
        templateReplaceParameters.put("#interfaceImplementPath", "import objects.fill.service.interfaces.CollectionTypeContainerService;");

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
