package member;

import java.util.HashMap;

public interface MemberService {
	boolean member_join(MemberVO vo); //회원가입시 회원정보 저장
	MemberVO member_select(String id); //회원정보조회:마이페이지
	MemberVO member_login(HashMap<String, Object> map); //회원 로그인처리
	boolean member_update(MemberVO vo); //회원정보 변경
	boolean member_delete(String id); //회원탈퇴
	boolean member_id_check(String id); //회원가입시 아이디중복확인
	boolean member_social_id(MemberVO vo);//소셜로그인시 회원의 존재여부
	boolean member_social_insert(MemberVO vo);//소셜로그인시 회원 신규저장
	boolean member_social_update(MemberVO vo);//소셜로그인시 회원 변경저장
}
