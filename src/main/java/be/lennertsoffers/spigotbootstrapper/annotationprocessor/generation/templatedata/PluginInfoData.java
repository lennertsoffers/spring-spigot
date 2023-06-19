package be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.templatedata;

import be.lennertsoffers.spigotbootstrapper.library.PluginLoad;

public record PluginInfoData(
        String main,
        String name,
        String version,
        String description,
        String apiVersion,
        PluginLoad load,
        String author,
        String[] authors,
        String website,
        String prefix,
        String[] depend,
        String[] softDepend,
        String[] loadBefore,
        String[] libraries
) {}
