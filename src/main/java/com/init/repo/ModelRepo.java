package com.init.repo;

import com.init.model.Brand;
import com.init.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepo extends JpaRepository<Model,Long> {

    Model getModelById(Long id);

    @Query("select m from Model m where  lower( m.name) =?1")
    Model getModelByName(String name);

    @Query("select m from Model m where m.brand.id = ?1 or m.brand.name =?2 and m.deleted =?3")
    List<Model> findAllByBrand(Long id, String brandName,boolean deleted);

    @Query("select m from Model m where (lower( m.brand.name) like %?1% or lower(m.name) like %?1%) and m.deleted =?2")
    List<Model> search(String brandName,boolean isDeleted);

    @Query("select m from Model m where  m.deleted =?2")
    List<Model> findAllModel();
}
