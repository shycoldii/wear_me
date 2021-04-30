package wearme.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearme.server.model.StoreProduct;
import wearme.server.repository.StoreProductRepository;

import javax.validation.Valid;
import java.util.List;

/**
 * Рест-контроллер для StoreProductRepository
 */
@RestController
@RequestMapping("shop")
public class StoreProductController {
  private final StoreProductRepository storeProductRepository;

    /**
     * Инициализирует контроллер товара
     * @param storeProductRepository - репозиторий товара
     */
    public StoreProductController(StoreProductRepository storeProductRepository){
      this.storeProductRepository = storeProductRepository;
  }

    /**
     * Используется для получения товаров по различным параметрам
     * @param articul - артикул
     * @param officeId - офис
     * @param productSize - размер
     * @param checkId - чек
     * @return список товаров
     */
    @GetMapping("/storeProducts")
    @ResponseBody
    public ResponseEntity<List<StoreProduct>> getStoreProduct(@RequestParam(required = false) Integer articul, @RequestParam(required = false) Long officeId,
                                                              @RequestParam(required = false) String productSize,
                                                              @RequestParam(required = false) Long checkId){
        List<StoreProduct> res;
        if(articul != null){
            if(officeId != null){
                res = storeProductRepository.findStoreProductByArticulAndOffice_IdAndSizeAndStatus(articul, officeId, productSize, 2);
            }
            else{
                res = storeProductRepository.findStoreProductByArticul(articul);
            }
        }
        else{
            if(checkId != null){
                res = storeProductRepository.findStoreProductByCheck_Id(checkId);
            }
            else{
                res=storeProductRepository.findAll();
            }


        }
        if(res!=null){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Изменение/добавление товаров
     * @param id - идентификатор
     * @param storeProduct - измененная сущность
     * @return товар
     */
    @PutMapping("/storeProducts/{id}")
    @ResponseBody
    public ResponseEntity<StoreProduct> updateStoreProduct(@PathVariable(value = "id") Long id,
                                                           @Valid @RequestBody StoreProduct storeProduct){

        StoreProduct storeProduct1 = storeProductRepository.findStoreProductById(id);
        if(storeProduct1 != null){
            if (storeProduct1.getStatus() ==2 & storeProduct1.getCheck() == null){
                storeProduct1.setCheck(storeProduct.getCheck());
                storeProduct1.setStatus(storeProduct.getStatus());
                return  new ResponseEntity<>(storeProductRepository.save(storeProduct1),HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(storeProductRepository.save(storeProduct),HttpStatus.OK);

    }

    /**
     * Добавление нового товара
     * @param newStoreProduct  - новый товар
     * @return StoreProduct
     */
    @PostMapping("/storeProducts")
    StoreProduct StoreProduct(@RequestBody StoreProduct newStoreProduct){
        return storeProductRepository.save(newStoreProduct);
    }
}
