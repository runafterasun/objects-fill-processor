package ru.objectsfill.template;

/**
 * Class with the template for generating a value of the corresponding boxed type.
 */
public class ClassTemplate {

    /**
     * sneaky constructor
     */
    private ClassTemplate() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * template for generating
     */
    public static final String CLASS_CONTAINER_TEMPLATE = """
            package generated.fill;
            #importListOfObject
            import #importInterface
            
            #interfaceImplementPath
            import java.util.HashMap;
            import java.util.Map;
                        
            public class #containerName implements #interfaceExtension {
                        
                private Map<Class<?>, #interfaceName> container;
                    
                public #containerName() {
                    container = new HashMap<>();
                    #fillContainerByObjects
                }
                
                 public Map<Class<?>, #interfaceName> getContainer() {
                        return container;
                 }

            }
            """;
}
