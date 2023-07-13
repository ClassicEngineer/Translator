package ru.daniladeveloper.translator.infrastucture;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.daniladeveloper.translator.api.TranslationDestination;
import ru.daniladeveloper.translator.app.TranslateApplicationService;


import java.io.FileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
@Service
@RequiredArgsConstructor
public class DictionaryLoader {

    @Value("${dictionary.load}")
    private Boolean isNeedToLoad = false;

    @Value("${dictionary.hello-world}")
    private Boolean isHelloWorldEdition = false;

    @Value("classpath:dictionary/dict.csv")
    private Resource resource;

    private final TranslateApplicationService translateService;

    @EventListener({ContextRefreshedEvent.class})
    public void logHrefOnStartup() {
        if (isNeedToLoad) {
            log.info("Loading dictionary to Redis");
            loadDictionary();
        }
        log.info("Dictionary loaded. Go to http://localhost:8080/");
    }

    private void loadDictionary() {
        CSVParser csvParser = new CSVParserBuilder().withSeparator('\t').build(); // custom separator
        try(CSVReader reader = new CSVReaderBuilder(
            new FileReader(resource.getFile()))
            .withCSVParser(csvParser)   // custom CSV parser
            .withSkipLines(1)           // skip the first line, header info
            .build()){
            List<String[]> row = reader.readAll();
            Map<String, String> enRuDict = new HashMap<>();
            for (int i = 0; i < row.size(); i++) {
                String ruWord = row.get(i)[0];
                String[] engWords = row.get(i)[2].split(",");

                for (String engWord: engWords) {
                    enRuDict.putIfAbsent(engWord.trim(), ruWord);
                }
            }
            if (isHelloWorldEdition) {
                enRuDict.put("hello", "привет");
                enRuDict.put("world", "мир");

            }

            translateService.loadTranslations(enRuDict, TranslationDestination.EN_RU);


        } catch (Exception exception) {
            log.severe(exception.getMessage());
        }

    }


}
