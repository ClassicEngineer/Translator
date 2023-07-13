package ru.daniladeveloper.translator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Token {

    private Integer order;
    private String value;
    private Boolean isText;

    public boolean isText() {
        return isText;
    }
}
