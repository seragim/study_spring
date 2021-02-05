package notice;

import java.util.List;

public interface NoticeService {
	//CRUD
	void notice_insert(NoticeVO vo); //신규공지글저장
	List<NoticeVO> notice_list(); //공지글목록조회
	NoticeVO notice_view(int id);//공지글상세조회(공지글보기)
	void notice_read(int id); //조회수 증가처리
	void notice_update(NoticeVO vo); //공지글변경저장
	void notice_delete(int id);//공지글삭제
	
}
