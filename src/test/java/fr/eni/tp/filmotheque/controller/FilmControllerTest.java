package fr.eni.tp.filmotheque.controller;

import fr.eni.tp.filmotheque.bll.BLLException;
import fr.eni.tp.filmotheque.bll.FilmService;
import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Membre;
import fr.eni.tp.filmotheque.bo.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class FilmControllerTest {

    @Mock
    private FilmService filmService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private FilmController filmController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAfficherFilms() throws BLLException {
        List<Film> films = Collections.singletonList(new Film());
        when(filmService.consulterFilms()).thenReturn(films);

        String viewName = filmController.afficherFilms(model);

        verify(filmService, times(1)).consulterFilms();
        verify(model, times(1)).addAttribute("films", films);
        assertEquals("view-films", viewName);
    }

    @Test
    void testAfficherUnFilm() throws BLLException {
        Film film = new Film();
        when(filmService.consulterFilmParId(anyLong())).thenReturn(film);

        String viewName = filmController.afficherUnFilm(1L, model);

        verify(filmService, times(1)).consulterFilmParId(1L);
        verify(model, times(1)).addAttribute("film", film);
        assertEquals("view-film-detail", viewName);
    }

    @Test
    void testChargerGenres() throws BLLException {
        List<Genre> genres = Collections.singletonList(new Genre());
        when(filmService.consulterGenres()).thenReturn(genres);

        List<Genre> result = filmController.chargerGenres();

        verify(filmService, times(1)).consulterGenres();
        assertEquals(genres, result);
    }

    @Test
    void testChargerParticipants() throws BLLException {
        List<Participant> participants = Collections.singletonList(new Participant());
        when(filmService.consulterParticipants()).thenReturn(participants);

        List<Participant> result = filmController.chargerParticipants();

        verify(filmService, times(1)).consulterParticipants();
        assertEquals(participants, result);
    }

    @Test
    void testCreerFilm() throws BLLException {
        // Créez un membre en session avec les droits d'administrateur
        Membre membreEnSession = new Membre();
        membreEnSession.setId(2L); // Assurez-vous que l'ID est supérieur à 1
        membreEnSession.setAdmin(true);

        // Créez un film avec des données valides
        Film film = new Film();
        film.setTitre("Test Film");
        film.setAnnee(2021);
        film.setDuree(120);
        film.setSynopsis("A test film synopsis.");
        film.setRealisateur(new Participant()); // Ajoutez un réalisateur avec un ID valide
        film.setGenre(new Genre()); // Ajoutez un genre avec un ID valide

        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(filmService).creerFilm(any(Film.class));

        String viewName = filmController.creerFilm(film, bindingResult, membreEnSession);

        verify(filmService, times(1)).creerFilm(film);
        assertEquals("redirect:/films", viewName);
    }

}
