package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.model.ClientHistory;
import server.repository.ClientHistoryRepository;

@RestController
@RequestMapping("shop")
public class ClientHistoryController {
    private final ClientHistoryRepository clientHistoryRepository;
    @Autowired
    ClientHistoryController(ClientHistoryRepository clientHistoryRepository){
        this.clientHistoryRepository = clientHistoryRepository;
    }

    /**
     * Добавляет новую клиентскую историю
     * @param clientHistory - сущность истории клиента
     * @return  ClientHistory
     */
    @PostMapping("/clients_history")
    ClientHistory newClientsHistory(@RequestBody ClientHistory clientHistory){
        return clientHistoryRepository.save(clientHistory);
    }
}
