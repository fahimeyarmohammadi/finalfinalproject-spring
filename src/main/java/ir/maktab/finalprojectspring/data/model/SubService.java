package ir.maktab.finalprojectspring.data.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;




@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString



public class SubService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String subName;
    Double basePrice;
    String description;

    @ManyToOne
    BaseService baseService;

}