package be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.generator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;

public abstract class ResourceGenerator extends AbstractGenerator {



    public ResourceGenerator(ProcessingEnvironment processingEnvironment, String template, String output) {
        super(processingEnvironment, template, output);
    }

    @Override
    protected FileObject getFileObject() throws IOException {
        return this.getProcessingEnvironment()
                .getFiler()
                .createResource(StandardLocation.CLASS_OUTPUT, "", this.getOutput() , (Element[]) null);
    }

}
