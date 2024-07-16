package fr.eni.tp.filmotheque.controller.security;

import fr.eni.tp.filmotheque.bll.contexte.ContexteService;
import fr.eni.tp.filmotheque.bo.Membre;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/login")
@SessionAttributes("membreEnSession")
public class LoginController {

    private final ContexteService service;

    @Autowired
    public LoginController(ContexteService service) {
        this.service = service;
    }

    @GetMapping
    public String loginForm() {
        return "login";
    }

    @ModelAttribute("membreEnSession")
    public Membre membreEnSession() {
        System.out.println("Add Attribut Session");
        return new Membre();
    }

    @GetMapping("/success")
    public String loginSuccess(HttpSession session, @ModelAttribute("membreEnSession") Membre membreEnSession) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();  // Récupère l'email de l'utilisateur authentifié

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

        session.setAttribute("membreEnSession", membreEnSession);
        return "redirect:/films";
    }
}
