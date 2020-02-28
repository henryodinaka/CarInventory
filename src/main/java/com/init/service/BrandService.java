package com.init.service;

import com.init.dto.BrandRequest;
import com.init.dto.response.Response;
import com.init.model.CarBrand;
import com.init.repo.BrandRepo;
import com.init.utils.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BrandService {
    private BrandRepo brandRepo;

    @Autowired
    public BrandService(BrandRepo brandRepo) {
        this.brandRepo = brandRepo;
    }

    public ResponseEntity<?> setup(BrandRequest brandRequest) {
        String error = Validation.validateBrandRequest(brandRequest);
        if (error != null)
            return Response.setUpResponse(400, error);

        if (brandRepo.getBrandByName(brandRequest.getName()) != null)
            return Response.setUpResponse(400, "Brand name already exist. Name must be unique");

        CarBrand carBrand = CarBrand.builder().name(brandRequest.getName()).build();

        brandRepo.save(carBrand);
        return Response.setUpResponse(200, carBrand.getName() + " was added successfully");

    }

    public ResponseEntity<?> deleteBrand(Long id) {

        CarBrand carBrand = brandRepo.getBrandById(id);
        if (carBrand == null)
            return Response.setUpResponse(400, "No record found");
        carBrand.setDeleted(true);
        brandRepo.save(carBrand);
        return Response.setUpResponse(200, "Brand deleted successfully");

    }

    public ResponseEntity<?> findAll() {
        List<CarBrand> carBrands = brandRepo.findAllBrand(false);
        if (carBrands.isEmpty())
            return Response.setUpResponse(404, "No record found");
        return Response.setUpResponse(200, "List of brands ", carBrands);
    }

    public ResponseEntity<?> findByName(String name) {
        CarBrand carBrand = brandRepo.getBrandByName(name.toLowerCase());
        if (carBrand == null)
            return Response.setUpResponse(404, "No record found");
        return Response.setUpResponse(200, "Record found ", carBrand);
    }

    public ResponseEntity<?> searchBrand(String name) {
        List<CarBrand> carBrand = brandRepo.searchBrand(name);
        if (carBrand == null || carBrand.isEmpty())
            return Response.setUpResponse(404, "No record found");
        return Response.setUpResponse(200, "Record found ", carBrand);
    }
}
