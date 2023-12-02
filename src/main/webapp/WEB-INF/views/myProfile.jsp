<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ page import="dto.UserDTO"%>

<style>
body {
    margin: 0;
    padding-top: 40px;
    color: #2e323c;
    background: #f5f6fa;
    position: relative;
    height: 100%;
}
.account-settings .user-profile {
    margin: 0 0 1rem 0;
    padding-bottom: 1rem;
    text-align: center;
}
.account-settings .user-profile .user-avatar {
    margin: 0 0 1rem 0;
}
.account-settings .user-profile .user-avatar img {
    width: 90px;
    height: 90px;
    -webkit-border-radius: 100px;
    -moz-border-radius: 100px;
    border-radius: 100px;
}
.account-settings .user-profile h5.user-name {
    margin: 0 0 0.5rem 0;
}
.account-settings .user-profile h6.user-email {
    margin: 0;
    font-size: 0.8rem;
    font-weight: 400;
    color: #9fa8b9;
}
.account-settings .about {
    margin: 2rem 0 0 0;
    text-align: center;
}
.account-settings .about h5 {
    margin: 0 0 15px 0;
    color: #007ae1;
}
.account-settings .about p {
    font-size: 0.825rem;
}
.form-control {
    border: 1px solid #cfd1d8;
    -webkit-border-radius: 2px;
    -moz-border-radius: 2px;
    border-radius: 2px;
    font-size: .825rem;
    background: #ffffff;
    color: #2e323c;
}

.card {
    background: #ffffff;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    border: 0;
    margin-bottom: 1rem;
}
</style>


<script src="assets/js/validate.js"></script>


<section class="pt-5" style="padding-top: 5rem; margin-top: 100px;">
<form action="myProfile.do" method="post" name="updateProfile" enctype="multipart/form-data">
<div class="container-fluid h-custom">
<div class="container">
<div class="row gutters">
<div class="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12">
<div class="card h-100">
	<div class="card-body">
		<div class="account-settings">
			<div class="user-profile">
				<div class="user-avatar"><br>
					<img src="${userDTO.img }" alt=".." id="preview" width="100px;">
				</div>
				<h5 class="user-name">${userDTO.name }</h5>
				<h6 class="user-email">${userDTO.email }</h6>
			</div>

			<div class="about">
				<input type="file" id="file" name="file" onchange="fileProcess(this, 'assets/img/profileIMG/default.png');">
				<br><br><br><br><br>
				<p style="color:red;">모든 필드를 입력하셔야 변경 가능합니다.</p>
			</div>

		</div>
	</div>
</div>
</div>

<div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
<div class="card h-100">
	<div class="card-body">
		<div class="row gutters">
			<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
				<h6 class="mb-2 text-primary">Personal Details</h6>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="fullName">ID</label>
					<input type="text" class="form-control" id="id" name="id" value="${userDTO.id }" readonly="readonly">
				</div>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="eMail">Name</label>
					<input type="email" class="form-control" id="name" name="name" value="${userDTO.name }" autofocus="autofocus">
				</div>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="phone">Birth</label>
					<input type="text" class="form-control" id="birth" value="${userDTO.birth }" readonly="readonly">
				</div>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="eMail">Gender</label>
					<input type="email" class="form-control" id="Gender" value="${userDTO.gender == 0 ? 'Male' : 'Female'}"  readonly="readonly">
				</div>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="eMail">Email</label>
					<input type="email" class="form-control" id="email" value="${userDTO.email }"  readonly="readonly">
				</div>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="eMail">JoinDate</label>
					<input type="email" class="form-control" id="joindate" value="${userDTO.joindate }"  readonly="readonly">
				</div>
			</div>
		</div>
		<div class="row gutters">
			<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
				<h6 class="mt-3 mb-2 text-primary">Change Password</h6>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="Street">Your Password</label>
					<input type="password" class="form-control" id="passwordPre" placeholder="Enter Password">
				</div>
			</div>
			<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
				<div class="form-group">
					<label for="ciTy">Change Password</label>
					<input type="password" class="form-control" id="password" name="password" placeholder="Enter Password Check">
				</div>
			</div>
		</div>
		<br>
		<div class="row gutters">
			<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
				<div class="text-right">	
					<button type="button" class="btn btn-primary" onclick="editProfile();">Update</button>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</div>
</div>
</div>
</form>
</section>



<%@ include file="footer.jsp"%>