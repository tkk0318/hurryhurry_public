<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
				<div class="login">
				  <form action="login" method="post">
				  	<input type="hidden" name="type" value="login"/>
					<div class="input_area">
						<p>
						 <label for="s_id">아이디</label>
						 <input type="text" name="userID" id="userID"/>
						</p>
					
					</div>
					<div >
						<span>
						 <%-- <a href="javascript:exe()">로그인</a> --%>
						 <a id="login_btn">로그인</a>
						</span>
					</div>
					<div ></div>
					<p>
						<input type="checkbox" name="chk" 
						 id="ch01"/><label for="ch01">
						 아이디저장</label>
						<span>
						  <a href="">아이디/비밀번호찾기</a>
						</span>
					</p>
				  </form>						
				</div>
<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script>

	$(function(){
		
		//  아이디가 login_btn인 요소에 클릭 이벤트 등록
		$("#login_btn").bind("click", function(){
			
			// 현재 문서에서 아이디가 s_id와 s_pw인 요소의 값을 얻어낸다.
			var id = $("#userID").val();			
			
			if(id.trim().length < 1){
				//하나도 입력하지 않은 경우! 또는 공백만 입력한 경우
				alert("아이디를 입력하세요");
				$("#userID").val("");
				$("#userID").focus();
				return;
			}			
			
			//console.log(id);
			
			//비동기식 통신 수행!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			$.ajax({
				url: "login",
				type: "post",
				data: "userID="+encodeURIComponent(id),
				dataType: "json",
			}).done(function(data){
				//요청에 성공했을 때만 수행
				//alert(data.res); // data.res의 값이 1이면 정상 로그인이 된 경우!
									// 0이면 아이디 및 비밀번호가 틀린경우!
				if(data.res == "1"){
					alert(data.mvo.userID+"님 ㅎㅇㅎㅇ. ");
					location.href="/mypage";
				}else{
					alert("아이디 또는 비밀번호가 다릅니다.");
				}
			});
		});
	});
	
</script>		
				
</body>



</html>