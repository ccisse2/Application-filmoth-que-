package fr.eni.tp.filmotheque.bo;

import java.io.Serializable;
import java.util.Objects;

public class Genre implements Serializable {
	/**
	 * Numéro de sérialisation
	 */
	private static final long serialVersionUID = 1L;
	//Attributs
	private long id;
	private String titre;

	//Default Constructor
	public Genre() {
	}

	//Constructeurs avec paramètres
	public Genre(String titre) {
		this.titre = titre;
	}

	public Genre(long id, String titre) {
		this.id = id;
		this.titre = titre;
	}
	
	//Getter + Setter
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	//Equals et hashCode pour comparer 2 instances de la classe selon leur 'id'
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
		Genre other = (Genre) obj;
		return id == other.id;
	}

	//Auto-génération du toString pour uniformiser les traces
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(titre);
		builder.append(" (");
		builder.append(id);
		builder.append(")");
		return builder.toString();
	}

}
