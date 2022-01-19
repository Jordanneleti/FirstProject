package fr.iiil.bigdata.reminder;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
@Slf4j
public class CollectionsApp {
    public static void main( String[] args ) {
        Config config = ConfigFactory.load();
        String inputPathStr = config.getString("3il.path.input");
        String outputPathStr = config.getString("3il.path.output");


        System.out.println( "Hello Java Collections!" );

        AtomicInteger counter = new AtomicInteger(1);
        Path outputPath = Paths.get(outputPathStr);
        try {
            Files.createDirectories(outputPath);

            List<String> lines = Files.lines(Paths.get(inputPathStr), Charset.defaultCharset())
                    .collect(Collectors.toList());

            Stream<String> sequentialLines = lines.stream();
            sequentialLines.forEach(l -> log.info("line #{}, value={}", lines.indexOf(l), l));
            Files.write(Paths.get(outputPathStr +"/sequential.txt"), String.valueOf(counter.get()).getBytes());


            log.info("\n\n");
            counter.set(1);

            Stream<String> parallelLines = lines.stream().parallel();
            parallelLines.forEach(l -> log.info("line #{}, value={}", counter.getAndIncrement(), l));
            Files.write(Paths.get(outputPathStr+"/parallel.txt"), String.valueOf(counter.get()).getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
