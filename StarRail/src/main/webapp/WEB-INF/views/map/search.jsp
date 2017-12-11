<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" , initial-scale=1">
<title>Map Search</title>

<!-- Link Swiper's CSS -->
<link rel="stylesheet" href="/starrail/resources/css/map/swiper.min.css">
<!-- 개인 Css File -->
<link rel="stylesheet"
	href="/starrail/resources/css/map/mapsearch.css?ver=1">
<!--  블로그 리뷰 폰트 css -->
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.1.0/css/font-awesome.min.css" />
	<!-- datePicker CSS File -->
<link rel="stylesheet"
   href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
<link rel='stylesheet prefetch'
   href='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/css/bootstrap-datepicker3.standalone.css'>
<link rel="stylesheet" href="/starrail/resources/css/main/style.css">
</head>
<body>
	<%@include file="../main/nav_page.jsp"%>
	<div style="margin-top: 60px;"></div>

	<div class="container-fluid"
		style="padding-right: 10%; padding-left: 10%;">
		<div class="row rails">star 레일</div>
		<div class="row course">
			<h2 class="fontapply">여행 코스</h2>
			<div class="row" id="courseButtons">
				<c:forEach items="${courseList }" var="courseVO">
					<button type="submit" value="${courseVO.c_id }"
						class="btn btn-default courseButton">${courseVO.c_name }</button>
				</c:forEach>
			</div>
		</div>

		<div class="row station">
			<h2 class="fontapply">역 선택</h2>
			<div class="row" id="stationButtons"></div>
		</div>

		<div class="row festival">
			<h2 class="fontapply">이달의 축제</h2>
			<div class="row">
				<div class="input-daterange input-group" id="flight-datepicker"
                              style="margin-left: 30%; margin-top: 0px;">
                              <span class="input-group-addon" id="basic-addon1"
                                 style="width: 25%;"> <span
                                 class="glyphicon glyphicon-calendar" aria-hidden="true"></span></span>
                              <span class="fontawesome-calendar"></span> <input
                                 class="input-sm form-control" type="text"
                                 id="datepicker_expense" name="e_sdate" placeholder="출발일"
                                 data-date-format="DD, MM d" style="size: 5px"/> <span
                                 class="date-text date-depart"></span>
             	</div>
             </div>
             
             	<div class="row festivaldetail" style="margin-left: 5%">
             		<div class="col-md-3 festivalimg" id="festivalimg1" data-contentid=""><img src="http://placehold.it/280x280" class="img-responsive center-block"></div>
             		<div class="col-md-3 festivalimg" id="festivalimg2" data-contentid=""><img src="http://placehold.it/280x280" class="img-responsive center-block"></div>
             		<div class="col-md-3 festivalimg" id="festivalimg3" data-contentid=""><img src="http://placehold.it/280x280" class="img-responsive center-block"></div>
             		<div class="col-md-3 festivalimg" id="festivalimg4" data-contentid=""><img src="http://placehold.it/280x280" class="img-responsive center-block"></div>
             		<div class='pagination' id="pagefestival" style="margin: 3% auto;"></div>
             	</div>
			 	
			 	<!-- 모달 -->
			<div class="modal fade" id="myModal">
				<div class="modal-dialog modal-lg" style="margin-top: 30%;"> <!-- 모달의 크기조절 -->
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title" id="myModalLabel"></h4>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
  							<span aria-hidden="true">×</span></button>
						</div>
						<div class="modal-body">
							
						</div>
						<div class="modal-footer"></div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row sort">
			<div class="row" id="sortButtons">
				<button class="btn btn-default sortButton" data-sort="1"
					type="submit" style="padding-left: 5%; padding-right: 5%;">
					<img src="/starrail/resources/images/map/food128.png">
				</button>
				<button class="btn btn-default sortButton" data-sort="2"
					type="submit" style="padding-left: 5%; padding-right: 5%;">
					<img src="/starrail/resources/images/map/stay128.png">
				</button>
				<button class="btn btn-default sortButton" data-sort="3"
					type="submit" style="padding-left: 5%; padding-right: 5%;">
					<img src="/starrail/resources/images/map/tour128.png">
				</button>
				<button class="btn btn-default sortButton" data-sort="4"
					type="submit" style="padding-left: 5%; padding-right: 5%;">
					<img src="/starrail/resources/images/map/map128.png">
				</button>
			</div>
		</div>
		<div id="map" class="row map">
			<div class="search">
				<input type="text" id="mapScope" placeholder="300m"> <input
					type="button" id="submit" value="반경검색">
			</div>
		</div>

		<div class="row info">

			<div class="col-md-6 info1">
				<div class="row">
					<div role="tabpanel" id="charttable">
						<!-- Nav tabs -->
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation" class="active"><a href="#food"
								aria-controls="home" role="tab" data-toggle="tab" id="foodtab">맛 집</a></li>
							<li role="presentation"><a href="#stay"
								aria-controls="profile" role="tab" data-toggle="tab" id="staytab">숙 소</a></li>
							<li role="presentation"><a href="#tour"
								aria-controls="messages" role="tab" data-toggle="tab" id="tourtab">관광지</a></li>
						</ul>
						
						<!-- Tab panes -->
						<div class="tab-content info-content">
							<div role="tabpanel" class="tab-pane active" id="food">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>순위</th>
											<th>업소명</th>
										</tr>
									</thead>
									<tbody id="foodTable">
										<!-- 데이터들이 들어감 -->
									</tbody>
								</table>
							</div>
							<div role="tabpanel" class="tab-pane" id="stay">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>순위</th>
											<th>숙소명</th>
										</tr>
									</thead>
									<tbody id="stayTable">
										<!-- 데이터들이 들어감 -->
									</tbody>
								</table>
							</div>
							<div role="tabpanel" class="tab-pane" id="tour">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>순위</th>
											<th>관광지명</th>
										</tr>
									</thead>
									<tbody id="tourTable">
										<!-- 데이터들이 들어감 -->
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class='pagination' id="pagefood" style="margin: 3% auto;"></div>
				<div class='pagination' id="pagestay" style="margin: 3% auto;"></div>
				<div class='pagination' id="pagetour" style="margin: 3% auto;"></div>
			</div>

			<div class="col-md-6 info2">
				<!-- 음식점 상세정보 -->
				<!-- Swiper -->
				<div class="swiper-container">
					<div class="swiper-wrapper">
						<div class="swiper-slide" id="foodimg1"></div>
						<div class="swiper-slide" id="foodimg2"></div>
						<div class="swiper-slide" id="foodimg3"></div>
					</div>
					<!-- Add Arrows -->
					<div class="swiper-button-next"></div>
					<div class="swiper-button-prev"></div>
				</div>
				<div class="row info2detail">
				<h3>가게 소개</h3>
				</div>
				<div id="saveButtons"></div>
			</div>
		</div>
	</div>

	<div style="margin-top: 30px;">
		<%@include file="../main/footer.jsp"%>
	</div>

	<!-- JS File -->
	<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
	<!-- 네이버 지도 API -->
	<script type="text/javascript"
		src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=FeSf9NchU5GMk0kap7Kn&submodules=geocoder"></script>
	<script type="text/javascript"
		src="https://apis.skplanetx.com/tmap/js?version=1&format=javascript&appKey=6b62e1fb-37a5-316d-a82a-a11b47fe9f56"></script>
	<!-- Swiper JS -->
	<script src="/starrail/resources/js/map/swiper.min.js"></script>
	<!-- datepicker JS File : 무조건 코드 맨마지막에 넣어야 실행됨 -->
   <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js'></script>
   <script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/js/bootstrap-datepicker.min.js'></script>
   <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery-dateFormat/1.0/jquery.dateFormat.js'></script>
   <!--  부트스트랩 자바스크립트 -->
   <script src="/starrail/resources/bootstrap/js/bootstrap.js" type="text/javascript"></script>
	<!-- 개인 js -->
	<script src="/starrail/resources/js/map/mapsearch.js"
		type="text/javascript"></script>
</body>
</html>