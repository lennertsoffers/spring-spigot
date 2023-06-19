package be.lennertsoffers.spigotbootstrapper.annotationprocessor.processor;

import be.lennertsoffers.spigotbootstrapper.annotationprocessor.exception.InvalidPluginCommandException;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.List;

public class PluginCommandValidator implements Validator {

    @Override
    public void validate(List<? extends Element> elements, ProcessingEnvironment processingEnvironment) {
        TypeMirror commandExecutorType = processingEnvironment.getElementUtils().getTypeElement("org.bukkit.command.CommandExecutor").asType();
        elements.forEach((pluginCommandExecutor) -> {
            TypeMirror pluginCommandExecutorType = pluginCommandExecutor.asType();

            if (!processingEnvironment.getTypeUtils().isSubtype(pluginCommandExecutorType, commandExecutorType)) throw new InvalidPluginCommandException(
                    "All classes annotated with '@PluginCommand' should implement the 'CommandExecutor' interface. Class '" + pluginCommandExecutor + "' does not implement it"
            );
        });
    }

}
