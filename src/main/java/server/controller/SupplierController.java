package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.model.Supplier;
import server.repository.SupplierRepository;

import java.util.List;

/**
 * Рест-контроллер для SupplierRepository
 */
@RestController
@RequestMapping("shop")
public class SupplierController {
    private final SupplierRepository supplierRepository;
    @Autowired public SupplierController(SupplierRepository supplierRepository){
        this.supplierRepository = supplierRepository;
    }

    /**
     * Возвращает список всех сотрудников
     * @return список поставщиков
     */
    @GetMapping("/suppliers")
    public ResponseEntity<List<Supplier>> getAllSuppliers(){
        return new ResponseEntity<>(supplierRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Возвращает поставщика по критериям параметров
     * @param email - электронная почта
     * @param phone - телефонный номер
     * @param name - название
     * @return поставщик
     */
    @GetMapping("/supplier")
    @ResponseBody
    public ResponseEntity<Supplier> getSupplier(@RequestParam(required = false) String email,
                                                @RequestParam(required = false) String phone, @RequestParam(required = false) String name){
        Supplier res = supplierRepository.findSupplierByNameAndEmailAndPhoneNumber(name,email,phone);
        if(res!=null){
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Создает нового поставщика  и возвращает его при удачном завершении
     * @param supplier - сущность поставщика
     * @return Supplier
     */
    @PostMapping("/suppliers")
    Supplier newSupplier(@RequestBody Supplier supplier){
        return supplierRepository.save(supplier);
    }
}
