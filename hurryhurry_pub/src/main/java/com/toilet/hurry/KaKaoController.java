package com.toilet.hurry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.toilet.dao.MemDAO;
import com.toilet.vo.MemVO;


@Controller
public class KaKaoController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private MemDAO m_dao;
	
	@RequestMapping("/kakao") //루트에서부터 절대경로
	public ModelAndView kakaoLogin(String code) {
		//카카오서버에서 인증코드를 전달해 주는 곳
		ModelAndView mv = new ModelAndView();
		
		//인증코드는 인자인 code로 받는다.
		//System.out.println("CODE:"+code);
		
		//받은 코드를 가지고 다시 토큰을 받기 위한 작업 - POST방식
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		
		//통신하기위한 예외 처리
		try {
			URL url = new URL(reqURL);
			//다운캐스팅을 시킨다
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			//POST방식으로 요청하기 위해 setDoOutput을 true로 지정
			conn.setRequestMethod("POST");//요청방식 설정
			conn.setDoOutput(true);
			
			//인자 4개를 만들어서 카카오 서버로 보낸다.
			//grant_type, client_id,redirect_uri,code
			StringBuffer sb = new StringBuffer();
			sb.append("grant_type=authorization_code&client_id=restapi키값");
			sb.append("&redirect_uri=http://localhost:8080/kakao");
			sb.append("&code="+code);
			
			//전달하고자 하는 파라미터들을 보낼 스트림 준비
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(sb.toString());//요청을 POST방식으로 보낸다.
			bw.flush();//스트림 비우기
			
			//결과코드가 200이면 성공!!
			int res_code = conn.getResponseCode();
			//System.out.println("RES_CODE"+res_code);
			
			if(res_code == 200) { //요청이 성공시 
				//요청을 통해 얻은 JSON타입의 결과메세지를 읽어온다.
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				String line = "";
				StringBuffer result = new StringBuffer();
				while((line = br.readLine()) != null) {
					result.append(line);
				}//while의 끝!
				
				br.close();
				bw.close();
				
				//받은 결과 확인
				//System.out.println(result.toString());
				
				//JSON파싱 처리
				//"access_token"과 "refresh_token"을 잡아내어 ModelAndView에 저장한 후 
				//result.jsp로 이동하여 결과를 표현한다.
				JSONParser j_par = new JSONParser();
				Object obj = j_par.parse(result.toString());
				
				//자바에서 편하게 사용할 수 있도록 JSON객체로 변환하자
				JSONObject j_obj = (JSONObject) obj;
				
				access_Token = (String) j_obj.get("access_token");
				refresh_Token = (String)j_obj.get("refresh_token");
				
				//사용자 정보를 얻기 위해 토큰을 이용한 서버요청시작
				String header = "Bearer " + access_Token;
				String apiURL = "https://kapi.kakao.com/v2/user/me";
				
				URL url2 = new URL(apiURL);
				HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
				
				//POST방식 설정
				conn2.setRequestMethod("POST");
				conn2.setDoOutput(true);
				
				conn2.setRequestProperty("Authorization", header);
				
				res_code = conn2.getResponseCode();
			
				if(res_code == HttpURLConnection.HTTP_OK) {
					//정상적으로 사용자 정보를 요청했다면...
					BufferedReader brd = new BufferedReader(
							new InputStreamReader(conn2.getInputStream()));
					
					StringBuffer sBuff = new StringBuffer();
					String str = null;
					while((str= brd.readLine()) !=null) {
						sBuff.append(str);//카카오 서버에서 전달되는 모든 값들이
										//sBuff에 누적된다.
					}//while의 끝
					
					obj = j_par.parse(sBuff.toString());//JSON인식
					
					//JSON으로 인식된 정보를 다시 JSON객체로 형변환한다.
					j_obj = (JSONObject) obj;
					
					JSONObject n = (JSONObject) j_obj.get("kakao_account");
					
					//System.out.println("obj="+obj);
					//System.out.println("j_obj="+j_obj);					
					
					String userID = (String) n.get("email");					
				
					MemVO lvo = m_dao.login(userID);//로그인
					
					if(lvo == null) {										 
						 m_dao.socialID(userID);//DB에 아이디가 없다면 저장
					   lvo=m_dao.login(userID);				
					}
					
					if(lvo.getStatus().equals("3")) {	//차단회원일 경우
						mv.setViewName("redirect:/?block=yes");
					}else {								//정상회원인 경우
						session.setAttribute("id", userID);
						session.setAttribute("mvo", lvo);//로그인
						mv.addObject("userID", userID);
						mv.setViewName("index");
					}
					
				}//res_code == HttpURLConnection.HTTP_OK if문의 끝
			}//res_code == 200 if문의 끝
		}catch(Exception e){	
			e.printStackTrace();
		}
		return mv;
	}
}
