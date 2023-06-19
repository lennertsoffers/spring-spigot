package be.lennertsoffers.spigotbootstrapper.core;

import be.lennertsoffers.spigotbootstrapper.annotationprocessor.exception.InvalidEventListenerException;
import be.lennertsoffers.spigotbootstrapper.annotationprocessor.exception.InvalidPluginCommandException;
import be.lennertsoffers.spigotbootstrapper.core.exception.InitializationException;
import be.lennertsoffers.spigotbootstrapper.library.annotations.CustomEnchantment;
import be.lennertsoffers.spigotbootstrapper.library.annotations.EventListener;
import be.lennertsoffers.spigotbootstrapper.library.annotations.PluginCommandExecutor;
import be.lennertsoffers.spigotbootstrapper.library.exception.EnchantmentRegistrationException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ContextInitializer {

    private final EnchantmentLoader enchantmentLoader;

    public ContextInitializer() {
        this.enchantmentLoader = new EnchantmentLoader();
    }

    public ConfigurableApplicationContext initialize(JavaPlugin plugin, Class<?>... configClasses) {
        ClassLoader classLoader = this.getMergedClassLoader(plugin);

        ConfigurableApplicationContext context = this.createContext(classLoader, configClasses);

        this.registerEventListeners(plugin, context);
        this.registerCommandExecutors(plugin, context);
        this.registerEnchantments(plugin, context);

        return context;
    }

    public void cleanup() {
        this.unloadEnchantments();
    }

    private ClassLoader getMergedClassLoader(JavaPlugin plugin) {
        return new CompoundClassLoader(
                plugin.getClass().getClassLoader(),
                Thread.currentThread().getContextClassLoader()
        );
    }

    private ConfigurableApplicationContext createContext(ClassLoader classLoader, Class<?>... configClasses) {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        try {
            Future<ConfigurableApplicationContext> contextFuture = singleThreadExecutor.submit(() -> {
                Thread.currentThread().setContextClassLoader(classLoader);
                return new AnnotationConfigApplicationContext(configClasses);
            });

            return contextFuture.get();
        } catch (ExecutionException | InterruptedException exception) {
            throw new InitializationException(exception);
        } finally {
            singleThreadExecutor.shutdown();
        }
    }

    private void registerEventListeners(JavaPlugin plugin, ApplicationContext context) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        Collection<Object> eventListeners = context.getBeansWithAnnotation(EventListener.class).values();
        plugin.getLogger().info("Found " + eventListeners.size() + " eventListener(s), registering them...");

        eventListeners.forEach((eventListener) -> {
            if (!(eventListener instanceof Listener listener)) {
                throw new InvalidEventListenerException(
                        "All classes annotated with '@EventListener' should implement the 'Listener' interface. Class '" + eventListener.getClass().getName() + "' does not implement it"
                );
            }

            pluginManager.registerEvents(listener, plugin);
        });
    }

    private void registerCommandExecutors(JavaPlugin plugin, ApplicationContext context) {
        Collection<Object> commandExecutors = context.getBeansWithAnnotation(PluginCommandExecutor.class).values();
        plugin.getLogger().info("Found " + commandExecutors.size() + " commandExecutor(s), registering them...");

        commandExecutors.forEach((commandExecutor) -> {
            if (!(commandExecutor instanceof CommandExecutor executor)) {
                throw new InvalidPluginCommandException(
                        "All classes annotated with '@PluginCommand' should implement the 'CommandExecutor' interface. Class '" + commandExecutor.getClass().getName() + "' does not implement it"
                );
            }

            PluginCommandExecutor pluginCommandExecutor = commandExecutor.getClass().getAnnotation(PluginCommandExecutor.class);

            PluginCommand command = plugin.getCommand(pluginCommandExecutor.commandName());
            if (command == null) {
                throw new InvalidPluginCommandException(
                        "The command could not be found in the plugin.yml file. If you chose to manually create it, check if you registered it"
                );
            }

            command.setExecutor(executor);
        });
    }

    private void registerEnchantments(JavaPlugin plugin, ApplicationContext context) {
        Collection<Object> customEnchantments = context.getBeansWithAnnotation(CustomEnchantment.class).values();
        plugin.getLogger().info("Found " + customEnchantments.size() + " custom enchantment(s), registering them...");

        customEnchantments.forEach((customEnchantment) -> {
            if (!(customEnchantment instanceof Enchantment enchantment)) {
                throw new EnchantmentRegistrationException(
                        "The enchantment of type " + customEnchantment.getClass().getName() + " does not extend org.bukkit.enchantments.Enchantment and cannot be registered"
                );
            }

            this.enchantmentLoader.addEnchantment(enchantment);
        });

        this.enchantmentLoader.registerEnchantments();
    }

    private void unloadEnchantments() {
        this.enchantmentLoader.unloadEnchantments();
    }

}
