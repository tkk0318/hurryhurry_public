package com.toilet.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toilet.vo.BbsVO;
import com.toilet.vo.LikeVO;
import com.toilet.vo.MemVO;
import com.toilet.vo.ReportVO;

@Component
public class BbsDAO {
	
	@Autowired
	private SqlSessionTemplate sst;
		
	//마이페이지 항목 불러오기
	public BbsVO[] getList(String userID) {
		
		
		List<BbsVO> list = sst.selectList("bbs.list", userID);
		
		BbsVO[] ar = null;
		
		if(list != null && list.size() > 0) {
			ar = new BbsVO[list.size()];
			
			// list에 있는 요소들을 ar에 복사해 넣는다.
			list.toArray(ar);
		}
		return ar;
	}
	
	
	//해당 화장실 리뷰 불러오기
		public BbsVO[] toiletReview(String toilet_name) {	
		
			List<BbsVO> list = sst.selectList("bbs.toilet",toilet_name);
			
			BbsVO[] ar = null;
			
			if(list != null && list.size() > 0) {
				ar = new BbsVO[list.size()];
				
				// list에 있는 요소들을 ar에 복사해 넣는다.
				list.toArray(ar);
			}				
			return ar;				
		}	
	
	//리뷰수정
	public boolean editBbs(BbsVO vo) {
		boolean value = false;
		int cnt = sst.update("bbs.edit", vo);
		
		if(cnt > 0) //수정 성공시
			value = true;	
		return value;
	}
	
	//리뷰수정 후 수정된 리뷰내용 가져오기
	public String getEditContent(String review_idx) {
		String editContent = sst.selectOne("bbs.getEditContent", review_idx);
		return editContent;
	}
	
	//리뷰 삭제
	public boolean delBbs(String review_idx) {
		boolean value = false;
		int cnt = sst.update("bbs.del", review_idx);		
		if(cnt > 0)
			value = true;
		
		return value;
	}
	
	//리뷰 다 불러오기(임시)
	public BbsVO[] review() {
		
		BbsVO[] ar = null;
		
		List<BbsVO>list = sst.selectList("bbs.all");
		
		if(list != null && list.size() > 0) {
			ar = new BbsVO[list.size()];
			
			// list에 있는 요소들을 ar에 복사해 넣는다.
			list.toArray(ar);
		}
		
		return ar;
	}

	// 리뷰 저장
	public void add(BbsVO vo) {
		int cnt = sst.insert("bbs.add", vo);
	}
	
	//리뷰 추천여부 검사
	public int likeCheck(LikeVO lvo){
		int result = sst.selectOne("bbs.like_check", lvo);
		return result;
	}
	
	//리뷰 추천
	public boolean likeNew(LikeVO lvo){
		int cnt = sst.insert("bbs.like_new", lvo);
		boolean value = false;
		
		if(cnt > 0)	//좋아요 완료!
			value = true;
		
		return value;
	}
	
	//리뷰 추천 제거
	public boolean likeDelete(LikeVO lvo){
		int cnt = sst.delete("bbs.like_delete", lvo);
		boolean value = false;
		
		if(cnt > 0)	//좋아요 취소완료!
			value = true;
		
		return value;
	}
	
	
	//게시글 추천여부체크 (좋아요 버튼 색깔 구분위함)
	public LikeVO[] like_didyou(String userID) {
		LikeVO[] like_rvs = null;
		
		List<LikeVO> list = sst.selectList("bbs.like_didyou", userID);
		if(list != null && list.size() > 0) {
			like_rvs = new LikeVO[list.size()];
			list.toArray(like_rvs);
		}
		return like_rvs;
	}
	
	//리뷰 신고하기
	public boolean reportReview(ReportVO rpvo) {
		int cnt = sst.insert("bbs.reportReview", rpvo);
		boolean value = false;
		
		if(cnt > 0)
			value = true;
		
		return value;
	}
	
	//리뷰 신고 시 필요한 화장실 이름 얻어오기
	public BbsVO getToiletInfo(String review_idx) {
		BbsVO vo = sst.selectOne("bbs.getToiletInfo", review_idx);
		
		return vo;
	}
	
	// 평균 별점
	public String avgStar(String toilet_name) {
		String avgStar = sst.selectOne("bbs.avgStar", toilet_name);
		avgStar = String.format("%.2f", Double.parseDouble(avgStar));
		return avgStar;
	}
	
	
	/******신고현황 페이지*******/
	//신고현황 페이지 - 신고 내역 확인
	public ReportVO[] report_view() {
		ReportVO[] ar = null;
		List<ReportVO> list = sst.selectList("bbs.report_view");
		
		if(list != null && list.size() > 0) {
			ar = new ReportVO[list.size()];
			
			list.toArray(ar);
		}
		
		return ar;
	}
	
	//신고현황 페이지 - 신고글 처리기능 (status 0 -> 1)
	public boolean report_cpl(String report_idx) {
		boolean value = false;
		int cnt = sst.update("bbs.report_cpl", report_idx);
		
		if(cnt > 0)
			value = true;
		
		return value;
	}

