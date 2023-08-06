package ru.objectsfill.template;

public class ClassTemplate {

    private ClassTemplate() {
        throw new IllegalStateException("Utility class");
    }

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
