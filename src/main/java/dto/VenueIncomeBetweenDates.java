package dto;

import domain.Venue;

import java.math.BigDecimal;

public record VenueIncomeBetweenDates(Venue venue, BigDecimal totalPrice) {
}
