package com.init.model;

import com.init.dto.ModelRequest;
import com.init.enums.Condition;
import com.init.utils.Validation;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CarModel {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String year;
    private String exterior;
    private String interior;
    private Integer quantityInStock;
    private String condition;
    private BigDecimal price;
    private boolean deleted;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "ModelBrand", joinColumns = @JoinColumn(name = "ModelId", referencedColumnName = "id"), inverseJoinColumns =
    @JoinColumn(name = "BrandId", referencedColumnName = "id"))
    private CarBrand carBrand;


    public CarModel buildModel(CarModel carModel, ModelRequest request, CarBrand carBrand, BigDecimal price, Condition condition) {
        carModel.setCarBrand(carBrand);
        carModel.setCondition(condition.name());
        carModel.setExterior(Validation.validData(request.getExterior()) ? request.getExterior() : carModel.getExterior());
        carModel.setInterior(Validation.validData(request.getInterior()) ? request.getInterior() : carModel.getInterior());
        carModel.setName(Validation.validData(request.getName()) ? request.getName() : carModel.getName());
        carModel.setPrice(price != null ? price : carModel.getPrice());
        carModel.setQuantityInStock(request.getQuantityInStock() > 0 ? request.getQuantityInStock() : carModel.getQuantityInStock());
        carModel.setYear(Validation.validData(request.getYear()) ? request.getYear() : carModel.getYear());
        return carModel;
    }

}
