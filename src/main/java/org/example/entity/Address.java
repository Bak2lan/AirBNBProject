package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "address_gen")
    @SequenceGenerator(name = "address_gen",
            sequenceName = "address_seq",
            allocationSize = 1)
    private Long id;
    private String city;
    private String region;
    @Column(unique = true)
    private String street;
    @OneToOne(mappedBy = "address")
    @ToString.Exclude
    private Agency agency;

    public Address(String city, String region, String street) {
        this.city = city;
        this.region = region;
        this.street = street;
    }
}
