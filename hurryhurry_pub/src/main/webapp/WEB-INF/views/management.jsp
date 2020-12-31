<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<div class="inner">
<head>
<meta charset="UTF-8">
<title>회원관리</title>
<link rel="stylesheet" type="text/css" href="css/management.css" />
</head>
<body>
<jsp:include page="header.jsp"/>

<section id="s_management">
<h1>회원관리 페이지</h1>

  <table class="management_table">
  <thead>
	  <tr>
	  	<th >INDEX</th>
	    <th >회원ID</th>
	    <th >회원상태</th>
	    <th >차단/복구</th>
	  </tr>
  </thead>
  
  <tbody>
  	<c:if test = "${ar ne null }">
  		<c:forEach var="vo" items="${requestScope.ar }" varStatus="st">
		  <tr>
		  	<input type="hidden" id="${st.index }userID" name="${st.index }userID" value="${vo.userID }"/>
		  	<input type="hidden" id="${st.index }cPage" name="${st.index }cPage" value="${nowPage }"/>
		    <td>${(nowPage-1)*5 + (st.index+1)}</td>
		    <td>${vo.userID }</td>
			<c:if test = "${vo.status eq '0'}">
		    	<td>정상</td>
			    <td><input type="button" value="차단" class="blc_mem" st="${st.index }" /></td>
		    </c:if>
		    <c:if test = "${vo.status eq '1'}">
		    	<td>경고(1)</td>
			     <td><input type="button" value="차단" class="blc_mem" st="${st.index }"/></td>
		    </c:if>
		    <c:if test = "${vo.status eq '2'}">
		    	<td>경고(2)</td>
			     <td><input type="button" value="차단" class="blc_mem" st="${st.index }"/></td>
		    </c:if>
		    <c:if test = "${vo.status >= '3' and vo.status < '50'}">
		    	<td>차단</td>
			    <td><input type="button" value="복구" class="rcv_mem" st="${st.index }"/></td>
		    </c:if>
		    <c:if test = "${vo.status eq '50'}">
		    	<td>관리자</td>
		    	<td>관리자</td>
		    </c:if>
		  </tr>
		 </c:forEach>
	 </c:if>
  </tbody>
  <tfoot>
		<tr>
              <td colspan="4">${p_code }</td>
		</tr>
   </tfoot>
</table>

</section>

</div>
<jsp:include page="footer.jsp"/>
<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script>
	$(function(){
		//차단버튼클릭시
			$(".blc_mem").bind("click", function(){
				var st = $(this).attr('st');
				var st1 = String(st) + 'userID';
				var st2 = String(st) + 'cPage';
				
				var userID = $('#'+st1).val();
				var cPage = $('#'+st2).val();
				
				$.ajax({
					url: "mem_block",
					type: "post",
					data: "userID="+encodeURIComponent(userID)+"&cPage="+encodeURIComponent(cPage),
					dataType: "json",
				}).done(function(data){
					if(data.res == "1"){
						alert("회원이 정상적으로 차단되었습니다");
						location.href="/manage?cPage="+data.cPage;
					}else{
						alert("회원차단 실패, 다시 시도해주세요");
					}
				});
			});
		
		//복구버튼클릭시
			$(".rcv_mem").bind("click", function(){
				var st = $(this).attr('st');
				var st1 = String(st) + 'userID';
				var st2 = String(st) + 'cPage';
				
				var userID = $('#'+st1).val();
				var cPage = $('#'+st2).val();
				
				$.ajax({
					url: "mem_recover",
					type: "post",
					data: "userID="+encodeURIComponent(userID)+"&cPage="+encodeURIComponent(cPage),
					dataType: "json",
				}).done(function(data){
					if(data.res == "1"){
						alert("회원이 정상적으로 복구되었습니다");
						location.href="/manage?cPage="+data.cPage;
					}else{
						alert("회원복구 실패, 다시 시도해주세요");
					}
				});
			});
	});
</script>
</body>
</html>