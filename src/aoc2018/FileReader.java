package aoc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {
    private String filePath;

    public FileReader(String path) {
        filePath = path;
    }

    public List<String> readFile(){
        List<String> allLines = null;
        try {
            allLines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return allLines;
    }
}
