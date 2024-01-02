package com.example.board.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.dto.User;

@Repository
//@RequiredArgsConstructor
public class UserDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsertOperations insertUser;

	public UserDao(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertUser = new SimpleJdbcInsert(dataSource).withTableName("user_t").usingGeneratedKeyColumns("user_id");

	}

	@Transactional
	public User addUser(String name, String email, String password) {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setRegdate(LocalDateTime.now());

		SqlParameterSource params = new BeanPropertySqlParameterSource(user);
		// number type을 return 하기때문에 int 로 변환필요
		int userId = insertUser.executeAndReturnKey(params).intValue();

		user.setUserId(userId);

		return user;
	}

	@Transactional
	public void mappingUserRole(int userId) {
		String sql = "insert into user_role(user_id, role_id) values (:userId,1)";
		SqlParameterSource params = new MapSqlParameterSource("userId", userId);

		jdbcTemplate.update(sql, params);
	}

	@Transactional
	public User getUser(String email) {
		// TODO Auto-generated method stub
		String sql = "select * from user_t where email = :email";
		SqlParameterSource params = new MapSqlParameterSource("email", email);
		RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);

		User user = jdbcTemplate.queryForObject(sql, params, rowMapper);

		return user;
	}

	@Transactional(readOnly = true)
	public List<String> getRoles(int userId) {
		String sql = "select r.name\r\n" + "from user_role ur, role r\r\n" + "where ur.role_id = r.role_id\r\n"
				+ "and ur.user_id = :userId";

		List<String> roles = jdbcTemplate.query(sql, Map.of("userId", userId), (rs, rowNum) -> {
			return rs.getString(1);
		});

		return roles;
	}

}
