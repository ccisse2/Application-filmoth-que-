package fr.eni.tp.filmotheque.bo;

import java.util.Objects;

public class Membre extends Personne {
	/**
	 * Numéro de sérialisation
	 */
	private static final long serialVersionUID = 1L;
	private String pseudo;
	private String motDePasse;
	private boolean admin;

	public Membre() {
	}

	public Membre(String nom, String prenom, String pseudo, String motDePasse) {
		super(nom, prenom);
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
	}

	public Membre(long id, String nom, String prenom, String pseudo, String motDePasse) {
		super(id, nom, prenom);
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append(" - Membre (pseudo=");
		builder.append(pseudo);
		builder.append(", admin=");
		builder.append(admin);
		builder.append(") ");
		return builder.toString();
	}

	/**
	 * Pour valider qu'un membre en session correspond à celui en base. 
	 * Redéfinition de la méthode equals sur toutes les propriétés sauf motDePasse.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(admin, pseudo);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Membre other = (Membre) obj;
		return admin == other.admin && Objects.equals(pseudo, other.pseudo);
	}

}
