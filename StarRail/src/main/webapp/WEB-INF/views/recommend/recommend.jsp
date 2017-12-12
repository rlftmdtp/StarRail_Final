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
		    <a class="btn1 btn-rounded1 btn-round-tosquare1 btn-lg1  btn-bordered-warning1" 
		    		data="${Hash_SearchVO.hs_search}">
		    <span style="font-weight: bold;"></span>${Hash_SearchVO.hs_search}</a>	&nbsp;&nbsp;	
				  
<%-- 			<c:if test="${status.count%4 eq 0}">
	     		<br><br><br>
	     	</c:if> --%>
	     </c:forEach>
     </div>
      <!-- end 로그인 한 사용자마다 맞춤 태그 생성 -->
      
      
     <!-- start Team Members Row -->
      <div class="row" style="margin-top: 100px;">
        <div class="col-lg-12">
          <h2 class="my-4">Recommended Review
          	<small>추천된 후기</small>
          </h2>
        </div>

	    <!-- start 추천태그 클릭 시 후기 리스트  -->
        <div id="reviewList" class="col-md-12"></div>
	   <!-- end 추천태그 클릭 시 후기 리스트  -->
                
      </div>	
      <!-- end Team Members Row -->

    </div>
    <!-- /.container -->


      
    <div style="margin-top: 30px;">
    	<%@include file="../main/footer.jsp"%>   
    </div>   



<!-- JS File -->
   <script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
   <script src="/starrail/resources/js/recommend/recommend.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
	<script id="template" type="text/x-handlebars-template">
		<div class="col-md-3">

            <div class="card hovercard">
                <div class="cardheader">

                </div>
                <div class="avatar">
                    <img alt="" src="http://lorempixel.com/100/100/people/9/">
                </div>
                <div class="info">
                    <div class="title">
                        <a target="_blank" href="http://scripteden.com/">{{r_title}}</a>
                    </div>
                    <div class="desc"></div>
                    <div class="desc">추천 수 : {{r_recomm}}</div>
                    <div class="desc">{{m_id}}</div>
                </div>
                <div class="bottom">
                    <a class="btn btn-primary btn-twitter btn-sm" href="https://twitter.com/webmaniac">
                        <i class="fa fa-twitter"></i>
                    </a>
                    <a class="btn btn-danger btn-sm" rel="publisher"
                       href="https://plus.google.com/+ahmshahnuralam">
                        <i class="fa fa-google-plus"></i>
                    </a>
                    <a class="btn btn-primary btn-sm" rel="publisher"
                       href="https://plus.google.com/shahnuralam">
                        <i class="fa fa-facebook"></i>
                    </a>
                    <a class="btn btn-warning btn-sm" rel="publisher" href="https://plus.google.com/shahnuralam">
                        <i class="fa fa-behance"></i>
                    </a>
                </div>
            </div>

        </div>
	</script>

  </body>
</html>