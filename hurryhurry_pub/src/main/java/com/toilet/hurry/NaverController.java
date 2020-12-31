package com.toilet.hurry;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.plus.PlusOperations;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.toilet.dao.BbsDAO;
import com.toilet.dao.MemDAO;
import com.toilet.vo.BbsVO;
import com.toilet.vo.MemVO;



@Controller
public class NaverController {
	
	/* NaverLoginBO */
	private NaverLoginBO naverLoginBO;
	private String apiResult = null;

	@Autowired
	private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO;
	}
	
	/* GoogleLogin */
	@Autowired
	private GoogleConnectionFactory googleConnectionFactory;
	@Autowired
	private OAuth2Parameters googleOAuth2Parameters;

	@Autowired
	private MemDAO m_dao;
	
	@Autowired
	private HttpSession session;
	
	//네이버아이디로 로그인!
	
	//로그인 첫 화면 요청 메소드
	@RequestMapping("/naverLogin")
	public String naverlogin(HttpSession session) {
		
		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 */
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		
		System.out.println("네이버:" + naverAuthUrl);
		
		/*네이버 로그인 창으로 이동*/
		return "redirect:"+naverAuthUrl;
	}

	//네이버 로그인 성공시 callback호출 메소드
	@RequestMapping("/callback")
	public ModelAndView callback(@RequestParam String code, @RequestParam String state, HttpSession session)
			throws IOException, ParseException {
		System.out.println("여기는 callback");
		OAuth2AccessToken oauthToken;
        oauthToken = naverLoginBO.getAccessToken(session, code, state);
        
        //1.로그인 사용자 정보를 읽어온다.
	    apiResult = naverLoginBO.getUserProfile(oauthToken); //String형식의 json데이터

	    /** apiResult json 구조
	    {"resultcode":"00",
	    "message":"success",
	    "response":{"id":"33666449","nickname":"shinn****","age":"20-29","gender":"M","email":"sh@naver.com","name":"\uc2e0\ubc94\ud638"}}
	    **/
	    
	    //2. String형식인 apiResult를 json형태로 바꿈
	    JSONParser parser = new JSONParser();
	    Object obj = parser.parse(apiResult);
	    JSONObject jsonObj = (JSONObject) obj;
	    
	    //3. 데이터 파싱
	    //Top레벨 단계 _response 파싱
	    JSONObject response_obj = (JSONObject)jsonObj.get("response");
	    //response의 email값 파싱
	    String userID = (String)response_obj.get("email");
	    System.out.println(userID);
	    
	    MemVO lvo = m_dao.login(userID);//로그인
	    
	    if(lvo == null) {										 
			 m_dao.socialID(userID);//DB에 아이디가 없다면 저장
		   lvo=m_dao.login(userID);				
		}
		
	    ModelAndView mv = new ModelAndView();
	    
		if(lvo.getStatus().equals("3")) {	//차단회원일 경우
			mv.setViewName("redirect:/?block=yes");
		}else {								//정상회원인 경우
			session.setAttribute("id", userID);
			session.setAttribute("mvo", lvo);//로그인
			mv.addObject("userID", userID);
			mv.setViewName("index");
		}
	    
        /* 네이버 로그인 성공시 index페이지 호출 */
		return mv;
	}
	
	//구글 아이디로 로그인!
	
			// 로그인 첫 화면 요청 메소드
			@RequestMapping("/googleLogin")
			public String googleLogin(HttpSession session) {

				/* 구글code 발행 */
				OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
				String google_url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);

				System.out.println("구글:" + google_url);
				
				/* 생성한 인증 URL을 View로 전달 */
				return "redirect:"+google_url;
			}

			// 구글 Callback호출 메소드
			@RequestMapping("/callbackGG")
			public ModelAndView googleCallback(Model model, @RequestParam String code,  HttpSession session) throws IOException {
				System.out.println("여기는 googleCallback");

				OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
				System.out.println(googleOAuth2Parameters.getRedirectUri());
				AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, googleOAuth2Parameters.getRedirectUri(),
						null);
				String accessToken = accessGrant.getAccessToken();
				System.out.println("accessToken: " + accessToken);
				Long expireTime = accessGrant.getExpireTime();
				if (expireTime != null && expireTime < System.currentTimeMillis()) {
					accessToken = accessGrant.getRefreshToken();
					System.out.printf("accessToken is expired. refresh token = {}", accessToken);
				}
				Connection<Google> connection = googleConnectionFactory.createConnection(accessGrant);
				
				if(connection == null)
					System.out.println("커넥션널");
				else
					System.out.println("널아님");
				
				Google google = connection == null ? new GoogleTemplate(accessToken) : connection.getApi();
				
				PlusOperations plusOperations = google.plusOperations();
				Person person = plusOperations.getGoogleProfile();
				
				String userID = person.getAccountEmail();
				
				MemVO lvo = m_dao.login(userID);//로그인
				
				if(lvo == null) {										 
					 m_dao.socialID(userID);//DB에 아이디가 없다면 저장
				   lvo=m_dao.login(userID);				
				}
				
				session.setAttribute("id", userID);
				session.setAttribute("mvo", lvo);//로그인
				
				ModelAndView mv = new ModelAndView();
				mv.addObject("userID", userID);
				mv.setViewName("index");

				/* 구글 로그인 성공시 index페이지 호출 */
				return mv;
			}
	}


