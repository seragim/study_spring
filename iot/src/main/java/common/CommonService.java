package common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class CommonService {

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