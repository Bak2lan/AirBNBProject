package org.example.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
@Entity
@Table(name = "rent_infos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Rent_Info {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "rent_info_gen")
    @SequenceGenerator(name = "rent_info_gen",
            sequenceName = "rent_info_seq",
            allocationSize = 1)
    private Long id;
    @Column(name = "check_in")
    private LocalDate checkIn;
    @Column(name = "check_out")
    private LocalDate checkOut;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private Customer customer;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Owner owner;
    @OneToOne(cascade = CascadeType.PERSIST)
    private House house;

    public Rent_Info(LocalDate checkIn, LocalDate checkOut, House house) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.house = house;
    }
}
