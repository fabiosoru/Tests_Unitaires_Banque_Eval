package fr.esgi.banque.service.impl;

import fr.esgi.banque.business.Compte;
import fr.esgi.banque.service.CompteIntrouvableException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VirementServiceImplTest {

	private static final String NUMERO_SOURCE = "FR001";
	private static final String NUMERO_DESTINATION = "FR002";

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
		assertThrows(IllegalArgumentException.class, () -> service.virer(NUMERO_SOURCE, NUMERO_DESTINATION, null));
	}

	@ParameterizedTest(name = "un virement de {0} € est refusé")
	@CsvSource({
			"0",
			"-0.01",
			"-100"
	})
	void UnVirementDUnMontantNulOuNegatifDoitLeverUneException(BigDecimal montant) {
		// Act + Assert
		assertThrows(IllegalArgumentException.class, () -> service.virer(NUMERO_SOURCE, NUMERO_DESTINATION, montant));
	}

	// V2 : un virement vers le même compte est refusé

	@Test
	@DisplayName("V2 : un virement vers le même compte est refusé")
	void UnVirementVersLeMemeCompteDoitLeverUneException() {
		// Arrange
		BigDecimal montant = new BigDecimal("100");

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () -> service.virer(NUMERO_SOURCE, NUMERO_SOURCE, montant));
	}

	// V3 : si l'un des deux comptes n'existe pas, uen exception CompteIntrouvableException est levée

	@Test
	@DisplayName("V3 : un virement depuis un compte source inexistant est refusé")
	void UnVirementDepuisUnCompteSourceInexistantDoitLeverUneException() {
		// Arrange
		BigDecimal montant = new BigDecimal("100");
		when(repository.trouver(NUMERO_SOURCE)).thenReturn(Optional.empty());

		// Act + Assert
		assertThrows(CompteIntrouvableException.class, () -> service.virer(NUMERO_SOURCE, NUMERO_DESTINATION, montant));
	}

	@Test
	@DisplayName("V3 : un virement vers un compte destination inexistant est refusé")
	void UnVirementVersUnCompteDestinationInexistantDoitLeverUneException() {
		// Arrange
		BigDecimal montant = new BigDecimal("100");
		Compte source = new Compte(NUMERO_SOURCE, "Alice", new BigDecimal("500"), BigDecimal.ZERO);
		when(repository.trouver(NUMERO_SOURCE)).thenReturn(Optional.of(source));
		when(repository.trouver(NUMERO_DESTINATION)).thenReturn(Optional.empty());

		// Act + Assert
		assertThrows(CompteIntrouvableException.class, () -> service.virer(NUMERO_SOURCE, NUMERO_DESTINATION, montant));
	}

}
