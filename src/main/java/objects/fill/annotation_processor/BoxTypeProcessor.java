package objects.fill.annotation_processor;

import com.google.auto.service.AutoService;
import objects.fill.template.ClassTemplate;
import objects.fill.utils.ScanningForClassUtils;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("objects.fill.annotations.BoxType")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class BoxTypeProcessor extends AbstractProcessor {

    private static final String CLASSTYPE = "Container";
    private static final Integer MIN_MAP_SIZE = 1;
    private static final String INTERFACE_NAME = "FillBoxType";
    private static final String INTERFACE_PATH = "objects.fill.types.box_type";
    private static final String INTERFACE_IMPORT = INTERFACE_PATH + "." + INTERFACE_NAME + ";";


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<String, String> templateReplaceParameters = new HashMap<>();

        for (TypeElement typeElement : annotations) {
            Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(typeElement);

            templateReplaceParameters.put("#containerName", typeElement.getSimpleName().toString() + CLASSTYPE);
            templateReplaceParameters.put("#interfaceName", INTERFACE_NAME);
            templateReplaceParameters.put("#importInterface", INTERFACE_IMPORT);

            StringBuilder containerFill = new StringBuilder();
            StringBuilder containerImportPath = new StringBuilder();

            for (Element element : elementsAnnotatedWith) {

                fillContainer(containerFill, element.getSimpleName().toString());
                fillImportContainer(containerImportPath, element.toString());

            }

            templateReplaceParameters.put("#fillContainerByObjects", containerFill.toString());
            templateReplaceParameters.put("#importListOfObject", containerImportPath.toString());
        }

        generateClass(templateReplaceParameters);

        return true;
    }

    private void generateClass(Map<String, String> classTemplateReplace) {
        try {
            if(classTemplateReplace.size() > MIN_MAP_SIZE) {

                JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(classTemplateReplace.get("#containerName"));
                String tesdt = getReadyClass(classTemplateReplace);
                try (Writer out = javaFileObject.openWriter()) {
                    out.write(tesdt);
                }
            }
        } catch (IOException ignored) {
            throw new RuntimeException(ignored);
        }
    }
    private void fillImportContainer(StringBuilder sb, String containerClassPath) {
        sb.append("import ").append(containerClassPath).append(";").append("\n");
    }
    private void fillContainer(StringBuilder sb, String containerClassName) {
        sb.append("container.add(new ").append(containerClassName).append("());\n");
    }

    private String getReadyClass(Map<String, String> classTemplateReplace) {
        String result = ClassTemplate.classContainerTemplate;
        for (Map.Entry<String, String> classTemplate : classTemplateReplace.entrySet()) {
            result = result.replaceAll(classTemplate.getKey(), classTemplate.getValue());
        }
        return result;
    }
}
