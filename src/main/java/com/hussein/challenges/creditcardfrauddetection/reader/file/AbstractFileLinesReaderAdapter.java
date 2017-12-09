package com.hussein.challenges.creditcardfrauddetection.reader.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

abstract class AbstractFileLinesReaderAdapter<T> implements FileReaderAdapter {

    private final FileRecordMapper<T> fileRecordMapper;

    protected AbstractFileLinesReaderAdapter(FileRecordMapper<T> fileRecordMapper) {
        this.fileRecordMapper = fileRecordMapper;
    }

    @Override
    public void consumeFileLines(Path path) throws IOException {
        Stream<String> fileLines = Files.lines(path);
        fileLines.map(String::trim)
                .filter(filterLines())
                .map(fileRecordMapper::map)
                .forEach(consumeLine());
    }

    protected abstract Consumer<T> consumeLine();

    protected Predicate<String> filterLines() {
        return s -> !s.isEmpty();
    }
}
