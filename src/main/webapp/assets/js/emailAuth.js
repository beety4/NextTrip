// Email Check boolean
var keyCheck = 0;
var isTimeout = 0;
var authKey = null;


// 이메일 전송 함수
function sendKey(){
    $.ajax({
    	url:"sendMail.do",
        type:"post",
        dataType:"text",
        data:{"email" : $("#email").val()},
        success: function(data){
			document.getElementById("emailConfirm").style.display = "block";
			timeAttack();
            authKey = data;
        },
        error: function(request, status, error) {
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
		keyCheck = 0;
		return;
	}
    if(inputKey == authKey){
        alert("인증되었습니다.");
        keyCheck = 1;
        return;
    }
    alert("번호가 다릅니다.");
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