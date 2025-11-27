package dto2;

import domain2.Arcade;

import java.math.BigDecimal;

public record ArcadeEstimatedIncome(Arcade arcade, BigDecimal estimatedIncome) {
}
