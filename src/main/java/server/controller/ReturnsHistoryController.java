package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.model.ReturnsHistory;
import server.repository.ReturnsHistoryRepository;

/**
 * Рест-контроллер для ReturnsHistoryRepository
 */
@RestController
@RequestMapping("shop")
public class ReturnsHistoryController {
    private final ReturnsHistoryRepository returnsHistoryRepository;
    @Autowired
    public ReturnsHistoryController(ReturnsHistoryRepository returnsHistoryRepository){
        this.returnsHistoryRepository = returnsHistoryRepository;
    }

    /**
     * Добавление нового возврата
     * @param returns - сущность возврата
     * @return ReturnsHistory
     */
    @PostMapping("/returns")
    ReturnsHistory newReturn(@RequestBody ReturnsHistory returns){
        return returnsHistoryRepository.save(returns);
    }

    /**
     * Удаление возврата по идентификатору
     * @param id - идентификатор
     */
    @DeleteMapping("/returns/{id}")
    void deleteReturn(@PathVariable Long id) {
        returnsHistoryRepository.deleteById(id);
    }
}
