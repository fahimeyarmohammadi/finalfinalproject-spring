package ir.maktab.finalprojectspring.data.model;


import ir.maktab.finalprojectspring.data.enumeration.OrderCondition;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

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
    OrderCondition orderCondition;

    @OneToOne
    SubService subService;

    @Temporal(value = TemporalType.TIMESTAMP)
    Date doneDate;

    @OneToOne(cascade =CascadeType.ALL)
    Address address;
}
