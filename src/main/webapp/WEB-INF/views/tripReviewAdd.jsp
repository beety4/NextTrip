<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>


<script type="text/javascript" src="assets/js/validate.js"></script>


<section class="pt-5" style="padding-top: 5rem; margin-top: 100px;">
<div class="container wConfig75">

	<br>
	<h1>여행 후기 작성</h1>
	<br>
	
	<!-- 게시글 작성, 수정 폼 idEdit 변수로 작성인지 편집인지 판별 -->
	<c:choose>
	<c:when test="${isEdit == null }">
	<form action="addReview.do" method="post" name="addBoardIt" id="reviewForm" enctype="multipart/form-data">
		<table class="table table-bordered customize">
			<tr>
				<th>제목</th>
				<td><input type="text" class="form-control" placeholder="제목을 적어주세요."
					id="title" name="title" maxlength="100" autofocus="autofocus"></td>
			</tr><tr>
				<th>지역</th>
				<td><input type="text" class="form-control" placeholder="여행 지역을 입력해주세요"
					id="region" name="region"></td>
			</tr><tr>
				<th>내용</th>
				<td><textarea class="form-control" id="content" name="content" placeholder="여행 후기를 작성해주세요!"
								maxlength="4096" style="height: 300px;"></textarea></td>
			</tr>
			<tr>
				<th>파일</th>
				<td>
					<input type="file" id="file" name="file" onchange="fileProcess(this, 'assets/img/reviewIMG/default.png');">
					<img src="" id="preview" width="300px;">
				</td>
			</tr>
		</table>

		<input type="button" class="btn btn-primary" value="업로드" style="float:right;" onclick="addBoard();">
	</form>
	</c:when>
	<c:when test="${isEdit == true }">
	<form action="editReview.do" method="post" name="editBoardIt">
		<input type="text" name="reviewNo" value="${reviewDTO.reviewNo }" style="display: none;" autofocus="autofocus">
		<table class="table table-bordered customize">
			<tr>
				<th>제목</th>
				<td><input type="text" class="form-control" placeholder="제목을 적어주세요."
					id="title" name="title" maxlength="100" value="${reviewDTO.title }"></td>
			</tr><tr>
				<th>지역</th>
				<td><input type="text" class="form-control" placeholder="여행 지역을 입력해주세요"
					id="region" name="region" value="${reviewDTO.region }"></td>
			</tr><tr>
				<th>내용</th>
				<td><textarea class="form-control" id="content" name="content" placeholder="여행 후기를 작성해주세요!"
								maxlength="4096" style="height: 300px;">${reviewDTO.content }</textarea></td>
			</tr>
		</table>
		
		<input type="button" class="btn btn-primary" value="수정" style="float:right;" onclick="editBoard();">
	</form>
	</c:when>
	</c:choose>
	
</div>
</section>


<%@ include file="footer.jsp"%>