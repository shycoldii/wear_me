package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.model.Check;
import server.repository.CheckRepository;

/**
 * Рест-контроллер для CheckRepository
 */
@RestController
@RequestMapping("shop")
public class CheckController {
    private final CheckRepository checkRepository;
    @Autowired
    public CheckController(CheckRepository checkRepository){
        this.checkRepository = checkRepository;
    }

    /**
     * Получение чека по идентификатору
     * @param id - идентификатор
     * @return чек
     */
    @GetMapping("/checks")
    @ResponseBody
    public ResponseEntity<Check> getCheck(@RequestParam Long id){
        Check res = checkRepository.findCheckById(id);
        if(res!=null){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        //не найден такой
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Добавление нового чека
     * @param newCheck - сущность нового чека
     * @return Check
     */
    @PostMapping("/checks")
    Check newCheck(@RequestBody Check newCheck){
        return checkRepository.save(newCheck);
    }
}
