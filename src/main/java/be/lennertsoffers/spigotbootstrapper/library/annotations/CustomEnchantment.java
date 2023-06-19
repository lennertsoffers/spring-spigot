package be.lennertsoffers.spigotbootstrapper.library.annotations;

import org.bukkit.enchantments.Enchantment;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Registers the {@link Enchantment} on startup and unloads it when the plugin is disabled.
 * An error will be thrown during compilation if the class annotated with {@link CustomEnchantment} does not implement {@link Enchantment}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface CustomEnchantment {
}
