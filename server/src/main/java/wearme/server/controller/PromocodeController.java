package wearme.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearme.server.model.Promocode;
import wearme.server.repository.PromocodeRepository;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Рест-контроллер для PromocodeRepository
 */
@RestController
@RequestMapping("shop")
public class PromocodeController {
    private final PromocodeRepository promocodeRepository;

    /**
     * Инициализирует контроллер промокода
     * @param promocodeRepository - репозиторий промокода
     */
    @Autowired public PromocodeController(PromocodeRepository promocodeRepository){
        this.promocodeRepository = promocodeRepository;
    }

    /**
     * Получение промокода по названию
     * @param name - название промокода
     * @return промокод
     */
    @GetMapping("/promocode")
    @ResponseBody
    public ResponseEntity<Promocode> getPromocode(@RequestParam String name) throws UnsupportedEncodingException {
        Promocode res = promocodeRepository.findPromocodeByName(URLDecoder.decode(name, StandardCharsets.UTF_8.toString()));
        if(res!=null){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Получение всех промокодов
     * @return список промокодов
     */
    @GetMapping("/promocodes")
    public ResponseEntity<List<Promocode>> getAllPromocodes(){
        return new ResponseEntity<>(promocodeRepository.findAll(),HttpStatus.OK);
    }

    /**
     * Создание нового промокода
     * @param promocode - сущность нового промокода
     * @return Promocode
     */
    @PostMapping("/promocodes")
    Promocode newPromocode(@RequestBody Promocode promocode){
        return promocodeRepository.save(promocode);
    }
}
