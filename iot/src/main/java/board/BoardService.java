package board;

import java.util.List;

public interface BoardService {
	//CRUD
	int board_insert(BoardVO vo); //방명록 신규저장
	BoardPage board_list(BoardPage page);  //페이지단위 목록조회
	BoardVO board_view(int id); //선택한 방명록보기조회
	void board_read(int id); //선택한 글의 조회수 증가처리
	int board_update(BoardVO vo); //선택한 글의 변경저장
	int board_delete(int id); //선택한 공지글 삭제
	
	int board_comment_insert(BoardCommentVO vo); //댓글저장처리
	List<BoardCommentVO> board_comment_list(int pid); //댓글목록조회
	int board_comment_update(BoardCommentVO vo); //댓글변경저장처리
	int board_comment_delete(int id); //댓글삭제처리
}










