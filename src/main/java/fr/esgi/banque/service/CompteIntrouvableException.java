package fr.esgi.banque.service;

public class CompteIntrouvableException extends RuntimeException {

	public CompteIntrouvableException(String numeroCompte) {
		super("Le compte " + numeroCompte + " est introuvable");
	}

}
