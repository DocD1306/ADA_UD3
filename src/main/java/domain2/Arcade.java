package domain2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "arcades")
@Data
@NamedQuery(
        name = "Arcade.arcadeByName",
        query = "FROM Arcade WHERE name = :name"
)
@NoArgsConstructor
@AllArgsConstructor
public class Arcade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "address", unique = true, nullable = false)
    private String address;

}
