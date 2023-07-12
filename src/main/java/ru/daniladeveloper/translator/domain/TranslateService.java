package ru.daniladeveloper.translator.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TranslateService {

    private final TranslateRepository translateRepository;

    public String translate(String text, Language src, Language dest) {
        StringBuilder result = new StringBuilder();
        List<String> words = clear(text);
        translateRepository.findAllById(words).forEach(entry -> result.append(entry.getTranslation()).append(" "));
        return result.toString();
    }

    private static List<String> clear(String text) {
        // extract words only
        return Stream.of(text.split(" ")).map(String::toLowerCase).toList();
    }

    public void loadTranslations(Map<String, String> dict) {
        List<DictionaryEntry> dictionaryEntries = dict.entrySet().stream()
            .map(entry -> new DictionaryEntry(entry.getKey(), entry.getValue()))
            .toList();
        translateRepository.saveAll(dictionaryEntries);
    }
}
