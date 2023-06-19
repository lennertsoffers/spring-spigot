package be.lennertsoffers.spigotbootstrapper.core;

import be.lennertsoffers.spigotbootstrapper.library.exception.EnchantmentRegistrationException;
import org.bukkit.enchantments.Enchantment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class EnchantmentLoader {

    private final List<Enchantment> enchantments = new ArrayList<>();

    public EnchantmentLoader() {
    }

    public void addEnchantments(List<Enchantment> enchantments) {
        this.enchantments.addAll(enchantments);
    }

    public void addEnchantment(Enchantment enchantment) {
        this.enchantments.add(enchantment);
    }

    public void registerEnchantments() {
        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, true);

            this.enchantments.forEach(Enchantment::registerEnchantment);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new EnchantmentRegistrationException("Could not register enchantments because server does not accept new enchantments");
        }
    }

    @SuppressWarnings("deprecation")
    public void unloadEnchantments() {
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");
            Field nameField = Enchantment.class.getDeclaredField("byName");
            keyField.setAccessible(true);
            nameField.setAccessible(true);

            if (!(keyField.get(null) instanceof Map<?, ?> byKey)) {
                throw new EnchantmentRegistrationException("Could not cast byKey to a map in the Enchantment class");
            }
            if (!(nameField.get(null) instanceof Map<?, ?> byName)) {
                throw new EnchantmentRegistrationException("Could not cast byName to a map in the Enchantment class");
            }

            this.enchantments.forEach((enchantment -> {
                byKey.remove(enchantment.getKey());
                byName.remove(enchantment.getName());
            }));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new EnchantmentRegistrationException("Could not unregister enchantments because could not access server enchantments");
        }
    }

}
