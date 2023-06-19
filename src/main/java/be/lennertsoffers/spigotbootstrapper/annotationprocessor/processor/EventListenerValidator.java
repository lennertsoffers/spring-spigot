package be.lennertsoffers.spigotbootstrapper.annotationprocessor.processor;

import be.lennertsoffers.spigotbootstrapper.annotationprocessor.exception.InvalidEventListenerException;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.List;

public class EventListenerValidator implements Validator {

    @Override
    public void validate(List<? extends Element> elements, ProcessingEnvironment processingEnvironment) {
        TypeMirror listenerType = processingEnvironment.getElementUtils().getTypeElement("org.bukkit.event.Listener").asType();
        elements.forEach((eventListener) -> {
            TypeMirror eventListenerType = eventListener.asType();

            if (!processingEnvironment.getTypeUtils().isSubtype(eventListenerType, listenerType)) throw new InvalidEventListenerException(
                    "All classes annotated with '@EventListener' should implement the 'Listener' interface. Class '" + eventListener + "' does not implement it"
            );
        });
    }

}
