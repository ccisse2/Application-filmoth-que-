package fr.eni.tp.filmotheque.bll.contexte;

import fr.eni.tp.filmotheque.bo.Membre;

public interface ContexteService {
	Membre charger(String email);
}
