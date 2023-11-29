// 초기 실행 
function init() {
	gapi.load('auth2', function() {
		gapi.auth2.init();
		options = new gapi.auth2.SigninOptionsBuilder();
		options.setPrompt('select_account');
		oprions.setScope('email profile openid https://www.googleapis.com/auth/user.birthday.read');
		gapi.auth2.getAuthInstance().attachClickHandler('GgCustomLogin', options, onSignIn, onSignInFailure);
	});
}


function onSignIn(googleUser) {
	var access_token = googleUser.getAuthResponse().access_token;

    $.ajax({
    	url:"https://people.google.com/v1/people/me",
    	type:"get",
        data:{"personFields" : "birthdays",
        	  "key" : "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",
        	  "access_token" : access_token
        },
        success: function(data){
			var profile = googleUser.getBasicProfile();
			console.log(profile);
        },
        error: function(request, status, error) {
			alert("오류가 발생했습니다.");
			console.log("code : " + request.status);
			console.log("message : " + request.responseText);
			console.log("error : " + error);
		}
	});
}


function onSignInFailure(t) {
	console.log(t);
}
