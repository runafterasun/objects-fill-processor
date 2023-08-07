package ru.objectsfill.annotation_processor;

import com.google.auto.service.AutoService;
import ru.objectsfill.annotation_processor.utils.ClassTemplatePrepare;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * generate class from template
 */
@SupportedAnnotationTypes("ru.objectsfill.annotations.*")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
@SuppressWarnings("unused")
public class TypeProcessor extends AbstractProcessor {

    private static final String CLASS_TYPE = "Container";
    /**
     * Processes the annotations and generates template classes.
     *
     * @param annotations the set of annotations being processed
     * @param roundEnv the round environment
     * @return true to indicate that the annotations have been processed
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement typeElement : annotations) {
            Map<String, String> templateReplaceParameters = TemplateClassPrototype.selectPrototype(typeElement.getSimpleName().toString());

            templateReplaceParameters.put("#containerName", typeElement.getSimpleName().toString() + CLASS_TYPE);

            StringBuilder containerFill = new StringBuilder();
            StringBuilder containerImportPath = new StringBuilder();

            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {

                var annotationValue = getAnnotationValue(element);
                if(annotationValue.isPresent()) {
                    var valAnnotation = annotationValue.get().toString();
                    var elementName = element.getSimpleName().toString();
                    ClassTemplatePrepare.fillContainer(containerFill, valAnnotation, elementName);
                    ClassTemplatePrepare.fillImportContainer(containerImportPath, element.toString());
                }
            }

            templateReplaceParameters.put("#fillContainerByObjects", containerFill.toString());
            templateReplaceParameters.put("#importListOfObject", containerImportPath.toString());

            if(!annotations.isEmpty()) {
                generateClass(templateReplaceParameters);
            }
        }

        return true;
    }
    /**
     * Retrieves the value of the annotation from the given element.
     *
     * @param element the element containing the annotation
     * @return the optional annotation value
     */
    private Optional<? extends AnnotationValue> getAnnotationValue(Element element) {
        return element.getAnnotationMirrors().stream()
                .map(annotationMirror -> annotationMirror.getElementValues().values())
                .flatMap(Collection::stream)
                .findFirst();
    }
    /**
     * Generates the template class based on the provided class template replacements.
     *
     * @param classTemplateReplace the map of class template replacements
     */
    private void generateClass(Map<String, String> classTemplateReplace) {
        try {
            JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(classTemplateReplace.get("#containerName"));
            try (Writer out = javaFileObject.openWriter()) {
                out.write(ClassTemplatePrepare.getReadyClass(classTemplateReplace));
            }
        } catch (IOException ignored) {
        }
    }

}
