package dto;

import domain.Space;

import java.math.BigDecimal;

public record SpaceConfirmedIncome(Space space, BigDecimal totalConfirmedIncome) {
}
