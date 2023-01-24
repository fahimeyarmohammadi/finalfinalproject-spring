package ir.maktab.finalprojectspring.data.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;


import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@FieldDefaults(level = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    Double credit;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String familyName;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    String username;

    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    Date date;
}