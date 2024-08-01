package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;


@Entity
@Table(name = "agencies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "agency_gen")
    @SequenceGenerator(name = "agency_gen",
    sequenceName = "agency_seq",
    allocationSize = 1)
    private Long id ;
    private String name;
    @Column(name = "phone_number")
    @Pattern(regexp = "^\\(\\+996\\)\\d{9}$",message = "Phone number must be start with +996 and length must be include 13 symbol")
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;
    @ManyToMany
    @ToString.Exclude
    private List<Owner>owners;
    @OneToMany
    @ToString.Exclude
    private List<Rent_Info>rentInfos;

    public Agency(String name, String phoneNumber, Address address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}