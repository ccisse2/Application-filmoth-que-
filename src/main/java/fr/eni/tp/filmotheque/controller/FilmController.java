package fr.eni.tp.filmotheque.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.tp.filmotheque.bll.FilmService;
import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Membre;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.exceptions.BusinessCode;
import fr.eni.tp.filmotheque.exceptions.BusinessException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/films")
// Injection de la liste des attributs en session
@SessionAttributes({ "genresEnSession", "membreEnSession", "participantsEnSession" })
public class FilmController {

	// Dépendance
	private FilmService filmService;

	public FilmController(FilmService filmService) {
		this.filmService = filmService;
	}

	/**
	 * La méthode réagit à l'url /films et la méthode Get du protocole HTTP
	 * 
	 * @param model -- pour injecter des données à la vue
	 * @return l'alias de la page à afficher
	 */
	@GetMapping
	public String afficherFilms(Model model) {
		System.out.println("\nTous les films : ");
		List<Film> films = filmService.consulterFilms();
		// Ajout des films dans le modèle
		model.addAttribute("films", films);
		return "view-films";
	}

	@GetMapping("/detail")
	public String afficherUnFilm(@RequestParam(name = "id", required = true) long id, Model model) {
		if (id > 0) {// L'identifiant en base commencera en 1
			Film film = filmService.consulterFilmParId(id);
			if (film != null) {
				// Ajout de l'instance dans le modèle
				model.addAttribute("film", film);
				return "view-film-detail";
			} else
				System.out.println("Film inconnu!!");
		} else {
			System.out.println("Identifiant inconnu");
		}
		// Par défaut redirection vers l'url pour afficher les films
		return "redirect:/films";
	}

	@ModelAttribute("genresEnSession")
	public List<Genre> chargerGenres() {
		System.out.println("Chargement en Session - GENRES");
		return filmService.consulterGenres();
	}

	/**
	 * - Cette méthode va transmettre l'instance de l'objet Film pour le formulaire
	 */
	@GetMapping("/creer")
	public String creerFilm(Model model, @ModelAttribute("membreEnSession") Membre membreEnSession) {
		if (membreEnSession != null && membreEnSession.getId() >= 1 && membreEnSession.isAdmin()) {
			// Il y a un membre en session et c'est un administrateur
			// Ajout de l'instance dans le modèle
			model.addAttribute("film", new Film());
			return "view-film-form";
		} else {
			// redirection vers la page des films
			return "redirect:/films";
		}
	}

	// Création d'un nouveau film
	@PostMapping("/creer")
	public String creerFilm(@Valid @ModelAttribute("film") Film film, BindingResult bindingResult,
			@ModelAttribute("membreEnSession") Membre membreEnSession) {

		if (membreEnSession != null && membreEnSession.getId() >= 1 && membreEnSession.isAdmin()) {
			// Il y a un membre en session et c'est un administrateur
			if (!bindingResult.hasErrors()) {
				try {
					filmService.creerFilm(film);
					return "redirect:/films";
				} catch (BusinessException e) {
					System.err.println(e.getClefsExternalisations());
					// Afficher les messages d’erreur - il faut les injecter dans le contexte de
					// BindingResult
					e.getClefsExternalisations().forEach(key -> {
						ObjectError error = new ObjectError("globalError", key);
						bindingResult.addError(error);
					});
				}
			}
		} else {
			// Gestion d'une exception à afficher dans le cas où aucun membre administrateur
			// en session
			// Afficher les messages d’erreur - il faut les injecter dans le contexte de
			// BindingResult
			System.out.println("Aucun administrateur en session");
			ObjectError error = new ObjectError("globalError", BusinessCode.VALIDATION_MEMBRE_ADMIN);
			bindingResult.addError(error);
		}
		// Il y a des erreurs sur le formulaire
		return "view-film-form";
	}

	// Injection en session des listes représentant les participants
	@ModelAttribute("participantsEnSession")
	public List<Participant> chargerParticipants() {
		System.out.println("Chargement en Session - PARTICIPANTS");
		return filmService.consulterParticipants();
	}

}
