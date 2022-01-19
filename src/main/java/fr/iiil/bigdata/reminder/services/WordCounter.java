package fr.iiil.bigdata.reminder.services;

import fr.iiil.bigdata.reminder.beans.FileContentAndStat;
import fr.iiil.bigdata.reminder.functions.MapUtils;
import fr.iiil.bigdata.reminder.functions.counter.WordCounterFunc;
import fr.iiil.bigdata.reminder.functions.reader.TextFileReader;
import fr.iiil.bigdata.reminder.functions.writer.TextFileWriter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class WordCounter implements Runnable {

    @Getter
    private final AtomicInteger runCounter = new AtomicInteger(0);


    private final TextFileReader reader;
    private final TextFileWriter writer;
    private final WordCounterFunc wordCounterFunc;

    private final Function<Stream<String>, Map<String, Integer>> wordCountFunction =
            MapUtils.genericCountWithStreamFunction();
    private final Consumer<Map<String, Integer>> mapPrinter =
            MapUtils.mapPrinter();

    @Override
    public void run() {
        log.info("runCounter={}", runCounter.incrementAndGet());

        Stream<String> lines1 = reader.get();
        Stream<String> lines2 = reader.get();
        writer.accept(
                new FileContentAndStat(lines1, wordCounterFunc.apply(lines2))
        );

        Stream<String> lines3 = reader.get();
        mapPrinter.accept(
                wordCountFunction.apply(
                        lines3.flatMap(line -> Arrays.stream(line.split("\\W")))
                )
        );
    }
}
