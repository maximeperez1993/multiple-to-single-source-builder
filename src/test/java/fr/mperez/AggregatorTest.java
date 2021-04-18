package fr.mperez;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class AggregatorTest {

	private final static LocalDate LOCAL_DATE = LocalDate.of(1989, 01, 13);
	private final Clock fixedClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

	@Test
	public void testBuild() {
		// Given
		Aggregator aggregator = new Aggregator("src/test/resources/input/Player.java", this.fixedClock);

		// When
		String result = aggregator.build();

		// Then
		String expectedContent = getContent("src/test/resources/ExpectedOutput.java").replaceAll("\r\n", "\n");
		Assert.assertEquals(expectedContent, result.replaceAll("\r\n", "\n"));
	}

	private String getContent(String pathString) {
		try {
			return new String(Files.readAllBytes(Paths.get(pathString)));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}