package ru.daniladeveloper.translator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("DictEntry")
@Data
@AllArgsConstructor
public class DictionaryEntry {

    private String id; // key word

    private String translation;
}
