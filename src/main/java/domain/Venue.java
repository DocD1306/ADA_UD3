package domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "venues")
@Data
//@NamedQuery(
//        name="Venue.findByCity",
//        query = "FROM Venue s WHERE s.city = :city"
//)
@NamedQuery(
        name = "Venue.findByCity",
        query = "SELECT v FROM Venue v WHERE v.city = :city"
)
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false, length = 250)
    private String address;

    @Column(name = "city", nullable = false, length = 120)
    private String city;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @OneToMany(mappedBy = "venue", fetch = FetchType.LAZY, orphanRemoval = false)
    @ToString.Exclude
    private List<Space> spaces;

}
