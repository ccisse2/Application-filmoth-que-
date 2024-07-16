package fr.eni.tp.filmotheque.bll;

import fr.eni.tp.filmotheque.bo.Avis;
import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dal.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class FilmServiceImplTest {

    @Mock
    private FilmDAO filmDAO;
    @Mock
    private GenreDAO genreDAO;
    @Mock
    private ParticipantDAO participantDAO;
    @Mock
    private AvisDAO avisDAO;
    @Mock
    private MembreDAO membreDAO;

    @InjectMocks
    private FilmServiceImpl filmService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsulterFilms() throws BLLException {
        List<Film> films = Arrays.asList(new Film(), new Film());
        when(filmDAO.findAll()).thenReturn(films);

        List<Film> result = filmService.consulterFilms();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testConsulterFilmParId() throws BLLException {
        Film film = new Film();
        when(filmDAO.read(anyLong())).thenReturn(film);

        Film result = filmService.consulterFilmParId(1L);
        assertNotNull(result);
    }

    @Test
    public void testConsulterFilmParIdNotFound() {
        when(filmDAO.read(anyLong())).thenReturn(null);

        assertThrows(BLLException.class, () -> filmService.consulterFilmParId(1L));
    }

    @Test
    public void testConsulterGenres() throws BLLException {
        List<Genre> genres = Arrays.asList(new Genre(), new Genre());
        when(genreDAO.findAll()).thenReturn(genres);

        List<Genre> result = filmService.consulterGenres();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testConsulterParticipants() throws BLLException {
        List<Participant> participants = Arrays.asList(new Participant(), new Participant());
        when(participantDAO.findAll()).thenReturn(participants);

        List<Participant> result = filmService.consulterParticipants();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testConsulterGenreParId() throws BLLException {
        Genre genre = new Genre();
        when(genreDAO.read(anyLong())).thenReturn(genre);

        Genre result = filmService.consulterGenreParId(1L);
        assertNotNull(result);
    }

    @Test
    public void testConsulterParticipantParId() throws BLLException {
        Participant participant = new Participant();
        when(participantDAO.read(anyLong())).thenReturn(participant);

        Participant result = filmService.consulterParticipantParId(1L);
        assertNotNull(result);
    }

    @Test
    public void testCreerFilm() throws BLLException {
        Film film = new Film();
        Genre genre = new Genre();
        genre.setId(1L);
        Participant realisateur = new Participant();
        realisateur.setId(1L);
        film.setGenre(genre);
        film.setRealisateur(realisateur);

        filmService.creerFilm(film);

        verify(filmDAO, times(1)).create(film);
    }

    @Test
    public void testCreerFilmNull() {
        assertThrows(BLLException.class, () -> filmService.creerFilm(null));
    }

    @Test
    public void testCreerFilmSansRealisateur() {
        Film film = new Film();
        film.setGenre(new Genre());
        assertThrows(BLLException.class, () -> filmService.creerFilm(film));
    }

    @Test
    public void testCreerFilmSansGenre() {
        Film film = new Film();
        film.setRealisateur(new Participant());
        assertThrows(BLLException.class, () -> filmService.creerFilm(film));
    }

    @Test
    public void testConsulterTitreFilm() throws BLLException {
        when(filmDAO.findTitre(anyLong())).thenReturn("Titre du film");

        String result = filmService.consulterTitreFilm(1L);
        assertEquals("Titre du film", result);
    }

    @Test
    public void testPublierAvis() throws BLLException {
        Avis avis = new Avis();
        filmService.publierAvis(avis, 1L);

        verify(avisDAO, times(1)).create(avis, 1L);
    }

    @Test
    public void testConsulterAvis() throws BLLException {
        List<Avis> avisList = Arrays.asList(new Avis(), new Avis());
        when(avisDAO.findByFilm(anyLong())).thenReturn(avisList);

        List<Avis> result = filmService.consulterAvis(1L);
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
