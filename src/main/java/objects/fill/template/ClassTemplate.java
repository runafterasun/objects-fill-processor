package objects.fill.template;

public class ClassTemplate {

    public static final String classContainerTemplate = """
            package object.fill;
            #importListOfObject
            import #importInterface
            
            import objects.fill.service.interfaces.BoxTypeContainerService;
            import java.util.ArrayList;
            import java.util.List;
                        
            public class #containerName implements BoxTypeContainerService {
                        
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
