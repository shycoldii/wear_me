package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.model.Office;
import server.repository.OfficeRepository;

import java.util.List;

/**
 * Рест-контроллер для OfficeRepository
 */
@RestController
@RequestMapping("shop")
public class OfficeController {
    private final OfficeRepository officeRepository;
    @Autowired public OfficeController(OfficeRepository officeRepository){
    this.officeRepository = officeRepository;
    }

    /**
     * Получение сущности офис по идентификатору
     * @param id - идентификатор
     * @return офис
     */
    @GetMapping("/office")
    @ResponseBody
    public ResponseEntity<Office> getOffice(@RequestParam Long id){
        Office res = officeRepository.findOfficeById(id);
        if(res!=null){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Получение списка всех офисов
     * @return список офисов
     */
    @GetMapping("/offices")
    public ResponseEntity<List<Office>> getAllOffices(){
        return new ResponseEntity<>(officeRepository.findAll(),HttpStatus.OK);
    }
}