<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>


<script type="text/javascript" src="assets/js/validate.js"></script>
<script>
// 구글 요청 성공 이후 code 값 확인 후 후처리를 위한 함수 실행
window.onload = function() {
	handleAuthorizationCode();
};



// 파라미터를 추가하여 구글 인증 화면으로 넘김
// 인증 성공 시 login.jsp로 다시 돌아오게끔 설정
function startGoogleOAuth() {
	// 상태 토큰 생성 (CSRF 방지를 위해 사용)
	var state = generateStateToken();

	// 인증 엔드포인트 URL 생성
	var authorizationUrl = "https://accounts.google.com/o/oauth2/auth" +
		"?response_type=code" +
		"&client_id=" + encodeURIComponent("1087314881525-abntlv67l2sr625qkd8ocle8os2n96nk.apps.googleusercontent.com") +
		"&redirect_uri=" + encodeURIComponent("http://localhost:8080/NextTrip/login/oauth2/code/google.do") +
		"&scope=email profile" +
		"&state=" + encodeURIComponent(state);

	// 사용자를 Google OAuth 인증 페이지로 리다이렉션
	window.location.href = authorizationUrl;
}

// 상태 토큰 생성 함수
function generateStateToken() {
	return Math.random().toString(36).substring(2, 15) +
		Math.random().toString(36).substring(2, 15);
}




// 인증에 성공하여 code 값을 가져올경우 실행
function handleAuthorizationCode() {
	// URL에서 Authorization Code 추출
	var urlParams = new URLSearchParams(window.location.search);
	var authorizationCode = urlParams.get('code');

	// Authorization Code를 사용하여 액세스 토큰 요청
	requestAccessToken(authorizationCode);
}

// 액세스 토큰 요청
function requestAccessToken(authorizationCode) {
	// 서버 측에서 액세스 토큰 요청을 처리
	var accessTokenEndpoint = "https://oauth2.googleapis.com/token";
	var clientId = "1087314881525-abntlv67l2sr625qkd8ocle8os2n96nk.apps.googleusercontent.com";
	var clientSecret = "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";
	var redirectUri = "http://localhost:8080/NextTrip/login/oauth2/code/google.do";

	var params = "code=" + encodeURIComponent(authorizationCode) +
		"&client_id=" + encodeURIComponent(clientId) +
		"&client_secret=" + encodeURIComponent(clientSecret) +
		"&redirect_uri=" + encodeURIComponent(redirectUri) +
		"&grant_type=authorization_code";

	// HTTP POST 요청으로 액세스 토큰 요청
	var xhr = new XMLHttpRequest();
	xhr.open("POST", accessTokenEndpoint, true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			// 응답에서 액세스 토큰 추출
			var response = JSON.parse(xhr.responseText);
			var accessToken = response.access_token;

			// 액세스 토큰을 사용하여 사용자 정보 요청
			requestUserInfo(accessToken);
		}
	};

	xhr.send(params);
}

// 엑세스 토큰을 가지고 사용자 정보 요청
function requestUserInfo(accessToken) {
	var userInfoEndpoint = "https://www.googleapis.com/oauth2/v3/userinfo";

	// HTTP GET 요청으로 사용자 정보 요청
	var xhr = new XMLHttpRequest();
	xhr.open("GET", userInfoEndpoint, true);
	xhr.setRequestHeader("Authorization", "Bearer " + accessToken);

	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			// 응답에서 사용자 정보 추출
			var userInfo = JSON.parse(xhr.responseText);
			// 반환 받은 사용자 정보를 서버로 보내 처리
			loginWithGoogle(xhr.responseText);
		}
	};

	xhr.send();
}


// 사용자 정보를 사용해 로그인 진행
function loginWithGoogle(userInfo) {
	$.ajax({
		url: "google.do",
		type: "post",
		dataType: "json",
		data: { "userInfo": userInfo },
		success: function(data) {
			location.href = "/NextTrip/index.do";
		},
		error: function(request, status, error) {
			alert('로그인에 실패하였습니다. 다시 시도 해주세요');
			console.log("code : " + request.status);
			console.log("message : " + request.responseText);
			console.log("error : " + error);
			location.href = "/NextTrip/sign-in.do";
		}
	})
}
</script>



<section class="pt-5" style="padding-top: 5rem; margin-top: 100px;">
<div class="container-fluid h-custom">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-md-9 col-lg-6 col-xl-5">
        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
          class="img-fluid" alt="Sample image">
      </div>
      <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
        <form action="sign-in.do" method="post" name="login">
          <div class="d-flex flex-row align-items-center justify-content-center justify-content-lg-start">
            <p class="lead fw-normal mb-0 me-3">Sign in with</p>
            <div class="btn btn-primary btn-floating mx-1 btn-google btn-user btn-block" onclick="startGoogleOAuth();">
            	<i class="fab fa-google fa-fw"></i> Google
            </div>
          </div>

          <div class="divider d-flex align-items-center my-4">
            <p class="text-center fw-bold mx-3 mb-0">Or</p>
          </div>

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

          <div class="d-flex justify-content-between align-items-center">
            <!-- Checkbox -->
            <div class="form-check mb-0">
              <input class="form-check-input me-2" type="checkbox" value="" id="form2Example3" />
              <label class="form-check-label" for="form2Example3">
                Remember me
              </label>
            </div>
            <a href="forgot-password.do" class="text-body">Forgot password?</a>
          </div>

          <div class="text-center text-lg-start mt-4 pt-2">
            <input type="button" class="btn btn-primary btn-lg"
              style="padding-left: 2.5rem; padding-right: 2.5rem;" onclick="loginCHK();" value="Login">
            <p class="small fw-bold mt-2 pt-1 mb-0">Don't have an account? <a href="sign-up.do"
                class="link-danger">Register</a></p>
          </div>

        </form>
      </div>
    </div>
  </div>
</section>



<%@ include file="footer.jsp"%>