package fr.eni.tp.filmotheque.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.tp.filmotheque.bo.Avis;
import fr.eni.tp.filmotheque.bo.Membre;

@Repository
public class AvisDAOImpl implements AvisDAO {
	private final String FIND_BY_FILM = "SELECT ID, NOTE, COMMENTAIRE, id_membre FROM AVIS WHERE id_film = :idFilm";
	private final String INSERT = "INSERT INTO AVIS(note,commentaire,id_membre,id_film) VALUES "
			+ " (:note, :commentaire, :idMembre, :idFilm)";
	private final String COUNT_AVIS = "SELECT count(id) FROM AVIS where id_membre = :idMembre and id_film= :idFilm";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void create(Avis avis, long idFilm) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("note", avis.getNote());
		namedParameters.addValue("commentaire", avis.getCommentaire());
		namedParameters.addValue("idMembre", avis.getMembre().getId());
		namedParameters.addValue("idFilm", idFilm);
		jdbcTemplate.update(INSERT, namedParameters);
	}

	@Override
	public List<Avis> findByFilm(long idFilm) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("idFilm", idFilm);
		return jdbcTemplate.query(FIND_BY_FILM, namedParameters, new AvisRowMapper());
	}

	@Override
	public int countAvis(long idFilm, long idMembre) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("idFilm", idFilm);
		namedParameters.addValue("idMembre", idMembre);
		return jdbcTemplate.queryForObject(COUNT_AVIS, namedParameters, Integer.class);
	}

	/**
	 * Classe de mapping pour gérer l'association vers Membre
	 */
	class AvisRowMapper implements RowMapper<Avis> {
		@Override
		public Avis mapRow(ResultSet rs, int rowNum) throws SQLException {
			Avis a = new Avis();
			a.setId(rs.getLong("ID"));
			a.setNote(rs.getInt("NOTE"));
			a.setCommentaire(rs.getString("COMMENTAIRE"));

			// Association vers le membre
			Membre membre = new Membre();
			membre.setId(rs.getInt("id_membre"));
			a.setMembre(membre);
			return a;
		}
	}

}
