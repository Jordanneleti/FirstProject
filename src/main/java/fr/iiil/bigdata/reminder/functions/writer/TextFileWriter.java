package fr.iiil.bigdata.reminder.functions.writer;

import fr.iiil.bigdata.reminder.beans.FileContentAndStat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class TextFileWriter implements Consumer<FileContentAndStat> {
    private final String outputPathStr;
    @Override
    public void accept(FileContentAndStat fileContentAndStat) {

        AtomicInteger counter = new AtomicInteger(1);
        fileContentAndStat.getLines().forEach(l -> log.info("line #{}, value={}", counter.getAndIncrement(), l));

        try {
            Path outputPath = Paths.get(outputPathStr);
            Files.createDirectories(outputPath);
            Files.write(
                    Paths.get(outputPathStr +"/sequential.txt"),
                    String.valueOf(fileContentAndStat.getNbLines()).getBytes()
            );
        } catch (IOException ioException) {
            log.error("failed to write count at outputPathStr={} due to...",outputPathStr, ioException);
        }
    }
}
