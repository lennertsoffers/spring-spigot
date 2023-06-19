package be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.generator;

import be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.VelocityProvider;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public abstract class AbstractGenerator implements Generator {

    private final ProcessingEnvironment processingEnvironment;
    private final String output;
    private final String template;

    public AbstractGenerator(ProcessingEnvironment processingEnvironment, String template, String output) {
        this.processingEnvironment = processingEnvironment;
        this.output = output;
        this.template = template;
    }

    public ProcessingEnvironment getProcessingEnvironment() {
        return processingEnvironment;
    }

    public String getOutput() {
        return output;
    }

    public void generate() {
        VelocityEngine velocityEngine = VelocityProvider.getVelocityEngine();
        Template template = velocityEngine.getTemplate(this.template);

        StringWriter stringWriter = new StringWriter();
        template.merge(this.getVelocityContext(), stringWriter);
        String outputContent = stringWriter.toString();

        try {
            FileObject fileObject = this.getFileObject();

            Writer fileWriter = fileObject.openWriter();
            fileWriter.write(outputContent);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract FileObject getFileObject() throws IOException;

}
