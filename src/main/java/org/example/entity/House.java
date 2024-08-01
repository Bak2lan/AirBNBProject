package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.enums.HouseType;

import java.math.BigDecimal;
@Entity
@Table(name = "houses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "house_gen")
    @SequenceGenerator(name = "house_gen",
            sequenceName = "house_seq",
            allocationSize = 1)
    private Long id;
    @Column(name = "house_type")
    @Enumerated(value = EnumType.STRING)
    private HouseType houseType;
    private BigDecimal price;
    private double rating;
    private String description;
    private int room;
    private boolean furniture;
    @OneToOne(cascade ={
            CascadeType.REMOVE,
            CascadeType.PERSIST
    })
    private Address address;
    @ManyToOne(cascade = {
            CascadeType.PERSIST
    })
    private Owner owner;

    public House(HouseType houseType, BigDecimal price, double rating, String description, int room, boolean furniture, Address address) {
        this.houseType = houseType;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.room = room;
        this.furniture = furniture;
        this.address = address;
    }

    public House(HouseType houseType, BigDecimal price, double rating, String description, int room, boolean furniture, Address address, Owner owner) {
        this.houseType = houseType;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.room = room;
        this.furniture = furniture;
        this.address = address;
        this.owner = owner;
    }
}
