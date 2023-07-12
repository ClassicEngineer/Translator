package ru.daniladeveloper.translator.api;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.daniladeveloper.translator.app.TranslateApplicationService;

@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/translator/")
public class TranslatorRestController {


    private final TranslateApplicationService translateApplicationService;

    @PostMapping("/translate")
    public ResponseEntity<?> translate(@RequestBody TranslateRequestBody body) {
        log.info("Request Body: " + body);
        String result = translateApplicationService.translate(body.getText(), body.getDestination());
        return ResponseEntity.ok(result);
    }



}
