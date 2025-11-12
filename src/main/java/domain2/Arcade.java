package domain2;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "arcades")
@Data
public class Arcade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "address", unique = true, nullable = false)
    private String address;

}
