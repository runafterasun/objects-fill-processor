package ru.objectsfill.annotation_processor;

import ru.objectsfill.annotation_processor.exceptions.AnnotationProcessorException;

import java.util.Map;

/**
 * A utility class for creating template class prototypes based on annotation names.
 */
public final class TemplateClassPrototype {

    private TemplateClassPrototype() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Retrieves the parameters for creating a template class for BoxType annotation.
     *
     * @return the parameters map
     */
    private static Map<String, String> getBoxTypeClassCreationParameters() {
        return new Params()
                .setInterfaceName("BoxTypeFill")
                .setImportInterface("ru.objectsfill.types.box_type.BoxTypeFill;")
                .setInterfaceExtension("BoxTypeContainerService")
                .setInterfaceImplementPath("import ru.objectsfill.service.interfaces.BoxTypeContainerService;")
                .getParameters();
    }

    /**
     * Retrieves the parameters for creating a template class for ObjectType annotation.
     *
     * @return the parameters map
     */
    private static Map<String, String> getObjectTypeClassCreationParameters() {
        return new Params()
                .setInterfaceName("ObjectTypeFill")
                .setImportInterface("ru.objectsfill.types.object_type.ObjectTypeFill;")
                .setInterfaceExtension("ObjectTypeContainerService")
                .setInterfaceImplementPath("import ru.objectsfill.service.interfaces.ObjectTypeContainerService;")
                .getParameters();
    }

    /**
     * Retrieves the parameters for creating a template class for CollectionType annotation.
     *
     * @return the parameters map
     */
    private static Map<String, String> getCollectionTypeClassCreationParameters() {
        return new Params()
                .setInterfaceName("CollectionTypeFill")
                .setImportInterface("ru.objectsfill.types.collection_type.CollectionTypeFill;")
                .setInterfaceExtension("CollectionTypeContainerService")
                .setInterfaceImplementPath("import ru.objectsfill.service.interfaces.CollectionTypeContainerService;")
                .getParameters();
    }

    /**
     * Selects the prototype parameters based on the given annotation name.
     *
     * @param annotationName the name of the annotation
     * @return the parameters map for the corresponding annotation
     * @throws AnnotationProcessorException if the annotation name is not recognized
     */
    public static Map<String, String> selectPrototype(String annotationName) throws AnnotationProcessorException {
        return switch (annotationName) {
            case "BoxType" -> getBoxTypeClassCreationParameters();
            case "CollectionType" -> getCollectionTypeClassCreationParameters();
            case "ObjectType" -> getObjectTypeClassCreationParameters();
            default -> throw new AnnotationProcessorException();
        };
    }

}
