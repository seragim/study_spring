package qna;

import java.util.List;

public interface QnaService {
	
	void qna_insert(QnaVO vo); //질문게시판 글 저장
	List<QnaVO> qna_list();	//질문게시판 글목록 조회
	QnaPage qna_list(QnaPage page);//페이지 단위로 질문게시판 글목록조회
	QnaVO qna_view(int id); //질문게시판 글 상세보기
	void qna_read(int id);	//질문게시판 조회수 증가
	void qna_update(QnaVO vo);	//글 변경 저장
	void qna_delete(int id);	//글 삭제 처리
}
