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

    @OneToOne(mappedBy = "user")
    private AccessCard accessCard;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;


}
