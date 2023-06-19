package be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.generator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.FileObject;
import java.io.IOException;

public abstract class SourceGenerator extends AbstractGenerator {

    public SourceGenerator(ProcessingEnvironment processingEnvironment, String template, String output) {
        super(processingEnvironment, template, output);
    }

    @Override
    protected FileObject getFileObject() throws IOException {
        return this.getProcessingEnvironment()
                .getFiler()
                .createSourceFile(this.getOutput(), (Element[]) null);
    }

}
