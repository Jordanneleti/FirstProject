package fr.iiil.bigdata.reminder;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
@Slf4j
public class WordCountApp {
    public static void main( String[] args ) {
        Config config = ConfigFactory.load();
        String inputPathStr = config.getString("3il.path.input");
        String outputPathStr = config.getString("3il.path.output");


        System.out.println( "Hello Count Words!" );

        Path outputPath = Paths.get(outputPathStr);
        try {
            Files.createDirectories(outputPath);

            List<String> lines = Files.lines(Paths.get(inputPathStr), Charset.defaultCharset()).collect(Collectors.toList());

            log.info("Nb lines={}", lines.size());


            AtomicInteger counter = new AtomicInteger(0);
            for(String line : lines) {
                for (String word : line.split("\\W")) {
                    if (!word.isEmpty())
                        counter.incrementAndGet();
                }
            }

            log.info("Nb 2-Loop words={}", counter.get());

            List<String> words = lines.stream().flatMap(line -> Arrays.stream(line.split("\\W"))).collect(Collectors.toList());

            log.info("Nb Stream words={}", words.stream().filter(word -> !word.isEmpty()).count());
            log.info("Nb MapReduce words={}", words.stream().map(word -> word.isEmpty() ? 0 : 1).reduce(0, Integer::sum));
            log.info("Nb MapReduce words={}", words.stream().map(word -> word.isEmpty() ? 0 : 1).reduce(Integer::sum));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
