package fr.iiil.bigdata.reminder.runner;

import fr.iiil.bigdata.reminder.beans.WordCountRuntime;
import fr.iiil.bigdata.reminder.services.WordCounter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class WordCountAppRunner implements AppStartable {
    private final WordCountRuntime runtime;

    private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(
            new BasicThreadFactory.Builder().namingPattern("3il-service").daemon(true).build()
    );

    @Override
    public void start(WordCounter wordCounter) {
        log.info( "Hello ExecutorService!");

        // init Delay = 5, repeat the task every 2 second
        ScheduledFuture<?> scheduledFuture = service.scheduleAtFixedRate(wordCounter, 5, 2, TimeUnit.SECONDS);

        switch (runtime){
            case STREAMING:
                try {
                    log.info("service.awaitTermination");
                    service.awaitTermination(1000, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                };
                break;
            case BATCH:
                while (true) {
                    try {
                        int runCounter = wordCounter.getRunCounter().intValue();
                        log.info("main runCounter={}", runCounter);
                        Thread.sleep(1000);
                        if (runCounter > 5) {
                            log.info("Count is 5, cancel the scheduledFuture!");
                            scheduledFuture.cancel(true);
                            service.shutdown();
                            break;
                        }
                    } catch (InterruptedException ie) {
                        log.warn("main could not be sleep due to...", ie);
                    }
                }
                break;
            default:
                log.warn("unknown runtime = {}", runtime);
        }
    }
}
