package fr.iiil.bigdata.reminder.beans;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Data
@RequiredArgsConstructor
public class FileContentAndStat {
    private final Stream<String> lines;
    private final Integer nbLines;
}
