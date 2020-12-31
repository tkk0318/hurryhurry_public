<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>등록된 전체리뷰 보기</title>
<link rel="stylesheet" type="text/css" href="css/totalpost.css" />
</head>
<body>
<jsp:include page="header.jsp"/>

<section id="s_tatalpost">

<c:if test="${totalCount ne '0' }">
	<h1>총 ${totalCount }개의 리뷰가 등록되었습니다! </h1>
</c:if>

<c:if test="${totalCount eq '0' }">
	<h1>아직 등록된 리뷰가 없어요.</h1>
</c:if>
		<!-- 리뷰검색바 -->
		<div id="post_search">
			<form action="searchReview" method="post">
				<select id="searchType" name="searchType">
					<option value="5">검색옵션</option>
					<option value="0">화장실명</option>
					<option value="1">리뷰내용</option>
				</select> 
				<input type="text" name="searchValue" id="searchValue"> 
				<input type="button" value="검색" onclick="exe(this.form)">
			</form>
		</div>


<table class="totalpost_table">
	<thead>
	  <tr>
		<th >화장실이름</th>
	    <th >리뷰내용</th>
	  </tr>
	</thead>
	
	<tbody>
	  	<c:if test="${ar ne null }">
	  	<c:forEach var="vo" items="${ar }" varStatus="st"> 
		  <tr>
		    <th>${vo.toilet_name }</th>
	 	    <td class="elip" st="${st.index }" style="cursor:pointer;">${vo.content }</td>
		  </tr>
		  <tr style= "display:none;" class="${st.index }">
		    <th >리뷰내용 자세히보기</th>
		    <td>
		    	<input type="hidden" id="${st.index}vidx" name="${st.index}vidx" value="${vo.review_idx}"/>
				<input type="hidden" id="${st.index}userID" name="${st.index}userID" value="${vo.userID}"/>
				<input type="hidden" id="${st.index }cPage" name="${st.index }cPage" value="${nowPage }"/>
		    	<p>${vo.content }</p>
		    	<p>리뷰등록 날짜: ${fn:substring(vo.reg_date, 0, 10) }</p>
		    	<p>star: 
		    	<c:if test = "${vo.star eq '1'}">★☆☆☆☆</c:if>
		    	<c:if test = "${vo.star eq '2'}">★★☆☆☆</c:if>
		    	<c:if test = "${vo.star eq '3'}">★★★☆☆</c:if>
		    	<c:if test = "${vo.star eq '4'}">★★★★☆</c:if>
		    	<c:if test = "${vo.star eq '5'}">★★★★★</c:if>
		    	</p>
		    	<c:if test="${vo.status eq '0' }">
		    	<input type="button" value="리뷰차단" class="rv_bl" st="${st.index}"/>
		    	</c:if>
		    	<c:if test="${vo.status eq '1' }">
		    	<a>차단처리 된 리뷰입니다. 차단해제를 원하시나요?</a>
		    	<input type="button" value="YES" class="rv_rc" st="${st.index }"/>
		    	</c:if>
		    </td>
		  </tr>
	</c:forEach>
  	</c:if>
  
  <c:if test="${ar eq null }">
  	<tr>
  		<td colspan="2">등록된 리뷰가 없어요. T^T</td>
  	</tr>
  </c:if>
  </tbody>
    <tfoot>
		<tr>
              <td colspan="4">${p_code }</td>
		</tr>
   </tfoot>
</table>
	
</section>







<jsp:include page="footer.jsp"/>

<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jQuery.dotdotdot/4.1.0/dotdotdot.js" integrity="sha512-y3NiupaD6wK/lVGW0sAoDJ0IR2f3+BWegGT20zcCVB+uPbJOsNO2PVi09pCXEiAj4rMZlEJpCGu6oDz0PvXxeg==" crossorigin="anonymous"></script>
<script>

	//글자수 줄이기 기능(...)
	$('.elip').each(function(){
	    var length = 30; //글자수
	    $(this).each(function(){
	      if($(this).text().length >= length){
	        $(this).text($(this).text().substr(0,length)+'...');
	      }
	    });
	  });
  
	//검색기능
	function exe(f){
		var v = f.searchValue;
		var t = f.searchType;
		
		if(t.value == 5)	//검색 옵션을 선택하지 않은 경우
			alert("검색옵션을 먼저 선택하세요.");
		
		if(t.value == 0 || t.value == 1){	//검색 옵션을 선택한 경우
			if(!v.value || v.value.trim().length == 0){	//검색어 입력하지 않은 경우
				alert("검색할 단어를 입력하세요.");
				v.value="";
				v.focus();
				return;
			}
			f.submit();
		}
	}
	
  	//자세히보기 기능
	$(function(){
	$('.elip').bind('click', function(){
		var st = $(this).attr('st');
		if($('.'+st).css('display') == 'none'){
			$('.'+st).show();
		}else{
			$('.'+st).hide();
		}
	});
	
	//리뷰차단버튼 클릭시 - 해당리뷰차단, 해당회원경고(status+1)
	$(".rv_bl").bind("click", function(){
		var st = $(this).attr('st');
		var st1 = String(st) + 'vidx';
		var st2 = String(st) + 'userID';
		var st3 = String(st) + 'cPage';
		
		var review_idx = $('#'+st1).val();
		var userID = $('#'+st2).val();
		var cPage = $('#'+st3).val();

		$.ajax({
			url: "review_block",
			type: "post",
			data: "review_idx="+encodeURIComponent(review_idx)+"&writer="+encodeURIComponent(userID)+"&cPage="+encodeURIComponent(cPage),
			dataType: "json",
		}).done(function(data){
			if(data.res == "1"){
				alert("리뷰가 정상적으로 차단되었습니다.");
				location.href="/totalPost?cPage="+data.cPage;
			}else{
				alert("리뷰차단 실패");
			}
		});
	});
	
	//차단해제버튼 클릭시
	$(".rv_rc").bind("click", function(){
		var st = $(this).attr('st');
		var st1 = String(st) + 'vidx';
		var st2 = String(st) + 'userID';
		var st3 = String(st) + 'cPage';
		
		var review_idx = $('#'+st1).val();
		var userID = $('#'+st2).val();
		var cPage = $('#'+st3).val();
		

		$.ajax({
			url: "review_recover",
			type: "post",
			data: "review_idx="+encodeURIComponent(review_idx)+"&userID="+encodeURIComponent(userID)+"&cPage="+encodeURIComponent(cPage),
			dataType: "json",
		}).done(function(data){
			if(data.res == "1"){
				alert("리뷰차단이 정상적으로 해제되었습니다.");
				location.href="/totalPost?cPage="+data.cPage;
			}else{
				alert("차단해제 실패");
			}
		});
	});
	});
</script>
</body>
</html>