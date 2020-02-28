package com.init.service;

import com.init.dto.ModelRequest;
import com.init.dto.response.Response;
import com.init.enums.Condition;
import com.init.model.Brand;
import com.init.model.Model;
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

        Brand brand = brandRepo.getBrandById(modelRequest.getBrandId());
        if (brand == null)
            return Response.setUpResponse(400, "Brand does not exist.");
        if (brand.isDeleted())
            return Response.setUpResponse(400," Brand not authorized ");
        Model model = modelRepo.getModelById(modelRequest.getBrandId());
        if (model == null)
            model = new Model();
        model = model.buildModel(model,modelRequest,brand,new BigDecimal(modelRequest.getPrice()),condition);

        modelRepo.save(model);
        return Response.setUpResponse(200, model.getName() + " was added successfully");

    }

    public ResponseEntity<?> deleteBrand(Long id) {

        Model model = modelRepo.getModelById(id);
        if (model == null)
            return Response.setUpResponse(400, "No record found");
        model.setDeleted(true);
        modelRepo.save(model);
        return Response.setUpResponse(200, "Model deleted successfully");

    }

    public ResponseEntity<?> findAll() {
        List<Model> models = modelRepo.findAllModel();
        if (models.isEmpty())
            return Response.setUpResponse(404, "No record found");
        return Response.setUpResponse(200, "List of models ", models);
    }

    public ResponseEntity<?> searchByBrand(String brandName) {
        List<Model> models = modelRepo.search(brandName,false);
        if (models.isEmpty())
            return Response.setUpResponse(404, "No record found");
        return Response.setUpResponse(200, "List of models ", models);
    }

    public ResponseEntity<?> findAllByBrand(Long id, String brandName) {
        List<Model> models = modelRepo.findAllByBrand(id,brandName,false);
        if (models.isEmpty())
            return Response.setUpResponse(404, "No record found");
        return Response.setUpResponse(200, "List of models ", models);
    }

    public ResponseEntity<?> findByName(String name) {
        Model model = modelRepo.getModelByName(name.toLowerCase());
        if (model == null)
            return Response.setUpResponse(404, "No record found");
        return Response.setUpResponse(200, "Record found ", model);
    }
}
