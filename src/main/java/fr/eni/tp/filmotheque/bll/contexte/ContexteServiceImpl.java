package fr.eni.tp.filmotheque.bll.contexte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eni.tp.filmotheque.bo.Membre;
import fr.eni.tp.filmotheque.dal.MembreDAO;

@Service
public class ContexteServiceImpl implements ContexteService {

	private MembreDAO membreDAO;

	@Autowired
	public ContexteServiceImpl(MembreDAO membreDAO) {
		this.membreDAO = membreDAO;
	}

	@Override
	public Membre charger(String email) {
		return membreDAO.read(email);
	}
}
