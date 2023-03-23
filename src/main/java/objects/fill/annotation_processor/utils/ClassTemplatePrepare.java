package objects.fill.annotation_processor.utils;

import objects.fill.template.ClassTemplate;

import java.util.Map;

public class ClassTemplatePrepare {

    private ClassTemplatePrepare() {
        throw new IllegalStateException("Utility class");
    }

    public static void fillImportContainer(StringBuilder sb, String containerClassPath) {
        sb.append("import ").append(containerClassPath).append(";").append("\n");
    }

    public static  void fillContainer(StringBuilder sb, String containerClassName) {
        sb.append("container.add(new ").append(containerClassName).append("());\n");
    }

    public static  String getReadyClass(Map<String, String> classTemplateReplace) {
        String result = ClassTemplate.CLASS_CONTAINER_TEMPLATE;
        for (Map.Entry<String, String> classTemplate : classTemplateReplace.entrySet()) {
            result = result.replaceAll(classTemplate.getKey(), classTemplate.getValue());
        }
        return result;
    }
}
