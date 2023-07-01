package objects.fill.annotation_processor.utils;

import objects.fill.template.ClassTemplate;

import java.util.Map;

/**
 * Utility class for preparing class templates.
 */
public class ClassTemplatePrepare {

    private ClassTemplatePrepare() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Appends an import statement for the specified container class path to the StringBuilder.
     *
     * @param sb               the StringBuilder to append the import statement to
     * @param containerClassPath the fully qualified path of the container class to import
     */
    public static void fillImportContainer(StringBuilder sb, String containerClassPath) {
        sb.append("import ").append(containerClassPath).append(";").append("\n");
    }

    /**
     * Fills the container StringBuilder with the putIfAbsent statement for the specified container class and container class name.
     *
     * @param sb               the StringBuilder representing the container
     * @param containerClass   the container class to putIfAbsent
     * @param containerClassName the name of the container class
     */
    public static void fillContainer(StringBuilder sb, String containerClass, String containerClassName) {
        sb.append("container.putIfAbsent(").append(containerClass).append(", new ").append(containerClassName).append("());\n");
    }

    /**
     * Gets the ready class by replacing placeholders in the class template with the provided values.
     *
     * @param classTemplateReplace the map containing placeholders and their corresponding replacement values
     * @return the resulting ready class
     */
    public static String getReadyClass(Map<String, String> classTemplateReplace) {
        String result = ClassTemplate.CLASS_CONTAINER_TEMPLATE;
        for (Map.Entry<String, String> classTemplate : classTemplateReplace.entrySet()) {
            result = result.replaceAll(classTemplate.getKey(), classTemplate.getValue());
        }
        return result;
    }
}
