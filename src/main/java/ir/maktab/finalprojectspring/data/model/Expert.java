package ir.maktab.finalprojectspring.data.model;


import ir.maktab.finalprojectspring.data.model.enumeration.ExpertCondition;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)


public class Expert extends Person {

    @Enumerated(value = EnumType.STRING)
    ExpertCondition expertcondition;

    @ManyToMany(fetch = FetchType.EAGER)
    List<SubService> subServiceList = new ArrayList<>();

    @OneToMany
    List<Review> reviewList = new ArrayList<>();

    int score;

    @Lob
    byte[] expertImage;

}