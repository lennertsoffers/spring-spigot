package be.lennertsoffers.spigotbootstrapper.annotationprocessor.processor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import java.util.List;

public interface Validator {

    void validate(List<? extends Element> elements, ProcessingEnvironment processingEnvironment);

}
