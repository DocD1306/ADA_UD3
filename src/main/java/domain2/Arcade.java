package domain2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "arcades")
@Data
@NamedQuery(
        name = "Arcade.arcadeByName",
        query = "FROM Arcade WHERE name = :name"
)
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"cabinets"})
public class Arcade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "address", unique = true, nullable = false)
    private String address;

    @OneToMany(mappedBy = "arcade", orphanRemoval = false, fetch = FetchType.LAZY)
    List<Cabinet> cabinets;

    public Arcade(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Arcade(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
