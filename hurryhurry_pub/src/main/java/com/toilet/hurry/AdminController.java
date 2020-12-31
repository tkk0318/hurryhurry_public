package com.toilet.hurry;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.toilet.dao.BbsDAO;
import com.toilet.vo.BbsVO;
import com.toilet.vo.MemVO;
import com.toilet.vo.ReportVO;

import bbs.util.Paging;
import bbs.util.PagingForSR;
import bbs.util.PagingForTP;

@Controller
public class AdminController {
	@Autowired
	private BbsDAO b_dao;
	
	//페이징 기법을 위한 페이지당 게시물 수, 블럭페이지 수 셋팅
	private int blockList = 5;
	private int blockPage = 5;
	
	/****************신고현황페이지 연결***************/
	@RequestMapping("/report")
	public ModelAndView report_view() {
		ModelAndView mv = new ModelAndView();
		
		ReportVO[] ar = b_dao.report_view();
		
		mv.addObject("ar", ar);
		mv.setViewName("report");
		
		return mv;
	}
	
	//신고현황페이지, 등록된 전체리뷰보기 페이지 - 리뷰차단 버튼 클릭시
	@RequestMapping("/review_block")
	@ResponseBody
	public Map<String, String> review_block(String review_idx, String writer, String cPage, String searchType, String searchValue) {
		Map<String, String> map = new Hashtable<String, String>();

		//신고현황페이지에서 넘어온 경우
		if(cPage == null) {
			
			//신고처리(같은 리뷰에 대한 중복신고들까지 모두 처리)
			boolean v1 = b_dao.report_cplALL(review_idx);
			
			//신고된 리뷰 차단
			boolean v2 = b_dao.review_block(review_idx);
			
			//신고된 리뷰어 경고+1
			boolean v3 = b_dao.user_warn(writer);
			
			if(v1 == true && v2 == true && v3 == true)
				map.put("res", "1");
			else
				map.put("res", "0");
		}
		
		//전체리뷰페이지에서 넘어온 경우
		if(cPage != null) {
			//신고된 리뷰 차단
			boolean v1 = b_dao.review_block(review_idx);
			//신고된 리뷰어 경고+1
			boolean v2 = b_dao.user_warn(writer);
			
			if(v1 == true && v2 == true) {
				map.put("res", "1");
				map.put("cPage", cPage);
				
				//검색결과 나온 리뷰를 차단한 경우 검색조건을 다시 가지고 돌아가야하므로 필요
				if(searchType != null && searchValue != null) {
				map.put("searchType", searchType);
				map.put("searchValue", searchValue);
				}
			}else {
				map.put("res", "0");
			}
		}
		
		return map;
	}
	
	//신고현황페이지 - 회원차단 버튼 클릭시 (신고된 리뷰도 같이 차단)
		@RequestMapping("/member_block")
		@ResponseBody
		public Map<String, String> member_block(String review_idx, String userID){
			Map<String, String> map = new Hashtable<String, String>();
			
			//신고처리(같은리뷰에 대한 중복신고들까지 함께처리)
			boolean v1 = b_dao.report_cplALL(review_idx);
			
			//해당리뷰차단
			boolean v2 = b_dao.review_block(review_idx);
					
			//회원차단
			boolean v3 = b_dao.user_block(userID);
			
			if(v1 == true && v2 == true && v3 == true)
				map.put("res", "1");
			else
				map.put("res", "0");
			
			return map;
		}
		
	//신고현황페이지 - 신고철회 (해당 신고내역의 status: 0-> 2)
		@RequestMapping("/report_deny")
		@ResponseBody
		public Map<String, String> report_deny(String report_idx){
			Map<String, String> map = new Hashtable<String, String>();
			
			boolean v1 = b_dao.report_deny(report_idx);
			
			if(v1 == true)
				map.put("res", "1");
			else
				map.put("res", "0");
			
			return map;
		}
		
		/****************회원관리페이지 연결***************/
		@RequestMapping("/manage")
		public ModelAndView manage(String cPage) {
			ModelAndView mv = new ModelAndView();
			
			//사용자가 선택한 페이지 번호를 받아야한다. (페이징기법 때문)
			int c_page = 1;
			if(cPage != null)//뭔가가 넘어온 경우
				c_page = Integer.parseInt(cPage);
			//현재 페이지 값인 cPage라는 파라미터값을 받지 못한 경우는
			//무조건 1페이지가 기본이 된다.
			
			int rowTotal = b_dao.totalMemCount();	//전체 회원 수
			Paging page = new Paging(c_page, rowTotal, blockList, blockPage);
			
			MemVO[] ar = b_dao.everyMem(page.getBegin(), page.getEnd());
			
			//페이징 HTML 코드를 저장
			mv.addObject("p_code", page.getSb().toString());
			
			mv.addObject("ar", ar);
			mv.addObject("rowTotal", rowTotal);
			mv.addObject("nowPage", c_page);
			mv.setViewName("management");
			
			return mv;
		}
		
