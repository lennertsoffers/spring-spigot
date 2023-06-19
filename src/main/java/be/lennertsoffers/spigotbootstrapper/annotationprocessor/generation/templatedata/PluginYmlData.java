package be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.templatedata;

import java.util.List;

public record PluginYmlData(
    PluginInfoData plugin,
    List<PluginCommandData> commands
) {}
