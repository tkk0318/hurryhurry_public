<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메뉴바</title>
</head>
<body>
	<ul>
		<c:if test="${sessionScope.mvo == null }"> <!-- 로그인 전 -->
			<li><a href=>로그인</a></li>
			<li><a href=>회원가입</a></li>
			<li><a href="">마이페이지</a></li>
		</c:if>
		<c:if test="${sessionScope.mvo != null }"> <!-- 로그인 후 -->
			<li><a href=>로그아웃</a></li>
			<c:if test="${sessionScope.mvo.status != '99' }"> <!-- 일반회원일 때 -->
					<li><a href="/mypage">마이페이지</a></li>
			</c:if>	
			<c:if test="${sessionScope.mvo.status == '99 }"> <!-- 관리자일 때 -->
				<li><a href=>신고현황</a></li>
				<li><a href=>회원관리</a></li>
				<li><a href=>전체리뷰보기</a></li>
			</c:if>
		</c:if>
	</ul>
</body>
</html>