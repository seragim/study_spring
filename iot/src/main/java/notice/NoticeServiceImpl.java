package notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired private NoticeDAO dao;

	@Override
	public void notice_insert(NoticeVO vo) {
		dao.notice_insert(vo);

	}

	@Override
	public List<NoticeVO> notice_list() {
		// TODO Auto-generated method stub
		return dao.notice_list();
	}

	@Override
	public NoticeVO notice_view(int id) {
		// TODO Auto-generated method stub
		return dao.notice_view(id);
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
