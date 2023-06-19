package be.lennertsoffers.spigotbootstrapper.library.annotations;

import org.bukkit.event.Listener;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Registers the {@link Listener} on startup.
 * An error will be thrown during compilation if the class annotated with {@link EventListener} does not implement {@link Listener}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface EventListener {
}
