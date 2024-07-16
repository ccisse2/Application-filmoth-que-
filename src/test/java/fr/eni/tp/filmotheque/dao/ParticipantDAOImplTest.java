package fr.eni.tp.filmotheque.dao;

import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dal.ParticipantDAO;
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
public class ParticipantDAOImplTest {

    @Autowired
    private ParticipantDAO participantDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Test
    void testRead() {
        Participant participant = participantDAO.read(1);
        assertNotNull(participant, "Le participant avec l'ID 1 doit exister dans la base de données");
        assertEquals("Spielberg", participant.getNom(), "Le nom du participant doit être 'Spielberg'");
        assertEquals("Steven", participant.getPrenom(), "Le prénom du participant doit être 'Steven'");

        participant = participantDAO.read(2);
        assertNotNull(participant, "Le participant avec l'ID 2 doit exister dans la base de données");
        assertEquals("Cronenberg", participant.getNom(), "Le nom du participant doit être 'Cronenberg'");
        assertEquals("David", participant.getPrenom(), "Le prénom du participant doit être 'David'");
    }

    @Test
    void testFindAll() {
        List<Participant> participants = participantDAO.findAll();
        assertNotNull(participants, "La liste des participants ne doit pas être nulle");
        assertEquals(9, participants.size(), "Il doit y avoir 9 participants dans la base de données");
    }
}
