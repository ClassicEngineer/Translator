package ru.daniladeveloper.translator.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslateRepository extends CrudRepository<DictionaryEntry, String> {
}
