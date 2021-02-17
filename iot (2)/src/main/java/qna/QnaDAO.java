package qna;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class QnaDAO implements QnaService{
	@Autowired @Qualifier("hanul") private SqlSession sql;

	@Override
	public void qna_insert(QnaVO vo) {
		sql.insert("qna.mapper.insert", vo);
		
	}

	@Override
	public List<QnaVO> qna_list() {
		// TODO Auto-generated method stub
		return sql.selectList("qna.mapper.list");
	}

	@Override
	public QnaVO qna_view(int id) {
		// TODO Auto-generated method stub
		return sql.selectOne("qna.mapper.view", id);
	}

	@Override
	public void qna_read(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void qna_update(QnaVO vo) {
		sql.update("qna.mapper.update", vo);
	}

	@Override
	public void qna_delete(int id) {
		sql.delete("qna.mapper.delete", id);
		
	}

	@Override
	public QnaPage qna_list(QnaPage page) {
		page.setTotalList(sql.selectOne("qna.mapper.totalList", page));
		page.setList( sql.selectList("qna.mapper.list", page) );
		return page;
	}

}
