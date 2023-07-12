package ru.daniladeveloper.translator.api;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TranslateRequestBody {

    private String text;
    private TranslationDestination destination;
}
