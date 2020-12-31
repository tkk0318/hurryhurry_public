package com.toilet.hurry;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.toilet.dao.BbsDAO;
import com.toilet.vo.BbsVO;
import com.toilet.vo.MemVO;






@Controller
public class MypageController {
	
	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	private HttpSession session;
	
	//마이페이지 조회
	@RequestMapping("/mypage")
	public ModelAndView mypage() {		
		ModelAndView mv = new ModelAndView();
		MemVO mvo = (MemVO) session.getAttribute("mvo");
		BbsVO[] ar = b_dao.getList(mvo.getUserID());
		//System.out.println(mvo.getUserID());
		mv.addObject("ar",ar);
		
		return mv;
	}
	
	//리뷰수정
	@RequestMapping("/edit")
	public ModelAndView edit(BbsVO vo){
		ModelAndView mv = new ModelAndView();		
		b_dao.editBbs(vo);		
		MemVO mvo = (MemVO) session.getAttribute("mvo");
		BbsVO[] ar = b_dao.getList(mvo.getUserID());				  

		mv.addObject("ar",ar);
		mv.setViewName("mypage");
			
		return mv;
	}
	
	//리뷰삭제
	@RequestMapping("/del")
	public ModelAndView del(String review_idx) {
		ModelAndView mv = new ModelAndView();
		MemVO mvo = (MemVO) session.getAttribute("mvo");
		b_dao.delBbs(review_idx);		
		BbsVO[] ar = b_dao.getList(mvo.getUserID());		
		
		mv.addObject("ar",ar);				
		mv.setViewName("mypage");
		
		return mv;
	}
	
	
}
