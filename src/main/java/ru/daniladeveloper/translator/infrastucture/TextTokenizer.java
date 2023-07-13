package ru.daniladeveloper.translator.infrastucture;

import ru.daniladeveloper.translator.domain.Token;

import java.util.ArrayList;
import java.util.List;

public class TextTokenizer {

    public static List<Token> tokenize(String text) {
        // extract words only
//        return Stream.of(text.split(" ")).map(String::toLowerCase).toList();
        text = text.toLowerCase();
        List<Token> tokens = new ArrayList<>();
        Integer order = 0;
        for (int i = 0; i < text.length(); i++) {
            Character ch = text.charAt(i);
            if (Character.isAlphabetic(text.charAt(i))) {
                StringBuilder word = new StringBuilder();
                while (i < text.length() && Character.isAlphabetic(text.charAt(i))) {
                    word.append(text.charAt(i));
                    i++;
                }
                tokens.add(new Token(order, word.toString(), true));
                if (i < text.length()) {
                    tokens.add(new Token(order, String.valueOf(text.charAt(i)), false));
                }
            }
            else {
                tokens.add(new Token(order, String.valueOf(ch), false));
            }
            order++;
        }
        return tokens;
    }

    public static void main(String[] args) {
        var result = tokenize("Hello, world!");
        System.out.println(result);
    }
}
