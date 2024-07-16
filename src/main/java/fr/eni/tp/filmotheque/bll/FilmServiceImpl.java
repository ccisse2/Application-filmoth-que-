package fr.eni.tp.filmotheque.bll;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.tp.filmotheque.bo.Avis;
import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Membre;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dal.AvisDAO;
import fr.eni.tp.filmotheque.dal.FilmDAO;
import fr.eni.tp.filmotheque.dal.GenreDAO;
import fr.eni.tp.filmotheque.dal.MembreDAO;
import fr.eni.tp.filmotheque.dal.ParticipantDAO;
import fr.eni.tp.filmotheque.exceptions.BusinessCode;
import fr.eni.tp.filmotheque.exceptions.BusinessException;

@Service
@Primary
public class FilmServiceImpl implements FilmService {
	// Injection des Repository
	private FilmDAO filmDAO;
	private GenreDAO genreDAO;
	private ParticipantDAO participantDAO;
	private AvisDAO avisDAO;
	private MembreDAO membreDAO;

	public FilmServiceImpl(FilmDAO filmDAO, GenreDAO genreDAO, ParticipantDAO participantDAO, AvisDAO avisDAO,
			MembreDAO membreDAO) {
		this.filmDAO = filmDAO;
		this.genreDAO = genreDAO;
		this.participantDAO = participantDAO;
		this.avisDAO = avisDAO;
		this.membreDAO = membreDAO;

	}

	@Override
	public List<Film> consulterFilms() {
		// Il faut remonter la liste des films
		List<Film> films = filmDAO.findAll();

		// Puis si cette liste n'est pas vide -- pour chaque film associé son genre et
		// son réalisateur
		if (films != null) {
			films.forEach(f -> {
				chargerGenreEtRealisateur1Film(f);
			});
		}
		return films;
	}

	@Override
	public Film consulterFilmParId(long id) {
		// Récupération d'un film par son identifiant
		Film f = filmDAO.read(id);

		if (f != null) {
			// Charger le genre et le réalisateur du film
			chargerGenreEtRealisateur1Film(f);
			// Charger la liste des acteurs
			List<Participant> acteurs = participantDAO.findActeurs(id);
			if (acteurs != null) {
				f.setActeurs(acteurs);
			}

			// Charger la liste des avis s'il y en a
			List<Avis> avis = avisDAO.findByFilm(id);
			if (avis != null) {
				// Association avec le membre
				avis.forEach(a -> {
					chargerMembre1Avis(a);
				});
				f.setAvis(avis);
			}
		}
		return f;
	}

	/**
	 * Méthode privée pour centraliser l'association entre un film et son genre et
	 * réalisateur
	 * 
	 * @param film
	 */
	private void chargerGenreEtRealisateur1Film(Film f) {
		Participant realisateur = participantDAO.read(f.getRealisateur().getId());
		f.setRealisateur(realisateur);
		Genre genre = genreDAO.read(f.getGenre().getId());
		f.setGenre(genre);
	}

	@Override
	public List<Genre> consulterGenres() {
		return genreDAO.findAll();
	}

	@Override
	public List<Participant> consulterParticipants() {
		return participantDAO.findAll();
	}

	@Override
	public Genre consulterGenreParId(long id) {
		return genreDAO.read(id);
	}

	@Override
	public Participant consulterParticipantParId(long id) {
		return participantDAO.read(id);
	}

	@Override
	public Membre findByEmail(String email) {
		return membreDAO.read(email);
	}

	@Override
	@Transactional
	public void creerFilm(Film film) {
		// Validation du film avant sauvegarde
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validerFilm(film, be);
		isValid &= validerTitre(film.getTitre(), be);
		isValid &= validerFilmUnique(film.getTitre(), be);
		isValid &= validerAnnee(film.getAnnee(), be);
		isValid &= validerGenre(film.getGenre(), be);
		isValid &= validerRealisateur(film.getRealisateur(), be);
		isValid &= validerActeurs(film.getActeurs(), be);
		isValid &= validerDuree(film.getDuree(), be);
		isValid &= validerSynopsis(film.getSynopsis(), be);

		// La classe Film n'est pas directement reliée à la classe Membre
		// Seul le contrôleur peut valider qu'il y a un membre administrateur de
		// connecté
		// Il faudra gérer l'exception à son niveau
		if (isValid) {
			filmDAO.create(film);
			
			//TEST TRANSACTION - Ajout d'un acteur qui n'existe pas
//			Participant acteur = new Participant();
//			acteur.setId(0);
//			film.getActeurs().add(acteur);
			
			// Attention, Il faut gérer l'insertion en base des acteurs
			// Récupération de la clef auto-généré pour le film
			long idFilm = film.getId();
			film.getActeurs().forEach(p -> {
				participantDAO.createActeur(p.getId(), idFilm);
			});
		} else {
			throw be;
		}
	}

	@Override
	public String consulterTitreFilm(long id) {
		return filmDAO.findTitre(id);
	}

