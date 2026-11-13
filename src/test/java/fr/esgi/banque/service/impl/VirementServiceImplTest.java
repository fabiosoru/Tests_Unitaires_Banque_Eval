package fr.esgi.banque.service.impl;

import fr.esgi.banque.service.CompteRepository;
import fr.esgi.banque.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class VirementServiceImplTest {

	@Mock
	CompteRepository repository;
	@Mock
	NotificationService notifications;

	// Vendredi 13 novembre 2026
	Clock horloge = Clock.fixed(Instant.parse("2026-11-13T10:00:00Z"), ZoneOffset.UTC);

	VirementServiceImpl service;

	@BeforeEach
	void setUp() {
		service = new VirementServiceImpl(repository, notifications, horloge);
	}

	// V1 : un montant null, nul ou négatif est refusé

	@Test
	@DisplayName("V1 : un virement d'un montant null est refusé")
	void UnVirementDUnMontantNullDoitLeverUneException() {
		// Act + Assert
		assertThrows(IllegalArgumentException.class, () -> service.virer("FR001", "FR002", null));
	}

	@ParameterizedTest(name = "un virement de {0} € est refusé")
	@CsvSource({
			"0",
			"-0.01",
			"-100"
	})
	void UnVirementDUnMontantNulOuNegatifDoitLeverUneException(BigDecimal montant) {
		// Act + Assert
		assertThrows(IllegalArgumentException.class, () -> service.virer("FR001", "FR002", montant));
	}

}
