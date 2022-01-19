package fr.iiil.bigdata.reminder.functions.reader;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import java.util.function.Supplier;

@Slf4j
@Builder
@RequiredArgsConstructor
public class TextFileReader implements Supplier<Dataset<String>> {
    private final String inputPathStr;
    private final SparkSession sparkSession;
    @Override
    public Dataset<String> get() {
        log.info("reading file at inputPathStr={}", inputPathStr);
        try {
            Dataset<String> lines = sparkSession.read().textFile(inputPathStr);
           return lines;
        } catch (Exception Exception) {
            log.error("failed to read file at inputPathStr={} due to ...", inputPathStr, Exception);
        }
        return sparkSession.emptyDataset(Encoders.STRING());
    }
}
