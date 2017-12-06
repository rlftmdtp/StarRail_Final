<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 반응형 웹 하기 위함 -->
<meta name="viewport" content="width=divice-width" , initial-scale="1">
<title>coding booster</title>
<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
<!-- bootstrap에서 받아온 js파일+css파일 사용 하기 위함 --><!-- 
<script src="/starrail/resources/bootstrap/js/bootstrap.js"></script> -->
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/starrail/resources/js/partner/partner.js"></script><!-- 
<script src="/starrail/resources/js/partner/partnerSearch.js"></script> -->

    <!-- Bootstrap core CSS -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"   href="/starrail/resources/css/partner/partner.css?ver=1">
    <!-- Custom styles for this template -->
<!-- <link href="/starrail/resources/css/partner/small-business.css" rel="stylesheet"> -->
<link rel="stylesheet" type="text/css"   href="/starrail/resources/bootstrap/css/bootstrap.css">

</head>

<body>

<!--  header영역 시작 -->
   <div class="container">

      <nav class="navbar navbar-default1">
         <div class="container-fluid">
            <div class="navbar-header1">
               <button type="button" class="navbar-toggle collapsed"
                           data-target="#bs-example-navbar-collapse-1" aria-expanded="flase"
                              data-toggle="collapse">
                  <span class="sr-only"></span> 
               <!-- 반응형 웹 ==> 페이지 크기를 줄였을 때 메뉴 세줄로 나타나게 하는 코드 -->
                  <span class="icon-bar"></span> 
                  <span class="icon-bar"></span> 
                  <span class="icon-bar"></span>
               </button>
               <a href="#" class="navbar-brand">Star Rail</a>
            </div>
         
            <div class="collapse navbar-collapse"   id="bs-example-navbar-collapse-1">
               <!-- 메인 메뉴 -->
               <ul class="nav navbar-nav">
                  <li class="active">
                     <a href="#">소개<span class="sr-only"></span></a>
                  </li>

                  <li><a href="/starrail/map/search">지도 추천 페이지</a></li>
                  <li><a href="/starrail/partner/partner">동행</a></li>

                  <li><a href="#">추천</a></li>
                  <li><a href="#">플래너 공유</a></li>
                  <li><a href="#">여행 후기</a></li>
                  <li><a href="#">여행 경비</a></li>
                  
                  
                  <!--  메뉴 눌렀을때 밑에 리스트 달아야 하면 이 코드 사용하기
                  <li class="dropdown">
                     <a href="#" class="dropdown-toggle"   data-toggle="dropdown" role="button" aria-haspopup="true"    aria-expanded="flase">
                        강의<span class="caret"></span>
                     </a>
                     <ul class="dropdown-menu">
                        <li><a href="#">C언어</a></li>
                        <li><a href="#">A언어</a></li>
                        <li><a href="#">B언어</a></li>
                        <li><a href="#">D언어</a></li>
                     </ul>
                  </li> -->
                  
                  
               </ul>
            
               <!-- 검색창 필요하면 이 코드 사용
               <form class="navbar-form navbar-right">
                  <div class="form-group">
                     <input type="text" class="form-control" placeholder="내용을 입력하세요.">
                  </div>
                  <button type="submit" class="btn btn-default">검색</button>
               </form>-->
            
               <!-- 접속메뉴 -->
               <ul class="nav navbar-nav navbar-right">
                  <li class="dropdown">
                     <a id="a" href="#"   class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="flase">접속하기<span   class="caret"></span></a>
                     <ul class="dropdown-menu">
                        <li><a href="/starrail/main/login">로그인</a></li>
                        <li><a href="#">회원가입</a></li>
                     </ul>
                  </li>
                  <li><a href="#">예약하기</a></li>
                  <li><a href="#">마이 홈</a></li>
               </ul>            
            </div>
         </div>
      </nav>
   </div>
<!--  header영역 끝 -->




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




<!--  footer영역 시작 -->
   <footer style="background-color: #000000;  color: #ffffff;" >
   <div class="container">
      <br>

      <div class="row">
         <div class="col-sm-2" style="text-align: center;">
            <h5>Copyright &copy; 2017</h5>
            <h5>배재현(Heejung Jang)</h5>
            <h5>조윤주(Heejung Jang)</h5>
            <h5>길승세(Heejung Jang)</h5>
            <h5>장&nbsp;&nbsp;&nbsp;솔(Heejung Jang)</h5>
            <h5>장희정(Heejung Jang)</h5>
         </div>
         <div class="col-sm-4">
            <h4>대표자 소개</h4>
            <p>저는 코딩 부스터의 대표 장희정입니다. 백석대학교에서 공부를 하고 있으며 웹 개발에 관심이 많습니다.</p>
         </div>
         <div class="col-sm-2">
            <h4 style="text-align: center;">내비게이션</h4>
            <div class="list-group">
               <a href="#" class="list-group-item">소개</a> <a href="#"
                  class="list-group-item">강사진</a> <a href="#"
                  class="list-group-item">강의</a>
            </div>
         </div>
         <div class="col-sm-2">
            <h4 style="text-align: center;">SNS</h4>
            <div class="list-group">
               <a href="#" class="list-group-item">facebook</a> <a href="#"
                  class="list-group-item">Instagram</a> <a href="#"
                  class="list-group-item">Twitter</a>
            </div>
         </div>
         <div class="col-sm-2">
            <h4 style="text-align: center;">
               <span class="glyphicon glyphicon-ok"></span>&nbsp;by 코스타167기
            </h4>
         </div>
      </div>
   </div>
   </footer>
<!--  footer영역 끝 -->



</body>
</html>