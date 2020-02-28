package com.init.repo;

import com.init.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepo extends JpaRepository<Brand,Long> {

    @Query("select b from Brand b where  lower( b.name) =?1")
    Brand getBrandByName(String name);

    @Query("select b from Brand b where  lower( b.name) like %?1%")
    List<Brand> searchBrand(String name);

    @Query("select b from Brand b where  b.deleted =?1")
    List<Brand> findAllBrand(boolean isDeleted);
    Brand getBrandById(Long id);
}
