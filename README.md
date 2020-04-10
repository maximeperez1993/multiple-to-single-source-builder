# multiple-to-single-source-builder
Aggregate multiple source code files into a single one. (Final file is compatible with Codingame puzzle)

## Languages supported

Only java at the moment

## Usage

Add a config file aggregate.cfg at the same level of the jar with property playerPath:
playerPath=./code-royal/src/main/java/fr/mperez/Player.java
outputPath=./target/Player.java
copyToClipboard=true

Run with ```java -jar multiple-to-single-source-builder.jar```

## Integration

To go faster, you can :
- set a build configuration to execute jar
- set an unit test to earn time (because run another configuration takes one or 2 seconds)

## Unit test

```java
public class Aggregator {

    private static final String JAR_NAME = "multiple-to-single-source-builder.jar";

    @Test
    public void shouldGenerateCode() throws IOException, InterruptedException {
        // Given
        String[] command = {"java", "-jar", JAR_NAME};
        ProcessBuilder builder = new ProcessBuilder(command).directory(new File("./src/main/resources"));
        // When
        Process p = builder.start();
        p.waitFor();

        // Then
        Assert.assertEquals(0, p.exitValue());
        System.out.println("Processed file Player.java has been copied to clipboard");
    }
}
```
