package common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
	
	public void sendEmail(HttpSession session ,String email, String name) {
		
		//기본이메일전송
		//sendSimple(email, name);
		
		//파일첨부이메일 전송
		sendAttach(session, email, name);
		
		//HTML형태 이메일전송
		sendHtml(session, email, name);
	}
	
	private void sendHtml(HttpSession session, String email, String name) {
		HtmlEmail mail = new HtmlEmail();
		mail.setHostName("smtp.naver.com");
		mail.setDebug(true);
		mail.setCharset("utf-8");
		
		mail.setAuthentication("tpfk373", "ghkdlxld!!2");
		mail.setSSLOnConnect(true);
		
		try {
			mail.setFrom("tpfk373@naver.com", "한울관리자");
			mail.addTo(email, name);
			
			mail.setSubject("회원가입축하 -HTML");
			StringBuffer msg = new StringBuffer();
			msg.append("<html>");
			msg.append("<body>");
			msg.append("<a href='http://www.naver.com'><img src='https://postfiles.pstatic.net/20120925_239/rlawjdgus190_1348529302269RKWgd_JPEG/naver_com_20120924_125454.jpg?type=w2'/></a>");
			msg.append("<hr>");
			msg.append("<h2>한울 IoT과정 가입 축하</h2>");
			msg.append("<p>회원가입을 축하합니다</p>");
			msg.append("<p>첨부된 파일을 꼭 확인해 주시고</p>");
			msg.append("<p>프로젝트까지 마무리해서</p>");
			msg.append("<p>취업 성공하시기 바랍니다</p>");
			msg.append("</body>");
			msg.append("</html>");
			mail.setHtmlMsg(msg.toString());
			
			EmailAttachment file = new EmailAttachment();
			file.setPath( session.getServletContext().getRealPath("resources")+ "/css/common.css");
			mail.attach(file);
			
			file = new EmailAttachment();
			file.setURL( new URL("https://postfiles.pstatic.net/20120925_111/rlawjdgus190_13485293025248SLbg_JPEG/naver_com_20120924_125511.jpg?type=w2") );
			mail.attach(file);
			
			mail.send();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private void sendAttach(HttpSession session, String email, String name) {
		MultiPartEmail mail = new MultiPartEmail();
		mail.setHostName("smtp.naver.com");
		mail.setCharset("utf-8");
		mail.setDebug(true);
		
		mail.setAuthentication("tpfk373", "ghkdlxld!!2");
		mail.setSSLOnConnect(true);
		
		try {
			mail.setFrom("tpfk373@naver.com", "한울관리자");
			mail.addTo(email, name);
			mail.setSubject("회원가입축하 메시지- 첨부파일확인요망");
			mail.setMsg("회원가입을 축하합니다. 첨부된 파일을 확인하세요!");
			//파일첨부하기
			EmailAttachment file = new EmailAttachment();
			file.setPath("D:\\HTML5CSS3.hwp");
			mail.attach(file);
			
			file = new EmailAttachment();
			file.setPath(session.getServletContext().getRealPath("resources")+"/images/kakao_login.png");
			mail.attach(file);
			
			file = new EmailAttachment();
			file.setURL( new URL("https://postfiles.pstatic.net/20120925_239/rlawjdgus190_1348529302269RKWgd_JPEG/naver_com_20120924_125454.jpg?type=w2") );
			mail.attach(file);
			
			mail.send();
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void sendSimple(String email, String name) {
		SimpleEmail mail = new SimpleEmail();
		
		mail.setHostName("smtp.naver.com");	//메일서버지정
		mail.setCharset("utf-8");
		mail.setDebug(true);
		
		//로그인하기위한 아이디/비번 지정
		mail.setAuthentication("tpfk373", "ghkdlxld!!2");
		mail.setSSLOnConnect(true);
		
		try {
			//메일송신인 지정
			mail.setFrom("tpfk373@naver.com","한울관리자");
			
			//메일수신인 지정
			mail.addTo(email, name);	//여러명에게 보낼때는 addTo 추가
			
			//메일제목
			mail.setSubject("회원가입축하 메시지");
			mail.setMsg(name + " 님! 한울IoT 과정 입교를 축하합니다");
			
			//메일전송버튼 클릭
			mail.send();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		
	}

	public String requestAPI(StringBuffer url, String property) {
		String result = url.toString();
		try {
			HttpURLConnection conn 
			= (HttpURLConnection)new URL( result ).openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			conn.setRequestProperty("Authorization", property);
			
			BufferedReader reader;
			if( conn.getResponseCode()>=200 && conn.getResponseCode()<=300 ) {
				reader = new BufferedReader( new InputStreamReader(
										conn.getInputStream(), "utf-8" ) );
			}else {
				reader = new BufferedReader( new InputStreamReader(
										conn.getErrorStream(), "utf-8" ) );
			}
			url = new StringBuffer();
			while( (result = reader.readLine())!=null ) {
				url.append(result);
			}
			reader.close();
			conn.disconnect();
			result = url.toString();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public String requestAPI(StringBuffer url) {
		String result = url.toString();
		try {
			HttpURLConnection conn 
			= (HttpURLConnection)new URL( result ).openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			
			BufferedReader reader;
			if( conn.getResponseCode()>=200 && conn.getResponseCode()<=300 ) {
				reader = new BufferedReader( new InputStreamReader(
										conn.getInputStream(), "utf-8" ) );
			}else {
				reader = new BufferedReader( new InputStreamReader(
										conn.getErrorStream(), "utf-8" ) );
			}
			url = new StringBuffer();
			while( (result = reader.readLine())!=null ) {
				url.append(result);
			}
			reader.close();
			conn.disconnect();
			result = url.toString();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
}