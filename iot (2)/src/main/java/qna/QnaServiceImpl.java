package qna;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QnaServiceImpl implements QnaService {
	@Autowired QnaDAO dao;

	@Override
	public void qna_insert(QnaVO vo) {
		dao.qna_insert(vo);
		
	}

	@Override
	public List<QnaVO> qna_list() {
		// TODO Auto-generated method stub
		return dao.qna_list();
	}

	@Override
	public QnaVO qna_view(int id) {
		// TODO Auto-generated method stub
		return dao.qna_view(id);
	}

	@Override
	public void qna_read(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void qna_update(QnaVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void qna_delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public QnaPage qna_list(QnaPage page) {
		// TODO Auto-generated method stub
		return dao.qna_list(page);
	}

	


}
