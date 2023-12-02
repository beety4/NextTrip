<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ page import="dto.tourAPI.TourSpotDetailDTO"%>
<%@ page import="java.util.ArrayList"%>


<link href="assets/css/table.css" rel="stylesheet">
<style>
.search {
	position: relative;
	box-shadow: 0 0 40px rgba(51, 51, 51, .1);
}

.search input {
	height: 60px;
	text-indent: 25px;
	border: 2px solid #d6d4d4;
}

.search input:focus {
	box-shadow: none;
	border: 2px solid blue;
}

.search .fa-search {
	position: absolute;
	top: 20px;
	left: 16px;
}

.search button {
	position: absolute;
	top: 5px;
	right: 5px;
	height: 50px;
	width: 110px;
}
</style>

<section class="pt-5" style="padding-top: 5rem; margin-top: 100px;">
<div class="container wConfig75">


	<br><br><br>
	<div class="row">
		<form action="showTourSpot.do" method="post" accept-charset="utf-8">
		<div class="row height d-flex justify-content-center align-items-center">
				<select name="contentTypeId" style="float:left; width:150px;">
						<option value="12">관광지</option>
						<option value="14">문화시설</option>
						<option value="15">행사/공연/축제</option>
						<option value="25">여행코스</option>
						<option value="28">레포츠</option>
						<option value="32">숙박</option>
						<option value="38">쇼핑</option>
						<option value="39">음식점</option>
				</select>
			<div class="col-md-8">
				<div class="search">
					<i class="fa fa-search"></i>
					<input type="text" class="form-control" placeholder="찾고 싶은 관광지를 입력해주세요!" name="keyword">
					<button type="submit" class="btn btn-primary">Search</button>
				</div>
			</div>
		</div>
		</form>
	</div>
	<br><br><br>
		
		<div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>재목</th>
						<th>주소</th>
						<th>수정일</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="p" items="${tourSpotSearchList }">
					<tr onclick="location.href='showTourSpotDetail.do?contentid=${p.contentid }'">
						<td>${p.title }</td>
						<td>${p.addr1 }</td>
						<td>${p.modifiedtime }</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>


</section>


<%@ include file="footer.jsp"%>