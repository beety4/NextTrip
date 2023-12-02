// 북마크 확인
window.addEventListener('DOMContentLoaded', function() {
	var cont = document.getElementById("contentid");
	bookmarkCheck(cont.value);
});

// 북마크 액션
function bookmarkAction(contentid, firstimage, addr1, title) {
	console.log("hi");
	$.ajax({
    	url:"bookmarkAction.do",
        type:"post",
        dataType:"json",
        data:{"contentid" : contentid,
        	  "firstimage" : firstimage,
        	  "addr1" : addr1,
        	  "title" : title
        },
        success: function(data) {
			if(data == 1) {			// 추가됨
				writePage(data);
			} else if(data == 0) { 	// 삭제됨
				writePage(data);
			} else {				// 로그인 X
				alert("401: 로그인이 필요한 서비스입니다.");
			}
        },
        error: function(request, status, error) {
			alert("401: 로그인이 필요한 서비스입니다.");
			console.log("code : " + request.status);
			console.log("message : " + request.responseText);
			console.log("error : " + error);
		}
	});
}

// 북마크 여부 가져오기
function bookmarkCheck(contentid) {
	$.ajax({
    	url:"bookmarkCheck.do",
        type:"post",
        dataType:"json",
        data:{"contentid" : contentid},
        success: function(data){
			writePage(data);
        },
        error: function(request, status, error) {
			writePage(data);
			console.log("code : " + request.status);
			console.log("message : " + request.responseText);
			console.log("error : " + error);
		}
	});
}


// 페이지 작성
function writePage(data) {
	var what = document.getElementById("bookmark");
	if(data == 1) {
		what.classList.remove("btn-success");
		what.classList.add("btn-secondary");
		what.value = "북마크 삭제";
	} else {
		what.classList.remove("btn-secondary");
		what.classList.add("btn-success");
		what.value = "북마크 추가"
	}
}

