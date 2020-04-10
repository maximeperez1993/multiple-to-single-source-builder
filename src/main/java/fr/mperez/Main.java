package fr.mperez;

import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class Main {

    private static final String CONFIG_FILE_PATH = "./aggregate.cfg";
    private static final String PLAYER_PATH_PROPERTY = "playerPath";

    public static void main(String[] args) throws Exception {
        String playerPath = Main.readPath().orElseThrow(() -> new IllegalStateException("Missing or empty property " + PLAYER_PATH_PROPERTY));

        System.out.println(new Aggregator(playerPath).build());
    }

    private static Optional<String> readPath() {
        Properties configFile = new Properties();
        try {
            configFile.load(new FileReader(CONFIG_FILE_PATH));
            return Optional.ofNullable(configFile.getProperty(PLAYER_PATH_PROPERTY));
        } catch (IOException ex) {
            throw new IllegalStateException(String.format("Need %s file with property %s", CONFIG_FILE_PATH, PLAYER_PATH_PROPERTY));
        }
    }

}
