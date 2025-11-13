package domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NamedQuery(
        name = "Booking.confirmedBookingsByVenueAndRange",
        query = """
                SELECT b FROM Booking b JOIN b.space s JOIN s.venue v 
                WHERE v.name = :venueName 
                AND b.status = 'CONFIRMED'
                AND b.startTime BETWEEN :startTime AND :endTime 
                AND b.endTime BETWEEN :startTime AND :endTime"""
)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "space_id")
    private Space space;

}

