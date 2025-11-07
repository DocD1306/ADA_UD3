package domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "spaces")
@Data
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "code", length = 60, nullable = false)
    private String code;

    @Column(name = "hourlyPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyPrice;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private SpaceType type;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @ManyToMany
    @JoinTable(
            name = "space_tag",
            joinColumns = @JoinColumn(name = "space_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @OneToMany(mappedBy = "space")
    private List<Booking> bookings;

}


