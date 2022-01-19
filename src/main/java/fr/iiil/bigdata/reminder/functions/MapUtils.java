package fr.iiil.bigdata.reminder.functions;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class MapUtils {

    private static <T> Map<T, Integer> genericCountMethod(List<T> genericList){
        return MapUtils.genericCountMethod(genericList.stream());
    }
    private static <T> Map<T, Integer> genericCountMethod(Stream<T> tStream){
        return tStream.map(t -> Pair.of(t, 1))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, Integer::sum));
    }

    public static <T> Function<List<T>, Map<T, Integer>> genericCountFunction() {
        return MapUtils::genericCountMethod;
    }

    public static <T> Function<Stream<T>, Map<T, Integer>> genericCountWithStreamFunction() {
        return MapUtils::genericCountMethod;
    }

    public static <T> Consumer<Map<T, Integer>> mapPrinter() {
        return (Map<T, Integer> map) ->
                log.info("Map=[\n{}\n]",
                        map.entrySet().stream()
                                .sorted(Comparator.comparing(e -> -1 * e.getValue(), Comparator.naturalOrder()))
                                .map(e -> String.format("(%s -> %d)", e.getKey(), e.getValue()))
                                .collect(Collectors.joining(",\n"))
                );
    }

}
