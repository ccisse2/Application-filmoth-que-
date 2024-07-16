package fr.eni.tp.filmotheque.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.tp.filmotheque.bo.Membre;

@Repository
public class MembreDAOImpl implements MembreDAO {
	private final String FIND_BY_ID = "SELECT id, email, nom, prenom, is_admin from MEMBRE WHERE id = :id";
	private final String FIND_BY_EMAIL = "SELECT id, email, nom, prenom, is_admin from MEMBRE "
							+" WHERE email = :email";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public Membre read(long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		return jdbcTemplate.queryForObject(FIND_BY_ID, namedParameters, new MembreRowMapper());
	}

	@Override
	public Membre read(String email) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("email", email);
		return jdbcTemplate.queryForObject(FIND_BY_EMAIL, namedParameters, new MembreRowMapper());
	}

	/**
	* Classe de mapping pour gérer les noms des colonnes
	*/
	class MembreRowMapper implements RowMapper<Membre> {
		@Override
		public Membre mapRow(ResultSet rs, int rowNum) throws SQLException {
			Membre m = new Membre();
			m.setId(rs.getLong("ID"));
			m.setPseudo(rs.getString("email"));
			m.setNom(rs.getString("nom"));
			m.setPrenom(rs.getString("prenom"));
			m.setAdmin(rs.getBoolean("is_admin"));
			return m;
		}
	}
}
