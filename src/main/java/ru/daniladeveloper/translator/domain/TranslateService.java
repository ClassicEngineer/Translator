package ru.daniladeveloper.translator.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.daniladeveloper.translator.infrastucture.TextTokenizer;


import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TranslateService {

    private final TranslateRepository translateRepository;

    public String translate(String text, Language src, Language dest) {
        StringBuilder result = new StringBuilder();
        List<Token> tokens = splitOnTokens(text);
        List<String> words = tokens.stream().filter(Token::isText)
            .map(Token::getValue).toList();

        Iterable<DictionaryEntry> allById = translateRepository.findAllById(words);

        Map<String, String> wordToTranslate = new HashMap<>();
        for (String word : words) {
            for (DictionaryEntry entry : allById) {
                if (word.equals(entry.getId())) {
                    wordToTranslate.put(word, entry.getTranslation());
                    break;
                }
            }
        }

        for (Token token : tokens) {
            if (token.isText()) {
                result.append(wordToTranslate.get(token.getValue()));
            } else {
                result.append(token.getValue());
            }
        }

        return result.toString();
    }

    private static List<Token> splitOnTokens(String text) {
        //return Arrays.stream(text.split(" ")).map((s -> new Token(0, s, true))).toList();
        return TextTokenizer.tokenize(text);
    }

    public void loadTranslations(Map<String, String> dict) {
        List<DictionaryEntry> dictionaryEntries = dict.entrySet().stream()
            .map(entry -> new DictionaryEntry(entry.getKey(), entry.getValue()))
            .toList();
        translateRepository.saveAll(dictionaryEntries);
    }
}
