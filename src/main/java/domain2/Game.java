package domain2;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "games")
@Data
@ToString(exclude = {"cabinets"})
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "release_year", nullable = false)
    private LocalDate releaseYear;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "game")
    List<Cabinet> cabinets;


}
