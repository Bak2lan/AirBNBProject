package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.enums.Gender;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "customer_gen")
    @SequenceGenerator(name = "customer_gen",
            sequenceName = "customer_seq",
            allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private String nationality;
    @OneToMany(mappedBy = "customer",cascade ={
            CascadeType.PERSIST,
            CascadeType.REMOVE
    },fetch = FetchType.EAGER)
    private List<Rent_Info >rentInfos= new ArrayList<>();

    public Customer(String firstName, String lastName, String email, LocalDate dateOfBirth, Gender gender, String nationality, List<Rent_Info> rentInfos) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.rentInfos = rentInfos;
    }

    public Customer(String firstName, String lastName, String email, LocalDate dateOfBirth, Gender gender, String nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
    }
}
