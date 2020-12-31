package com.toilet.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toilet.vo.MemVO;

@Component
public class MemDAO {
	
	@Autowired
	private SqlSessionTemplate sst;
	
	public MemVO[] getAll() {
		MemVO[] ar = null;
		
		List<MemVO> list = sst.selectList("mem.all");
		if(list != null && list.size() > 0) {
			ar = new MemVO[list.size()];// 현재 배열은 MemVO가 생성된 것이 아니라
			// MemVO를 저장할 수 있는 공간이 마련된 것이다. 그크기가 list의 크기와 같다.
			
			//list에 있는 각 요소들을 배열인 ar에 복사한다.
			list.toArray(ar);
		}
		
		return ar;
	}
	
	//로그인 기능
	public MemVO login(String userID) {
		Map<String, String> map = new Hashtable<String, String>();
		map.put("userID", userID);
		MemVO mvo = sst.selectOne("mem.login", map);
		
		return mvo;
	}
	
	//임시로그인 기능(은혜테스트용)
	public MemVO temp_login(String userID) {
		MemVO mvo = sst.selectOne("mem.temp_login", userID);
		return mvo;
	}
	
	//소셜로그인 (카카오, 네이버, 구글 모두 이걸로!)
	public int socialID(String userID) {				
		int cnt = sst.insert("mem.socialID", userID);		
		return cnt;
	}
	
}
