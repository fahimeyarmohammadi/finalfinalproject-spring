package ir.maktab.finalprojectspring.data.model;


import ir.maktab.finalprojectspring.enumeration.OrderCondition;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)


public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Double proposedPrice;

    @Column(nullable = false)
    String description;

    @Temporal(value = TemporalType.TIMESTAMP)
    Date preferDate;

    @Enumerated(value = EnumType.STRING)
    OrderCondition ordercondition;

    @OneToOne
    SubService subService;

    @Temporal(value = TemporalType.TIMESTAMP)
    Date doneDate;

    @OneToOne(cascade =CascadeType.MERGE)
    Address address;


}
