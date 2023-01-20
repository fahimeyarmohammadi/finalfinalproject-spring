package ir.maktab.finalprojectspring.data.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.Date;

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
    ORDERCONDITION ordercondition;

    @OneToOne
    SubService subService;

    @Temporal(value = TemporalType.TIMESTAMP)
    Date doneDate;

    @OneToOne(cascade =CascadeType.MERGE)
    Address address;

}
