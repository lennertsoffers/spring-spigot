package be.lennertsoffers.spigotbootstrapper.library.annotations;

import be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.templatedata.PluginCommandData;
import org.bukkit.command.CommandExecutor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Registers the {@link CommandExecutor} on startup and writes it to the plugin.yml file.
 * An error will be thrown during compilation if the class annotated with {@link PluginCommandExecutor} does not implement {@link CommandExecutor}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface PluginCommandExecutor {

    String commandName();

    String description();

    String usage() default "";

    String[] aliases() default {};

    String permission() default "";

    String permissionMessage() default "";

}
