package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.model.Client;
import server.repository.ClientRepository;

import javax.validation.Valid;
import java.util.List;

/**
 * Рест-контроллер для ClientRepository
 */
@RestController
@RequestMapping("shop")
public class ClientController {
    private final ClientRepository clientRepository;
    @Autowired
    public ClientController(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    /**
     * Изменяет/добавляет нового клиента по идентификатору
     * @param id - идентификатор
     * @param client - сущность клиента
     * @return клиент
     */
    @PutMapping("/clients/{id}")
    @ResponseBody
    public ResponseEntity<Client> updateClient(@PathVariable(value = "id") Long id,
                                               @Valid @RequestBody Client client){

        Client client1 = clientRepository.findClientById(id);
        if(client1 != null){
            client1.setNumberOfBonuses(client1.getNumberOfBonuses()+client.getNumberOfBonuses());
            return  new ResponseEntity<>(clientRepository.save(client1), HttpStatus.OK);
        }
        return new ResponseEntity<>(clientRepository.save(client),HttpStatus.OK);

    }

    /**
     * Получение клиента по заданным параметрам
     * @param email - электронная почта
     * @param phone - номер телефона
     * @return клиент
     */
    @GetMapping("/client")
    @ResponseBody
    public ResponseEntity<Client> getClient(@RequestParam(required = false) String email,
                                            @RequestParam(required = false) String phone){
        Client res = null;
        if (email == null){
            res = clientRepository.findClientByPhoneNumber(phone);
        }
        else if(phone == null){
            res = clientRepository.findClientByEmail(email);

        }
        if (phone!=null & email !=null){
            res = clientRepository.findClientByPhoneNumberAndEmail(phone,email);
        }
        if(res!=null){
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        //не найден такой
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Получение всех клиентов
     * @return список клиентов
     */
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients(){
        return new ResponseEntity<>(clientRepository.findAll(),HttpStatus.OK);
    }

    /**
     * Добавление нового клиента
     * @param client - сущность нового клиента
     * @return Client
     */
    @PostMapping("/clients")
    Client newReturn(@RequestBody Client client){
        return clientRepository.save(client);
    }
}
