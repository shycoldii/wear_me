package wearme.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearme.server.model.Position;
import wearme.server.repository.PositionRepository;

/**
 * Рест-контроллер для PositionRepository
 */
@RestController
@RequestMapping("shop")
public class PositionController {
    private final PositionRepository positionRepository;

    /**
     * Инициализирует контроллер  роли
     * @param positionRepository - репозиторий роли
     */
    public PositionController(PositionRepository positionRepository){
        this.positionRepository = positionRepository;
    }

    /**
     * Получение названия позиции по идентификатору
     * @param id - идентификатор позиции
     * @return позиция
     */
    @GetMapping("/positions")
    @ResponseBody
    public ResponseEntity<String> getPosition(@RequestParam Long id){
        Position res = positionRepository.findPositionById(id);
        if(res!=null){
            return new ResponseEntity<>(res.getName(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
