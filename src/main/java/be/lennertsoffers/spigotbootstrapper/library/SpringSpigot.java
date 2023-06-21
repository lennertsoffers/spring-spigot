package be.lennertsoffers.spigotbootstrapper.library;

import be.lennertsoffers.spigotbootstrapper.core.ContextInitializer;
import be.lennertsoffers.spigotbootstrapper.core.exception.InitializationException;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Entry point to enable the plugin context
 */
public class SpringSpigot {

    private static final ContextInitializer contextInitializer = new ContextInitializer();

    /**
     * Starts up the spring context and registers all configured elements.
     * Should be the first statement in your {@link JavaPlugin#onEnable()}.
     * @param plugin your main plugin instance
     * @return the created Spring context
     */
    public static ConfigurableApplicationContext initialize(JavaPlugin plugin) {
        try {
            Class<?> pluginConfigClass = Class.forName(plugin.getClass().getPackageName() + ".config.PluginConfig");

            return contextInitializer.initialize(
                    plugin,
                    pluginConfigClass
            );
        } catch (ClassNotFoundException exception) {
            throw new InitializationException(
                    "Could not initialize the context because the PluginConfig class could not be found. This class should be generated during compilation.",
                    exception
            );
        }
    }

    /**
     * Unregisters all data of the context so that no errors will be thrown when re-initializing the context.
     * Should be the last call in your {@link JavaPlugin#onDisable()}.
     */
    public static void unload() {
        contextInitializer.cleanup();
    }

}
