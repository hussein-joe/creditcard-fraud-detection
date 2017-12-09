package com.hussein.challenges.creditcardfrauddetection.reader.file;

import java.io.IOException;
import java.nio.file.Path;

public interface FileReaderAdapter {

    void consumeFileLines(Path path) throws IOException;
}
