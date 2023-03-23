package objects.fill.template;

public class ClassTemplate {

    private ClassTemplate() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CLASS_CONTAINER_TEMPLATE = """
            package generated.fill;
            #importListOfObject
            import #importInterface
            
            #interfaceImplementPath
            import java.util.ArrayList;
            import java.util.List;
                        
            public class #containerName implements #interfaceExtension {
                        
                private List<#interfaceName> container;
                    
                public #containerName() {
                    container = new ArrayList<>();
                    #fillContainerByObjects
                }
                
                 public List<#interfaceName> getContainer() {
                        return container;
                 }

            }
            """;
}
