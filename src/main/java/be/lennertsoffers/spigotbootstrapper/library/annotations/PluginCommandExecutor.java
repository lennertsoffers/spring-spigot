package be.lennertsoffers.spigotbootstrapper.library.annotations;

import org.bukkit.command.CommandExecutor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 *     Registers the {@link CommandExecutor} on startup and writes it to the plugin.yml file.
 *     An error will be thrown during compilation if the class annotated with {@link PluginCommandExecutor} does not implement {@link CommandExecutor}.
 * </p>
 * <p>
 *     All fields that are not empty will be added to the plugin.yml if you chose to generate it.
 * </p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface PluginCommandExecutor {

    /**
     * Name of the command that will be registered in the plugin.yml
     * @return the name of the command
     */
    String commandName();

    /**
     * Description of the command that will be registered in the plugin.yml
     * @return the description of the command
     */
    String description();

    /**
     * Will be sent to the player when using this command wrong
     * @return the usage instructions for the command
     */
    String usage() default "";

    /**
     * Array of aliases for the command that can also be used to trigger it
     * @return aliases for the command
     */
    String[] aliases() default {};

    /**
     * The permission needed to execute this command
     * @return the permission needed to execute this command
     */
    String permission() default "";

    /**
     * Text that will be sent to the player if he/she doesn't have sufficient permissions
     * @return Insufficient permissions message
     */
    String permissionMessage() default "";

}
