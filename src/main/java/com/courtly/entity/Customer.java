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
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(
        name = Customer.FIND_BY_PHONE_NUMBER,
        query = "SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber AND c.deleted IS FALSE"
)
public class Customer extends BaseEntity{

    public static final String FIND_BY_PHONE_NUMBER = "Customer.findByPhoneNumber";

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;
}
