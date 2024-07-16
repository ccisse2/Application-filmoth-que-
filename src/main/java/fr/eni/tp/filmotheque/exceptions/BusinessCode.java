package fr.eni.tp.filmotheque.exceptions;

public class BusinessCode {

	// Clefs de validation des BO
	public static final String VALIDATION_FILM_NULL = "validation.film.null";
	public static final String VALIDATION_FILM_TITRE_BLANK = "validation.film.titre.blank";
	public static final String VALIDATION_FILM_TITRE_LENGTH = "validation.film.titre.length";
	public static final String VALIDATION_FILM_ANNEE = "validation.film.annee";
	public static final String VALIDATION_FILM_DUREE = "validation.film.duree";
	public static final String VALIDATION_FILM_SYNOPSIS_BLANK = "validation.film.synopsis.blank";
	public static final String VALIDATION_FILM_SYNOPSIS_LENGTH = "validation.film.synopsis.length";
	public static final String VALIDATION_FILM_GENRE_NULL = "validation.film.genre.null";
	public static final String VALIDATION_FILM_GENRE_ID_INCONNU = "validation.film.genre.id.inconnu";
	public static final String VALIDATION_FILM_REALISATEUR_NULL = "validation.film.realisateur.null";
	public static final String VALIDATION_FILM_REALISATEUR_ID_INCONNU = "validation.film.realisateur.id.inconnu";
	public static final String VALIDATION_FILM_ACTEUR_ID_INCONNU = "validation.film.acteur.id.inconnu";	
	public static final String VALIDATION_FILM_UNIQUE = "validation.film.unique";
	
	public static final String BLL_FILM_CREER_ERREUR="bll.film.creer.erreur";
	public static final String BLL_AVIS_CREER_ERREUR="bll.avis.creer.erreur";
	
	public static final String VALIDATION_MEMBRE_ADMIN = "validation.membre.admin";
	public static final String VALIDATION_MEMBRE = "validation.membre";
	
	
	
	public static final String VALIDATION_AVIS_NULL = "validation.avis.null";
	public static final String VALIDATION_AVIS_NOTE = "validation.avis.note";
	public static final String VALIDATION_AVIS_COMMENTAIRE_BLANK = "validation.avis.commentaire.blank";
	public static final String VALIDATION_AVIS_COMMENTAIRE_LENGTH = "validation.avis.commentaire.length";
	public static final String VALIDATION_AVIS_MEMBRE_NULL = "validation.avis.membre.null";
	public static final String VALIDATION_AVIS_MEMBRE_ID_INCONNU = "validation.avis.membre.id.inconnu";
	public static final String VALIDATION_AVIS_MEMBRE_INCONNU = "validation.avis.membre.inconnu";
	public static final String VALIDATION_AVIS_UNIQUE = "validation.avis.unique";

	public static final String VALIDATION_FILM_INCONNU = "validation.film.inconnu";
	public static final String VALIDATION_FILM_ID_INCONNU = "validation.film.id.inconnu";
}
