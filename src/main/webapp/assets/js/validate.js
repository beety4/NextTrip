// Register validate
function registerCHK() {
    var id = document.getElementById("id");
    var pw = document.getElementById("password");
    var pwc = document.getElementById("passwordChk");
    var name = document.getElementById("name");
    var birth = document.getElementById("birth");
    var email = document.getElementById("email");

	

    if (id.value == "") {
        alert("아이디를 입력하세요");
        id.focus();
        return false;
    }
    if (name.value == "") {
        alert("이름을 입력하세요");
        name.focus();
        return false;
    }
    if (pw.value == "") {
        alert("비밀번호를 입력하세요");
        pw.focus();
        return false;
    }
    var pwform = /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,15}$/;
    if (!pwform.test(pw.value)) {
        alert("영문자+숫자 조합으로 6~15자리 사용해야 합니다");
        pw.focus();
        return false;
    }
    if (pwc.value !== pw.value) {
        alert("비밀번호가 일치하지 않습니다!");
        pw.focus();
        return false;
    }
	if (email.value == "") {
		alert("이메일을 입력하세요");
		email.focus();
		return false;
	}
	var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
    if (!exptext.test(email.value)) {
        alert("올바른 형식의 이메일이 아닙니다");
        email.focus();
        return false;
    };
    if (birth.value == "") {
		alert("생년월일을 입력해주세요");
		return false;
	}
	if (isKeyCheck == 0) {
		alert("이메일 인증을 완료해주세요");
		return false;
	}

    document.register.submit();
}


// Login validate
function loginCHK() {
    var id = document.getElementById("id");
    var pw = document.getElementById("password");


    if (id.value == "") {
        alert("아이디를 입력하세요");
        id.focus();
        return false;
    }
    if (pw.value == "") {
        alert("비밀번호를 입력하세요");
        pw.focus();
        return false;
    }
    
    document.login.submit();
}


// Profile Update validate
function editProfile() {
    var name = document.getElementById("name");
    var pwPre = document.getElementById("passwordPre");
    var pw = document.getElementById("password");


    if (name.value == "") {
        alert("이름을 입력하세요");
        name.focus();
        return false;
    }
    if (pwPre.value == "") {
        alert("이전 비밀번호를 입력하세요");
        pwPre.focus();
        return false;
    }
    if (pw.value == "") {
        alert("이전 비밀번호를 입력하세요");
        pw.focus();
        return false;
    }
    var pwform = /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,15}$/;
    if (!pwform.test(pw.value)) {
        alert("영문자+숫자 조합으로 6~15자리 사용해야 합니다");
        pw.focus();
        return false;
    }
 
    
    document.updateProfile.submit();
}



// 게시글 업로드 확인
function addBoard() {
	var title = document.getElementById("title");
	var region = document.getElementById("region");
	var content = document.getElementById("content");
	
	if(title.value == "") {
		alert("제목을 입력해 주세요");
		title.focus();
		return false;
	}
	
	if(region.value == "") {
		alert("지역을 입력해 주세요");
		region.focus();
		return false;
	}
	
	if(content.value == "") {
		alert("내용을 입력해 주세요");
		content.focus();
		return false;
	}
	

	document.addBoardIt.submit();
}


// 게시글 수정 확인
function editBoard() {
	var title = document.getElementById("title");
	var region = document.getElementById("region");
	var content = document.getElementById("content");
	
	if(title.value == "") {
		alert("제목을 입력해 주세요");
		title.focus();
		return false;
	}
	
	if(region.value == "") {
		alert("지역을 입력해 주세요");
		region.focus();
		return false;
	}
	
	if(content.value == "") {
		alert("내용을 입력해 주세요");
		content.focus();
		return false;
	}

	document.editBoardIt.submit();
}


function commentAction() {
	var comment = document.getElementById("comment");
	
	if(comment.value == "") {
		alert("내용을 입력해주세요");
		comment.focus();
		return false;
	}
	
	document.commentIt.submit();
}


