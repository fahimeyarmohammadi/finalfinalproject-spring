package ir.maktab.finalprojectspring.data.model;


import ir.maktab.finalprojectspring.data.enumeration.ExpertCondition;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)


public class Expert extends Person {

    @Enumerated(value = EnumType.STRING)
    ExpertCondition expertCondition;

    @ManyToMany
    List<SubService> subServiceList = new ArrayList<>();

    @OneToMany
    List<Review> reviewList = new ArrayList<>();

    int score;

   // @Lob
    @Column(length = 300000)
    byte[] image;

    String path;

}