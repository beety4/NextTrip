<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ page import="dto.ReviewDTO" %>


<link href="assets/css/table.css" rel="stylesheet">
<script src="assets/js/async/likeProcess.js"></script>
<script src="assets/js/validate.js"></script>


<section class="pt-5" style="padding-top: 5rem; margin-top: 100px;">
<div class="container wConfig75">

	<br>
	<br>
    <div class="card" style="width:70%; border: 1px solid black;margin: auto;">
    	<input type="text" id="reviewNo" value="${reviewDTO.reviewNo }" style="display: none;">
		<div class="card-header">
			<strong>제목 : ${reviewDTO.title }</strong>
			<div style="float:right">작성일 : ${reviewDTO.date }</div>
		</div>
		<div class="card-body" style="min-height: 250px;">
			${reviewDTO.content }
		</div>
		
		<div class="card-footer" style="display: flex; justify-content: space-between;">
		
			<div><button id="isCheck" onclick="likeAction(${reviewDTO.reviewNo });" class="btn btn-secondary">
					LIKE :&nbsp; <div id="like" style="float:right;">${reviewDTO.likeC }</div>
				</button>
			</div>
		
			<c:if test="${sNAME.equals(reviewDTO.name) }">
			<div style="float:right;">
			<input type="button" class="btn btn-primary" value="수정" style="float:right; margin-left: 10px;"
				onclick="location.href='editReview.do?reviewNo=${reviewDTO.reviewNo }'">	
			<form action="deleteReview.do" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');" style="float:right;">
				<input type="text" name="reviewNo" value="${reviewDTO.reviewNo }" style="display:none; ">
				<input type="submit" class="btn btn-danger" value="삭제">
			</form>
			</div>
			</c:if>
		</div>
	</div>
	
	<br><hr>
	
	<table class="table" style="width : 60%;">
  	<tbody>
  		<c:forEach var="p" items="${commentList }">
    	<tr>
    	<th scope="row">
      			${p.name } - ${p.date }<br>
      			${p.content }
      			<c:if test="${sNAME.equals(p.name) }">
    				<form action="deleteReviewComment.do" method="post" style="float:right;">
    					<input type="text" name="reviewNo" value="${reviewDTO.reviewNo }" style="display:none;">
    					<input type="text" name="commentNo" value="${p.commentNo }" style="display:none;">
   						<input type="submit" class="btn btn-danger" value="삭제">
    				</form>
    			</c:if>
      	</th>
    	</tr>
    	</c:forEach>
  	</tbody>	
	</table>
	
	
	
	<div style="display: flex; justify-content:center; align-items:center; margin: 0">
		<form action="addReviewComment.do" method="post" name="commentIt" style="display:flex;align-items: center;">
			<input type="text" name="reviewNo" style="display: none;" value="${reviewDTO.reviewNo }">
			<input type="text" class="form-control" id="comment" name="comment" 
				placeholder="댓글을 입력해주세요" style="margin-right: 10px; width:500px;">
      		<input type="button" class="btn btn-primary" value="전송" onclick="commentAction();">
      	</form>
	</div>
	

	
		
</div>
</section>


<%@ include file="footer.jsp"%>