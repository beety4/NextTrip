<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ page import="dto.UserDTO" %>


<style>
	.emailInputSize {
		width : 500px;
		float : left;
	}
	.emailInputBtn {
		padding-left: 1rem;
		padding-right: 1rem;
		margin-left : 30px; 
	}
</style>
<script src="assets/js/emailAuth.js"></script>
<script src="assets/js/validate.js"></script>


<section class="pt-5" style="padding-top: 5rem; margin-top: 100px;">
<div class="container-fluid h-custom">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-md-9 col-lg-6 col-xl-5">
        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
          class="img-fluid" alt="Sample image">
      </div>
      <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
        <form action="sign-up.do" method="post" name="register">
          <div class="d-flex flex-row align-items-center justify-content-center justify-content-lg-start">
            <p class="lead fw-normal mb-0 me-3">Register Account</p>
          </div><br>


          <!-- Email input -->
          <div class="form-outline mb-4">
            <input type="text" id="id" name="id" class="form-control form-control-lg"
              placeholder="Enter ID" />
          </div>

          <!-- Password input -->
          <div class="form-outline mb-3">
            <input type="password" id="password" name="password" class="form-control form-control-lg"
              placeholder="Enter password" />
          </div>
          
          <!-- Password input -->
          <div class="form-outline mb-4">
            <input type="password" id="passwordChk" name="passwordChk" class="form-control form-control-lg"
              placeholder="Enter password Check" />
          </div>
          
          <!-- Password input -->
          <div class="form-outline mb-3">
            <input type="text" id="name" name="name" class="form-control form-control-lg"
              placeholder="Enter Name" />
          </div>
          
          <div class="form-outline mb-3">
            <input type="date" id="birth" name="birth" class="form-control form-control-lg"
              placeholder="Enter Birth" />
          </div>
          
          <div class="form-outline mb-3">
          	Gender :&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio" id="genderM" name="gender" class="form-check-input"
              placeholder="Gender" value="0" checked="checked">Male&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio" id="genderF" name="gender" class="form-check-input"
              placeholder="Gender" value="1">FeMale
          </div>
          
          
          <!-- Password input -->
          <div class="form-outline mb-3">
            <input type="email" id="email" name="email" class="form-control form-control-lg emailInputSize"
              placeholder="Enter Email Address" />
            <button id="btnsendKey" type="button" class="btn btn-primary emailInputBtn" onclick="sendKey();">전송</button>
          </div>
          
          <div id="emailConfirm" class="form-outline mb-3" style="display : none;">
            <input type="email" id="authKey" name="authKey" class="form-control form-control-lg emailInputSize"
              placeholder="Enter Email AuthKey" />
            <button id="btnchecKey" type="button" class="btn btn-primary emailInputBtn" onclick="checKey();">확인</button>
          </div>

		  <div id="timeAttack" style="display : none;"></div>


          <div class="text-center text-lg-start mt-4 pt-2">
            <input type="button" class="btn btn-primary btn-lg"
              style="padding-left: 2.5rem; padding-right: 2.5rem;" onclick="registerCHK();" value="Register">
            <p class="small fw-bold mt-2 pt-1 mb-0">Do you already have an account? <a href="sign-in.do"
                class="link-danger">Login</a></p>
          </div>
        </form>
      </div>
    </div>
  </div>
</section>



<%@ include file="footer.jsp"%>