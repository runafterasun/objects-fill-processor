package objects.fill.annotation_processor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import static objects.fill.annotation_processor.utils.ClassTemplatePrepare.fillContainer;
import static objects.fill.annotation_processor.utils.ClassTemplatePrepare.fillImportContainer;
import static objects.fill.annotation_processor.utils.ClassTemplatePrepare.getReadyClass;

@SupportedAnnotationTypes("objects.fill.annotations.*")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
@SuppressWarnings("unused")
public class TypeProcessor extends AbstractProcessor {

    private static final String CLASS_TYPE = "Container";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement typeElement : annotations) {
            Map<String, String> templateReplaceParameters = TemplateClassPrototype.selectPrototype(typeElement.getSimpleName().toString());

            templateReplaceParameters.put("#containerName", typeElement.getSimpleName().toString() + CLASS_TYPE);

            StringBuilder containerFill = new StringBuilder();
            StringBuilder containerImportPath = new StringBuilder();

            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
                fillContainer(containerFill, element.getSimpleName().toString());
                fillImportContainer(containerImportPath, element.toString());
            }

            templateReplaceParameters.put("#fillContainerByObjects", containerFill.toString());
            templateReplaceParameters.put("#importListOfObject", containerImportPath.toString());

            if(!annotations.isEmpty()) {
                generateClass(templateReplaceParameters);
            }
        }

        return true;
    }

    private void generateClass(Map<String, String> classTemplateReplace) {
        try {
            JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(classTemplateReplace.get("#containerName"));
            try (Writer out = javaFileObject.openWriter()) {
                out.write(getReadyClass(classTemplateReplace));
            }
        } catch (IOException ignored) {
        }
    }

}
