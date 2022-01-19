package fr.iiil.bigdata.reminder.functions.writer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.function.Consumer;

@Slf4j
public class ConsoleWriter implements Consumer<ImmutablePair<String,Integer>> {
    @Override
    public void accept(ImmutablePair<String,Integer> msgNbWords) {
        log.info("{}={}", msgNbWords.getLeft(), msgNbWords.getRight());
    }
}
