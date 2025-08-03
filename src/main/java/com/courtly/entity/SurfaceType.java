package com.courtly.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "surface_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurfaceType extends BaseEntity{

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "price")
    private Integer price;
}
