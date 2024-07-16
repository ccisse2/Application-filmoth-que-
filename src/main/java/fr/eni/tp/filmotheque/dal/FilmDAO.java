package fr.eni.tp.filmotheque.dal;

import java.util.List;

import fr.eni.tp.filmotheque.bo.Film;

public interface FilmDAO {
	void create(Film film);

	Film read(long id);

	List<Film> findAll();

	String findTitre(long id);
	
	boolean findTitre(String titre);
}
