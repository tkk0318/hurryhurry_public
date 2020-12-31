<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mypage</title>
<link rel="stylesheet" type="text/css" href="css/mypage.css" />
</head>
<body onload="starShow()">
<jsp:include page="header.jsp"/>
<section id="s_mypage">
	<h1>MyPage</h1>
		
				
		<table class="mypage_table">		
			<tbody>
			<c:if test="${ar ne null }">
				<c:forEach var="vo" items="${requestScope.ar }" varStatus="st">
				<form method="post" name="frm">
				<tr>
					<th>화장실이름:</th>
					<td>${vo.toilet_name }</td>					
					<input type="hidden" name="review_idx" value="${vo.review_idx }"/>  
				</tr>
				<tr>
					<th>리뷰내용:</th>
					<c:if test="${vo.status == '0' }">
					<td>
					<textarea name="content" cols="50" 
							rows="8">${vo.content }</textarea>
					</td>
					</c:if>
					<c:if test="${vo.status == '1' }">
					<td>차단된 리뷰입니다.</td>
					</c:if>
				</tr>
				<tr>
					<th>별점:</th>
					<td>
						<div class="starRev" starNum = "${vo.star }">
				  			<span class="starR">별1</span>
				  			<span class="starR">별2</span>
				  			<span class="starR">별3</span>
				  			<span class="starR">별4</span>
				  			<span class="starR">별5</span>
				  			<input type="hidden" id="${st.index }star" name="star" value=""/> <!-- 새로 채운 별 개수 -->
						</div>
						
					</td>
				</tr>
				<tr>
					<td colspan="2" id="cut_btn">
					<input type="button"  value="수정하기" onclick="editBbs(${st.index})"/>
					<input type="button"  value="삭제" onclick="delBbs(${st.index})"/></td>
				</tr>
				</form>
				</c:forEach>
				</c:if>
				<c:if test="${ar eq null }">
					<tr>
						<td>등록된 리뷰가 없습니다.</td>
					</tr>
				</c:if>
				</tbody>
			</table>
		</section>
		

	
	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	
<script>
	//기존 별점표시기능(마이페이지 들어오자마자 바로)
	function starShow(){
		var starRevs = $(".starRev");	//starRev가 class인 div 모두 가져오기
		
		for(i=0; i<starRevs.length; i++){
			var starRev = starRevs[i];	//starRev div 하나하나 뽑기
			var starCount = $(starRev).attr('starNum');	//각 리뷰별 기존별 개수 구하기
			
			for(k=0; k<starCount; k++){	//점수 개수만큼 별 채워넣기
				$(starRev).children('span').eq(k).addClass('on');
			}
		}
	}

	//리뷰수정
	function editBbs(st){
		//별 개수 세는 함수(starCount)를 실행하여 ID가 star인 element에 값을 채워준다
		document.getElementById(st+'star').value= starCount(st);
		
		document.forms[st].action = "edit";
		document.forms[st].submit();
		alert("수정되었습니다.");
		
	}
	
	//별점수정시 별점개수 카운트 기능
	function starCount(st){
		var starRevs = $(".starRev");
		var starRev = starRevs[st];
		var starCount = $(starRev).children('.starR.on').length;
		
		console.log(starCount);
		return starCount;
	}
	
	//별점수정
	$(function(){
		var starRevs = $(".starRev");
		
		for(var i=0; i<starRevs.length; i++){
			var starRev = starRevs[i];
			$(starRev).children('span').bind("click", function(){
				  $(this).parent().children('span').removeClass('on');
				  $(this).addClass('on').prevAll('span').addClass('on');
				  return false;
			});

		}
	});
	
	//리뷰삭제
	function delBbs(st){
		document.forms[st].action = "del";
		document.forms[st].submit();
		alert("삭제되었습니다.");
	}	
	
</script>
	<jsp:include page="footer.jsp"/>
</body>
</html>