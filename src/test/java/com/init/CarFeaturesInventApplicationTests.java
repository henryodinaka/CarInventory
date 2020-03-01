package com.init;

import com.init.dto.BrandRequest;
import com.init.dto.ModelRequest;
import com.init.dto.response.Response;
import com.init.enums.Condition;
import com.init.model.CarBrand;
import com.init.model.CarModel;
import com.init.repo.BrandRepo;
import com.init.repo.ModelRepo;
import com.init.service.BrandService;
import com.init.service.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@WebMvcTest
class CarFeaturesInventApplicationTests {

    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandRepo brandRepo;
    @Autowired
    private ModelRepo modelRepo;

    @Autowired
    private ModelService modelService;

    private Long brandId;
    private Long modelId;

    @Test
    void contextLoads() {
    }

    @BeforeEach()
    void init() {

        CarBrand carBrand= brandRepo.save(new CarBrand(("Jeep")));
        System.out.println("This is before each " + carBrand);
        brandId = carBrand.getId();
        CarModel carModel = CarModel.builder()
                .carBrand(carBrand).exterior("#FFFFF").interior("#FFFFFF").name("Corolla").price(new BigDecimal("8498583")).quantityInStock(23).year("2019")
                .condition(Condition.USED.name()).build();
        carModel = modelRepo.save(carModel);
        System.out.println("Save model at before method "+carModel);
        modelId= carModel.getId();

    }

    @Test
    void insertBrand() {

        BrandRequest request = new BrandRequest();
        request.setName("Toyota");

        ResponseEntity response = brandService.setup(request);
        System.out.println("This is the insert test result" + response);

        assertThat(brandService.findByName("Toyota")).isNotNull();
    }


    @Test
    void searchBrand() {
        BrandRequest request = new BrandRequest();
        request.setName("Rio");
        brandService.setup(request);
        ResponseEntity response = brandService.searchBrand("ri");
        System.out.println(response);
        assertEquals(200, response.getStatusCode().value(), "Failed search test");
    }

    @Test
    void checkUniqueName() {

        BrandRequest request = new BrandRequest();
        request.setName("Jeep");

        ResponseEntity response = brandService.setup(request);
        System.out.println("This is the insert test result" + response);

        assertEquals(400, response.getStatusCode().value(), "Brand name must be unique");
    }

    @Test
    void insertModel() {

//        CarBrand brand = brandRepo.save(new CarBrand("Kia"));

        ModelRequest request = ModelRequest.builder()
                .brandId(brandId).exterior("#FFFFF").interior("#FFFFFF").name("Corolla").price("8498583").quantityInStock(23).year("2019")
                .build();
        ResponseEntity responseEntity = modelService.setup(request, Condition.USED);

        assertEquals(200,responseEntity.getStatusCode().value(),"Failed to save Car Model");
    }

    @Test
    void checkUniqueModelName() {

//        CarBrand brand = brandRepo.save(new CarBrand("Kia"));

        ModelRequest request = ModelRequest.builder()
                .brandId(brandId).exterior("#FFFFF").interior("#FFFFFF").name("Corolla").price("8498583").quantityInStock(23).year("2019")
                .build();
        ResponseEntity responseEntity = modelService.setup(request, Condition.USED);

        assertEquals(400,responseEntity.getStatusCode().value(),"Failed to save Car Model");
    }

    @Test
    void searchCaModel() {

        ResponseEntity searchResult = modelService.searchCarModel("co");
//        System.out.println("Result "+ searchResult);
        assertEquals(200,searchResult.getStatusCode().value(),"No result found");
    }
    @Test
    void editModel() {

        ModelRequest request = ModelRequest.builder().id(modelId)
                .brandId(brandId).exterior("#FFFFF").interior("#FFFFFF").name("CorollaEdited").price("8498583").quantityInStock(23).year("2019")
                .build();
        ResponseEntity responseEntity = modelService.setup(request, Condition.NEW);
        Response response = (Response) responseEntity.getBody();
        assert response != null;
        Object data = response.getData();
        System.out.println("data "+responseEntity);
        CarModel c = (CarModel) data;
        assertEquals("CorollaEdited",c.getName(),"Update failed the test");

    }

    @Test
    void deleteModel() {
        Optional<CarModel> byId = modelRepo.findById(modelId);

        CarModel carModel = byId.get();
        carModel.setDeleted(true);
       carModel = modelRepo.save(carModel);
        assertTrue(carModel.isDeleted(), "Delete test failed");

    }

    @Test
    void searchModelByName() {

        ModelRequest request = ModelRequest.builder()
                .brandId(brandId).exterior("#FFFFF").interior("#FFFFFF").name("Kia").price("8498583").quantityInStock(23).year("2019")
                .build();
       modelService.setup(request, Condition.USED);
        List<CarModel> corolla = modelRepo.search("k",false);
        assertFalse(corolla.isEmpty(), "Search result is empty");
    }


}
