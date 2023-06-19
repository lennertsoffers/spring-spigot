package be.lennertsoffers.spigotbootstrapper.annotationprocessor.processor;

import be.lennertsoffers.spigotbootstrapper.annotationprocessor.exception.InvalidSpigotPluginException;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.util.List;

public class SpigotPluginValidator implements Validator {

    @Override
    public void validate(List<? extends Element> elements, ProcessingEnvironment processingEnvironment) {
        // Only one SpigotPlugin annotated element
        if (elements.size() > 1) {
            throw new InvalidSpigotPluginException(
                    "You can only annotate one class as Spigot plugin but found multiple: " +
                            String.join(", ", elements.stream().map(Object::toString).toList())
            );
        }

        // SpigotPlugin class extends JavaPlugin
        Element spigotPlugin = elements.get(0);
        TypeMirror pluginType = spigotPlugin.asType();
        TypeMirror javaPluginType = processingEnvironment
                .getElementUtils()
                .getTypeElement("org.bukkit.plugin.java.JavaPlugin")
                .asType();
        if (!processingEnvironment.getTypeUtils().isSubtype(pluginType, javaPluginType)) {
            throw new InvalidSpigotPluginException(
                    "You can only annotate classes extending JavaPlugin with '@SpigotPlugin'"
            );
        }

        // Check if JavaPlugin does not have accessors without using dependency injection
        spigotPlugin.getEnclosedElements().forEach((spigotPluginClassMember) -> {
            if (spigotPluginClassMember.getKind().equals(ElementKind.METHOD)) {
                // Validate no getters for the plugin
                if (!(spigotPluginClassMember instanceof ExecutableElement method)) return;

                if (processingEnvironment.getTypeUtils().isSubtype(method.getReturnType(), javaPluginType)) {
                    throw new InvalidSpigotPluginException(
                            "You are not allowed to create a getter method for your Plugin instance. If you need you plugin in a class, use dependency injection instead. There is a bean of your plugin already registered to the spring context."
                    );
                }
            }

            else if (spigotPluginClassMember.getKind().equals(ElementKind.FIELD)) {
                // Validate no static fields with non-private access for the plugin
                if (!(spigotPluginClassMember instanceof VariableElement field)) return;

                if (
                        (!field.getModifiers().contains(Modifier.PRIVATE)) &&
                                (field.getModifiers().contains(Modifier.STATIC)) &&
                                (processingEnvironment.getTypeUtils().isSubtype(field.asType(), javaPluginType))
                ) {
                    throw new InvalidSpigotPluginException(
                            "You are not allowed to create a non-private static field containing your Plugin instance. If you need you plugin in a class, use dependency injection instead. There is a bean of your plugin already registered to the spring context."
                    );
                }
            }
        });
    }

}
