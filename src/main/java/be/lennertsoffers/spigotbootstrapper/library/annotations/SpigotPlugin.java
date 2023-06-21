package be.lennertsoffers.spigotbootstrapper.library.annotations;

import be.lennertsoffers.spigotbootstrapper.library.PluginLoad;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that this is the entry point of your plugin and extends JavaPlugin.
 * Will generate a plugin.yml file by default but this can be disabled.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpigotPlugin {

    /**
     * Configure if the plugin.yml must be created or not
     * @return true if the plugin.yml must be created, false otherwise
     */
    boolean generatePluginYml() default true;

    /**
     * <p>
     *     Name of the plugin that will be registered to the plugin.yml.
     * </p>
     * <p>
     *     <b>Required</b> if {@link SpigotPlugin#generatePluginYml()} is true.
     * </p>
     * @return the name of your plugin
     */
    String name() default "";

    /**
     * <p>
     *     Version of the plugin that will be registered to the plugin.yml.
     * </p>
     * <p>
     *     <b>Required</b> if {@link SpigotPlugin#generatePluginYml()} is true.
     * </p>
     * @return the version of the plugin
     */
    String version() default "";

    /**
     * Description of the plugin that will be registered to the plugin.yml.
     * @return the description of the plugin
     */
    String description() default "";

    /**
     * Spigot api version that will be registered to the plugin.yml.
     * @return the api version that the plugin uses
     */
    String apiVersion() default "";

    /**
     * Configures when the plugin will load.
     * @return when the plugin will load
     */
    PluginLoad load() default PluginLoad.POSTWORLD;

    /**
     * Author of the plugin that will be registered to the plugin.yml.
     * @return the author of the plugin
     */
    String author() default "";

    /**
     * List of authors of the plugin that will be registered to the plugin.yml.
     * @return authors of the plugin
     */
    String[] authors() default {};

    /**
     * Website where more information about the plugin can be found.
     * @return website of the plugin
     */
    String website() default "";

    /**
     * Configures prefix for all log statements used in the plugin.
     * @return prefix for log statements
     */
    String prefix() default "";

    /**
     * A list of plugins that your plugin requires to load.
     * @return plugins required by this plugin
     */
    String[] depend() default {};

    /**
     * A list of plugins that are required for your plugin to have full functionality.
     * @return plugins required by this plugin to have full functionality
     */
    String[] softDepend() default {};

    /**
     * A list of plugins that should be loaded after your plugin.
     * @return plugins that should load after this plugin
     */
    String[] loadBefore() default {};

    /**
     * A list of libraries your plugin needs which can be loaded from Maven Central.
     * Helps reduce plugin size and eliminates the need for relocation.
     * Intended for use with large non-Minecraft dependencies. Specialized libraries should still be shaded and relocated.
     * @return maven central libraries that your plugin needs
     */
    String[] libraries() default {};

}
