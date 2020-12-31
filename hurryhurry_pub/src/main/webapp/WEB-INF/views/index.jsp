<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>급해?급해!</title>
<link rel="stylesheet" type="text/css" href="css/index.css" />
 <style>
    .overlay_info {border-radius: 6px; margin-bottom: 12px; float:left;position: relative; border: 1px solid #ccc; border-bottom: 2px solid #ddd;background-color:#fff;}
    .overlay_info:nth-of-type(n) {border:0; box-shadow: 0px 1px 2px #888;}
    .overlay_info a {display: block; background: #d95050; background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center; text-decoration: none; color: #fff; padding:12px 36px 12px 14px; font-size: 14px; border-radius: 6px 6px 0 0}
    .overlay_info a strong {background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/place_icon.png) no-repeat; padding-left: 27px;}
    .overlay_info .desc {padding:14px;position: relative; min-width: 190px; height: 56px}
    .overlay_info img {vertical-align: top;}
    .overlay_info .address {font-size: 12px; color: #333; position: absolute; left: 80px; right: 14px; top: 24px; white-space: normal}
    .overlay_info:after {content:'';position: absolute; margin-left: -11px; left: 50%; bottom: -12px; width: 22px; height: 12px; background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png) no-repeat 0 bottom;}
</style>

</head>

<body>
	<!-- 차단회원인 경우 -->
	<c:if test="${sessionScope.alertGO eq 'blockGO' }">
		<script>alert('차단되어 회원서비스 이용은 불가합니다.');</script>
		<% request.getSession().removeAttribute("alertGO"); %>
	</c:if>
	
	<!-- 좋아요, 신고를 하기 위해 로그인한 회원의 ID값 받아오기 -->
	<input type="hidden" id="likeID" name="likeID" value="${sessionScope.mvo.userID }"/> 
	
	<!-- 현재 위치 찾기  -->
	<button id="Current">
		<img  id="current" alt="현재위치" src="img/Current_location.png">
	</button>
	
	
	<!-- 왼쪽 자세히 보기 창  -->
	<div id="leftArea">
	<p id="Tname">(화장실 이름)</p>
	<p id="Taddr">(화장실 주소)</p>
	<p id="Tstar">(별점)</p>
	<p id="Ttime">(개방시간)</p>
	<p id="Tdetail">(장애인, 분리, 아동)</p>
	
	<div id="write_t">
			<table>
				<tr>
					<td>
					<c:if test="${sessionScope.mvo == null }">
						로그인 시에만 리뷰 작성이 가능합니다.
					</c:if>
					<c:if test="${sessionScope.mvo != null }">
						<jsp:include page="review.jsp"/>
					</c:if>
					</td>
				</tr>
			</table>
	</div>
	
	<!-- 리뷰쓰기 테이블 -->
	<div id="review_t">
		<table id=review>	
	 
		</table>
	</div>
	
	</div>	
	<div id="map">	
		<div id="map_search" class="center">				
				<input type="text" name="search" id="search"> 
				<input type="button" value="검색" id="s_btn" onclick="exe()">	
				<div>
					<button class="divBtn" id="gender">분리</button>
					<button class="divBtn" id="impaired">장애인</button>
					<button class="divBtn" id="child">아동</button>				
				</div>
		</div>
	</div>
	
	<!-- 신고하기 모달창 -->
    <div id="myModal" class="modal">
      <!-- Modal content -->
      <div class="modal-content">
        <span class="close">&times;</span>
        <jsp:include page="reportReview.jsp"/>
      </div>
 
    </div>
	
	<!-- -------------------메뉴바-------------------- -->
		<div id="sidenav" class="sidenav">
		<!-----------------------햄버거 라인-------- --------- -->
					<div id="line-wrapper">
						<div id="line-top" class="line init top-reverse"></div>
						<div id="line-mid" class="line init mid-reverse"></div>
						<div id="line-bot" class="line init bot-reverse"></div>
					</div>

			<!-- MENU상단 -->
			<p>MENU</p>
			<!-- 로그인시 이메일 주소 또는 관리자임을 나타내줌 -->
			<c:if test="${sessionScope.mvo ne null }">
				<!-- 일반회원 로그인시 메일주소를 보여줌 -->
				<c:if test="${sessionScope.mvo.status < '50' }">
				<p style="font-size:20px";>${sessionScope.mvo.userID }</p>
				</c:if>
				<!-- 관리자가 로그인시 관리자임을 나타내줌 -->
				<c:if test="${sessionScope.mvo.status >= '50'}">
				<p style="font-size:20px";>관리자로 로그인하셨습니다.</p>
				</c:if>
			</c:if>
			
			<!-- 로그인 전-->
			<c:if test="${sessionScope.mvo eq null }">
			<!--<a href="temp_login">임시로그인(은혜)</a> -->
			<a href="#" id="toggle1">소셜로그인</a>
				<!-- 소셜로그인 -->
				<div id="menu1">
					<!-- 카카오 -->
					<a href="https://kauth.kakao.com/oauth/authorize?client_id=f57259f7678b116ed607a9ea7083c3b1
					&redirect_uri=http://localhost:8080/kakao&response_type=code">
						<img alt="kakao" src="img/kakao.png">
					</a>
					<!-- 네이버 -->
					<a href="naverLogin"><img alt="naver" src="img/naver.png"></a>
					<!-- 구글로그인 
					<a href="googleLogin"><img alt="google" src="img/google.png"></a>
					-->
				</div>
					
					<!-- 마이페이지 -->
					<a href="javascript:alert('로그인이 필요합니다.')">마이페이지</a>
			</c:if>
		
			<!-- 로그인 후 -->
			<c:if test="${sessionScope.mvo ne null }">	
			<a href="logout">로그아웃</a>
		
				<!-- 회원이 로그인 한 경우 -->
					<c:if test="${sessionScope.mvo.status < '50' }">
					<a href="mypage">마이페이지</a>
					</c:if>
				
				<!-- 관리자가 로그인 한 경우-->
					<c:if test="${sessionScope.mvo.status >= '50'}">
					<a href="report">신고현황</a> 
					<a href="manage">회원관리</a> 
					<a href="totalPost">등록된 전체리뷰</a> 
					</c:if>
			</c:if>
			
			<!-- 번역부분 			
			<a href="#" id="toggle2">Language</a>
				<div id="menu2">
					<a href="#"><img alt="한국" src="img/KOREA.png"></a>
					 <a href="#"><img alt="미국" src="img/USA.png"></a> 
					 <a href="#"><img alt="중국" src="img/CHINA.png"></a>
				</div>
			-->
			
	</div>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=ccd2f5f046ad38dfd406c9e6f5d73d1a"></script>




	<script src="https://code.jquery.com/jquery-3.5.1.min.js"
		integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
		crossorigin="anonymous"></script>

	<script type="text/javascript">
		/* 햄버거 동작 */
		$('#line-wrapper').click(function() {
			/* 추가된 부분 */
			$('.line').removeClass('init');
			$('#line-top').toggleClass('line-top').toggleClass('top-reverse');
			$('#line-mid').toggleClass('line-mid').toggleClass('mid-reverse');
			$('#line-bot').toggleClass('line-bot').toggleClass('bot-reverse');
		});
		
		/* 슬라이드 메뉴 */
$(document).ready(function(){
  $("#line-wrapper").click(function(){    
    if($("#sidenav").width() == 0){      
        document.getElementById("sidenav").style.width = "300px";
        document.getElementById("map").style.paddingRight = "300px";
        document.getElementById("line-wrapper").style.paddingRight = "300px";
        document.getElementById("line-wrapper").style.right = "0px";
    }else{
        document.getElementById("sidenav").style.width = "0";
        document.getElementById("map").style.paddingRight = "0";
        document.getElementById("line-wrapper").style.paddingRight = "0";
        document.getElementById("line-wrapper").style.right = "80px";
    }
    
    /* 메뉴 Toggle */
    $('#toggle1').click(function(){
	    $('#menu1').slideToggle();
	  });
	   $('#toggle2').click(function(){
	    $('#menu2').slideToggle();
	  });
  });
  
  
  var lat;
  var lon;
	function gpsSearch(){
		//gps 잡기
		  if (navigator.geolocation) {
			    navigator.geolocation.getCurrentPosition(
			        function(location){
			            //succ - 유저가 허용버튼을 클릭하여 값을 가져올 수 있는 상태
			            lat = location.coords.latitude;
			            lon = location.coords.longitude;
			            
			        	//새로 지도 중심잡으러 가기
			  	      newCenter(lat, lon);
			  	      
			  	      //전체데이터 불러오기
			  	      allList(lat, lon);
			            
			        },
			        function(error){
			            //fail - 유저가 차단버튼을 클릭하여 값을 가져올 수 없는 상태
			       }
			    );
			}
			else {
			    //fail - 애초에 GPS 정보를 사용할 수 없는 상태
			}
	}
	
	window.onload= gpsSearch();
	
	//gps그림누르면 다시 잡히기 
	$("#current").click(function() {
	     gpsSearch();
	});
  
	//전체 마커 데이터 불러오기
	function allList(lat, lon) {
	
	
		$.ajax({
			url:"allList",
			type:"post",
			data:"lat="+lat+"&lon="+lon,
			dataType: "json"
		}).done(function(data){
			const lista = data.list;
			mapsetterSize = data.list.length; 
			
			
			//지도에 범위잡고 마커찍자
			//myallList(lista, lat, lon)
			mapsetter(lista);
		});
		
	}
});
		
		
	var mapsetterSize = 0;
	//검색버튼 클릭시 	
	function exe() {
		hideMarkers();
		if (navigator.geolocation) {
		    navigator.geolocation.getCurrentPosition(
		        function(location){
		            lat = location.coords.latitude;
		            lon = location.coords.longitude;
		            
					$.ajax({
						url:"dtSearch",
						type:"post",
						data: "search="+encodeURIComponent($("#search").val())+
							"&gender="+gend+"&child="+chil+"&impaired="+impa+"&lat="+lat+"&lon="+lon,
						dataType: "json"
					}).done(function(data){
						const lista = data.list;
						mapsetterSize = data.list.length; 
						
						mapsetter(lista);
				
						//지도에 중심 잡으러 갑니다
						var lat2 = lista[0].mapy;
						var lon2 = lista[0].mapx;
						newCenter(lat2, lon2);
						showMarkers();
						
					});
		            
		        },
		        function(error){
		       }
		    );
		}
		else {
		}
	}
	
	
	
	
	//상세 검색 설정 관련
	var gend = 0;
	var chil = 0;
	var impa = 0;
	$("#gender").click(function(){
        if(gend>0){
			gend=0;
			$(this).removeClass('checked');
			$(this).addClass('divBtn');
		}else{
				gend=1;
				$(this).addClass("checked");
				$(this).removeClass('divBtn');
			}
    });
	
	$("#child").click(function(){
        if(chil>0){
        	chil=0;
			$(this).removeClass('checked');
			$(this).addClass('divBtn');
		}else{
				chil=1;
				$(this).addClass("checked");
				$(this).removeClass('divBtn');
			}
    });
	
	$("#impaired").click(function(){
        if(impa>0){
        	impa=0;
			$(this).removeClass('checked');
			$(this).addClass('divBtn');
		}else{
				impa=1;
				$(this).addClass("checked");
				$(this).removeClass('divBtn');
			}
    });
	
	
	//* 지도 관련 *//
	
		var container = document.getElementById('map');
		var options = {
			center : new kakao.maps.LatLng(33.450701, 126.570667),
			level : 5
		};
		var map = new kakao.maps.Map(container, options);
		
		
	//지도 중심 이동하기
	function newCenter(lat, lon) {
		var moveLatLon = new kakao.maps.LatLng(lat, lon);
	 
	    map.setCenter(moveLatLon);		
	}
		
	 var markers = [];
	 var name;
	 
	//마커 불러오기	
		function mapsetter(lista) {
			
			var positions=[];
			var details=[];
			
			// 마커 이미지의 이미지 주소입니다
			var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
			
			for (var i = 0; i < lista.length; i ++) {
				var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";  
			    var imageSize = new kakao.maps.Size(24, 35);  
			    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 
			    var marker = new kakao.maps.Marker({
			        map: map, 
			        position: new kakao.maps.LatLng(lista[i].mapy, lista[i].mapx), 
			        title : lista[i].toilet_NAME,
			        image : markerImage 
			    });
			    
			    var overlay = new kakao.maps.CustomOverlay({
	                map: map,
	                position: marker.getPosition() , 
	                clickable: true
	            });
					
	       
		        var content = document.createElement('div');
		       		content.className = 'wrap';
		            
				var info = document.createElement('div');
			    	info.className = 'info';
				var title = document.createElement('div');
					title.className = 'title';
					title.innerHTML = lista[i].toilet_NAME;	
					
				var body = document.createElement('div');
					body.className = 'body';
					
				var desc = document.createElement('div');
					desc.className = 'desc';
					
				var ellipsis = document.createElement('div');
					ellipsis.className = 'ellipsis';
					ellipsis.innerHTML = lista[i].address;	
					
				var butt = document.createElement('input');
					butt.className ='butt';
					butt.type = 'button';
					butt.value ='자세히보기';
					/* butt.onclick =function(){alert("자세히보기");}	 */			
					
					/* 자세히보기 버튼 클릭시 왼쪽창 나오기  */
					/*butt.onclick = function() {
						
						showDetail(details, name);
						$('#leftArea' ).css( { 'width' : '400px' } );
					}*/
					details.push(lista[i]);
					butt.addEventListener('click', function(event) {
						for(i=0; i<details.length;i++){
							if(details[i].toilet_NAME == name){
								var detail=details[i];
								showDetail(detail);
								document.getElementById('review').innerHTML = "";
								$('#leftArea' ).css( { 'width' : '400px' } );
							}
						}
					})
					
					/* 왼쪽 창 숨기기 */
					kakao.maps.event.addListener(map, 'click', outDetail(map));
				    function outDetail(map) {
				    		 return function() {
				    			 $('#leftArea').css({'width':'0'});
				    	}
				    }
					
					/* document.getElementById("map").onclick = function () {
	        		document.getElementById("leftArea").style.display = "none"; */
					 
		      		
					desc.appendChild(ellipsis);
					desc.appendChild(butt);
					body.appendChild(desc);
					info.appendChild(title);
					info.appendChild(body);
					content.appendChild(info);
		      		overlay.setContent(content);
	            
			    
			    marker.setMap(map);
			    markers.push(marker);
			    
			    
			    
			    
			 	//오버레이는 기본적으로 닫혀있고, 클릭시 열립니다
			    overlay.setMap(null);
			    kakao.maps.event.addListener(marker, 'click', makeoverlay(map, marker, overlay));
			   kakao.maps.event.addListener(map, 'click', outoverlay(map, marker, overlay));
			    
			}//for문끝
					
		}
			
	
    
 // 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
    function setMarkers(map) {
        for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(map);
        }            
    }
 
 // 마커 세팅 함수
    function hideMarkers() {
        setMarkers(null);  
       	markers=[];
    }
  
    function showMarkers() {
        setMarkers(map)    
    }
    
  //오버레이여는함수		
	function makeoverlay(map, marker, overlay) {
        return function() {
        	name = marker.getTitle();
        	overlay.setMap(map, overlay);
        };
    }
	//오버레이닫는함수
	function outoverlay(map, marker, overlay) {
		 return function() {
       		overlay.setMap(null);
		}
  
	}
    
   //자세히보기
  	function showDetail(detail) {   
	   
	   var detailI;
 	   var detailC;
 	    if(detail.child>0){ detailC = "Y"}else{ detailC = "N"}
 	    if(detail.impaired>0){ detailI = "Y"}else{ detailI = "N"}
	   
	   $('#Tname').text(detail.toilet_NAME);
	   $('#Taddr').html('도로명 주소: '+ detail.address);
	   
	   var tname = detail.toilet_NAME;
   	   $('input#toilet_name').attr('value',tname);
	   
   	   // 별 받아오기 + 화장실 이름
   	   $.ajax({
			type:"post",
			url: "avgStar",
			data: "toilet_name="+encodeURIComponent(tname),
			dataType: "json",			
		}).done(function(data){
			if(data.res == "1"){
				$('#Tstar').text("평균 별점: " + data.avgStar + "/5");
			}else{
				$('#Tstar').text('등록된 별점이 없습니다.');
			}	
		}).fail(function(data){
			$('#Tstar').text('평균 별점을 가져오는 데 실패했습니다.');
		});
		
	   
	   /*$('#Tstar').innerHTML 비동기통신으로 dao가서 평균별점 계산해서 데꼬와서 여기에 넣어주세요*/
	   $('#Ttime').text(detail.hours);
	   /* $('#Tdetail').html('<table><tr><td>남녀 공용 화장실: ' + detail.gender + '</td><td> 장애인 화장실: ' + detailI + '</td><td> 아동용 화장실: '+ detailC '</td></tr></table>'); */
 	   $('#Tdetail').html('남녀 공용 화장실: ' + detail.gender + '<br/> 장애인 화장실: ' + detailI + '<br/> 아동용 화장실: '+ detailC); 
	   toiletReview();
   }
   
  	function toiletReview(){
  		var IDforLike = document.getElementById('likeID').value;	//로그인한 사용자의 ID (각 리뷰에 대한 좋아요 파악위함)
		var toilet_name = name	
		//console.log("aaaa")
		$.ajax({
			url:"ToiletReview",
			type:"post",
			dataType: "json",
			data:"toilet_name="+toilet_name+"&IDforLike="+IDforLike,	
		}).done(function(data){
			//데이터를 받으면 바로 리뷰 표시		
			var uID = "<%= session.getAttribute( "id" ) %>";
			var reviewList = data.vo;	//해당 화장실의 리뷰들 받아오기
			
			//사용자가 기존에 좋아요를 한 리뷰가 1개라도 있는 지 없는 지 판단
			if(data.like_rvsIDX != null){	//사용자가 좋아요한 리뷰가 있다면
				var like_rvs = data.like_rvsIDX;	//like_rvs에 리뷰idx를 배열로 저장
			}else{						//사용자가 좋아요한 리뷰가 하나도 없다면
				var like_rvs = null;		//like_rvs에는 null을 저장
			}
			
			var content="";	//html에 추가할 내용 담을 변수
			
			if(data.vo != null){
			for(i=0; i<reviewList.length;i++){		
			 var rID = reviewList[i].userID;	//리뷰 쓴 회원ID
			 var rIDX = reviewList[i].review_idx;	//리뷰idx
			 var star = reviewList[i].star;	//기존리뷰의 별 개수
			 var reg_date = (reviewList[i].reg_date).substr(0,10);	//리뷰쓴 날짜만 잘라오기
			 var num = i;	//리뷰 i번째임을 나타내기 위한 변수
			 
			 //각각의 리뷰에 대한 좋아요 여부 판단
			 var like_ornot;	//사용자가 좋아요한 리뷰들의 idx에 현재 리뷰idx가 있는지를 파악하여 true 또는 false로 저장
			 if(data.like_rvsIDX != null){	//사용자가 기존에 좋아요한 리뷰들이 있었다면
				 like_ornot = like_rvs.includes(reviewList[i].review_idx);	//그 리뷰들에 현재 리뷰값이 있는지를 파악
			 }else{	//사용자가 기존에 좋아요한 리뷰가 하나도 없다면
				 like_ornot = null;	//null 저장
			 }
			 
			 var starCh;
				switch(star){
					case '1' :starCh = '★☆☆☆☆';
					break;
					case '2' :starCh = '★★☆☆☆';
					break;
					case '3' :starCh = '★★★☆☆';
					break;
					case '4' :starCh = '★★★★☆';
					break;
					case '5' :starCh = '★★★★★';
				}
				
			content+=
			'<div id="review_re'+num+'">'+
			'<form method="post" name="frm">'+
			'	<tr>'+
			'		<td colspan="2">리 뷰</td>'+
			'	</tr>'+
			'	<tr>'+
			'		<td width="90%">'+					
			'		<div><p>'+starCh+'</p></div>'+
			'		<div><p>'+reg_date+'</p></div>'+
			'		<div><p id="contentShown'+num+'" name="contentShown'+num+'" style="border-bottom:none;">'+reviewList[i].content+'</p>'
            if(uID != 'null'){	//로그인을 했다면 좋아요, 신고버튼 나옴
					if(like_ornot == true){	//사용자가 기존에 좋아요한 리뷰라면 진한색 좋아요 버튼
					content +=
					'<p id=img_btn style="text-align:right;"><input type="image" id="like'+num+'" name="like'+num+'" src="img/like_click.png" onclick="likeUpdate('+rIDX+','+num+')"/>'	
					}else{	//사용자가 기존에 좋아요 하지 않은 리뷰라면 옅은색 좋아요 버튼
					content +=	
					'<p id=img_btn style="text-align:right;"><input type="image" id="like'+num+'" name="like'+num+'" src="img/like.png" onclick="likeUpdate('+rIDX+','+num+')"/>'
					}//밑에는 신고버튼
				content +=
				'<input type="image" name="button" src="img/report.png" onclick="reportModal('+rIDX+')"/></p>'
            }
			'</div>'
			
			if(uID == rID){					
				content += 
						'<br/>'+
						'<div class="starRev" starNum="'+star+'">'+	//수정할 리뷰 별
				  			'<span class="starR">별1</span>'+
				  			'<span class="starR">별2</span>'+
				  			'<span class="starR">별3</span>'+
				  			'<span class="starR">별4</span>'+
				  			'<span class="starR">별5</span>'+
						'</div>'+
						'<br/>'+
						'<div><textarea id="content'+num+'" name="content'+num+'" cols="40" rows="6">'+reviewList[i].content+'</textarea></div>'+
						'<input type="button" value="수정" onclick="editBbs('+rIDX+','+num+')">'+
						'<input type="button" value="삭제" onclick="delBbs('+rIDX+')">'
				}
			
				content+=
	            '		</td >'
	            content +=
				'   </tr>'+
				' </form>'+
				'</div>'
				}
			}else{
				content;
			}
				document.getElementById('review').innerHTML = content;
				reStar();
				starShow();
			});
	 
	}  
  	
 	//리뷰수정
  	function editBbs(rIDX, num){
 		//리뷰 수정내용 받기
 		var content123 = document.getElementById('content'+num).value;
 		
 		//별점 수정을 위해 색칠된 별점 개수 구하기
 		var starRevs = $(".starRev");	//starRev가 class인 div를 모두 구함
		var starRev = starRevs[num];	//그 중에 수정하려는 리뷰의 starRev div를 구함
		var starCount = $(starRev).children('.starR.on').length;	//색칠된 별의 개수 구하기
		
		console.log(starCount);
	 		$.ajax({
	 			url: "editToilet",
	 			type: "post",
	 			data: "review_idx="+encodeURIComponent(rIDX)+"&content="+encodeURIComponent(content123)+"&star="+encodeURIComponent(starCount),
	 			dataType: "json",
	 		}).done(function(data){
	 			if(data.res == "1"){
	 				alert("리뷰가 정상적으로 수정되었습니다.");
	 				//$('#contentShown'+m).text(data.editContent);//수정된 리뷰내용으로 바꾸기
	 				toiletReview();
	 			}else{
	 				alert("리뷰 수정 실패, 다시 시도해주세요.")
	 			}
	 		});
		}
	
	//리뷰삭제
	function delBbs(rIDX){
	 		$.ajax({
	 			url: "delToilet",
	 			type: "post",
	 			data: "review_idx="+encodeURIComponent(rIDX),
	 			dataType: "json",
	 		}).done(function(data){
	 			if(data.res == "1"){
	 				alert("리뷰가 정상적으로 삭제되었습니다.");
	 				toiletReview();
	 			}else{
	 				alert("리뷰 삭제 실패, 다시 시도해주세요.")
	 			}
	 		});
	}
	
	//좋아요 기능
	function  likeUpdate(rIDX, num){
		console.log(rIDX);
		var likeID = document.getElementById('likeID').value;
		//submitPost('RecUpdate', {'idx' : rIDX, 'id' : likeID});
		$.ajax({
			url: "likeUpdate",
			type: "post",
			data: "review_idx="+encodeURIComponent(rIDX)+"&userID="+encodeURIComponent(likeID),
			dataType: "json",
		}).done(function(data){
			if(data.res == "1"){
				//alert("좋아요!");
				$('#like'+num).attr('src', 'img/like_click.png');//진한좋아요버튼으로
			}else if(data.res == "2"){
				alert("좋아요 실패")
			}else if(data.res == "3"){
				//alert("좋아요 취소!");
				$('#like'+num).attr('src', 'img/like.png');//연한좋아요버튼으로
			}else if(data.res == "4"){
				alert("좋아요 취소 실패");
			}
		});
	}
    
	//신고 모달창 띄우기
	function reportModal(rIDX){
		sessionStorage.setItem("review_idx", rIDX);	//신고할 리뷰의 idx 저장
		sessionStorage.setItem("reporter", document.getElementById('likeID').value); //신고하는 회원 ID 저장
		
		 // 모달요소 가져오기
        var modal = document.getElementById('myModal');
 
        // 모달창 닫는 X버튼 가져오기
        var close = document.getElementsByClassName("close")[0];
        
        //모달창 열기
        modal.style.display = "block";
 
        // X버튼 눌렀을 때 모달창 끄기
        close.onclick = function() {
            modal.style.display = "none";
        }
 
        // 모달창 바깥화면 클릭시 모달창 끄기
        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
	}
	
	//별점표시기능(리뷰페이지 들어오자마자 바로)
	function starShow(){
		var starRevs = $(".starRev");	//starRev가 class인 div 모두 가져오기
		
		for(i=0; i<starRevs.length; i++){
			var starRev = starRevs[i];	//starRev div 하나하나 뽑기
			var starCount = $(starRev).attr('starNum');	//각 리뷰별 기존별 개수 구하기
			
			for(k=0; k<starCount; k++){	//점수 개수만큼 별 채워넣기
				$(starRev).children('span').eq(k).addClass('on');
			}
			
		}//큰 for문 끝
	}
	
	//리뷰수정시 별 클릭
	function reStar(){
		var starRevs = $(".starRev");	//starRev가 class인 div 모두 가져오기
		
		for(var i=0; i<starRevs.length; i++){
			var starRev = starRevs[i];	//starRev div 하나하나 뽑기
			$(starRev).children('span').bind("click", function(){	//starRev div안에 span태그를 클릭하면
				  $(this).parent().children('span').removeClass('on');
				  $(this).addClass('on').prevAll('span').addClass('on');
				  return false;
			});
		}
	}
	</script>	
</body>
</html>