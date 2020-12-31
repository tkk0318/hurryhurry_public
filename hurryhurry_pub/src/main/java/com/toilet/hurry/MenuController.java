package com.toilet.hurry;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.toilet.dao.BbsDAO;
import com.toilet.dao.MemDAO;
import com.toilet.vo.MemVO;

@Controller
public class MenuController {
	@Autowired
	private MemDAO m_dao;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/menu")
	public String menu() {
		return "menu";
	}
	
	//임시로그인 페이지 이동(은혜테스트중)
	@RequestMapping("/temp_login")
	public String temp_login() {
		return "temp_login";
	}
	
	//임시로그인
	@RequestMapping("/temp_loginOK")
	public ModelAndView temp_loginOK(String s_id){
		//System.out.println("s_id");
		ModelAndView mv = new ModelAndView();
		MemVO mvo = m_dao.temp_login(s_id);
		
		if(mvo.getStatus().equals("3")) {	//차단된 회원인 경우(status>=3)
			mv.setViewName("redirect:/?block=yes");
		}else {
			mv.addObject("mvo", mvo);
			session.setAttribute("mvo", mvo);
			session.setAttribute("id", s_id);
			mv.setViewName("index");
		}
		return mv;
		
	}
	
	//로그아웃
	@RequestMapping("logout")
	public String temp_logout() {
		session.removeAttribute("mvo");
		session.removeAttribute("id");
		return "index";
	}
	
}
