package com.init.service;

import com.init.dto.ModelRequest;
import com.init.dto.response.Response;
import com.init.enums.Condition;
import com.init.model.CarBrand;
import com.init.model.CarModel;
import com.init.repo.BrandRepo;
import com.init.repo.ModelRepo;
import com.init.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ModelService {
    private ModelRepo modelRepo;
    private BrandRepo brandRepo;

    @Autowired
    public ModelService(ModelRepo modelRepo, BrandRepo brandRepo) {
        this.modelRepo = modelRepo;
        this.brandRepo = brandRepo;
    }

    public ResponseEntity<?> setup(ModelRequest modelRequest, Condition condition) {
        String error = Validation.validateModelRequest(modelRequest);
        if (error != null)
            return Response.setUpResponse(400, error);

        CarBrand carBrand = brandRepo.getBrandById(modelRequest.getBrandId());
        if (carBrand == null)
            return Response.setUpResponse(400, "Brand does not exist.");
        if (carBrand.isDeleted())
            return Response.setUpResponse(400," Brand not authorized ");
        CarModel carModel = modelRepo.getModelById(modelRequest.getBrandId());
        if (carModel == null)
            carModel = new CarModel();
        carModel = carModel.buildModel(carModel,modelRequest, carBrand,new BigDecimal(modelRequest.getPrice()),condition);

        modelRepo.save(carModel);
        return Response.setUpResponse(200, carModel.getName() + " was added successfully");

    }

    public ResponseEntity<?> deleteBrand(Long id) {

        CarModel carModel = modelRepo.getModelById(id);
        if (carModel == null)
            return Response.setUpResponse(400, "No record found");
        carModel.setDeleted(true);
        modelRepo.save(carModel);
        return Response.setUpResponse(200, "Model deleted successfully");

    }

    public ResponseEntity<?> findAll() {
        List<CarModel> carModels = modelRepo.findAllModel();
        if (carModels.isEmpty())
            return Response.setUpResponse(404, "No record found");
        return Response.setUpResponse(200, "List of models ", carModels);
    }

    public ResponseEntity<?> searchByBrand(String brandName) {
        List<CarModel> carModels = modelRepo.search(brandName,false);
        if (carModels.isEmpty())
            return Response.setUpResponse(404, "No record found");
        return Response.setUpResponse(200, "List of models ", carModels);
    }

    public ResponseEntity<?> findAllByBrand(Long id, String brandName) {
        List<CarModel> carModels = modelRepo.findAllByBrand(id,brandName,false);
        if (carModels.isEmpty())
            return Response.setUpResponse(404, "No record found");
        return Response.setUpResponse(200, "List of models ", carModels);
    }

    public ResponseEntity<?> findByName(String name) {
        CarModel carModel = modelRepo.getModelByName(name.toLowerCase());
        if (carModel == null)
            return Response.setUpResponse(404, "No record found");
        return Response.setUpResponse(200, "Record found ", carModel);
    }
}
