package ru.daniladeveloper.translator.infrastucture;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.*;

public class DictionaryParser {

    public static void main(String[] args) throws Exception {
        CSVParser csvParser = new CSVParserBuilder().withSeparator('\t').build(); // custom separator
        try(CSVReader reader = new CSVReaderBuilder(
            new FileReader("dictionary/verbs.csv"))
            .withCSVParser(csvParser)   // custom CSV parser
            .withSkipLines(1)           // skip the first line, header info
            .build()){
            List<String[]> r = reader.readAll();
//            r.forEach(x -> System.out.println(Arrays.toString(x)));

            Map<String, String> enRuDict = new HashMap<>();
            Map<String, String> ruEnDict = new HashMap<>();

            for (int i = 0; i < 5; i++) {
                String ruWord = r.get(i)[0];
                String[] engWords = r.get(i)[2].split(",");
                ruEnDict.put(ruWord, engWords[0]);

                for (String engWord: engWords) {
                    enRuDict.putIfAbsent(engWord, ruWord);
                }
            }
            System.out.println(ruEnDict);
        }
    }

}
