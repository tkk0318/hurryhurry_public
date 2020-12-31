package com.toilet.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//일반회원이 관리자페이지에 get방식으로 접근했을 때 접근을 인터셉트하는 곳
		
		//로그인 체크
		HttpSession session = request.getSession(true); //true: session이 있으면 그걸 주고, 없으면 새로 만들어주세요.
														//cf. false: session이 있으면 그걸 주고, 없으면 주지마세요.
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//로그인 시 저장한 객체 얻어내기
				Object obj = session.getAttribute("mvo");

				if(obj == null) {	//로그인을 안했을 때 : 경고창 + 첫화면으로 돌아감
					out.println("<script>alert('로그인이 필요합니다.'); location.href='/';</script>");
					return false;
				}else{	// 로그인을 했을 때
					String userID = (String) session.getAttribute("id");
					if(!userID.startsWith("alstnddl92")) {	//ADMIN이 아닐 때 : 경고창 + 첫화면으로 돌아감
						out.println("<script>alert('관리자로 접속해주세요.'); location.href='/';</script>");
						return false;	
					}
				}
		
		return true;	//관리자로 로그인했을 때
	}

	
}
