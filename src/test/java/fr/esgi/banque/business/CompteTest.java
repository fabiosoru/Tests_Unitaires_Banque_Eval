package fr.esgi.banque.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test d'exemple fourni pour vérifier que le projet est bien configuré.
 * À compléter (partie A).
 */
class CompteTest {

	@Test
	void shouldIncreaseBalanceWhenCrediting() {
		// Arrange
		Compte compte = new Compte("FR001", "Alice", new BigDecimal("100.00"), new BigDecimal("200"));

		// Act
		compte.crediter(new BigDecimal("50.00"));

		// Assert
		assertThat(compte.getSolde()).isEqualByComparingTo("150.00");
	}

	// C1 : numéro et titulaire obligatoires

	@Test
	@DisplayName("C1 : un compte avec un numéro et un titulaire valides est créé")
	void UnCompteAvecNumeroEtTitulaireValidesEstCree() {
		// Arrange
		String numero = "FR001";
		String titulaire = "Alice";

		// Act
		Compte compte = new Compte(numero, titulaire, new BigDecimal("100.00"), new BigDecimal("200"));

		// Assert
		assertEquals(numero, compte.getNumero(), "Numéros non concordants");
		assertEquals(titulaire, compte.getTitulaire(), "Titulaires non concordants");
	}

	@Test
	@DisplayName("C1 : un numéro null lève une exception")
	void UnNumeroNullDoitLeverUneException() {
		// Arrange
		BigDecimal soldeInitial = new BigDecimal("100.00");
		BigDecimal decouvertAutorise = new BigDecimal("200");

		// Act + Assert
		assertThrows(IllegalArgumentException.class,
				() -> new Compte(null, "Alice", soldeInitial, decouvertAutorise));
	}

	@Test
	@DisplayName("C1 : un numéro vide lève une exception")
	void UnNumeroVideDoitLeverUneException() {
		// Arrange
		BigDecimal soldeInitial = new BigDecimal("100.00");
		BigDecimal decouvertAutorise = new BigDecimal("200");

		// Act + Assert
		assertThrows(IllegalArgumentException.class,
				() -> new Compte("", "Alice", soldeInitial, decouvertAutorise));
	}

	@Test
	@DisplayName("C1 : un numéro composé d'espaces lève une exception")
	void UnNumeroAvecEspacesDoitLeverUneException() {
		// Arrange
		BigDecimal soldeInitial = new BigDecimal("100.00");
		BigDecimal decouvertAutorise = new BigDecimal("200");

		// Act + Assert
		assertThrows(IllegalArgumentException.class,
				() -> new Compte("   ", "Alice", soldeInitial, decouvertAutorise));
	}

	@Test
	@DisplayName("C1 : un titulaire null lève une exception")
	void UnTitulaireNullDoitLeverUneException() {
		// Arrange
		BigDecimal soldeInitial = new BigDecimal("100.00");
		BigDecimal decouvertAutorise = new BigDecimal("200");

		// Act + Assert
		assertThrows(IllegalArgumentException.class,
				() -> new Compte("FR001", null, soldeInitial, decouvertAutorise));
	}

	@Test
	@DisplayName("C1 : un titulaire vide lève une exception")
	void UnTitulaireVideDoitLeverUneException() {
		// Arrange
		BigDecimal soldeInitial = new BigDecimal("100.00");
		BigDecimal decouvertAutorise = new BigDecimal("200");

		// Act + Assert
		assertThrows(IllegalArgumentException.class,
				() -> new Compte("FR001", "", soldeInitial, decouvertAutorise));
	}

	@Test
	@DisplayName("C1 : un titulaire composé d'espaces lève une exception")
	void UnTitulaireAvecEspacesDoitLeverUneException() {
		// Arrange
		BigDecimal soldeInitial = new BigDecimal("100.00");
		BigDecimal decouvertAutorise = new BigDecimal("200");

		// Act + Assert
		assertThrows(IllegalArgumentException.class,
				() -> new Compte("FR001", "   ", soldeInitial, decouvertAutorise));
	}

}
