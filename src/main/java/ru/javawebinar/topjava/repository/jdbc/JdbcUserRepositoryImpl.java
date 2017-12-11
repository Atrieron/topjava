package ru.javawebinar.topjava.repository.jdbc;

import org.hibernate.cfg.beanvalidation.BeanValidationEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

	private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
	private static final BeanPropertyRowMapper<Role> ROLE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Role.class);

	private final JdbcTemplate jdbcTemplate;

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final SimpleJdbcInsert insertUser;
	
	private final DataSourceTransactionManager dataSourceTransactionManager;

	@Autowired
	public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.insertUser = new SimpleJdbcInsert(dataSource).withTableName("users").usingGeneratedKeyColumns("id");

		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.dataSourceTransactionManager = new DataSourceTransactionManager(dataSource); 
	}

	@Override
	public User save(User user) {
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

		if (user.isNew()) {			
			Number newKey = insertUser.executeAndReturnKey(parameterSource);
			user.setId(newKey.intValue());
		} else if (namedParameterJdbcTemplate.update(
				"UPDATE users SET name=:name, email=:email, password=:password, "
						+ "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id",
				parameterSource) == 0) {
			return null;
		}
		return user;
	}

	@Override
	public boolean delete(int id) {
		return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
	}

	private void getUserRoles(User user) {
		if (user != null) {
			Object[] params = new Object[] { user.getId() };
			List<Role> roles = jdbcTemplate.query("SELECT role FROM user_roles WHERE user_id=?", params,
					new RowMapper<Role>() {
						@Override
						public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
							return Role.valueOf(rs.getString("role"));
						}
					});
			user.setRoles(roles);
		}
	}

	@Override
	public User get(int id) {
		List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
		User res = DataAccessUtils.singleResult(users);
		getUserRoles(res);
		return res;
	}

	@Override
	public User getByEmail(String email) {
		// return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?",
		// ROW_MAPPER, email);
		List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
		User res = DataAccessUtils.singleResult(users);
		getUserRoles(res);
		return res;
	}

	private User getUserByIdFromCollection(int userId, List<User> users) {
		for (User user : users) {
			if (user.getId().intValue() == userId) {
				return user;
			}
		}

		return null;
	}

	@Override
	public List<User> getAll() {
		List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
		Map<User, Set<Role>> usersRoles = new HashMap<>();
		if (!users.isEmpty()) {
			StringBuilder stringBuilder = new StringBuilder();
			boolean append = false;
			for (User user : users) {
				if (append) {
					stringBuilder.append(",");
				} else {
					append = true;
				}
				stringBuilder.append(user.getId());
				usersRoles.put(user, new HashSet<>());
			}

			jdbcTemplate.query(
					"SELECT role, user_id FROM user_roles WHERE user_id in(" + stringBuilder.toString() + ")",
					new RowCallbackHandler() {
						@Override
						public void processRow(ResultSet rs) throws SQLException {
							User currentUser = getUserByIdFromCollection(rs.getInt("user_id"), users);
							if (currentUser != null) {
								Set<Role> currentRoles = usersRoles.get(currentUser);
								currentRoles.add(Role.valueOf(rs.getString("role")));
								usersRoles.put(currentUser, currentRoles);
								//currentUser.getRoles().add(Role.valueOf(rs.getString("role")));
							}
						}
					});
		}
		for(Map.Entry<User, Set<Role>> entry: usersRoles.entrySet()) {
			entry.getKey().setRoles(entry.getValue());
		}
		return users;
	}
}
