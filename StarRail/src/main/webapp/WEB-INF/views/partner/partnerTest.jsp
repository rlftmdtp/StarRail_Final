<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
/* 헤더에 색상주기 */
.header_color{
   /*  background-color: #F07575; */ /* 그레디언트가 지원되지 않는 경우의 색상(fallback color) */
/*   background-image: -webkit-linear-gradient(top, hsl(0, 30%, 80%), #FFFF8F);
  background-image:    -moz-linear-gradient(top, hsl(0, 30%, 80%), #FFFF8F); /* Fx 3.6 부터 Fx 15 까지 처리 
  background-image:     -ms-linear-gradient(top, hsl(0, 30%, 80%), #FFFF8F); /* IE 10 Platform Previews, Consumer Preview 
  background-image:      -o-linear-gradient(top, hsl(0, 30%, 80%), #FFFF8F); /* Opera 11.1 부터 12.0 까지 처리 
  background-image:         linear-gradient(to bottom, hsl(0, 30%, 80%), #FFFF8F); /* 표준구문, 표준구문은 반드시 마지막에 삽입해야한다. */
}
</style>

<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Star Raile Partner</title>

   <link rel="stylesheet" type="text/css"   href="/starrail/resources/css/partner/partner.css?ver=1">

   <!-- Bootstrap core CSS -->
    <link href="/starrail/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template -->
    <link rel="stylesheet" href="/starrail/resources/vendor/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/starrail/resources/vendor/simple-line-icons/css/simple-line-icons.css">
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Catamaran:100,200,300,400,500,600,700,800,900" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Muli" rel="stylesheet">
    <!-- Plugin CSS -->
    <link rel="stylesheet" href="/starrail/resources/device-mockups/device-mockups.min.css">
    <link href="/starrail/resources/css/partner/small-business.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    
    
    
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
<link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/css/bootstrap-datepicker3.standalone.css'>
<link rel="stylesheet" href="/starrail/resources/css/main/style.css">




   <script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
   <script src="/starrail/resources/bootstrap/js/bootstrap.js"></script>
   <script src="/starrail/resources/js/partner/partner.js"></script>
   <script src="/starrail/resources/js/partner/partnerSearch.js"></script>
   <!-- Bootstrap core JavaScript -->
   <script src="/starrail/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
   <!-- Plugin JavaScript -->
   <script src="/starrail/resources/vendor/jquery-easing/jquery.easing.min.js"></script>
   <!-- Custom scripts for this template -->
   <!-- <script src="/starrail/resources/js/main/new-age.min.js"></script> -->

</head>

<body>


