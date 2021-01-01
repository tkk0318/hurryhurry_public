<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="css/header.css" />

<header id="header">
<div class="h_inner">

<h1><a href="/">급해?급해!</a></h1>

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
					<a href="https://kauth.kakao.com/oauth/authorize?client_id=restapi키값
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

</div>

</header>

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
	        /* document.getElementById("header").style.paddingRight = "300px"; */
	        document.getElementById("line-wrapper").style.paddingRight = "300px";
	        document.getElementById("line-wrapper").style.right = "0px";
	    }else{
	        document.getElementById("sidenav").style.width = "0";
	        /* document.getElementById("header").style.paddingRight = "0"; */
	        document.getElementById("line-wrapper").style.paddingRight = "0";
	        document.getElementById("line-wrapper").style.right = "150px";
	    }
	  });
  
  
  /* 메뉴 Toggle */
  $(document).ready(function(){
	  
	  $('#toggle1').click(function(){
	    $('#menu1').slideToggle();
	  })
	  
	  
	});
  $(document).ready(function(){
	  
	  $('#toggle2').click(function(){
	    $('#menu2').slideToggle();
	  })
	  
	  
	});
});
		
	</script>
