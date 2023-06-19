package be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.templatedata;

public record PluginCommandData(
        String name,
        String description,
        String usage,
        String[] aliases,
        String permission,
        String permissionMessage
) {}
