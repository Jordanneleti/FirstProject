package fr.iiil.bigdata.reminder;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import fr.iiil.bigdata.reminder.beans.WordCountRuntime;
import fr.iiil.bigdata.reminder.functions.counter.WordCounterFunc;
import fr.iiil.bigdata.reminder.functions.reader.TextFileReader;
import fr.iiil.bigdata.reminder.functions.writer.TextFileWriter;
import fr.iiil.bigdata.reminder.runner.WordCountAppRunner;
import fr.iiil.bigdata.reminder.services.WordCounter;
import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 */
@Slf4j
public class ScheduledExecutorServiceApp {
    public static void main( String[] args ) {
        Config config = ConfigFactory.load("application.conf");
        String inputPathStr = config.getString("3il.path.input");
        String outputPathStr = config.getString("3il.path.output");
        WordCountRuntime runtime = WordCountRuntime.valueOf(config.getString("3il.runtime").toUpperCase());

        final TextFileReader reader = new TextFileReader(inputPathStr);
        final TextFileWriter writer = new TextFileWriter(outputPathStr);
        final WordCounterFunc wordCounterFunc = new WordCounterFunc();

        WordCounter wordCounter = new WordCounter(reader, writer, wordCounterFunc);

        WordCountAppRunner wordCountAppRunner = new WordCountAppRunner(runtime);
        wordCountAppRunner.start(wordCounter);

    }
}
