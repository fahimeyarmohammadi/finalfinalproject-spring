package ir.maktab.finalprojectspring.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


import java.util.ArrayList;
import java.util.List;


@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)


public class Customer extends Person {

    @OneToMany
    List<CustomerOrder> orderList = new ArrayList<>();
}
