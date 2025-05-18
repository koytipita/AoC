package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileParseUtil {
    public static final String warningLine = "Error reading file:  %s";

    public static List<String> readLinesFromFile(String filePath, Logger logger) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, String.format(warningLine, e.getMessage()));
        }
        return lines;
    }

    public static List<String> readStringsFromFile(String filePath, Logger logger) {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            words = Arrays.stream(line.split(",")).toList();
        } catch (IOException e) {
            logger.log(Level.WARNING, String.format(warningLine, e.getMessage()));
        }
        return words;
    }
    
    public static String readStringFromFile(String filePath, Logger logger) {
        String str = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            str= reader.readLine();
        } catch (IOException e) {
            logger.log(Level.WARNING, String.format(warningLine, e.getMessage()));
        }
        return str;
    } 
}
