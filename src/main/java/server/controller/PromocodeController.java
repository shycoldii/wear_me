package server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.model.Promocode;
import server.repository.PromocodeRepository;

import java.net.URLDecoder;
import java.util.List;

/**
 * Рест-контроллер для PromocodeRepository
 */
@RestController
@RequestMapping("shop")
public class PromocodeController {
    private final PromocodeRepository promocodeRepository;
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
    public ResponseEntity<Promocode> getPromocode(@RequestParam String name){
        Promocode res = promocodeRepository.findPromocodeByName(URLDecoder.decode(name));
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
