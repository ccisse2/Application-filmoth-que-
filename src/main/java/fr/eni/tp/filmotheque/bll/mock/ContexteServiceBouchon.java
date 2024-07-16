package fr.eni.tp.filmotheque.bll.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import fr.eni.tp.filmotheque.bll.contexte.ContexteService;
import fr.eni.tp.filmotheque.bo.Membre;

@Service
@Profile("reel")

public class ContexteServiceBouchon implements ContexteService {
	// Liste des membres
	private static List<Membre> lstMembres;

	public ContexteServiceBouchon() {
		// initialisation de la liste
		lstMembres = new ArrayList<>();
		lstMembres.add(new Membre(1, "Baille", "Anne-Lise", "abaille@campus-eni.fr", null));
		Membre admin = new Membre(2, "Gobin", "Stéphane", "sgobin@campus-eni.fr", null);
		admin.setAdmin(true);
		lstMembres.add(admin);
		lstMembres.add(new Membre(3, "Trillard", "Julien", "jtrillard@campus-eni.fr", null));
	}

	@Override
	public Membre charger(String email) {
		return lstMembres.stream().filter(item -> item.getPseudo().equals(email)).findAny().orElse(null);
	}
}
