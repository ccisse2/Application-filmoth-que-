package fr.eni.tp.filmotheque.dal;

import java.util.List;

import fr.eni.tp.filmotheque.bo.Avis;

public interface AvisDAO {
	void create(Avis avis,  long idFilm);

	List<Avis> findByFilm(long idFilm);
	
	int countAvis(long idFilm, long idMembre);
}
