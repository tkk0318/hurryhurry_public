package com.toilet.hurry;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.toilet.dao.BbsDAO;
import com.toilet.vo.BbsVO;
import com.toilet.vo.LikeVO;
import com.toilet.vo.MemVO;
import com.toilet.vo.ReportVO;

@Controller
public class ReviewController {
	
	@Autowired
	private BbsDAO b_dao;
	@Autowired
	private HttpSession session;
	@Autowired
	private HttpServletRequest request;
	
	 @RequestMapping("/review")
	 public String review() {
	 
	  return "review";
	 
	 }
	 
	 //해당 화장실 선택시 리뷰불러오기, 접속중인 회원의 해당리뷰에 대한 like상태 파악하기
	 @RequestMapping("/ToiletReview")
	 @ResponseBody
	 public Map<String,Object> toilet_review(String toilet_name, String IDforLike) {
		 Map<String, Object> map = new Hashtable<String, Object>();
		 
		 BbsVO[] vo = b_dao.toiletReview(toilet_name);	//해당 화장실의 전체리뷰 불러오기
		 
		 /**좋아요 기능**/
		 LikeVO[] like_rvs = b_dao.like_didyou(IDforLike);	//접속중인 사용자가 좋아요 한 리뷰 불러오기
		 String[] like_rvsIDX = null;	//사용자가 좋아요한 리뷰들의 idx들을 저장할 배열
		 
		 if(like_rvs != null) {		//사용자가 좋아요한 리뷰가 1개라도 있다면
			 like_rvsIDX = new String[like_rvs.length];
			 for(int i=0; i<like_rvs.length; i++) {
				 like_rvsIDX[i] = like_rvs[i].getReview_idx();
			}
		}
		 
		 if(vo!=null) {
			 map.put("vo", vo);
		 	if(like_rvs != null) {	//사용자가 좋아요한 리뷰가 1개라도 있을 때
		 			map.put("like_rvsIDX", like_rvsIDX);
		 	}
		 }else if(vo == null) {
			 map.put("chk", "n");
		 }
		
		return map;
	 }	 
	 	 
	 //해당 화장실 본인 리뷰 수정
	 @RequestMapping("/editToilet")
	 @ResponseBody
		public Map<String, String> editToilet(BbsVO vo){
		 	Map<String, String> map = new Hashtable<String, String>();
			
			boolean value = b_dao.editBbs(vo);	//리뷰내용 수정하기			
			String editContent = b_dao.getEditContent(vo.getReview_idx());	//수정된 리뷰내용 가져오기

			if(value) {	//수정 완료시
				map.put("res", "1");
				map.put("editContent", editContent);
			}else					//수정 실패시
				map.put("res", "0");				
				
			return map;
		}
		
	//해당 화장실 페이지에서 본인 리뷰 삭제
	@RequestMapping("/delToilet")
	@ResponseBody
		public Map<String, String> delToilet(String review_idx) {
			Map<String, String> map = new Hashtable<String, String>();
			
			boolean value = b_dao.delBbs(review_idx);
			if(value)	//삭제 완료시
				map.put("res", "1");
			else
				map.put("res", "0");
			
			return map;
		} 
		
	//리뷰 좋아요 기능
	@RequestMapping("/likeUpdate")
	@ResponseBody
	public Map<String, String> likeUpdate(LikeVO lvo) {
		Map<String, String> map = new Hashtable<String, String>();
		int result = b_dao.likeCheck(lvo);
		
		if(result == 0) {	//기존에 좋아요 하지 않은 경우
			if(b_dao.likeNew(lvo))
				map.put("res", "1");	//좋아요 성공
			else
				map.put("res", "2");	//좋아요 실패
		}else {	//기존에 좋아요를 했던 경우
			if(b_dao.likeDelete(lvo))
				map.put("res", "3");	//좋아요 취소 성공
			else
				map.put("res", "4");	//좋아요 취소 실패
		}
		return map;
	}
	
	//리뷰 신고하기
	@RequestMapping("/reportReview")
	@ResponseBody
	public Map<String, String> reportReview(ReportVO rpvo){
		Map<String, String> map = new Hashtable<String, String>();
		
		//리뷰 신고사유와 review_idx는 rpvo에 들어와있음
		
		/**리뷰 신고테이블에 넣을 나머지 값들 rpvo에 set해주어야 함**/
		//리뷰idx를 가지고 화장실 정보를 모두 가져온다
		BbsVO bvo = b_dao.getToiletInfo(rpvo.getReview_idx());
		//해당 화장실 이름
		rpvo.setToilet_name(bvo.getToilet_name());
		//리뷰쓴 회원ID
		rpvo.setWriter(bvo.getUserID());
		//리뷰 내용 얻어오기
		rpvo.setContent(bvo.getContent());
		
		//IP값 셋팅
		rpvo.setIp(request.getRemoteAddr());
		
		//리뷰 신고
		boolean value = b_dao.reportReview(rpvo);
		
		if(value) {	//신고접수 성공시
			map.put("res", "1");
		}else		//신고접수 실패시
			map.put("res", "0");
		
		return map;
	}
	
	//해당 화장실 평균 별점 구하기
	@RequestMapping(value="/avgStar", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> avgStar(String toilet_name) {
		Map<String, Object> map = new Hashtable<String, Object>();

		String avgStar = b_dao.avgStar(toilet_name);
		/* int avgStar = 0;
		
		if(list != null && list.size() > 0) {
			for(int i=0; i > list.size(); i++) {
				avgStar += i;
			}*/
		if(avgStar != null) {
			map.put("avgStar", avgStar);
			map.put("res", 1);
		} else {
			map.put("res", 0);
		}
		
		
		return map;
	}
	

}