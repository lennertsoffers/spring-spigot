package be.lennertsoffers.spigotbootstrapper.library.annotations;

import be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.templatedata.PluginInfoData;
import be.lennertsoffers.spigotbootstrapper.library.PluginLoad;

import javax.lang.model.element.Element;
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

    String name() default "";

    String version() default "";

    String description() default "";

    String apiVersion() default "";

    PluginLoad load() default PluginLoad.POSTWORLD;

    String author() default "";

    String[] authors() default {};

    String website() default "";

    String prefix() default "";

    String[] depend() default {};

    String[] softDepend() default {};

    String[] loadBefore() default {};

    String[] libraries() default {};

}
