// Email Check boolean
var isKeyCheck = 0;
var isTimeout = 0;
var authKey = null;


// 이메일 전송 함수
function sendKey(){
	var email = $("#email").val();
	if(email == "") {
		alert("이메일을 입력해 주세요!");
		return;
	}
	
    $.ajax({
    	url:"sendMail.do",
        type:"post",
        dataType:"text",
        data:{"email" : email},
        success: function(data){
			document.getElementById("emailConfirm").style.display = "block";
			timeAttack();
            authKey = data;
        },
        error: function(request, status, error) {
			alert('이메일 인증에 실패하였습니다! 다시 시도해주세요');
			console.log("code : " + request.status);
			console.log("message : " + request.responseText);
			console.log("error : " + error);
		}
        
	});
};

// 키 확인 함수
function checKey(){
    var inputKey = document.getElementById("authKey").value;

	if(isTimeout == 1) {
		alert("시간이 초과되었습니다!");
		isKeyCheck = 0;
		return;
	}
    if(inputKey == authKey){
		document.getElementById("timeAttack").style.display = 'none';
		document.getElementById("email").readOnly = true;
		document.getElementById("authKey").readOnly = true;
		document.getElementById("btnsendKey").disabled = true;
		document.getElementById("btnchecKey").disabled = true;
		
        alert("인증되었습니다.");
        isKeyCheck = 1;
        return;
    }
    alert("인증번호가 틀렸습니다.");
    return;
}

// 지정한 시간 타이머
function timeAttack() {
	var time = 180;	// 3분 -> 180초
	var min = "";
	var sec = "";
	try {
        var timeView = document.getElementById("timeAttack");
        timeView.style.color = 'red';
        timeView.style.display = 'block';
          
        	var x = setInterval(function() {
				min = parseInt(time/60);
				sec = time%60;
				   
				timeView.innerHTML = min + "분" + sec + "초";
				time--;
				   
				if(time<0) {
					clearInterval(x);
					timeView.innerHTML = "시간초과";
					isTimeout = 1;
				}
			}, 1000);	
	} catch (error) {
		console.log(error);
	}
}