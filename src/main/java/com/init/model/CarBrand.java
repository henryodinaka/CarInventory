package com.init.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CarBrand {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private boolean deleted;
}
