package fr.eni.tp.filmotheque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import fr.eni.tp.filmotheque.bll.contexte.ContexteService;
import fr.eni.tp.filmotheque.bo.Membre;

@Controller
@RequestMapping("/contexte")
//Injection de la liste des attributs en session
@SessionAttributes({ "membreEnSession" })
public class ContexteController {

	private ContexteService service;

	public ContexteController(ContexteService service) {
		this.service = service;
	}

	// Cette méthode met par défaut un nouveau membre en session
	@ModelAttribute("membreEnSession")
	public Membre membreEnSession() {
		System.out.println("Add Attribut Session");
		return new Membre();
	}

	@GetMapping("/session")
	public String connexion(@ModelAttribute("membreEnSession") Membre membreEnSession,
			@RequestParam(name = "email", required = false, defaultValue = "jtrillard@campus-eni.fr") String email) {
		Membre aCharger = service.charger(email);
		if (aCharger != null) {
			membreEnSession.setId(aCharger.getId());
			membreEnSession.setNom(aCharger.getNom());
			membreEnSession.setPrenom(aCharger.getPrenom());
			membreEnSession.setPseudo(aCharger.getPseudo());
			membreEnSession.setAdmin(aCharger.isAdmin());

		} else {
			membreEnSession.setId(0);
			membreEnSession.setNom(null);
			membreEnSession.setPrenom(null);
			membreEnSession.setPseudo(null);
			membreEnSession.setAdmin(false);

		}
		System.out.println(membreEnSession);
		// Evidemment on évite de mettre un mot de passe en session
		// (surtout non chiffré)

		return "redirect:/films";
	}

	@GetMapping("/cloture")
	public String finSession(SessionStatus status) {
		// Suppression des attributs de @SessionAttributs
		status.setComplete();

		return "redirect:/films";// Redirection
	}

	@GetMapping
	public String choixContexte() {
		return "contexte/view-contexte";
	}
}
