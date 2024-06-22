package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVProcessor {

    public static List<String[]> readCSV(String csvFile) throws IOException, CsvException {
        List<String[]> lines;
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            lines = reader.readAll();
        }
        return lines;
    }
}
