package ru.daniladeveloper.translator.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.daniladeveloper.translator.api.TranslationDestination;
import ru.daniladeveloper.translator.domain.Language;
import ru.daniladeveloper.translator.domain.TranslateService;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class TranslateApplicationService {

    private final TranslateService translateService;

    public String translate(String text, TranslationDestination destination) {
        return switch (destination) {
            case EN_RU -> translateService.translate(text, Language.EN, Language.RU);
            case RU_EN -> translateService.translate(text, Language.RU, Language.EN);
            default -> "ERROR, not implemented";
        };

    }

    public void loadTranslations(Map<String, String> dict, TranslationDestination destination) {
        translateService.loadTranslations(dict);
    }
}
