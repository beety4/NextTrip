<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ page import="dto.TourSpotDetailDTO"%>
<%@ page import="java.util.ArrayList"%>


<link href="assets/css/table.css" rel="stylesheet">


<section class="pt-5" style="padding-top: 5rem; margin-top: 100px;">
<div class="container wConfig75">


	<br><br>
	<input type="text" placeholder="지역을 입력해주세요!">
	<input type="button" value="검색">

		<div class="row">
			<c:forEach var="p" items="${tourSpotList}">
				<div class="col-md-4 mb-4">
					<div class="card overflow-hidden shadow" style="height: 500px;">
						<!-- 우선순위 : firstImage -> firstImage2 -> noImage.png -->
						<img class="card-img-top"
							src="${(not empty p.firstimage) ? p.firstimage : ((not empty p.firstimage2) ? p.firstimage2 : 'assets/img/noImage.png') }"
							style="height: 60%;" />
						<div class="card-body py-4 px-3">
							<div
								class="d-flex flex-column flex-lg-row justify-content-between mb-3">
								<h4 class="text-secondary fw-medium">
									<a class="link-900 text-decoration-none stretched-link"
										href="showTourSpotDetail.do?contentid=${p.contentid }">${p.title }</a>
								</h4>
								<span class="fs-1 fw-medium"></span>
							</div>
							<div class="d-flex align-items-center">
								<img src="assets/img/dest/navigation.svg"
									style="margin-right: 14px" width="20" alt="navigation" /><span
									class="fs-0 fw-medium">${p.addr1 }</span>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		
		


</div>
</section>


<%@ include file="footer.jsp"%>