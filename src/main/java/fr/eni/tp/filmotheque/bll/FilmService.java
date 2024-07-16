package fr.eni.tp.filmotheque.bll;

import java.util.List;

import fr.eni.tp.filmotheque.bo.*;

public interface FilmService {
	List<Film> consulterFilms();

	Film consulterFilmParId(long id);

	List<Genre> consulterGenres();

	List<Participant> consulterParticipants();

	Genre consulterGenreParId(long id);

	Participant consulterParticipantParId(long id);

    Membre findByEmail(String email);

    void creerFilm(Film film);
	
	String consulterTitreFilm(long id);
	
	void publierAvis(Avis avis, long idFilm);
	
	List<Avis> consulterAvis(long idFilm);
}
