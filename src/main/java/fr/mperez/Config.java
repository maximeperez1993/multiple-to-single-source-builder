package fr.mperez;

import java.util.Optional;
import java.util.Properties;

public class Config {

    private final Properties properties;

    public Config(Properties properties) {
        this.properties = properties;
    }

    public boolean readAsBoolean(String property) {
        return Boolean.parseBoolean(read(property));
    }

    public String read(String property) {
        return Optional.ofNullable(properties.getProperty(property))
                .orElseThrow(() -> new IllegalStateException("Missing or empty property " + property));
    }

}
