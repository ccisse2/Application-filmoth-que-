package fr.eni.tp.filmotheque.bo;

import java.io.Serializable;
import java.util.Objects;

public abstract class Personne implements Serializable {
	/**
	 * Numéro de sérialisation
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String nom;
	private String prenom;

	public Personne() {
	}

	public Personne(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}

	public Personne(long id, String nom, String prenom) {
		this(nom, prenom);// Appel du constructeur précédent
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(prenom);
		builder.append(" ");
		builder.append(nom);
		builder.append(" (");
		builder.append(id);
		builder.append(") ");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personne other = (Personne) obj;
		return id == other.id;
	}

}
