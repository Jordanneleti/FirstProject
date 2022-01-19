package fr.iiil.bigdata.reminder;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import fr.iiil.bigdata.reminder.functions.MapUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
@Slf4j
public class FunctionalProgrammingApp {
    public static void main(String[] args) {
        Config config = ConfigFactory.load();
        String inputPathStr = config.getString("3il.path.input");
        String outputPathStr = config.getString("3il.path.output");


        System.out.println("Hello Functional Programming!");


        Function<List<String>, Map<String, Integer>> wordCountFunction = MapUtils.genericCountFunction();
        Consumer<Map<String, Integer>> mapPrinter = MapUtils.mapPrinter();

        linesSupplier(inputPathStr)
                .get()
                .map(
                        lines -> {
                            List<String> words = lines.stream()
                                    .flatMap(line -> Arrays.stream(line.split("\\W")))
                                    .collect(Collectors.toList());



                            Map<String, Integer> wordCount = wordCountFunction.apply(words);
                            mapPrinter.accept(wordCount);

                            return wordCount;
                        }
                )
                .ifPresent(
                        wordCountMap -> {

                            Path outputPath = Paths.get(outputPathStr);
                            try {
                                Files.createDirectories(outputPath);
                                String contents = wordCountMap.entrySet()
                                        .stream()
                                        .filter(e -> !e.getKey().isEmpty())
                                        .map(e -> e.getKey() + " -> " + e.getValue())
                                        .reduce("", (r, s) -> r + "\n" + s)
                                        .trim()
                                        ;
                                Files.write(Paths.get(outputPathStr+"/wordcount.txt"), contents.getBytes());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                )
        ;

    }


    private static Supplier<Optional<List<String>>> linesSupplier(String inputPathStr) {
        return () -> {
            try {
                return Optional.of(
                        Files.lines(Paths.get(inputPathStr), Charset.defaultCharset())
                                .collect(Collectors.toList())
                );
            } catch (IOException ioe) {
                log.error("could not read inputPathStr={} due to...", inputPathStr, ioe);
            }
            return Optional.empty();
        };
    }
}
