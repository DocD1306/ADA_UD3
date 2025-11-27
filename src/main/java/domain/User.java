package domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "email", nullable = false, unique = true, length = 190)
    private String email;

    @Column(name = "fullName", nullable = false, length = 150)
    private String fullName;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private AccessCard accessCard;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Booking> bookings;


}
