package fr.eni.tp.filmotheque.dao;

import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.dal.GenreDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class GenreDAOImplTest {

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    void testRead() {
        Genre genre = genreDAO.read(1);
        assertNotNull(genre, "Le genre avec l'ID 1 doit exister dans la base de données");
        assertEquals("Animation", genre.getTitre(), "Le titre du genre doit être 'Animation'");

        genre = genreDAO.read(2);
        assertNotNull(genre, "Le genre avec l'ID 2 doit exister dans la base de données");
        assertEquals("Science-fiction", genre.getTitre(), "Le titre du genre doit être 'Science-fiction'");
    }

    @Test
    void testFindAll() {
        List<Genre> genres = genreDAO.findAll();
        assertNotNull(genres, "La liste des genres ne doit pas être nulle");
        assertEquals(8, genres.size(), "Il doit y avoir 8 genres dans la base de données");
    }
}