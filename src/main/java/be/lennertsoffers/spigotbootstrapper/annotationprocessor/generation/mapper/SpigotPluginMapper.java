package be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.mapper;

import be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.templatedata.PluginInfoData;
import be.lennertsoffers.spigotbootstrapper.library.annotations.SpigotPlugin;

public class SpigotPluginMapper {

    public PluginInfoData toPluginInfoData(SpigotPlugin spigotPlugin, String mainClassName) {
        return new PluginInfoData(
                mainClassName,
                spigotPlugin.name(),
                spigotPlugin.version(),
                spigotPlugin.description(),
                spigotPlugin.apiVersion(),
                spigotPlugin.load(),
                spigotPlugin.author(),
                spigotPlugin.authors(),
                spigotPlugin.website(),
                spigotPlugin.prefix(),
                spigotPlugin.depend(),
                spigotPlugin.softDepend(),
                spigotPlugin.loadBefore(),
                spigotPlugin.libraries()
        );
    }

}
