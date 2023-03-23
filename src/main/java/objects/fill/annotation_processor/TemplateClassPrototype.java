package objects.fill.annotation_processor;

import objects.fill.annotation_processor.exceptions.AnnotationProcessorException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class TemplateClassPrototype {

    private TemplateClassPrototype() {
        throw new IllegalStateException("Utility class");
    }

    private static final Function<Map<String, String>, Function<String, String>> interfaceName = map -> tempVal -> map.put("#interfaceName", tempVal);
    private static final Function<Map<String, String>, Function<String, String>> importInterface = map -> tempVal -> map.put("#importInterface", tempVal);
    private static final Function<Map<String, String>, Function<String, String>> interfaceExtension = map -> tempVal -> map.put("#interfaceExtension", tempVal);
    private static final Function<Map<String, String>, Function<String, String>> interfaceImplementPath = map -> tempVal -> map.put("#interfaceImplementPath", tempVal);

    private static Map<String, String> getBoxTypeClassCreationParameters() {
        Map<String, String> templateReplaceParameters = new HashMap<>();

        interfaceName.apply(templateReplaceParameters).apply("BoxTypeFill");
        importInterface.apply(templateReplaceParameters).apply("objects.fill.types.box_type.BoxTypeFill;");
        interfaceExtension.apply(templateReplaceParameters).apply("BoxTypeContainerService");
        interfaceImplementPath.apply(templateReplaceParameters).apply("import objects.fill.service.interfaces.BoxTypeContainerService;");

        return templateReplaceParameters;
    }

    private static Map<String, String> getObjectTypeClassCreationParameters() {
        Map<String, String> templateReplaceParameters = new HashMap<>();

        interfaceName.apply(templateReplaceParameters).apply("ObjectTypeFill");
        importInterface.apply(templateReplaceParameters).apply("objects.fill.types.object_type.ObjectTypeFill;");
        interfaceExtension.apply(templateReplaceParameters).apply("ObjectTypeContainerService");
        interfaceImplementPath.apply(templateReplaceParameters).apply("import objects.fill.service.interfaces.ObjectTypeContainerService;");

        return templateReplaceParameters;
    }

    private static Map<String, String> getCollectionTypeClassCreationParameters() {
        Map<String, String> templateReplaceParameters = new HashMap<>();

        interfaceName.apply(templateReplaceParameters).apply("CollectionTypeFill");
        importInterface.apply(templateReplaceParameters).apply("objects.fill.types.collection_type.CollectionTypeFill;");
        interfaceExtension.apply(templateReplaceParameters).apply("CollectionTypeContainerService");
        interfaceImplementPath.apply(templateReplaceParameters).apply("import objects.fill.service.interfaces.CollectionTypeContainerService;");

        return templateReplaceParameters;
    }

    public static Map<String, String> selectPrototype(String annotationName) {
        return switch (annotationName) {
            case "BoxType" -> getBoxTypeClassCreationParameters();
            case "CollectionType" -> getCollectionTypeClassCreationParameters();
            case "ObjectType" -> getObjectTypeClassCreationParameters();
            default -> throw new AnnotationProcessorException();
        };
    }


}
