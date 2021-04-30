package wearme.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wearme.server.model.SupplyHistory;
import wearme.server.repository.SupplyHistoryRepository;

/**
 * Рест-контроллер для SupplyHistoryRepository
 */
@RestController
@RequestMapping("shop")
public class SupplyHistoryController {
    private final SupplyHistoryRepository supplyHistoryRepository;

    /**
     * Инициализирует контроллер истории поставок
     * @param supplyHistoryRepository - контроллер истории поставок
     */
    @Autowired public SupplyHistoryController(SupplyHistoryRepository supplyHistoryRepository){
        this.supplyHistoryRepository = supplyHistoryRepository;
    }

    /**
     * Создает новую историю поставки
     * @param supplyHistory - сущность истории поставки
     * @return SupplyHistory
     */
    @PostMapping("/supplyHistory")
    SupplyHistory SupplyHistory(@RequestBody SupplyHistory supplyHistory){
        return supplyHistoryRepository.save(supplyHistory);
    }
}
