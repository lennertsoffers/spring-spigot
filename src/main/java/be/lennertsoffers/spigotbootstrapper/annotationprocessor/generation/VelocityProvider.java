package be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation;

import be.lennertsoffers.spigotbootstrapper.annotationprocessor.exception.VelocityCreationException;
import org.apache.velocity.app.VelocityEngine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class VelocityProvider {

    public static final String VELOCITY_PROPERTIES = "velocity/velocity.properties";

    private static VelocityEngine velocityEngine;

    public static VelocityEngine getVelocityEngine() {
        if (velocityEngine == null) velocityEngine = createNewVelocityEngine();

        return velocityEngine;
    }

    private static VelocityEngine createNewVelocityEngine() {
        try {
            Properties properties = new Properties();
            URL url = VelocityProvider.class.getClassLoader().getResource(VELOCITY_PROPERTIES);

            if (url == null) throw new FileNotFoundException("velocity.properties was not found in resources folder");

            properties.load(url.openStream());

            VelocityEngine velocityEngine = new VelocityEngine(properties);
            velocityEngine.init();

            return velocityEngine;
        } catch (IOException exception) {
            throw new VelocityCreationException("Could not initialize the velocity context", exception);
        }
    }

}
