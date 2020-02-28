package com.init;

import com.init.model.CarBrand;
import com.init.model.CarModel;
import com.init.service.BrandService;
import com.init.service.ModelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CarFeaturesInventApplicationTests {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelService modelService;
    @Test
    void contextLoads() {
    }

    @Test
    void insertBrand(){
        CarBrand carBrand = new CarBrand();
    }

    @Test
    void insertModel(){
        CarModel carModel = new CarModel();
    }

    @Test
    void editModel(){

    }

    @Test
    void deleteModel(){

    }

    @Test
    void searchModelByName(){

    }


    @Test
    void searchModelByBrandNameOrId(){

    }

    @Test
    void searchBrand(){

    }

    @Test
    void searchModel(){

    }

}
