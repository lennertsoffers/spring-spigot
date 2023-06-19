package be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.mapper;

import be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.templatedata.PluginCommandData;
import be.lennertsoffers.spigotbootstrapper.library.annotations.PluginCommandExecutor;

public class PluginCommandExecutorMapper {

    public PluginCommandData toPluginCommandData(PluginCommandExecutor pluginCommandExecutor) {
        return new PluginCommandData(
                pluginCommandExecutor.commandName(),
                pluginCommandExecutor.description(),
                pluginCommandExecutor.usage(),
                pluginCommandExecutor.aliases(),
                pluginCommandExecutor.permission(),
                pluginCommandExecutor.permissionMessage()
        );
    }

}
