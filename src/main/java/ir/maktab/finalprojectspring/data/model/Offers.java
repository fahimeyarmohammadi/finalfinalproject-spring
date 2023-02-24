package ir.maktab.finalprojectspring.data.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Duration;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class Offers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    Date offerDate;

    @Column(nullable = false)
    Double offerPrice;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false)
    Date startWork;

    @OneToOne
    Expert expert;

    @Column(nullable = false)
    Duration duration;

    @ManyToOne
    CustomerOrder customerOrder;

    boolean acceptOffer;
}
