import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final Pattern packagePattern = Pattern.compile("package (?<PACKAGE>[a-z0-9.]+);[\r]*[\n]");
    private static final Pattern importPattern = Pattern.compile("import (?<PACKAGE>[a-zA-Z0-9.*]+);[\r]*[\n]");

    private final Path playerFilePath;
    private final String playerFile;

    public Main(String playerFilePath) {
        this.playerFilePath = Paths.get(playerFilePath);
        this.playerFile = this.playerFilePath.getFileName().toString();
    }

    public String build() throws IOException {
        String mainCode = readFileAsString(this.playerFilePath);
        String innerContent = buildInnerContent();
        String content = mainCode.replaceAll("[\r][\n]}$", "\n" + innerContent + "\n}");
        return resolvePackage(resolveImport(content.replace("public class","class")));
    }

    private String buildInnerContent() throws IOException {
        return prepareInnerContent(readFiles(this.playerFilePath.getParent()));
    }

    private String readFiles(Path directory) {
        try (Stream<Path> walk = Files.walk(directory)) {
            return walk.filter(Files::isRegularFile)
                    .filter(file -> !this.playerFile.equals(file.getFileName().toString()))
                    .map(Path::toString)
                    .map(this::readFileAsString)
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String prepareInnerContent(String content) {
        return content
                .replace("public class", "static class")
                .replace("public final class", "static final class")
                .replaceAll("\n", "\n\t");
    }


    private String readFileAsString(String fileName) {
        return readFileAsString(Paths.get(fileName));
    }

    private String readFileAsString(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    private String resolvePackage(String content) {
        Matcher matcher = packagePattern.matcher(content);
        while (matcher.find()) {
            String importGroup = matcher.group();
            content = content.replace(importGroup, "");
        }
        return content;
    }

    private String resolveImport(String content) {
        Matcher matcher = importPattern.matcher(content);
        Set<String> importsToKeep = new HashSet<>();
        while (matcher.find()) {
            String importGroup = matcher.group();
            String packageGroup = matcher.group("PACKAGE");
            if (packageGroup.startsWith("java")) {
                importsToKeep.add(importGroup);
            }
            content = content.replace(importGroup, "");
        }
        String importString = String.join("", importsToKeep);
        return importString + "\n" + content;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Need path to main source file (Player.java for Codingame)");
            return;
        }
        System.out.println(new Main(args[0]).build());
    }
}