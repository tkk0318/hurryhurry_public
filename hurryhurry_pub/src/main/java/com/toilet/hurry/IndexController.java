package com.toilet.hurry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.toilet.vo.MemVO;

@Controller
public class IndexController {
	
	@Autowired
	private HttpSession session;
	@Autowired
	private HttpServletRequest request;

	//처음 화면 접속시
		@RequestMapping("/")
		public ModelAndView map() {
			ModelAndView mv = new ModelAndView();
			if(request.getParameter("block") == null && session.getAttribute("mvo") != null) {	//정상로그인인 경우
				MemVO mvo = (MemVO)session.getAttribute("mvo");
				String userID = (String)session.getAttribute("id");
				mv.addObject("mvo", mvo);
				mv.addObject("id", userID);
			}else if(request.getParameter("block") != null){	//차단회원인 경우
				session.setAttribute("alertGO", "blockGO");
			}
			mv.setViewName("index");
			return mv;
		}
		
		@RequestMapping("/index")
		public ModelAndView map2() {
			ModelAndView mv = new ModelAndView();
			if(session.getAttribute("mvo")!=null) {
				MemVO mvo = (MemVO)session.getAttribute("mvo");
				String userID = (String)session.getAttribute("id");
				mv.addObject("mvo", mvo);
				mv.addObject("id", userID);
			}
			mv.setViewName("index");
			return mv;
		}
}
