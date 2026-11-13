package fr.esgi.banque.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculFraisTest {

	private final CalculFrais calcul = new CalculFrais();

	// F1 : le titulaire doit avoir 18 ans ou plus, et le solde moyen est obligatoire

	@ParameterizedTest(name = "un titulaire de {0} ans est refusé")
	@CsvSource({
			"17",
			"0"
	})
	void UnTitulaireMineurDoitLeverUneException(int age) {
		// Arrange
		BigDecimal soldeMoyen = new BigDecimal("500");

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () -> calcul.fraisMensuels(age, soldeMoyen, false));
	}

	@Test
	@DisplayName("F1 : un titulaire de 18 ans est accepté")
	void UnTitulaireDe18AnsEstAccepte() {
		// Arrange
		BigDecimal soldeMoyen = new BigDecimal("500");

		// Act
		BigDecimal frais = calcul.fraisMensuels(18, soldeMoyen, false);

		// Assert
		assertNotNull(frais);
	}

	@Test
	@DisplayName("F1 : un solde moyen null lève une exception")
	void UnSoldeMoyenNullDoitLeverUneException() {
		// Act + Assert
		assertThrows(IllegalArgumentException.class, () -> calcul.fraisMensuels(30, null, false));
	}

	// F2 : les frais de tenue de compte sont de 2,00 € par mois

	@Test
	@DisplayName("F2 : les frais de tenue de compte sont de 2,00 € par mois")
	void LesFraisDeTenueDeCompteSontDe2Euros() {
		// Arrange
		int age = 30;
		BigDecimal soldeMoyen = new BigDecimal("500");

		// Act
		BigDecimal frais = calcul.fraisMensuels(age, soldeMoyen, false);

		// Assert
		assertThat(frais).isEqualByComparingTo("2.00");
	}

}
