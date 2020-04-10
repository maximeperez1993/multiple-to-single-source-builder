package fr.mperez;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class Main {

    private static final String CONFIG_FILE_PATH = "./aggregate.cfg";
    private static final String PLAYER_PATH_PROPERTY = "playerPath";
    private static final String OUTPUT_PATH_PROPERTY = "outputPath";
    private static final String COPY_TO_CLIPBOARD_BOOLEAN_PROPERTY = "copyToClipboard";

    public static void main(String[] args) throws Exception {
        Config config = new Config(readConfigFile());

        String playerPath = config.read(PLAYER_PATH_PROPERTY);
        String finalContent = new Aggregator(playerPath).build();

        String outputPath = config.read(OUTPUT_PATH_PROPERTY);
        generateOutputFile(outputPath, finalContent);

        if (config.readAsBoolean(COPY_TO_CLIPBOARD_BOOLEAN_PROPERTY)) {
            copyToClipboard(finalContent);
        }
    }

    private static void generateOutputFile(String outputFileName, String content) throws IOException {
        Path outputPath = Paths.get(outputFileName);
        if (!Files.exists(outputPath.getParent())) {
            Files.createDirectories(Files.createDirectory(outputPath.getParent()));
        }
        Files.write(outputPath, content.getBytes(), StandardOpenOption.CREATE);
    }

    private static void copyToClipboard(String content) {
        StringSelection stringSelection = new StringSelection(content);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private static Properties readConfigFile() {
        Properties configFile = new Properties();
        try {
            configFile.load(new FileReader(CONFIG_FILE_PATH));
            return configFile;
        } catch (IOException ex) {
            throw new IllegalStateException("Need config file " + CONFIG_FILE_PATH);
        }
    }

}
