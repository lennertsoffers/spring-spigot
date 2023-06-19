package be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.generator;

import be.lennertsoffers.spigotbootstrapper.annotationprocessor.exception.InvalidSpigotPluginException;
import be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.mapper.PluginCommandExecutorMapper;
import be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.mapper.SpigotPluginMapper;
import be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.templatedata.PluginYmlData;
import be.lennertsoffers.spigotbootstrapper.library.annotations.PluginCommandExecutor;
import be.lennertsoffers.spigotbootstrapper.library.annotations.SpigotPlugin;
import org.apache.velocity.VelocityContext;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import java.util.List;

public class PluginYmlGenerator extends ResourceGenerator {

    public static final String PLUGINYML_TEMPLATE = "velocity/plugin.yml.vm";
    public static final String PLUGINYML_OUTPUT = "plugin.yml";

    private final SpigotPlugin spigotPlugin;
    private final List<PluginCommandExecutor> pluginCommandExecutors;
    private final Element pluginElement;

    public PluginYmlGenerator(
            SpigotPlugin spigotPlugin,
            List<PluginCommandExecutor> pluginCommandExecutors,
            Element pluginElement,
            ProcessingEnvironment processingEnvironment
    ) {
        super(
                processingEnvironment,
                PLUGINYML_TEMPLATE,
                PLUGINYML_OUTPUT
        );

        if (spigotPlugin.name().isEmpty() || spigotPlugin.version().isEmpty()) {
            throw new InvalidSpigotPluginException("The name and version are required when you want to generate a plugin.yml file");
        }

        this.spigotPlugin = spigotPlugin;
        this.pluginCommandExecutors = pluginCommandExecutors;
        this.pluginElement = pluginElement;
    }

    @Override
    public VelocityContext getVelocityContext() {
        VelocityContext velocityContext = new VelocityContext();

        SpigotPluginMapper spigotPluginMapper = new SpigotPluginMapper();
        PluginCommandExecutorMapper pluginCommandExecutorMapper = new PluginCommandExecutorMapper();
        PluginYmlData pluginYmlData = new PluginYmlData(
                spigotPluginMapper.toPluginInfoData(this.spigotPlugin, this.pluginElement.toString()),
                this.pluginCommandExecutors.stream().map(pluginCommandExecutorMapper::toPluginCommandData).toList()
        );

        velocityContext.put("data", pluginYmlData);

        return velocityContext;
    }

}
