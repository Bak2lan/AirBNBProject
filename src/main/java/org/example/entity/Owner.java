package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.enums.Gender;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "owners")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "owner_gen")
    @SequenceGenerator(name = "owner_gen",
            sequenceName = "owner_seq",
            allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @ToString.Exclude
    @ManyToMany(mappedBy = "owners",cascade = {
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    private List<Agency>agencies;
    @ToString.Exclude
    @OneToMany(mappedBy = "owner",cascade = {
            CascadeType.PERSIST,
            CascadeType.REMOVE
    })
    private List<House>houses;
    @ToString.Exclude
    @OneToMany(mappedBy = "owner",cascade = {
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    private List<Rent_Info>rentInfos;

    public Owner(String firstName, String lastName, String email, LocalDate dateOfBirth, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public Owner(String firstName, String lastName, String email, LocalDate dateOfBirth, Gender gender, List<House> houses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.houses = houses;
    }
}
