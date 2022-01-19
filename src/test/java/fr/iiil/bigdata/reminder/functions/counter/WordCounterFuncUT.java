package fr.iiil.bigdata.reminder.functions.counter;

import org.junit.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class WordCounterFuncUT {

    @Test
    public void testWordCounter() {
        WordCounterFunc f = new WordCounterFunc();
        Stream<String> input = Stream.of("hello At data");
        Integer expected = 1;
        Integer actual = f.apply(input);

        assertThat(actual).isEqualTo(expected);
    }
}