<header  class="header_color">
<%@include file="../main/nav_page.jsp"%><div style="margin-top: 30px;"></div>   
</header>

  <!-- Page Content -->
    <div class="container">

      <!-- Heading Row -->
      <div class="row my-4">
        <div class="col-lg-7">
                   <!-- 썸네일 시작 -->
      <div class='row'>
      <!-- 코스이름 띄우기 시작 -->
         <div >
            <h1 style="font-weight: bold;">My Course</h1>
            &nbsp;&nbsp;
            <h4 id="courseName">코스를 선택해 주세요</h4>
         </div>
         <!-- 코스 이름 띄우기 끝 -->
         <div>
            <div class="carousel slide media-carousel" id="media">

               <!-- 대표 코스 이미지 시작 -->
               <div class="col-md-12" id="largeimg">
                  <a class="thumbnail" href="#"><img alt=""
                     src="/starrail/resources/images2/partner/map.PNG"
                     style="width: 300px; height: 300px;"></a>
               </div>
               <!-- 대표 코스 이미지 끝 -->


               <!-- 썸네일 코스 이미지 시작 -->
               <div class="carousel-inner">

                  <div class="item  active">
                     <div class="row">

                        <c:forEach items="${courseVO}" var="CourseVO">
                           <div class="col-md-4" id="thumnail">
                              <a class="thumbnail"
                                 href="/starrail/resources/images2/partner/test_map.PNG"
                                 data-name="${CourseVO.c_name}" data="${CourseVO.c_id}"> <img
                                 alt="" src="/starrail/resources/images2/partner/test_map.PNG">
                                 <span>${CourseVO.c_name}</span>
                              </a>
                           </div>
                        </c:forEach>


                        <!-- <div class="col-md-4">
                   <a class="thumbnail" href="#"><img alt="" src="http://placehold.it/150x150"></a>
                 </div>
                 
                 <div class="col-md-4">
                   <a class="thumbnail" href="#"><img alt="" src="http://placehold.it/150x150"></a>
                 </div>     -->

                     </div>
                  </div>
               </div>
               <!-- 썸네일 코스 이미지 끝 -->

               <!-- prev / next 버튼 -->
               <a data-slide="prev" href="#media" class="left carousel-control"
                  style="margin-top: 360px;">‹</a> <a data-slide="next"
                  href="#media" class="right carousel-control"
                  style="margin-top: 360px;">›</a>
            </div>
         </div>
      </div>
       </div>        
        <!-- /.col-lg-8 -->
        
        
        
        
        <div class="col-lg-5" style="margin-top: 50px;">
          <h1>Course schedule</h1>
          <p></p>
          
           <!-- 코스선택 시 나오는 일정 시작 -->
           <div class="row">
        <div>
          <div class="card h-100">
            <div class="card-body" style="width: 473px;">
              <h2 class="card-title">Click One</h2>
                             <div id="schedule_deatil">
                  <form action="">
                     <div id="courseDetailInfo">
                        <input type="hidden" name="cd_id1" id="cd_id1">
                        <input type="hidden" name="cd_id2" id="cd_id2">
                        <input type="hidden" name="cd_id3" id="cd_id3">
                        <input type="hidden" name="cd_id4" id="cd_id4">
                        <input type="hidden" name="cd_id5" id="cd_id5">
                        <input type="hidden" name="cd_id6" id="cd_id6">
                        <input type="hidden" name="cd_id7" id="cd_id7">
                     </div>
                     
                     <br>
                     <table class="table" id="scheduleTable" width="200"
                        cellpadding="5" cellspacing="2" align="center"
                        style="table-layout: fixed; word-break: break-all;">
                        <thead>
                           <tr>
                              <th>일정</th>
                              <th>출발역</th>
                              <th>도착역</th>
                              <th>날짜</th>
                           </tr>
                        </thead>
                        <tbody>
                        </tbody>                        
                     </table>
                  </form>
               </div>
             </div>
            <div class="card-footer">
              <div id="scedule-button"></div>
            </div>
          </div>
        </div>
        </div>
           
           

        </div>
        <!-- /.col-md-4 -->
      </div>
      <!-- /.row -->

      <!-- Call to Action Well -->
      <div class="card text-white bg-secondary my-4 text-center">
        <div class="card-body">
          <p class="text-white m-0">This is where the partner list is printed!</p>
        </div>
      </div>

      <!-- Content Row -->
      <!-- 일정 선택 시 동반자 리스트 출력하는 곳 시작 -->
      <div id="partner-Info">
      
         <div class="container">
            <div class="row" style="margin-top: 30px;">
               <h1 style="font-weight: bold;">
                 Partner List
                 </h1>
            </div>
             <div class="row" id="partner-Profile" style="margin-top: 20px;">
                  
             </div>
         </div>
         
         
         <!-- <img class="src-image"  src="/starrail/resources/images2/partner/F.png"/> -->
      </div>
      <!-- 일정 선택 시 동반자 리스트 출력하는 곳 끝 -->
      <!-- /.row -->

    </div>
    <!-- /.container -->


    <!-- Bootstrap core JavaScript -->
    <script src="/starrail/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>


      
        <div style="margin-top: 30px;">
           <%@include file="../main/footer.jsp"%>   
      </div>   

  
<main>
  <h1><span class="fontawesome-plane"></span> Moon Flights</h1>
  <h2>Select your flight dates below</h2>
  <div class="input-daterange input-group" id="flight-datepicker">
    <div class="form-item">
      <label>Depart on</label><span class="fontawesome-calendar"></span>
      <input class="input-sm form-control" type="text" id="start-date" name="start" placeholder="Select depart date" data-date-format="DD, MM d"/><span class="date-text date-depart"></span>
    </div>
    <div class="form-item">
      <label>Return on</label><span class="fontawesome-calendar"></span>
      <input class="input-sm form-control" type="text" id="end-date" name="end" placeholder="Select return date" data-date-format="DD, MM d"/><span class="date-text date-return"></span>
    </div>
  </div>
</main>
  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/js/bootstrap-datepicker.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery-dateFormat/1.0/jquery.dateFormat.js'></script>

    <script src="/starrail/resources/js/main/index.js"></script>

  </body>
</html>