	@Override
	public void publierAvis(Avis avis, long idFilm) {
		// Validation des avis avant publication
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validerAvis(avis, be);
		isValid &= validerNote(avis.getNote(), be);
		isValid &= validerCommentaire(avis.getCommentaire(), be);
		isValid &= validerMembre(avis.getMembre(), be);
		isValid &= validerIdFilm(idFilm, be);
		isValid &= validerMembreAvisFilm(idFilm, avis.getMembre().getId(), be);

		if (isValid) {
			try {
				avisDAO.create(avis, idFilm);
			} catch (DataAccessException e) {// Exception de la couche DAL
				// Rollback automatique
				be.add(BusinessCode.BLL_AVIS_CREER_ERREUR);
				throw be;
			}

		} else {
			throw be;
		}
	}

	@Override
	public List<Avis> consulterAvis(long idFilm) {
		List<Avis> avis = avisDAO.findByFilm(idFilm);
		if (avis != null) {
			// Association avec le membre
			avis.forEach(a -> {
				chargerMembre1Avis(a);
			});
		}
		return avis;
	}

	/**
	 * Méthode privée pour centraliser l'association entre un avis et son membre
	 * 
	 * @param Avis
	 */
	private void chargerMembre1Avis(Avis a) {
		Membre membre = membreDAO.read(a.getMembre().getId());
		a.setMembre(membre);
	}

	/**
	 * Validation d'un film
	 */
	private boolean validerFilm(Film f, BusinessException be) {
		if (f == null) {
			be.add(BusinessCode.VALIDATION_FILM_NULL);
			return false;
		}
		return true;
	}

	// Titre doit être non nul, non vide et maximum 250 caractères
	private boolean validerTitre(String titre, BusinessException be) {
		if (titre == null || titre.isBlank()) {
			be.add(BusinessCode.VALIDATION_FILM_TITRE_BLANK);
			return false;
		}
		if (titre.length() > 250) {
			be.add(BusinessCode.VALIDATION_FILM_TITRE_LENGTH);
			return false;
		}
		return true;
	}

	// Année doit être au minimum de 1900
	private boolean validerAnnee(int annee, BusinessException be) {
		if (annee < 1900) {
			be.add(BusinessCode.VALIDATION_FILM_ANNEE);
			return false;
		}
		return true;
	}

	// Durée doit être au minimum 1 minute
	private boolean validerDuree(int duree, BusinessException be) {
		if (duree < 1) {
			be.add(BusinessCode.VALIDATION_FILM_DUREE);
			return false;
		}
		return true;
	}

	// Synopsis associé (entre 20 et 250 caractères)
	private boolean validerSynopsis(String synopsis, BusinessException be) {
		if (synopsis == null || synopsis.isBlank()) {
			be.add(BusinessCode.VALIDATION_FILM_SYNOPSIS_BLANK);
			return false;
		}
		if (synopsis.length() < 20 || synopsis.length() > 250) {
			be.add(BusinessCode.VALIDATION_FILM_SYNOPSIS_LENGTH);
			return false;
		}
		return true;
	}

	// Validation du Genre
	private boolean validerGenre(Genre genre, BusinessException be) {
		if (genre == null) {
			be.add(BusinessCode.VALIDATION_FILM_GENRE_NULL);
			return false;
		}

		// Valider que l'identifiant du genre est valide
		if (genre.getId() <= 0) {
			be.add(BusinessCode.VALIDATION_FILM_GENRE_ID_INCONNU);
			return false;
		}

		// Valider que l'identifiant du genre existe en base
		// L'id doit exister - s'il n'existe pas il y aura levée de l'exception
		// DataAccessException
		// Il faut gérer les 2 cas
		try {
			Genre genreEnBase = genreDAO.read(genre.getId());
			if (genreEnBase == null) {
				be.add(BusinessCode.VALIDATION_FILM_GENRE_ID_INCONNU);
				return false;
			}
		} catch (DataAccessException e) {
			// Impossible de trouver le genre
			be.add(BusinessCode.VALIDATION_FILM_GENRE_ID_INCONNU);
			return false;
		}

		return true;
	}

	// Validation du Réalisateur
	private boolean validerRealisateur(Participant participant, BusinessException be) {
		if (participant == null) {
			be.add(BusinessCode.VALIDATION_FILM_REALISATEUR_NULL);
			return false;
		}

		// Valider que l'identifiant du Réalisateur est valide
		if (participant.getId() <= 0) {
			be.add(BusinessCode.VALIDATION_FILM_REALISATEUR_ID_INCONNU);
			return false;
		}

		// Valider que l'identifiant du Réalisateur existe en base
		try {
			Participant participantEnBase = participantDAO.read(participant.getId());
			if (participantEnBase == null) {
				be.add(BusinessCode.VALIDATION_FILM_ACTEUR_ID_INCONNU);
				return false;
			}
		} catch (DataAccessException e) {
			// Impossible de trouver le réalisateur
			be.add(BusinessCode.VALIDATION_FILM_ACTEUR_ID_INCONNU);
			return false;
		}

		return true;
	}

