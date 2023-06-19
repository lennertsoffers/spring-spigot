package be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.generator;

import be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.templatedata.ClassData;
import be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.templatedata.PluginConfigData;
import be.lennertsoffers.spigotbootstrapper.library.annotations.SpigotPlugin;
import org.apache.velocity.VelocityContext;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

public class PluginConfigGenerator extends SourceGenerator {

    public static final String PLUGIN_CONFIG_TEMPLATE = "velocity/PluginConfig.java.vm";
    public static final String PLUGIN_CONFIG_OUTPUT = "PluginConfig";

    private final SpigotPlugin spigotPlugin;
    private final Element pluginElement;
    private final ProcessingEnvironment processingEnvironment;

    public PluginConfigGenerator(
            SpigotPlugin spigotPlugin,
            Element pluginElement,
            ProcessingEnvironment processingEnvironment
    ) {
        super(
                processingEnvironment,
                PLUGIN_CONFIG_TEMPLATE,
                PLUGIN_CONFIG_OUTPUT
        );

        this.spigotPlugin = spigotPlugin;
        this.pluginElement = pluginElement;
        this.processingEnvironment = processingEnvironment;
    }

    @Override
    public VelocityContext getVelocityContext() {
        VelocityContext velocityContext = new VelocityContext();

        PluginConfigData pluginConfigData = new PluginConfigData(
                this.spigotPlugin.name(),
                new ClassData(
                        this.pluginElement.getSimpleName().toString(),
                        this.processingEnvironment.getElementUtils().getPackageOf(this.pluginElement).toString()
                )
        );

        velocityContext.put("data", pluginConfigData);

        return velocityContext;
    }

}
