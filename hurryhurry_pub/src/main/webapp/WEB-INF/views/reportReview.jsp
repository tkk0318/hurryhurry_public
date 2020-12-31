<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div class="modal-header">
	<h2 class="modal-title" id="modal">신고하기</h2>
</div>

<div class="modal-body">
	<form>
		<div>
			<label for="claimOption">신고 사유</label>
			<select id="claimOption" name="claimOption">
				<option value="영리목적/홍보성" selected>영리목적/홍보성</option>
				<option value="개인정보 노출">개인정보 노출</option>
				<option value="욕설/인신공격">욕설/인신공격</option>
				<option value="음란/선정성">음란/선정성</option>
				<option value="불법정보">불법정보</option>
			</select> <input type="button" id="reportReview" name="reportReview" value="신고하기" />
		</div>
	</form>
</div>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"
		integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
		crossorigin="anonymous"></script>
		
<script>
	$(function(){
		$("#reportReview").bind("click",function(){
			var whyReport = $("#claimOption").val();	//신고사유
			var review_idx = sessionStorage.getItem("review_idx");	//신고하는 리뷰의 idx
			var reporter = sessionStorage.getItem("reporter");	//신고하는 회원의 id
			
			$.ajax({
				url: "reportReview",
				type: "post",
				data: "reason="+encodeURIComponent(whyReport)+"&review_idx="+encodeURIComponent(review_idx)+"&reporter="+encodeURIComponent(reporter),
				dataType: "json",
			}).done(function(data){ 
				if(data.res == "1"){
					alert("신고가 접수되었습니다.");
					sessionStorage.removeItem("review_idx"); //세션에서 review_idx제거
					sessionStorage.removeItem("reporter"); //세션에서 reporter제거
				}else{
					alert("신고 실패, 다시 시도해주세요.");
				}
			});
		});
		
	});
</script>