	//회원관리페이지 - 회원차단 버튼 클릭시
		@RequestMapping("/mem_block")
		@ResponseBody
		public Map<String, String> mem_block(String userID, String cPage){
			Map<String, String> map = new Hashtable<String, String>();
			
			boolean value = b_dao.mem_block(userID);
			
			if(value == true) {
				map.put("res", "1");
				map.put("cPage", cPage);
			}else {
				map.put("res", "0");
			}
			return map;
		}
		
	//회원관리페이지 - (차단된) 회원복구 버튼 클릭시
		@RequestMapping("/mem_recover")
		@ResponseBody
		public Map<String, String> mem_recover(String userID, String cPage){
			Map<String, String> map = new Hashtable<String, String>();
			boolean value = b_dao.mem_recover(userID);
			
			if(value == true) {
				map.put("res", "1");
				map.put("cPage", cPage);
			}else {
				map.put("res", "0");
			}
			return map;
		}
	
	/****************등록된 전체리뷰보기 페이지 연결***************/
		@RequestMapping("/totalPost")
		public ModelAndView totalPost(String cPage) {
			ModelAndView mv = new ModelAndView();
			
			String cnt = String.valueOf(b_dao.totalRevCount());	//전체리뷰개수
			
			//사용자가 선택한 페이지 번호를 받아야한다.
			int c_page = 1;
			if(cPage != null)//뭔가가 넘어온 경우
				c_page = Integer.parseInt(cPage);
			//현재 페이지 값인 cPage라는 파라미터값을 받지 못한 경우는
			//무조건 1페이지가 기본이 된다.
			
			int rowTotal = Integer.parseInt(cnt);
			PagingForTP page = new PagingForTP(c_page, rowTotal, blockList, blockPage);
			
			BbsVO[] ar = b_dao.totalReview(page.getBegin(), page.getEnd());
			
			//페이징 HTML 코드를 저장
			mv.addObject("p_code", page.getSb().toString());
			
			mv.addObject("ar", ar);
			mv.addObject("rowTotal", rowTotal);
			mv.addObject("nowPage", c_page);
			mv.addObject("totalCount", cnt);
			mv.setViewName("totalpost");

			
			return mv;
		}
		
	//등록된 전체리뷰보기 페이지 - 검색기능
		@RequestMapping("/searchReview")
		public ModelAndView searchReview(String searchType, String searchValue, String cPage) {
			ModelAndView mv = new ModelAndView();
			String cnt = String.valueOf(b_dao.SearchRevCount(searchType, searchValue));	//검색된 리뷰의 개수
			
			//사용자가 선택한 페이지 번호를 받아야한다.
			int c_page = 1;
			if(cPage != null)//뭔가가 넘어온 경우
				c_page = Integer.parseInt(cPage);
			//현재 페이지 값인 cPage라는 파라미터값을 받지 못한 경우는
			//무조건 1페이지가 기본이 된다.
			
			int rowTotal = Integer.parseInt(cnt);
			PagingForSR page = new PagingForSR(c_page, rowTotal, blockList, blockPage, searchType, searchValue);
			BbsVO[] ar = b_dao.searchReview(searchType, searchValue, page.getBegin(), page.getEnd());
			
			//페이징 HTML 코드를 저장
			mv.addObject("p_code", page.getSb().toString());
			
			mv.addObject("ar", ar);
			mv.addObject("rowTotal", rowTotal);
			mv.addObject("nowPage", c_page);
			mv.addObject("SearchRevCount", cnt);
			mv.addObject("searchType", searchType);
			mv.addObject("searchValue", searchValue);
			mv.setViewName("searchReview");
			
			return mv;
		}
		
	//등록된 전체리뷰보기 페이지 - 리뷰 차단해제 버튼 클릭시
		@RequestMapping("review_recover")
		@ResponseBody
		public Map<String, String> review_recover(String review_idx, String userID, String cPage, 
														String searchType, String searchValue){
			Map<String, String> map = new Hashtable<String, String>();
			
			boolean v1 = b_dao.review_recover(review_idx);	//리뷰차단해제
			boolean v2 = b_dao.mem_statDOWN(userID);	//차단된 리뷰를 쓴 회원의 status -1 회복
			
			if(v1 == true && v2 == true) {
				map.put("res", "1");
				map.put("cPage", cPage);
				
				//검색결과에 나온 리뷰의 차단해제를 하는 경우 필요
				if(searchType != null && searchValue != null) {
				map.put("searchType", searchType);
				map.put("searchValue", searchValue);
				}
			}else
				map.put("res", "0");
			
			return map;
			
		}
}
