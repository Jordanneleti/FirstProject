package fr.iiil.bigdata.reminder;

import com.typesafe.config.ConfigFactory;
import fr.iiil.bigdata.reminder.CollectionsApp;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for simple CollectionsApp.
 */
public class CollectionsAppTest {

    @Test
    public void shouldReadFile() throws IOException {
        CollectionsApp.main(new String[0]);
        assertThat(Files.list(Paths.get(ConfigFactory.load().getString("3il.path.output"))).count()).isGreaterThan(0L);
    }
}
