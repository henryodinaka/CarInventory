package com.init.controller;

import com.init.dto.BrandRequest;
import com.init.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
@Slf4j
public class BrandController {
    private BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/name")
    public ResponseEntity<?> getByName(@RequestParam String name){
        return brandService.findByName(name);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String name){
        return brandService.searchBrand(name.toLowerCase());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return brandService.findAll();
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody BrandRequest brandRequest){

        log.info("Request {}",brandRequest);
        ResponseEntity<?> response = brandService.setup(brandRequest);
        log.info("Response {}",response);
        return response;
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id){
        log.info("Brand Id to be deleted{}",id);
        ResponseEntity<?> response = brandService.deleteBrand(id);
        log.info("Response {}",response);
        return response;
    }
}
