package notice;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDAO implements NoticeService {
	@Autowired @Qualifier("hanul") private SqlSession sql;

	@Override
	public void notice_insert(NoticeVO vo) {
		sql.insert("notice.mapper.insert", vo);

	}

	@Override
	public List<NoticeVO> notice_list() {
		return sql.selectList("notice.mapper.list");
	}

	@Override
	public NoticeVO notice_view(int id) {
		// TODO Auto-generated method stub
		return sql.selectOne("notice.mapper.view", id);
	}

	@Override
	public void notice_update(NoticeVO vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notice_delete(int id) {
		// TODO Auto-generated method stub

	}

}
