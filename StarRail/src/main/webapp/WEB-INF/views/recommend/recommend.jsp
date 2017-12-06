<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Star Raile Partner</title>  

		
 <!-- Css File -->   
     <link href="/starrail/resources/css/recommend/recommend.css" rel="stylesheet">
    <link href="/starrail/resources/css/recommend/round-about.css" rel="stylesheet">

<!-- datePicker CSS File -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
	<link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/css/bootstrap-datepicker3.standalone.css'>
	<link rel="stylesheet" href="/starrail/resources/css/main/style.css">
</head>
<body>

	<%@include file="../main/nav_page.jsp"%>
	<div style="margin-top: 80px;"></div>   






<!-- Page Content -->
    <div class="container">

      <!-- Introduction Row -->
      <h1 class="my-4" style="margin-top: 50px; margin-left: 20px;">
      	<span style="font-size: 30px; color: #F0AD4E; font-weight: bold;">${m_name}&nbsp;</span>님에게 추천해드리는 오늘의 태그
        <small>오늘의 추천 태그</small>
      </h1>
      
     <!-- start 로그인 한 사용자마다 맞춤 태그 생성 -->
     <div style="margin: 30px 40px;">
	     <c:forEach items="${hashSearchVO}" var="Hash_SearchVO" varStatus="status">
		    <a class="btn1 btn-rounded1 btn-round-tosquare1 btn-lg1  btn-bordered-warning1">
		    <span style="font-weight: bold;"></span>${Hash_SearchVO.hs_search}</a>	&nbsp;&nbsp;	
				  
<%-- 			<c:if test="${status.count%4 eq 0}">
	     		<br><br><br>
	     	</c:if> --%>
	     </c:forEach>
     </div>
      <!-- end 로그인 한 사용자마다 맞춤 태그 생성 -->
      
      
      <!-- Team Members Row -->
      <div class="row" style="margin-top: 100px;">
        <div class="col-lg-12">
          <h2 class="my-4">Recommended Review
          	<small>추천된 후기</small>
          </h2>
        </div>
        <div class="col-lg-4 col-sm-6 text-center mb-4">
          <img class="rounded-circle img-fluid d-block mx-auto" src="http://placehold.it/200x200" alt="">
          <h3>John Smith
            <small>Job Title</small>
          </h3>
          <p>What does this team member to? Keep it short! This is also a great spot for social links!</p>
        </div>
        <div class="col-lg-4 col-sm-6 text-center mb-4">
          <img class="rounded-circle img-fluid d-block mx-auto" src="http://placehold.it/200x200" alt="">
          <h3>John Smith
            <small>Job Title</small>
          </h3>
          <p>What does this team member to? Keep it short! This is also a great spot for social links!</p>
        </div>
        <div class="col-lg-4 col-sm-6 text-center mb-4">
          <img class="rounded-circle img-fluid d-block mx-auto" src="http://placehold.it/200x200" alt="">
          <h3>John Smith
            <small>Job Title</small>
          </h3>
          <p>What does this team member to? Keep it short! This is also a great spot for social links!</p>
        </div>
        <div class="col-lg-4 col-sm-6 text-center mb-4">
          <img class="rounded-circle img-fluid d-block mx-auto" src="http://placehold.it/200x200" alt="">
          <h3>John Smith
            <small>Job Title</small>
          </h3>
          <p>What does this team member to? Keep it short! This is also a great spot for social links!</p>
        </div>
        <div class="col-lg-4 col-sm-6 text-center mb-4">
          <img class="rounded-circle img-fluid d-block mx-auto" src="http://placehold.it/200x200" alt="">
          <h3>John Smith
            <small>Job Title</small>
          </h3>
          <p>What does this team member to? Keep it short! This is also a great spot for social links!</p>
        </div>
        <div class="col-lg-4 col-sm-6 text-center mb-4">
          <img class="rounded-circle img-fluid d-block mx-auto" src="http://placehold.it/200x200" alt="">
          <h3>John Smith
            <small>Job Title</small>
          </h3>
          <p>What does this team member to? Keep it short! This is also a great spot for social links!</p>
        </div>
      </div>

    </div>
    <!-- /.container -->


      
    <div style="margin-top: 30px;">
    	<%@include file="../main/footer.jsp"%>   
    </div>   


<!-- datepicker JS File : 무조건 코드 맨마지막에 넣어야 실행됨 -->   
     <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/js/bootstrap-datepicker.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery-dateFormat/1.0/jquery.dateFormat.js'></script>
    <script src="/starrail/resources/js/main/index.js"></script>  
<!-- JS File -->
   <script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
   <script src="/starrail/resources/js/partner/partner.js"></script>
   <script src="/starrail/resources/js/partner/partnerSearch.js"></script>

  </body>
</html>