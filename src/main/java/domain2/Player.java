package domain2;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "players")
@Data
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname",unique = true , nullable = false)
    private String nickname;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private RfidCard rfidCard;


}
