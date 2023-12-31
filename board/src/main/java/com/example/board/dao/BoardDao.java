package com.example.board.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.dto.Board;
import com.example.board.dto.User;

@Repository
//@RequiredArgsConstructor
public class BoardDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsertOperations insertBoard;

	public BoardDao(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertBoard = new SimpleJdbcInsert(dataSource).withTableName("board").usingGeneratedKeyColumns("board_id");
	}

	@Transactional
	public void addBoard(int userId, String title, String content) {
		Board board = new Board();

		board.setUserId(userId);
		board.setTitle(title);
		board.setContent(content);
		board.setRegDate(LocalDateTime.now());

		SqlParameterSource params = new BeanPropertySqlParameterSource(board);

		// TODO Auto-generated method stub
		insertBoard.execute(params);
	}

	@Transactional(readOnly = true)
	public int getTotalCount() {
		String sql = "select count(*) as total_count from board";
		int totalCount = jdbcTemplate.queryForObject(sql, Map.of(), Integer.class);

		return totalCount;
	}

	@Transactional(readOnly = true)
	public int getPageCount() {
		String sql = "select ceil(count(*)/10) as page_count from board";
		int pageCount = jdbcTemplate.queryForObject(sql, Map.of(), Integer.class);

		return pageCount;
	}

	@Transactional(readOnly = true)
	public List<Board> getBoards(int page) {
		int start = (page - 1) * 10;
		String sql = "select * from (\r\n"
				+ "select rownum as row_num, b.user_id, b.board_id,b.title,b.regdate,b.view_cnt, u.name\r\n"
				+ "from board b, user_t u\r\n" + "where b.user_id=u.user_id\r\n" + "order by board_id desc \r\n"
				+ ")\r\n" + "where row_num between :start+1 and :start+10\r\n";

		RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);

		List<Board> list = jdbcTemplate.query(sql, Map.of("start", start), rowMapper);

		return list;
	}

	@Transactional(readOnly = true)
	public Board getBoard(int boardId) {
		String sql = "select b.user_id, b.board_id,b.title,b.regdate,b.view_cnt, u.name, b.content\r\n"
				+ "from board b, user_t u\r\n" + "where b.user_id=u.user_id\r\n" + "and b.board_id = :boardId";

		RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);
		Board board = jdbcTemplate.queryForObject(sql, Map.of("boardId", boardId), rowMapper);

		return board;
	}

	@Transactional
	public void updateViewCnt(int boardId) {
		String sql = "update board\r\n" + "set view_cnt = view_cnt + 1\r\n" + "where board_id = :boardId";

		jdbcTemplate.update(sql, Map.of("boardId", boardId));
	}

	@Transactional
	public void deleteBoard(int boardId) {
		String sql = "delete from board where board_id = :boardId";

		jdbcTemplate.update(sql, Map.of("boardId", boardId));

	}

	@Transactional
	public void updateBoard(int boardId, String title, String content) {
		String sql = "update board\r\n"
				+ "set title = :title\r\n"
				+ "  , content = :content\r\n"
				+ "where board_id = :boardId\r\n"
				+ "";

		Board board = new Board();
		board.setBoardId(boardId);
		board.setTitle(title);
		board.setContent(content);
		
		SqlParameterSource params = new BeanPropertySqlParameterSource(board);
		
		jdbcTemplate.update(sql, params);
		
	}

}