	//신고현황 페이지 - 같은 리뷰에 대한 중복신고 처리
	public boolean report_cplALL(String review_idx) {
		boolean value = false;
		int cnt = sst.update("bbs.report_cplALL", review_idx);
				
		if(cnt > 0)
			value = true;
		
		return value;
	}
	
	//신고현황 페이지 - 신고된 리뷰 차단 (status 0 -> 1)
	public boolean review_block(String review_idx) {
		boolean value = false;
		int cnt = sst.update("bbs.review_block", review_idx);
		
		if(cnt > 0)
			value = true;
		
		return value;
	}

	//신고현황 페이지 - 신고된 회원 경고 (status+1)
	public boolean user_warn(String writer) {
		boolean value = false;
		int cnt = sst.update("bbs.user_warn", writer);
		
		if(cnt > 0)
			value = true;
		
		return value;
	}
	
	//신고현황 페이지 - 신고된 회원 차단(status == 3)
		public boolean user_block(String userID) {
			boolean value = false;
			int cnt = sst.update("bbs.user_block", userID);
			
			if(cnt > 0)
				value = true;
			
			return value;
		}
		
	//신고현황 페이지 - 신고철회 (status: 0 -> 2)
		public boolean report_deny(String report_idx) {
			boolean value = false;
			int cnt = sst.update("bbs.report_deny", report_idx);
			
			if(cnt > 0)
				value = true;
			
			return value;
		}
	
		
	/******회원관리 페이지*******/
	//회원관리페이지 - 모든 회원내역 불러오기
		public MemVO[] everyMem(int begin, int end) {
			Map<String, String> map = new Hashtable<String, String>();
			
			map.put("begin", String.valueOf(begin));
			map.put("end", String.valueOf(end));
			
			List<MemVO> list = sst.selectList("bbs.everyMem", map);
			MemVO[] ar = null;
			
			if(list != null && list.size() > 0) {
				ar = new MemVO[list.size()];
				list.toArray(ar);
			}
			return ar;
		}
		
	//회원관리페이지 - 페이징기법을 위해 회원 전체 수 구하기
		public int totalMemCount() {
			int cnt = sst.selectOne("bbs.totalMemCount");
			
			return cnt;
		}
		
	//회원관리페이지 - 회원차단
		public boolean mem_block(String userID) {
			boolean value = false;
			int cnt = sst.update("bbs.mem_block", userID);
			
			if(cnt > 0)
				value = true;
			
			return value;
		}
		
	//회원관리페이지 - 차단된 회원복구
		public boolean mem_recover(String userID) {
			boolean value = false;
			int cnt = sst.update("bbs.mem_recover", userID);
			
			if(cnt > 0)
				value = true;
			
			return value;
		}
	
	/******전체리뷰 페이지*******/
	//등록된 전체리뷰 페이지 - 전체 리뷰 불러오기
		public BbsVO[] totalReview(int begin, int end) {
			Map<String, String> map = new Hashtable<String, String>();
			
			map.put("begin", String.valueOf(begin));
			map.put("end", String.valueOf(end));
			
			List<MemVO> list = sst.selectList("bbs.totalReview", map);
			BbsVO[] ar = null;
			
			if(list != null && list.size() > 0) {
				ar = new BbsVO[list.size()];
				list.toArray(ar);
			}
			return ar;
		}
	
	//등록된 전체리뷰 페이지 - 등록된 리뷰 전체 개수 구하기
		public int totalRevCount() {
			int cnt = sst.selectOne("bbs.totalRevCount");
			return cnt;
		}
		
	//등록된 전체리뷰 페이지 - 리뷰 검색기능
		public BbsVO[] searchReview(String searchType, String searchValue, int begin, int end) {
			Map<String, Object> map = new Hashtable<String, Object>();
			map.put("searchType", searchType);
			map.put("searchValue", searchValue);
			map.put("begin", begin);
			map.put("end", end);
			
			BbsVO[] ar = null;
			List<BbsVO> list = sst.selectList("bbs.searchReview", map);
			
			if(list != null && list.size() > 0) {
				ar = new BbsVO[list.size()];
				list.toArray(ar);
			}
			return ar;
		}
	
	//등록된 전체리뷰 페이지 - 검색된 리뷰 개수 구하기
		public int SearchRevCount(String searchType, String searchValue) {
			Map<String, String> map = new Hashtable<String, String>();
			map.put("searchType", searchType);
			map.put("searchValue", searchValue);
			
			int cnt = sst.selectOne("bbs.searchRevCount", map);
			
			return cnt;
		}
		
	//등록된 전체리뷰 페이지 - 리뷰차단 해제하기
		public boolean review_recover(String review_idx) {
			boolean value = false;
			int cnt = sst.update("bbs.review_recover", review_idx);
			
			if(cnt > 0)
				value = true;
			
			return value;
		}
		
	//등록된 전체리뷰 페이지 - 리뷰차단해제하면서 회원status -1 회복
		public boolean mem_statDOWN(String userID) {
			boolean value = false;
			int cnt = sst.update("bbs.mem_statDOWN", userID);
			
			if(cnt > 0)
				value = true;
			
			return value;
		}
}