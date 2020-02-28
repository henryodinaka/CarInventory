package com.init.repo;

import com.init.model.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepo extends JpaRepository<CarBrand,Long> {

    @Query("select b from CarBrand b where  lower( b.name) =?1")
    CarBrand getBrandByName(String name);

    @Query("select b from CarBrand b where  lower( b.name) like %?1%")
    List<CarBrand> searchBrand(String name);

    @Query("select b from CarBrand b where  b.deleted =?1")
    List<CarBrand> findAllBrand(boolean isDeleted);
    CarBrand getBrandById(Long id);
}
