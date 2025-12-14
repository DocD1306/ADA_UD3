package domain2;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rfid_cards")
@ToString(exclude = "player")
public class RfidCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "u_id", unique = true, nullable = false)
    private String uId;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "player_id", unique = true, nullable = false)
    private Player player;

}
