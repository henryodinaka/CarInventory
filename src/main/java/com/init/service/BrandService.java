package com.init.service;

import com.init.dto.BrandRequest;
import com.init.dto.response.Response;
import com.init.model.Brand;
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

        Brand brand = Brand.builder().name(brandRequest.getName()).build();

        brandRepo.save(brand);
        return Response.setUpResponse(200, brand.getName() + " was added successfully");

    }

    public ResponseEntity<?> deleteBrand(Long id) {

        Brand brand = brandRepo.getBrandById(id);
        if (brand == null)
            return Response.setUpResponse(400, "No record found");
        brand.setDeleted(true);
        brandRepo.save(brand);
        return Response.setUpResponse(200, "Brand deleted successfully");

    }

    public ResponseEntity<?> findAll() {
        List<Brand> brands = brandRepo.findAllBrand(false);
        if (brands.isEmpty())
            return Response.setUpResponse(404, "No record found");
        return Response.setUpResponse(200, "List of brands ", brands);
    }

    public ResponseEntity<?> findByName(String name) {
        Brand brand = brandRepo.getBrandByName(name.toLowerCase());
        if (brand == null)
            return Response.setUpResponse(404, "No record found");
        return Response.setUpResponse(200, "Record found ", brand);
    }

    public ResponseEntity<?> searchBrand(String name) {
        List<Brand> brand = brandRepo.searchBrand(name);
        if (brand == null || brand.isEmpty())
            return Response.setUpResponse(404, "No record found");
        return Response.setUpResponse(200, "Record found ", brand);
    }
}
