package domain2;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Data
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "duration_sec", nullable = false)
    private long durationSec;

    @Column(name = "score", nullable = false)
    private long score;

    @Enumerated(EnumType.STRING)
    @Column(name = "result", nullable = false)
    private Result result;

    @Column(name = "credits_used", nullable = false)
    private long creditsUsed;

    @ManyToOne
    @JoinColumn(name = "rfid_card_id", nullable = false)
    private RfidCard rfidCard;

    @ManyToOne
    @JoinColumn(name = "cabinet_id", nullable = false)
    private Cabinet cabinet;


}
