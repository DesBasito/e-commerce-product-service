package kg.manurov.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Category {
    @Id
    @GeneratedValue
    Integer id;
    String name;
    String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    List<Product> products;
}
