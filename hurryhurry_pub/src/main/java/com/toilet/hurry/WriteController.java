package com.toilet.hurry;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toilet.dao.BbsDAO;
import com.toilet.vo.BbsVO;
import com.toilet.vo.MemVO;


@Controller
public class WriteController {
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession session;
	
	@Autowired
	private BbsDAO b_dao;

	@RequestMapping(value="/review_ok", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> writeOK(BbsVO vo){
		
		vo.setIp(request.getRemoteAddr());
		
		MemVO mvo = (MemVO) session.getAttribute("mvo");
		vo.setUserID(mvo.getUserID());
		
		b_dao.add(vo);
		
		Map<String, String> map = new Hashtable<String, String>();
		map.put("ok", "1");
		
		return map;
	}
		
}