package be.lennertsoffers.spigotbootstrapper.annotationprocessor;

import be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.generator.PluginConfigGenerator;
import be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.generator.PluginYmlGenerator;
import be.lennertsoffers.spigotbootstrapper.annotationprocessor.processor.EventListenerValidator;
import be.lennertsoffers.spigotbootstrapper.annotationprocessor.processor.PluginCommandValidator;
import be.lennertsoffers.spigotbootstrapper.annotationprocessor.processor.SpigotPluginValidator;
import be.lennertsoffers.spigotbootstrapper.library.annotations.EventListener;
import be.lennertsoffers.spigotbootstrapper.library.annotations.PluginCommandExecutor;
import be.lennertsoffers.spigotbootstrapper.library.annotations.SpigotPlugin;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@SupportedAnnotationTypes({
        "be.lennertsoffers.spigotbootstrapper.library.annotations.SpigotPlugin",
        "be.lennertsoffers.spigotbootstrapper.library.annotations.PluginCommandExecutor",
        "be.lennertsoffers.spigotbootstrapper.library.annotations.EventListener",
})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class PluginAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        List<? extends Element> spigotPluginElements = roundEnv.getElementsAnnotatedWith(SpigotPlugin.class).stream().toList();
        List<? extends Element> eventListenerElements = roundEnv.getElementsAnnotatedWith(EventListener.class).stream().toList();
        List<? extends Element> pluginCommandExecutorElements = roundEnv.getElementsAnnotatedWith(PluginCommandExecutor.class).stream().toList();

        if (spigotPluginElements.isEmpty()) return true;

        new SpigotPluginValidator().validate(spigotPluginElements, processingEnv);
        new EventListenerValidator().validate(eventListenerElements, processingEnv);
        new PluginCommandValidator().validate(pluginCommandExecutorElements, processingEnv);

        Element spigotPluginElement = spigotPluginElements.get(0);
        SpigotPlugin spigotPlugin = spigotPluginElement.getAnnotation(SpigotPlugin.class);
        List<PluginCommandExecutor> pluginCommandExecutors = pluginCommandExecutorElements
                .stream()
                .map((element -> element.getAnnotation(PluginCommandExecutor.class)))
                .toList();

        if (spigotPlugin.generatePluginYml()) {
            new PluginYmlGenerator(
                    spigotPlugin,
                    pluginCommandExecutors,
                    spigotPluginElement,
                    this.processingEnv
            ).generate();
        }

        new PluginConfigGenerator(
                spigotPlugin,
                spigotPluginElement,
                this.processingEnv
        ).generate();

        return false;
    }

}
