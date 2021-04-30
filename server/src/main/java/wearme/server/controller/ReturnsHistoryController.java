package wearme.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wearme.server.model.ReturnsHistory;
import wearme.server.repository.ReturnsHistoryRepository;

/**
 * Рест-контроллер для ReturnsHistoryRepository
 */
@RestController
@RequestMapping("shop")
public class ReturnsHistoryController {
    private final ReturnsHistoryRepository returnsHistoryRepository;

    /**
     * Инициализирует контроллер истории возвратов
     * @param returnsHistoryRepository - репозиторий истории возвратов
     */
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
