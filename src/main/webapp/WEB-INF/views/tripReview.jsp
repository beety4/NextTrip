<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>



<link href="assets/css/table.css" rel="stylesheet">


<section class="pt-5" style="padding-top: 5rem; margin-top: 100px;">
<div class="container wConfig75">

	<br><br><br>
	<div>
	<div>
		<c:if test="${sID != null }">
			<input type="button" class="btn btn-primary" value="글 작성" style="float:left;" onclick="location.href='addReview.do'">
		</c:if>
	</div>
	<form action="tripReviewSearch.do" method="post">
	<div class="setRight">
		<input type="text" name="pageNo" value="1" style="display:none;">
		<select class="form-select" name="search">
  			<option value="1" selected>제목</option>
  			<option value="2">본문</option>
  			<option value="3">제목+본문</option>
		</select>
		<input type="text" name="searchIt" placeholder="검색할 단어를 입력하세요" style="margin-left : 5px;">
		<input type="submit" class="btn btn-primary" value="검색" style="margin-left : 5px;">
	</div>
	</form>
	</div>
	<br><br>

	<div class="limiter">
		<table>
			<thead>
				<tr class="table100-head">
					<th class="column1">번호</th>
					<th class="column2">제목</th>
					<th class="column3">지역</th>
					<th class="column4">작성일</th>
					<th class="column5">조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="p" items="${reviewList}">
					<tr onclick="location.href='tripReviewDetail.do?reviewNo=${p.reviewNo }'">
						<td class="column1">${p.reviewNo }</td>
						<td class="column2">${p.title }</td>
						<td class="column3">${p.region }</td>
						<td class="column4">${p.date.split(" ")[0] }</td>
						<td class="column5">${p.viewC }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<%
		// 페이징 처리
		int totalPage = (int)request.getAttribute("totalPage");
		int pageNo = (int)request.getAttribute("pageNo");
		int prev = (pageNo > 1) ? pageNo - 1 : 1;
		int next = (pageNo < totalPage) ? pageNo + 1 : totalPage;

		
		if(request.getAttribute("isSearch") == null) {
	%>
		<br>
		<div style="text-align: center;">
		<a href="tripReview.do?pageNo=<%=prev %>">이전</a>
    	<% for (int i = 1; i <= totalPage; i++) { %>
        	<a href="tripReview.do?pageNo=<%=i %>" <%= (i == pageNo) ? "style='font-weight: bold;color:red;'" : "" %>><%=i %></a>
    	<% } %>
    	<a href="tripReview.do?pageNo=<%=next %>">다음</a>
		</div>
		
		
	<% } %>
	

</div>
</section>


<%@ include file="footer.jsp"%>