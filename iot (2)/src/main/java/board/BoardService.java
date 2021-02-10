package board;

public interface BoardService {
	//CRUD
	int board_insert(BoardVO vo);	//방명록 신규저장
	BoardPage board_list(BoardPage page);	//페이지단위 목록조회
	BoardVO board_view(int id);//선택한 방명록보기조회
	void board_read(int id);//선택한 글의 조회수 증가처리
	int board_update(BoardVO vo);//선택한 글의 변경저장
	int board_delete(int id);//선택한 공지글 삭제
}
