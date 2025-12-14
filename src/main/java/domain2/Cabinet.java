package domain2;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cabinets")
@Data
@ToString(exclude = {"tags", "matches"})
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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "arcade_id", nullable = false)
    private Arcade arcade;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cabinet_tag",
            joinColumns = @JoinColumn(name = "cabinet_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @OneToMany(mappedBy = "cabinet", fetch = FetchType.LAZY)
    private Set<Match> matches = new HashSet<>();


}
