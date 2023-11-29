<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
</head>
<body>
	<%
   		// 에러코드 반환 시 보여줄 alert 처리
    	// 각 Service 에서 response로 반환해도 가능하지만 쉬운 에러 메세지 관리를 위해 한곳에 정리
    	// how to use : controller에서 "redirect:error.do?msg=202"
    	if(request.getParameter("msg") != null) {
    		int msg = Integer.parseInt(request.getParameter("msg"));
    		switch(msg) {
    			// 회원가입 에러 처리
    			case 101:
    				out.println("<script>alert('101: 회원가입 도중 에러가 발생했습니다!');history.back();</script>");
    				out.close();
    				break;
    			// 로그인 에러 처리
    			case 201:
    				out.println("<script>alert('201: 패스워드가 일치하지 않습니다!');history.back();</script>");
    				out.close();
    				break;
    			case 202:
    				out.println("<script>alert('202: 존재하지 않는 아이디 입니다!');history.back();</script>");
    				out.close();
    				break;
    			case 301:
    				out.println("<script>alert('301: 프로필 수정에 실패하였습니다!');history.back();</script>");
    				out.close();
    				break;
    			case 401:
    				out.println("<script>alert('401: 로그인이 필요한 서비스 입니다!');location.href='sign-in.do';</script>");
    				out.close();
    				break;
    			case 402:
    				out.println("<script>alert('402: 해당 Plan에 접근권한이 없습니다!');history.back();</script>");
    				out.close();
    				break;
				// 기본 에러 전부 여기로
    			default:
    				out.println("<script>alert('999: 알 수 없는 에러입니다. 관리자에게 문의하세요!');history.back();</script>");
    				out.close();
    				break;
    		}
    	}
    %>
</body>
</html>