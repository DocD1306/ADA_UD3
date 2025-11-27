package domain2;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cabinets")
@Data
public class Cabinet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slug", unique = true, nullable = false)
    private String slug;

    @Column(name = "build_year", nullable = false)
    private LocalDate bulidYear;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "hourly_cost", nullable = false)
    private BigDecimal hourlyCost;

    @ManyToOne()
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "arcade_id")
    private Arcade arcade;

    @ManyToMany
    @JoinTable(
            name = "cabinet_tag",
            joinColumns = @JoinColumn(name = "cabinet_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @ToString.Exclude
    private List<Tag> tags;


}
