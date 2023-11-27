<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>


<link href="assets/css/cardView.css" rel="stylesheet">


<section class="pt-5" style="padding-top: 5rem; margin-top: 100px;">
<div class="container wConfig75" style="width:1500px; margin-left:220px;">
<div class="row">
	<c:forEach var="p" items="${planList}">
		<div class="col-md-4" style="height: 290px; width: 330px; margin-right: 50px; margin-bottom: 50px">
			<div class="profile-card-2" style="height: 300px; width: 400px"
				onclick="location.href='showPlan.do?planNo=${p.planNo }';">
				<img src="assets/img/planIMG/${p.planImg }" class="img img-responsive" style="height: 300px; width: 400px">
				<div class="profile-name"style="text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;">${p.planName }</div>
				<div class="profile-username" style="text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;">${p.startDate }
					~
					${p.endDate }
				</div>
			</div>
		</div>
	</c:forEach>
</div>
</div>
</section>


<%@ include file="footer.jsp"%>