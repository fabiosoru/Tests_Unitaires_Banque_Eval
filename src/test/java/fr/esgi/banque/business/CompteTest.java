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

	// C2 : solde initial et découvert autorisé obligatoires, découvert positif ou nul

	@Test
	@DisplayName("C2 : un solde initial null lève une exception")
	void UnSoldeInitialNullDoitLeverUneException() {
		// Arrange
		BigDecimal decouvertAutorise = new BigDecimal("200");

		// Act + Assert
		assertThrows(IllegalArgumentException.class,
				() -> new Compte("FR001", "Alice", null, decouvertAutorise));
	}

	@Test
	@DisplayName("C2 : un découvert autorisé null lève une exception")
	void UnDecouvertAutoriseNullDoitLeverUneException() {
		// Arrange
		BigDecimal soldeInitial = new BigDecimal("100.00");

		// Act + Assert
		assertThrows(IllegalArgumentException.class,
				() -> new Compte("FR001", "Alice", soldeInitial, null));
	}

	@Test
	@DisplayName("C2 : un découvert autorisé négatif lève une exception")
	void UnDecouvertAutoriseNegatifDoitLeverUneException() {
		// Arrange
		BigDecimal soldeInitial = new BigDecimal("100.00");
		BigDecimal decouvertAutorise = new BigDecimal("-0.01");

		// Act + Assert
		assertThrows(IllegalArgumentException.class,
				() -> new Compte("FR001", "Alice", soldeInitial, decouvertAutorise));
	}

	@Test
	@DisplayName("C2 : un découvert autorisé à zéro est accepté")
	void UnDecouvertAutoriseAZeroEstAccepte() {
		// Arrange
		BigDecimal soldeInitial = new BigDecimal("100.00");
		BigDecimal decouvertAutorise = BigDecimal.ZERO;

		// Act
		Compte compte = new Compte("FR001", "Alice", soldeInitial, decouvertAutorise);

		// Assert
		assertThat(compte.getDecouvertAutorise()).isEqualByComparingTo("0");
	}

	@Test
	@DisplayName("C2 : le solde initial et le découvert autorisé sont bien enregistrés")
	void LeSoldeInitialEtLeDecouvertAutoriseSontEnregistres() {
		// Arrange
		BigDecimal soldeInitial = new BigDecimal("100.00");
		BigDecimal decouvertAutorise = new BigDecimal("200");

		// Act
		Compte compte = new Compte("FR001", "Alice", soldeInitial, decouvertAutorise);

		// Assert
		assertThat(compte.getSolde()).isEqualByComparingTo("100.00");
		assertThat(compte.getDecouvertAutorise()).isEqualByComparingTo("200");
	}

	// C3 : crediter n'accepte qu'un montant strictement positif

	@Test
	@DisplayName("C3 : créditer un montant null lève une exception")
	void CrediterUnMontantNullDoitLeverUneException() {
		// Arrange
		Compte compte = new Compte("FR001", "Alice", new BigDecimal("100.00"), new BigDecimal("200"));

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () -> compte.crediter(null));
	}

	@Test
	@DisplayName("C3 : créditer un montant négatif lève une exception")
	void CrediterUnMontantNegatifDoitLeverUneException() {
		// Arrange
		Compte compte = new Compte("FR001", "Alice", new BigDecimal("100.00"), new BigDecimal("200"));

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () -> compte.crediter(new BigDecimal("-10.00")));
	}

	@Test
	@DisplayName("C3 : créditer un montant nul lève une exception")
	void CrediterUnMontantNulDoitLeverUneException() {
		// Arrange
		Compte compte = new Compte("FR001", "Alice", new BigDecimal("100.00"), new BigDecimal("200"));

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () -> compte.crediter(BigDecimal.ZERO));
	}

	// C4 : debiter n'accepte qu'un montant strictement positif

	@Test
	@DisplayName("C4 : débiter un montant null lève une exception")
	void DebiterUnMontantNullDoitLeverUneException() {
		// Arrange
		Compte compte = new Compte("FR001", "Alice", new BigDecimal("100.00"), new BigDecimal("200"));

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () -> compte.debiter(null));
	}

	@Test
	@DisplayName("C4 : débiter un montant négatif lève une exception")
	void DebiterUnMontantNegatifDoitLeverUneException() {
		// Arrange
		Compte compte = new Compte("FR001", "Alice", new BigDecimal("100.00"), new BigDecimal("200"));

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () -> compte.debiter(new BigDecimal("-10.00")));
	}

	@Test
	@DisplayName("C4 : débiter un montant nul lève une exception")
	void DebiterUnMontantNulDoitLeverUneException() {
		// Arrange
		Compte compte = new Compte("FR001", "Alice", new BigDecimal("100.00"), new BigDecimal("200"));

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () -> compte.debiter(BigDecimal.ZERO));
	}

	@Test
	@DisplayName("C4 : débiter un montant positif diminue le solde")
	void DebiterUnMontantPositifDiminueLeSolde() {
		// Arrange
		Compte compte = new Compte("FR001", "Alice", new BigDecimal("100.00"), new BigDecimal("200"));

		// Act
		compte.debiter(new BigDecimal("30.00"));

		// Assert
		assertThat(compte.getSolde()).isEqualByComparingTo("70.00");
	}

}
