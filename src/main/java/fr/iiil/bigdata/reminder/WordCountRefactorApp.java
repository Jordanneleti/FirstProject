package fr.iiil.bigdata.reminder;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import fr.iiil.bigdata.reminder.functions.reader.TextFileReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

/**
 * Hello world!
 *
 */
@Slf4j
public class WordCountRefactorApp {
    public static void main( String[] args ) {
        Config config = ConfigFactory.load();
        String inputPathStr = config.getString("3il.path.input");
        String outputPathStr = config.getString("3il.path.output");

        log.info( "Hello Count Words!" );

        SparkConf sparkConf= new SparkConf().setMaster("local[2]").setAppName("Hello World");
        SparkSession sparkSession= SparkSession.builder().config(sparkConf).getOrCreate();
        TextFileReader reader = new TextFileReader(inputPathStr, sparkSession);
        Dataset<String> lines = reader.get();

        long nblines = lines.count();
        log.info("nblines={}", nblines);
           /* WordCounterFunc wordCounterFunc = new WordCounterFunc();
            ResultWriter writer = new ResultWriter();

            Stream<String> lines1 = (Stream<String>) reader.get();
            writer.accept(
                    wordCounterFunc.apply(
                            lines1
                    )
            );

            */

    }

}