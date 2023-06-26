package objects.fill.annotation_processor;

import objects.fill.annotation_processor.exceptions.AnnotationProcessorException;

import java.util.Map;

public final class TemplateClassPrototype {

    private TemplateClassPrototype() {
        throw new IllegalStateException("Utility class");
    }
    private static Map<String, String> getBoxTypeClassCreationParameters() {
        return new Params()
                .setInterfaceName("BoxTypeFill")
                .setImportInterface("objects.fill.types.box_type.BoxTypeFill;")
                .setInterfaceExtension("BoxTypeContainerService")
                .setInterfaceImplementPath("import objects.fill.service.interfaces.BoxTypeContainerService;")
                .getParameters();
    }

    private static Map<String, String> getObjectTypeClassCreationParameters() {
        return new Params()
                .setInterfaceName("ObjectTypeFill")
                .setImportInterface("objects.fill.types.object_type.ObjectTypeFill;")
                .setInterfaceExtension("ObjectTypeContainerService")
                .setInterfaceImplementPath("import objects.fill.service.interfaces.ObjectTypeContainerService;")
                .getParameters();
    }

    private static Map<String, String> getCollectionTypeClassCreationParameters() {

        return new Params()
                .setInterfaceName("CollectionTypeFill")
                .setImportInterface("objects.fill.types.collection_type.CollectionTypeFill;")
                .setInterfaceExtension("CollectionTypeContainerService")
                .setInterfaceImplementPath("import objects.fill.service.interfaces.CollectionTypeContainerService;")
                .getParameters();

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
