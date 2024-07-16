package fr.eni.tp.filmotheque.dal;

import java.util.List;

import fr.eni.tp.filmotheque.bo.Genre;

public interface GenreDAO {
	Genre read(long id);

	List<Genre> findAll();
}
