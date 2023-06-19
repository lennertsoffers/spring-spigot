package be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.generator;

import org.apache.velocity.VelocityContext;

public interface Generator {

    void generate();

    VelocityContext getVelocityContext();

}
