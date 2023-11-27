<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-US" dir="ltr">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">


    <!-- ===============================================-->
    <!--    Document Title-->
    <!-- ===============================================-->
    <title>NextTrip | Planning Your Travel</title>


    <!-- ===============================================-->
    <!--    Favicons-->
    <!-- ===============================================-->
    <link rel="apple-touch-icon" sizes="180x180" href="assets/img/favicons/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="assets/img/favicons/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="assets/img/favicons/favicon-16x16.png">
    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicons/favicon.ico">
    <link rel="manifest" href="assets/img/favicons/manifest.json">
    <meta name="msapplication-TileImage" content="assets/img/favicons/mstile-150x150.png">
    <meta name="theme-color" content="#ffffff">


    <!-- ===============================================-->
    <!--    Stylesheets-->
    <!-- ===============================================-->
    <link href="assets/css/theme.css" rel="stylesheet" />

  </head>


  <body>
    <%
    	// 에러코드 반환 시 보여줄 alert 처리
    	// 각 Service 에서 response로 반환해도 가능하지만 쉬운 에러 메세지 관리를 위해 한곳에 정리
    	if(request.getParameter("msg") != null) {
    		int msg = Integer.parseInt(request.getParameter("msg"));
    		switch(msg) {
    			// 회원가입 에러 처리
    			case 101:
    				out.println("<script>alert('회원가입 도중 에러가 발생했습니다!');history.back();</script>");
    				out.close();
    				break;
    			// 로그인 에러 처리
    			case 201:
    				out.println("<script>alert('패스워드가 일치하지 않습니다!');history.back();</script>");
    				out.close();
    				break;
    			case 202:
    				out.println("<script>alert('존재하지 않는 아이디 입니다!');history.back();</script>");
    				out.close();
    				break;
    			case 301:
    				out.println("<script>alert('프로필 수정에 실패하였습니다!');history.back();</script>");
    				out.close();
    				break;
    			case 401:
    				out.println("<script>alert('로그인이 필요한 서비스 입니다!');location.href='sign-in.do';</script>");
    				out.close();
    				break;
    			case 402:
    				out.println("<script>alert('해당 Plan에 접근권한이 없습니다!');history.back();</script>");
    				out.close();
    				break;
				// 기본 에러 전부 여기로
    			default:
    				out.println("<script>alert('알 수 없는 에러입니다. 관리자에게 문의하세요!');history.back();</script>");
    				out.close();
    				break;
    		}
    	}
    %>

    <!-- ===============================================-->
    <!--    Main Content-->
    <!-- ===============================================-->
    <main class="main" id="top">
      <nav class="navbar navbar-expand-lg navbar-light fixed-top py-5 d-block" data-navbar-on-scroll="data-navbar-on-scroll">
        <div class="container"><a class="navbar-brand" href="index.do"><img src="assets/img/logo.png" height="50" alt="logo" /></a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"> </span></button>
          <div class="collapse navbar-collapse border-top border-lg-0 mt-4 mt-lg-0" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto pt-2 pt-lg-0 font-base align-items-lg-center align-items-start">
              <li class="nav-item px-3 px-xl-4"><a class="nav-link fw-medium" aria-current="page" href="index.do#service">소개</a></li>
              <li class="nav-item px-3 px-xl-4"><a class="nav-link fw-medium" aria-current="page" href="index.do#destination">여행지</a></li>
              <li class="nav-item px-3 px-xl-4"><a class="nav-link fw-medium" aria-current="page" href="tripReview.do">여행후기</a></li>
              <li class="nav-item px-3 px-xl-4"><a class="nav-link fw-medium" aria-current="page" href="myPlan.do">내 여행</a></li>
              <%
              	if((String)session.getAttribute("sID") == null) {
              %>
              <li class="nav-item px-3 px-xl-4"><a class="btn btn-outline-dark order-1 order-lg-0 fw-medium" href="sign-in.do">로그인</a></li>
              <%
              	} else {
              		String sID = (String)session.getAttribute("sID");
              		String sNAME = (String)session.getAttribute("sNAME");
              		String sIMG = (String)session.getAttribute("sIMG");
              %>
              
              <li class="nav-item dropdown px-3 px-lg-0">
              <a class="d-inline-block ps-0 py-2 pe-3 text-decoration-none dropdown-toggle fw-medium" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              	<%=sNAME %>
              	<img src="assets/img/profileIMG/<%=sIMG %>" width="30px;">
              </a>
                <ul class="dropdown-menu dropdown-menu-end border-0 shadow-lg" style="border-radius:0.3rem;" aria-labelledby="navbarDropdown">
                  <li><a class="dropdown-item" href="myProfile.do">내 정보</a></li>
                  <li><a class="dropdown-item" href="logout.do">로그아웃</a></li>
                </ul>
              </li>

              <%
              	}
              %>
            </ul>
          </div>
        </div>
      </nav>
      