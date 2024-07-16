package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Membre;

public interface MembreDAO {
	Membre read(long id);

	Membre read(String email);
}
