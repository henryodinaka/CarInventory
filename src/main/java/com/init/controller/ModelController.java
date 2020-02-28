package com.init.controller;

import com.init.dto.ModelRequest;
import com.init.enums.Condition;
import com.init.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/model")
@Slf4j
public class ModelController {

    private ModelService modelService;

    @Autowired
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/name")
    public ResponseEntity<?> getByName(@RequestParam String name){
        return modelService.findByName(name);
    }


    @GetMapping("/search")
    public ResponseEntity<?> search(String name){
        return modelService.searchByBrand(name);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return modelService.findAll();
    }


    @GetMapping("/brand/all")
    public ResponseEntity<?> getAllByBrand(Long brandId,String brandName){
        return modelService.findAllByBrand(brandId,brandName);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody ModelRequest brandRequest,@RequestParam Condition condition){

        log.info("Request {}",brandRequest);
        ResponseEntity<?> response = modelService.setup(brandRequest,condition);
        log.info("Response {}",response);
        return response;
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id){
        log.info("Brand Id to be deleted{}",id);
        ResponseEntity<?> response = modelService.deleteBrand(id);
        log.info("Response {}",response);
        return response;
    }
}
