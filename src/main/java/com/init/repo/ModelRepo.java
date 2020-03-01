package com.init.repo;

import com.init.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepo extends JpaRepository<CarModel,Long> {

    CarModel getModelById(Long id);

    @Query("select m from CarModel m where  lower( m.name) =?1 and m.condition =?2")
    CarModel getModelByName(String name,String condition);

    @Query("select m from CarModel m where m.carBrand.id = ?1 or m.carBrand.name =?2 and m.deleted =?3")
    List<CarModel> findAllByBrand(Long id, String brandName, boolean deleted);

    @Query("select m from CarModel m where (lower( m.carBrand.name) like %?1% or lower(m.name) like %?1%) and m.deleted =?2")
    List<CarModel> search(String brandName, boolean isDeleted);

    @Query("select m from CarModel m where  m.deleted =?1")
    List<CarModel> findAllModel(boolean isDeleted);
//    @Query("select count(m) from CarModel m where m.carBrand.id=?1 and m.name =?2")
    long countByCarBrandIdAndNameAndCondition(Long brandId, String modelName,String condition);
}
