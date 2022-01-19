package fr.iiil.bigdata.reminder.functions.counter;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class WordCounterFunc implements Function<Stream<String>, Integer> {
    @Override
    public Integer apply(Stream<String> lines) {
        Stream<String> var1 = lines
                .flatMap(getWordFunction())
                .filter(Objects::nonNull);

        Stream<Integer> var2 = var1
                .map(word -> word.isEmpty() || !word.startsWith("A") ? 0 : 1);

        Integer res = var2.reduce(0, Integer::sum);

        return res;
    }

    private Function<String, Stream<String>> getWordFunction() {
        return line -> {
            Stream<String> words = Arrays.stream(line.split("\\W"));
            return words;
        };
    }
}