	// Validation des acteurs s'il y en a
	private boolean validerActeurs(List<Participant> acteurs, BusinessException be) {
		if (acteurs == null || acteurs.isEmpty()) {
			return true;//Pas de validation si aucun acteur dans la liste
		}

		// Valider que l'identifiant des acteurs est valide
		for (Participant participant : acteurs) {
			if (participant.getId() <= 0) {
				be.add(BusinessCode.VALIDATION_FILM_ACTEUR_ID_INCONNU);
				return false;
			}else {
				// Valider que l'identifiant de l'acteur existe en base
				try {
					Participant participantEnBase = participantDAO.read(participant.getId());
					if (participantEnBase == null) {
						be.add(BusinessCode.VALIDATION_FILM_REALISATEUR_ID_INCONNU);
						return false;
					}
				} catch (DataAccessException e) {
					// Impossible de trouver l'acteur
					be.add(BusinessCode.VALIDATION_FILM_REALISATEUR_ID_INCONNU);
					return false;
				}
			}
		}		
		return true;
	}

	// Validation de l'unicité du film par son titre
	private boolean validerFilmUnique(String titre, BusinessException be) {
		// Valider que le titre est unique
		try {
			boolean titreExiste = filmDAO.findTitre(titre);
			if (titreExiste) {
				be.add(BusinessCode.VALIDATION_FILM_UNIQUE);
				return false;
			}
		} catch (DataAccessException e) {
			// Impossible de trouver le film
			be.add(BusinessCode.VALIDATION_FILM_UNIQUE);
			return false;
		}

		return true;
	}

	/**
	 * Validation d'un avis
	 */
	private boolean validerAvis(Avis a, BusinessException be) {
		if (a == null) {
			be.add(BusinessCode.VALIDATION_AVIS_NULL);
			return false;
		}
		return true;
	}

	// Note donnée de 0 à 5
	private boolean validerNote(int note, BusinessException be) {
		if (note < 0 || note > 5) {
			be.add(BusinessCode.VALIDATION_AVIS_NOTE);
			return false;
		}
		return true;
	}

	// Commentaire associé (entre 1 et 250 caractères)
	private boolean validerCommentaire(String commentaire, BusinessException be) {
		if (commentaire == null || commentaire.isBlank()) {
			be.add(BusinessCode.VALIDATION_AVIS_COMMENTAIRE_BLANK);
			return false;
		}
		if (commentaire.length() < 1 || commentaire.length() > 250) {
			be.add(BusinessCode.VALIDATION_AVIS_COMMENTAIRE_LENGTH);
			return false;
		}
		return true;
	}

	// Publier un avis si et seulement si un membre est connecté
	private boolean validerMembre(Membre membre, BusinessException be) {
		if (membre == null) {
			be.add(BusinessCode.VALIDATION_AVIS_MEMBRE_NULL);
			return false;
		}

		// Valider que l'identifiant du membre est valide
		if (membre.getId() <= 0) {
			be.add(BusinessCode.VALIDATION_AVIS_MEMBRE_ID_INCONNU);
			return false;
		}

		// Valider que l'identifiant du membre existe en base
		try {
			Membre membreEnBase = membreDAO.read(membre.getId());
			if (membreEnBase == null) {
				be.add(BusinessCode.VALIDATION_AVIS_MEMBRE_INCONNU);
				return false;
			}

			// Valider que le membre correspond à celui de la base
			// Pour valider l'égalité du membre en session et de celui en base
			// Il faut valider tous les champs sauf le mot de passe
			// Redéfinir la méthode equals dans la classe Membre
			if (!membreEnBase.equals(membre)) {
				be.add(BusinessCode.VALIDATION_AVIS_MEMBRE_INCONNU);
				return false;
			}

		} catch (DataAccessException e) {
			be.add(BusinessCode.VALIDATION_AVIS_MEMBRE_INCONNU);
			return false;
		}

		return true;
	}

	// Valider l'identifiant du film associé
	private boolean validerIdFilm(long idFilm, BusinessException be) {
		if (idFilm < 0) {
			be.add(BusinessCode.VALIDATION_FILM_ID_INCONNU);
			return false;
		}
		try {
			String titre = filmDAO.findTitre(idFilm);
			if (titre == null) {
				be.add(BusinessCode.VALIDATION_FILM_INCONNU);
				return false;
			}
		} catch (DataAccessException e) {
			be.add(BusinessCode.VALIDATION_FILM_INCONNU);
			return false;
		}

		return true;
	}

	// Valider que le membre n'a pas donné d'avis sur le film
	private boolean validerMembreAvisFilm(long idFilm, long idMembre, BusinessException be) {
		try {
			int count = avisDAO.countAvis(idFilm, idMembre);
			if (count > 0) {
				be.add(BusinessCode.VALIDATION_AVIS_UNIQUE);
				return false;
			}
		} catch (DataAccessException e) {
			be.add(BusinessCode.VALIDATION_AVIS_UNIQUE);
			return false;
		}

		return true;
	}

}
