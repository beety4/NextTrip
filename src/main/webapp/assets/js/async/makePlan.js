// planName 변경 Ajax
function setPlanName() {
	var planName = $("#planName").val();
	if(planName == "") {
		alert("변경할 이름을 입력해주세요!");
		return;
	}
	
    $.ajax({
    	url:"setPlanName.do",
        type:"post",
        dataType:"text",
        data:{"planName" : planName},
        success: function(data){
			if(data != "1") {
				alert('이름 변경 중 오류가 발생했습니다!');
				return;
			}
			toggleItem();
        },
        error: function(request, status, error) {
			alert('통신 에러 발생 - 다시 시도해주세요');
			console.log("code : " + request.status);
			console.log("message : " + request.responseText);
			console.log("error : " + error);
		}
        
	});
};


// planName 이름 확인-변경 토글
function toggleItem() {
	var chk = document.getElementById("planName").readOnly
	var edit = document.getElementById("planNameEdit");
	var confirmIt = document.getElementById("planNameConfirm");
	
	if(chk) {
		chk = false;
		edit.style.display = 'block';
		confirmIt.style.display = 'none';
	} else {
		chk = true;
		edit.style.display = 'none';
		confirmIt.style.display = 'block';
	}
}

