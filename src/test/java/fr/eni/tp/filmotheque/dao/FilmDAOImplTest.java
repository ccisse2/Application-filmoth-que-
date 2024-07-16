package fr.eni.tp.filmotheque.dao;


import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dal.FilmDAO;
import fr.eni.tp.filmotheque.dal.GenreDAO;
import fr.eni.tp.filmotheque.dal.ParticipantDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class FilmDAOImplTest {

    @Autowired
    private FilmDAO filmDAO;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private ParticipantDAO participantDAO;

    @Test
    void testRead() {
        Film film = filmDAO.read(1); // Assurez-vous que l'ID 1 existe dans la base de données
        assertNotNull(film, "Le film avec l'ID 1 doit exister dans la base de données");
        assertEquals("Jurassic Park", film.getTitre(), "Le titre du film doit être 'Jurassic Park'"); // Modifiez ce titre selon vos données

        film = filmDAO.read(2);
        assertNotNull(film, "Le film avec l'ID 2 doit exister dans la base de données");
        assertEquals("The Fly", film.getTitre(), "Le titre du film doit être 'The Fly'"); // Modifiez ce titre selon vos données
    }

    @Test
    void testFindAll() {
        List<Film> films = filmDAO.findAll();
        assertNotNull(films, "La liste des films ne doit pas être nulle");
        assertTrue(films.size() > 0, "Il doit y avoir des films dans la base de données");
    }

    @Test
    void testFindTitre() {
        String titre = filmDAO.findTitre(1); // Assurez-vous que l'ID 1 existe dans la base de données
        assertNotNull(titre, "Le titre du film avec l'ID 1 ne doit pas être nul");
        assertEquals("Jurassic Park", titre, "Le titre du film doit être 'Jurassic Park'"); // Modifiez ce titre selon vos données

        titre = filmDAO.findTitre(2);
        assertNotNull(titre, "Le titre du film avec l'ID 2 ne doit pas être nul");
        assertEquals("The Fly", titre, "Le titre du film doit être 'The Fly'"); // Modifiez ce titre selon vos données
    }

    @Test
    void testCreate() {
        // Récupérez le genre et le réalisateur depuis la base de données
        Genre genre = genreDAO.read(1);
        Participant realisateur = participantDAO.read(1);

        assertNotNull(genre, "Le genre avec l'ID 1 doit exister dans la base de données");
        assertNotNull(realisateur, "Le réalisateur avec l'ID 1 doit exister dans la base de données");

        // Créez un film avec des données valides
        Film film = new Film();
        film.setTitre("Nouveau Film");
        film.setAnnee(2022);
        film.setDuree(130);
        film.setSynopsis("Ceci est un nouveau film pour les tests unitaires.");
        film.setGenre(genre);
        film.setRealisateur(realisateur);

        filmDAO.create(film);

        assertNotEquals(0, film.getId(), "L'ID du film doit être mis à jour après la création");

    }
}
