package com.courtly.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "surface_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(
        name = SurfaceType.FIND_BY_NAME,
        query = "SELECT s FROM SurfaceType s WHERE s.name = :name AND s.deleted IS FALSE"
)
public class SurfaceType extends BaseEntity{
    public static final String FIND_BY_NAME = "SurfaceType.findByName";

    @Column(name = "name")
    private String name;

    @Column(name = "first_tariff")
    private Double firstTariff;

    @Column(name = "second_tariff")
    private Double secondTariff;

    @Column(name = "third_tariff")
    private Double thirdTariff;

    @Column(name = "fourth_tariff")
    private Double fourthTariff;
}
