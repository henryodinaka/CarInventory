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
public class Model {
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
    private Brand brand;


    public Model buildModel(Model model, ModelRequest request, Brand brand, BigDecimal price, Condition condition) {
        model.setBrand(brand);
        model.setCondition(condition.name());
        model.setExterior(Validation.validData(request.getExterior()) ? request.getExterior() : model.getExterior());
        model.setInterior(Validation.validData(request.getInterior()) ? request.getInterior() : model.getInterior());
        model.setName(Validation.validData(request.getName()) ? request.getName() : model.getName());
        model.setPrice(price != null ? price : model.getPrice());
        model.setQuantityInStock(request.getQuantityInStock() > 0 ? request.getQuantityInStock() : model.getQuantityInStock());
        model.setYear(Validation.validData(request.getYear()) ? request.getYear() : model.getYear());
        return model;
    }

}
