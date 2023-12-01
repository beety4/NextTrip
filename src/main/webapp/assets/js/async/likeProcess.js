// 좋아요 체크 확인
window.addEventListener('DOMContentLoaded', function() {
	var reviewNo = document.getElementById("reviewNo");
	checkLike(reviewNo.value);
});

// 좋아요 액션
function likeAction(reviewNo) {
	$.ajax({
    	url:"addReviewLike.do",
        type:"post",
        dataType:"json",
        data:{"reviewNo" : reviewNo},
        success: function(data){	
			writeLike(data, reviewNo)
        },
        error: function(request, status, error) {
			alert("401: 로그인이 필요한 서비스입니다.");
			console.log("code : " + request.status);
			console.log("message : " + request.responseText);
			console.log("error : " + error);
		}
	});
}

// 좋아요 여부 가져오기
function checkLike(reviewNo) {
	var likeIt = document.getElementById("isCheck");
	$.ajax({
    	url:"isLike.do",
        type:"post",
        dataType:"json",
        data:{"reviewNo" : reviewNo},
        success: function(data){
			if(data == 1) {
				likeIt.classList.remove('btn-secondary');
				likeIt.classList.add('btn-success');
			} else {
				likeIt.classList.remove('btn-success');
				likeIt.classList.add('btn-secondary');
			}
        },
        error: function(request, status, error) {
			likeIt.classList.remove('btn-success');
			likeIt.classList.add('btn-secondary');
			console.log("code : " + request.status);
			console.log("message : " + request.responseText);
			console.log("error : " + error);
		}
	});
}


// 페이지 작성
function writeLike(data, reviewNo) {
	var likeIt = document.getElementById("like");
	likeIt.innerText = data;
	checkLike(reviewNo);
}

