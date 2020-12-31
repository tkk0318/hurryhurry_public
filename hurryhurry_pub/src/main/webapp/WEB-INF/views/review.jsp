<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰 페이지</title>
<link rel="stylesheet" type="text/css" href="css/star.css"/>
</head>
<body>
    <%-- <c:if test="${sessionScope.userID == null}">
        	로그인 후 이용 가능한 서비스입니다.
    </c:if>
    <c:if test="${sessionScope.userID != null}"> --%>
	    <form name="reviewform" class="reviewform" method="post" action="review_ok">
	        <input type="hidden" name="rate" id="rate" value="0"/>
	        <p class="title_star">별점과 리뷰를 남겨주세요.</p>
	 
	        <div class="review_rating">
	            <div class="warning_msg">별점을 선택해 주세요.</div>
	            <div class="rating">
	            	<div class="ratefill"></div>
	                <!-- 해당 별점을 클릭하면 해당 별과 그 왼쪽의 모든 별의 체크박스에 checked 적용 -->
	                <input type="checkbox" name="rating" id="rating1" value="1" class="rate_radio" title="1점">
	                <label for="rating1"></label>
	                <input type="checkbox" name="rating" id="rating2" value="2" class="rate_radio" title="2점">
	                <label for="rating2"></label>
	                <input type="checkbox" name="rating" id="rating3" value="3" class="rate_radio" title="3점" >
	                <label for="rating3"></label>
	                <input type="checkbox" name="rating" id="rating4" value="4" class="rate_radio" title="4점">
	                <label for="rating4"></label>
	                <input type="checkbox" name="rating" id="rating5" value="5" class="rate_radio" title="5점">
	                <label for="rating5"></label>
	            </div>
	        </div>
	        <div class="review_contents">
	            <div class="warning_msg">5자 이상으로 작성해 주세요.</div>
	            <textarea rows="10" class="review_textarea" name="content" id="content"></textarea>
	        </div>   
	        <div class="cmd">
	            <input type="button" name="save" id="save" value="등록" onclick="sendData()">
				<input type="hidden" name="star" id="star"/>
				<input type="hidden" name="toilet_name" id="toilet_name"/>
	        </div>
		</form>
	<%-- </c:if> --%>

<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script>
function Rating(){};
Rating.prototype.rate = 0;
Rating.prototype.setRate = function(newrate){
    //별점 마킹 - 클릭한 별 이하 모든 별 체크 처리
    this.rate = newrate;
    let items = document.querySelectorAll('.rate_radio');
    items.forEach(function(item, idx){
        if(idx < newrate){
            item.checked = true;
        }else{
            item.checked = false;
        }
    });
}
let rating = new Rating();//별점 인스턴스 생성

document.addEventListener('DOMContentLoaded', function(){
    //별점선택 이벤트
    document.querySelector('.rating').addEventListener('click',function(e){
        let elem = e.target;
        if(elem.classList.contains('rate_radio')){
            rating.setRate(parseInt(elem.value));
        }
    })
});
//글자 수 초과 체크 이벤트
document.querySelector('.review_textarea').addEventListener('keydown',function(){
    //리뷰 400자 초과 안되게 자동 자름
    let review = document.querySelector('.review_textarea');
    let lengthCheckEx = /^.{400,}$/;
    if(lengthCheckEx.test(review.value)){
        //400자 초과 컷
        review.value = review.value.substr(0,400);
    }
});

function sendData(){
//저장 전송전 필드 체크 이벤트
document.querySelector('#save').addEventListener('click', function(e){
    //별점 선택 안했으면 메시지 표시
    if(rating.rate == 0){
        rating.showMessage('rate');
        return false;
    }
    //리뷰 5자 미만이면 메시지 표시
    if(document.querySelector('.review_textarea').value.length < 5){
        rating.showMessage('review');
        return false;
    }
});


var content;

Rating.prototype.showMessage = function(type){//경고메시지 표시
    switch(type){
        case 'rate':
            //안내메시지 표시
            document.querySelector('.review_rating .warning_msg').style.display = 'block';
            //지정된 시간 후 안내 메시지 감춤
            setTimeout(function(){
                document.querySelector('.review_rating .warning_msg').style.display = 'none';
            },1000);
            return;
            break;
            
        case 'review':
            //안내메시지 표시
            document.querySelector('.review_contents .warning_msg').style.display = 'block';
            //지정된 시간 후 안내 메시지 감춤
            setTimeout(function(){
                document.querySelector('.review_contents .warning_msg').style.display = 'none';
            },1000);
            
			content = document.getElementById('content');
            
            content.focus();
            return;
            break;
    }
}
    
	    var starArray = [];
	    var star;
	    $('input[name="rating"]:checked').each(function(i){
	    	starArray.push($(this).val());
	    });
	    
	    var star = starArray.reduce(function(previous, current){
	    	return previous > current ? previous : current;
	    });
	    
	    $('input#star').val(star);
	    
	    var reviewf = $("form[name=reviewform]").serialize();

if(rating.rate != 0 && document.querySelector('.review_textarea').value.length > 5){
	    
	    $.ajax({
			url:"review_ok",
			type:"post",
			dataType: "json",
			data: reviewf,
		}).done(function(data){
			alert("리뷰가 등록되었습니다.");
			toiletReview();
			
			document.querySelector('.review_textarea').value = '';
			
			var color = '#f0f0f0';
			var check = null;
			
			//전체 라디오 버튼 초기화
			checks = document.querySelectorAll('.rate_radio');
		    checks.forEach(function(item, idx){
		    	item.checked = false;
		    });
			
			toiletReview();
	    }).fail(function(data){
	    	alert("에러");
	    });
   }
}
</script>
</body>
</html>