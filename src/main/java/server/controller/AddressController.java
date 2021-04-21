package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.model.Address;
import server.repository.AddressRepository;

/**
 * Рест-контроллер для AddressRepository
 */
@RestController
@RequestMapping("shop")
public class AddressController {
    private final AddressRepository addressRepository;
    @Autowired public AddressController(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    /**
     * Получение адреса по идентификатору
     * @param id - идентификатор
     * @return адрес
     */
    @GetMapping("/addresses")
    @ResponseBody
    public ResponseEntity<Address> getAddress(@RequestParam Long id){
        Address res = addressRepository.findAddressById(id);
        if(res!=null){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
