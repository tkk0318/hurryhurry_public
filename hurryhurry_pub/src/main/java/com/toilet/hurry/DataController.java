package com.toilet.hurry;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toilet.vo.DataVO;

@Controller
public class DataController {

//자료에 예외가 있어서 설정한 변수
private int CHILD_M_B = 0;
private int CHILD_M_S = 0;
private int CHILD_W_B = 0;
	
	//검색화면으로 가기 (테스트용 - 주소검색만 됩니다)
	@RequestMapping("/testList")
	public String testList() {
		return "index";
	}
	
	//(1)전체 데이터 불러오기
	@RequestMapping("/allList")
	@ResponseBody
	public Map<String, Object> Alllist  (double lat, double lon)  throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONParser jsonParser = new JSONParser();
		
		//json 파싱
		DefaultResourceLoader resourceloade = new DefaultResourceLoader();
		Resource resource = resourceloade.getResource("classpath:data/전국공중화장실표준데이터.json");
		String r = resource.getFile().getAbsolutePath();
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(r));
	    
	    
	    JSONArray jsonar = (JSONArray) jsonObject.get("records");
	    	
	    	List<Object> lista = new ArrayList<Object>();
	    
	    	
	    	//전체 다 돈다
	    	for(int i=0; i<jsonar.size();i++) {
	    		JSONObject object = (JSONObject) jsonar.get(i);
	 	    	String ADDRESS =(String) object.get("소재지도로명주소");
	 	    	String ADDRESS2 =(String) object.get("소재지지번주소");
	 	    	String TOILET_NAME =(String) object.get("화장실명");
		 	    String HOURS =(String) object.get("개방시간");
		 	    String GENDER =(String) object.get("남녀공용화장실여부");
		 	    String PHONE =(String) object.get("전화번호");
		 	    String DIAPER_CHANGED =(String) object.get("기저귀교환대장소");
		 	    int IMPAIRED_M_B = Integer.parseInt((String) object.get("남성용-장애인용대변기수"));
		 	    int IMPAIRED_M_S = Integer.parseInt((String) object.get("남성용-장애인용소변기수"));
		 	    int IMPAIRED_W_B = Integer.parseInt((String) object.get("여성용-장애인용대변기수"));
		 	   
		 	    if(isNumberbic((String) object.get("남성용-어린이용대변기수")))
		 	    	CHILD_M_B = Integer.parseInt((String) object.get("남성용-어린이용대변기수"));
		 	    
		 	   if(isNumberbic((String) object.get("남성용-어린이용소변기수")))
		 		   CHILD_M_S = Integer.parseInt((String) object.get("남성용-어린이용소변기수"));
		 	   
		 	  if(isNumberbic((String) object.get("여성용-어린이용대변기수")))
		 		  CHILD_W_B = Integer.parseInt((String) object.get("여성용-어린이용대변기수"));
		 	  
		 	    String mapy = (String) object.get("위도");
		 	    String mapx = (String) object.get("경도");
		 	   if(isNumberbic(mapy) && isNumberbic(mapx)) {
			 		 double dmapy = Double.parseDouble(mapy);
				 	 double dmapx = Double.parseDouble(mapx);
				 	 int radius = 3;
				 	 double colC = col(lat, lon, dmapy, dmapx);
				 	 if(colC < radius) {
			 	    	DataVO vo = new DataVO(TOILET_NAME, ADDRESS, ADDRESS2, HOURS, GENDER, PHONE, DIAPER_CHANGED, mapx, mapy, 
			 	    			IMPAIRED_M_B, IMPAIRED_M_S, IMPAIRED_W_B ,CHILD_M_B, CHILD_M_S,CHILD_W_B);
			 	    	
						
						lista.add(vo);
			 	    }		
			 	  }
					
	    	}
	    DataVO[] ar;
	    ar = new DataVO[lista.size()];
	    lista.toArray(ar);
		map.put("list", ar);
		
		
	       
	    return map;

	}
	
	
	
	//(2) 상세 검색을 하는 경우
	@RequestMapping("/dtSearch")
	@ResponseBody
	public Map<String, Object> SearchDT (String search, int gender, int child, int impaired, double lat, double lon) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Object> lista = new ArrayList<Object>();
		
		JSONParser jsonParser = new JSONParser();
		
		//json 파싱
		DefaultResourceLoader resourceloade = new DefaultResourceLoader();
		Resource resource = resourceloade.getResource("classpath:data/전국공중화장실표준데이터.json");
		String r = resource.getFile().getAbsolutePath();
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(r));
	    
	    //만약 주소를 입력했다면, 주소를 추출하고, 아니라면 gps 신호 위치를 추출한다
	    if(search!=null && search.trim().length()>1) {
	    	//주소입력경우 
	    	 //현재 주소의 마지막 부분(예를들어 동) 추출
		    String newaddr;
		    
		    if(search.contains(" ")) {
		    	int bk = search.lastIndexOf(" ");
			    newaddr = search.substring(bk, search.length());
		    	
		    }else {
		    	//만약 공백없이 입력했다면
		    	newaddr = search;	
		    }
		    
		    JSONArray jsonar = (JSONArray) jsonObject.get("records");
		    	
		    	//적당한 수가 나올때 까지 반복문을 돌려서 검색한다
		    	
		    	int count =0;
		    	int count2 = 0;
		    	
		    	//5개의 결과가 나올때 까지 돈다
		    	while(count<10) {
		    		JSONObject object = (JSONObject) jsonar.get(count2);
		 	    	String ADDRESS =(String) object.get("소재지도로명주소");
		 	    	String ADDRESS2 =(String) object.get("소재지지번주소");
		 	    	count2++;
		 	    	
		 	    	if(ADDRESS ==null)
		 	    		ADDRESS = " ";
		 	    	if(ADDRESS2 ==null)
		 	    		ADDRESS2 = " ";
		 	    	
		 	    	//만약 데이터를 다돌면 멈춘다
		 	    	if(count2==35754)
		 	    		break;
		 	    	
		 	    	//만약 검색주소가 포함된 주소를 발견한다면
		 	    	if(ADDRESS.contains(newaddr) || ADDRESS2.contains(newaddr)) {
		 	    		String TOILET_NAME =(String) object.get("화장실명");
			 	    	String HOURS =(String) object.get("개방시간");
			 	    	String GENDER =(String) object.get("남녀공용화장실여부");
			 	    	String PHONE =(String) object.get("전화번호");
			 	    	String DIAPER_CHANGED =(String) object.get("기저귀교환대장소");
			 	    	int IMPAIRED_M_B = Integer.parseInt((String) object.get("남성용-장애인용대변기수"));
			 	    	int IMPAIRED_M_S = Integer.parseInt((String) object.get("남성용-장애인용소변기수"));
			 	    	int IMPAIRED_W_B = Integer.parseInt((String) object.get("여성용-장애인용대변기수"));
			 	       if(isNumberbic((String) object.get("남성용-어린이용대변기수"))) {
				 	    	CHILD_M_B = Integer.parseInt((String) object.get("남성용-어린이용대변기수"));
			 	       }
				 	    
				 	   if(isNumberbic((String) object.get("남성용-어린이용소변기수"))) {
				 		   CHILD_M_S = Integer.parseInt((String) object.get("남성용-어린이용소변기수"));
				 	   }
				 	   
				 	  if(isNumberbic((String) object.get("여성용-어린이용대변기수"))) {
				 		  CHILD_W_B = Integer.parseInt((String) object.get("여성용-어린이용대변기수"));
				 	  }
			 	    	String mapy = (String) object.get("위도");
			 	    	String mapx = (String) object.get("경도");
			 	    	
			 	 	 	DataVO vo = searchif(TOILET_NAME, ADDRESS, ADDRESS2, 
			 				HOURS, GENDER, PHONE, DIAPER_CHANGED, mapx, mapy, IMPAIRED_M_B, 
			 				IMPAIRED_M_S, IMPAIRED_W_B ,CHILD_M_B, CHILD_M_S, CHILD_W_B, 
			 				gender, child, impaired);
			 		 	    	if(vo!=null) {
			 		 	    		lista.add(vo);
			 		 	    		count++;
			 		 	    	}
						
		 	    	}//주소검색 if문 끝	
		    		
		    	}//while문 끝
	    	
	    }else{
	    	//주소 미입력경우 (gps경우) 
	    	
	    	JSONArray jsonar = (JSONArray) jsonObject.get("records");
	    	
	    	//전체 다 돈다
	    	for(int i=0; i<jsonar.size();i++) {
	    		JSONObject object = (JSONObject) jsonar.get(i);
	 	    	String ADDRESS =(String) object.get("소재지도로명주소");
	 	    	String ADDRESS2 =(String) object.get("소재지지번주소");
	 	    	String TOILET_NAME =(String) object.get("화장실명");
		 	    String HOURS =(String) object.get("개방시간");
		 	    String GENDER =(String) object.get("남녀공용화장실여부");
		 	    String PHONE =(String) object.get("전화번호");
		 	    String DIAPER_CHANGED =(String) object.get("기저귀교환대장소");
		 	    int IMPAIRED_M_B = Integer.parseInt((String) object.get("남성용-장애인용대변기수"));
		 	    int IMPAIRED_M_S = Integer.parseInt((String) object.get("남성용-장애인용소변기수"));
		 	    int IMPAIRED_W_B = Integer.parseInt((String) object.get("여성용-장애인용대변기수"));
		 	   
		 	    if(isNumberbic((String) object.get("남성용-어린이용대변기수")))
		 	    	CHILD_M_B = Integer.parseInt((String) object.get("남성용-어린이용대변기수"));
		 	    
		 	   if(isNumberbic((String) object.get("남성용-어린이용소변기수")))
		 		   CHILD_M_S = Integer.parseInt((String) object.get("남성용-어린이용소변기수"));
		 	   
		 	  if(isNumberbic((String) object.get("여성용-어린이용대변기수")))
		 		  CHILD_W_B = Integer.parseInt((String) object.get("여성용-어린이용대변기수"));
		 	  
		 	    String mapy = (String) object.get("위도");
		 	    String mapx = (String) object.get("경도");
		 	   if(isNumberbic(mapy) && isNumberbic(mapx)) {
			 		 double dmapy = Double.parseDouble(mapy);
				 	 double dmapx = Double.parseDouble(mapx);
				 	 int radius = 3;
				 	 double colC = col(lat, lon, dmapy, dmapx);
				 	 if(colC < radius) {
				 		DataVO vo = searchif(TOILET_NAME, ADDRESS, ADDRESS2, 
								HOURS, GENDER, PHONE, DIAPER_CHANGED, mapx, mapy, IMPAIRED_M_B, 
								IMPAIRED_M_S, IMPAIRED_W_B ,CHILD_M_B, CHILD_M_S, CHILD_W_B, 
								gender, child, impaired);
						 	    	if(vo!=null) {
						 	    		lista.add(vo);
						 	    	}
			 	    }		
			 	  }
	 	    		
	    	}//for문끝
	    	
	    }//주소입력-미입력 if문 끝 
	    DataVO[] ar;
	    ar = new DataVO[lista.size()];
	    lista.toArray(ar);
		map.put("list", ar);
	       
	    return map;

	}
	
	//검색용 if문메소드 분리
	private DataVO searchif(String TOILET_NAME, String ADDRESS, String ADDRESS2, 
			String HOURS, String GENDER, String PHONE, String DIAPER_CHANGED, String mapx, String mapy, int IMPAIRED_M_B, 
			int IMPAIRED_M_S, int IMPAIRED_W_B ,int CHILD_M_B, int CHILD_M_S,int CHILD_W_B, 
			int gender, int child, int impaired){
		
		DataVO vo = null;
		
		if( gender ==1 && child ==1 && impaired ==1) {
	 	    	//전부다 선택된 경우 
  		 		   if(GENDER.equals("N") && IMPAIRED_M_B+IMPAIRED_M_S+IMPAIRED_W_B>0 && CHILD_M_B+CHILD_M_S+CHILD_W_B>0) {
  		 			   vo = new DataVO(TOILET_NAME, ADDRESS, ADDRESS2, HOURS, GENDER, PHONE, DIAPER_CHANGED, mapx, mapy, 
  			 	    			IMPAIRED_M_B, IMPAIRED_M_S, IMPAIRED_W_B ,CHILD_M_B, CHILD_M_S,CHILD_W_B); 	    	
	  		
	  			
  		 	    	}   	
  	 	   	}else if(gender==1 && child ==1) {
	    		//남녀분리, 아동만 선택된 경우,
	  	 	   	if(GENDER.equals("N") && CHILD_M_B+CHILD_M_S+CHILD_W_B>0) {
	  	 	   		vo =new DataVO(TOILET_NAME, ADDRESS, ADDRESS2, HOURS, GENDER, PHONE, DIAPER_CHANGED, mapx, mapy, 
		 	    			IMPAIRED_M_B, IMPAIRED_M_S, IMPAIRED_W_B ,CHILD_M_B, CHILD_M_S,CHILD_W_B);	 	    	 	    	
  					
  				}   	
	    		
	    	}else if(gender==1 && impaired ==1) {
	    		//남녀분리, 장애인만 선택된 경우
	    		if(GENDER.equals("N") && IMPAIRED_M_B+IMPAIRED_M_S+IMPAIRED_W_B>0 ) {
	    			vo = new DataVO(TOILET_NAME, ADDRESS, ADDRESS2, HOURS, GENDER, PHONE, DIAPER_CHANGED, mapx, mapy, 
		 	    			IMPAIRED_M_B, IMPAIRED_M_S, IMPAIRED_W_B ,CHILD_M_B, CHILD_M_S,CHILD_W_B);	 	    		 	    	
  					
  				}   	
	    		
	    	}else if(child ==1 && impaired ==1) {
	    		//아동, 장애인만 선택된 경우
	    		if(IMPAIRED_M_B+IMPAIRED_M_S+IMPAIRED_W_B>0 && CHILD_M_B+CHILD_M_S+CHILD_W_B>0) {
	    			vo = new DataVO(TOILET_NAME, ADDRESS, ADDRESS2, HOURS, GENDER, PHONE, DIAPER_CHANGED, mapx, mapy, 
		 	    			IMPAIRED_M_B, IMPAIRED_M_S, IMPAIRED_W_B ,CHILD_M_B, CHILD_M_S,CHILD_W_B);	 	    	 	    		
  				}   	
	    		
	    	}else if(impaired ==1) {
	    		//장애인만 선택된 경우
	    		if(IMPAIRED_M_B+IMPAIRED_M_S+IMPAIRED_W_B>0 ) {
	    			
	    			vo = new DataVO(TOILET_NAME, ADDRESS, ADDRESS2, HOURS, GENDER, PHONE, DIAPER_CHANGED, mapx, mapy, 
		 	    			IMPAIRED_M_B, IMPAIRED_M_S, IMPAIRED_W_B ,CHILD_M_B, CHILD_M_S,CHILD_W_B);	 	    	
	    		}   	
	    	}else if(child ==1) {
	    		//아동만 선택된 경우
	    		if(CHILD_M_B+CHILD_M_S+CHILD_W_B>0) {
  		 	    	vo = new DataVO(TOILET_NAME, ADDRESS, ADDRESS2, HOURS, GENDER, PHONE, DIAPER_CHANGED, mapx, mapy, 
		 	    			IMPAIRED_M_B, IMPAIRED_M_S, IMPAIRED_W_B ,CHILD_M_B, CHILD_M_S,CHILD_W_B);	 	    	
  		 	    	
  					}   	
	    	}else if(gender ==1) {
	    		//남녀분리만 선택된 경우
	    		if(GENDER.contains("N")) {
	    			
	    			vo = new DataVO(TOILET_NAME, ADDRESS, ADDRESS2, HOURS, GENDER, PHONE, DIAPER_CHANGED, mapx, mapy, 
		 	    			IMPAIRED_M_B, IMPAIRED_M_S, IMPAIRED_W_B ,CHILD_M_B, CHILD_M_S,CHILD_W_B);	 	    	
	    			
		 	    	}
	    	}else{
		 	    	vo = new DataVO(TOILET_NAME, ADDRESS, ADDRESS2, HOURS, GENDER, PHONE, DIAPER_CHANGED, mapx, mapy, 
		 	    		IMPAIRED_M_B, IMPAIRED_M_S, IMPAIRED_W_B ,CHILD_M_B, CHILD_M_S,CHILD_W_B);	 	    		 	    	

		 	    	}
	    		return vo;
	}
	

	//문자열이 숫자인지 아닌지 판별
	private boolean isNumberbic(String string) {
		try {
	        Double.parseDouble(string);
	        return true;
	    } catch(NumberFormatException e) {
	        return false;
	    }catch(NullPointerException e) {
	    	return false;
	    }
	}
	
	//거리계산
	 //거리계산
	private double col(double lat, double lon, double dmapy, double dmapx) {
    	 int earthRadiusKm = 6371;

    	 double dLat = degreesToRadians(lat-dmapy);
    	 double dLon = degreesToRadians(lon-dmapx);
		
    	 double lat1 = degreesToRadians(lat);
    	 double lat3 = degreesToRadians(dmapy);
		
    	 double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		          Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat3); 
    	 double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		  return earthRadiusKm * c;
	}
	 
	private double degreesToRadians(double degrees) {
    	  return degrees * Math.PI / 180;
    	}
	
	
	
}
