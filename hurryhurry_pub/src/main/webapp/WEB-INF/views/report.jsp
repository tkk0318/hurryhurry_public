<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신고 현황 </title>
<link rel="stylesheet" type="text/css" href="css/report.css" />
</head>
<body>
<jsp:include page="header.jsp"/>

<section id="s_report">
<h1>신고 현황</h1>

<input type="button" value="등록된 전체 리뷰 보기" id="totalbutton" onclick="javascript:location.href='totalPost'"/>

<table class="report_table">
  <thead>
	  <tr>
	    <th>화장실명</th>
	    <th>신고날짜</th>
	  </tr>
  </thead>
  
  <tbody>
			<c:if test="${ar ne null }">
				<c:forEach var="vo" items="${requestScope.ar }" varStatus="st">
				<tr>
					<th class="toggle" st="${st.index}" style="cursor:pointer;">${vo.toilet_name }</th>
					<td>${fn:substring(vo.report_date, 0, 10) }</td>
				</tr>
				<tr style= "display:none;" class="${st.index}">
					<input type="hidden" id="${st.index}vidx" name="${st.index}vidx" value="${vo.review_idx}"/>
					<input type="hidden" id="${st.index}writer" name="${st.index}writer" value="${vo.writer}"/>
					<input type="hidden" id="${st.index}pidx" name="${st.index}pidx" value="${vo.report_idx}"/>
					<td colspan="2">- 리뷰내용: ${vo.content }<br/>
									- 신고사유: ${vo.reason }<br/>
									<input type="button" value="리뷰차단" class="rv_bl" st="${st.index}"/>
									<input type="button" value="신고철회" class="rp_dn" st="${st.index}"/>
									<input type="button" value="회원차단" class="mb_bl" st="${st.index}"/>
					</td>
				</tr>
				</c:forEach>
			</c:if>
			<c:if test="${ar eq null }">
				<tr>
					<td>신고된 리뷰가 없습니다.</td>
				</tr>
			</c:if>
			</tbody>
 
  </tbody>
</table>

</section>

<jsp:include page="footer.jsp"/>


<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	<script>
		$(function(){
			//신고내역의 화장실이름 클릭시 - 해당 리뷰와 신고내역 자세히 보기
			$('.toggle').bind('click', function(){
				var st = $(this).attr('st');
				if($('.'+st).css('display') == 'none'){
					$('.'+st).show();
				}else{
					$('.'+st).hide();
				}
			});
			
			//리뷰차단버튼 클릭시 - 해당리뷰차단, 해당회원경고(status+1), 중복된 신고들 처리
			$(".rv_bl").bind("click", function(){
				var st = $(this).attr('st');
				var st1 = String(st) + 'vidx';
				var st2 = String(st) + 'writer';

				var review_idx = $('#'+st1).val();
				var writer = $('#'+st2).val();
				
				$.ajax({
					url: "review_block",
					type: "post",
					data: "review_idx="+encodeURIComponent(review_idx)+"&writer="+encodeURIComponent(writer),
					dataType: "json",
				}).done(function(data){
					if(data.res == "1"){
						alert("리뷰가 정상적으로 차단되었습니다.");
						location.href="/report";
					}else{
						alert("리뷰차단 실패");
					}
				});
			});
			
			//신고철회버튼 클릭시
			$(".rp_dn").bind("click", function(){
				var st = $(this).attr('st');
				var st3 = String(st) + 'pidx';
				
				var report_idx = $('#'+st3).val();
				$.ajax({
					url: "report_deny",
					type: "post",
					data: "report_idx="+encodeURIComponent(report_idx),
					dataType: "json",
				}).done(function(data){
					if(data.res == "1"){
						alert("신고가 정상적으로 철회되었습니다");
						location.href="/report";
					}else{
						alert("신고철회 실패, 다시 시도해주세요");
					}
				});
			});
			
			//회원차단버튼 클릭시 - 회원차단, 해당리뷰차단, 해당하는 신고글 모두 처리
			$(".mb_bl").bind("click", function(){
				var st = $(this).attr('st');
				var st1 = String(st) + 'vidx';
				var st2 = String(st) + 'writer';
				
				var review_idx = $('#'+st1).val();
				var userID = $('#'+st2).val();
				
				$.ajax({
					url: "member_block",
					type: "post",
					data: "review_idx="+encodeURIComponent(review_idx)+"&userID="+encodeURIComponent(userID),
					dataType: "json",
				}).done(function(data){
					if(data.res == "1"){
						alert("회원차단 완료");
						location.href="/report";
					}else{
						alert("회원차단 실패, 다시 시도해주세요");
					}
				});
			});
		});
	</script>


</body>
</html>