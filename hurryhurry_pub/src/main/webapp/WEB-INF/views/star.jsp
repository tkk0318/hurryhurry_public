<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
     <input type="hidden" name="rate" id="rate" value="0"/>
     <input type="hidden" name="star" id="star"/>
     <div class="review_rating">
         <div class="rating">
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