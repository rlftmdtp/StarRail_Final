<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" , initial-scale=1">
<title>Map Search</title>

<!-- 개인 Css File -->
<link rel="stylesheet"
	href="/starrail/resources/css/map/mapsearch.css?ver=1">
<!--  블로그 리뷰 폰트 css -->
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.1.0/css/font-awesome.min.css" />
</head>
<body>
	<%@include file="../main/nav_page.jsp"%>
	<div style="margin-top: 60px;"></div>

	<div class="container-fluid" style="padding-right: 10%; padding-left: 10%;">
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
								aria-controls="home" role="tab" data-toggle="tab">맛 집</a></li>
							<li role="presentation"><a href="#stay"
								aria-controls="profile" role="tab" data-toggle="tab">숙 소</a></li>
							<li role="presentation"><a href="#tour"
								aria-controls="messages" role="tab" data-toggle="tab">관광지</a></li>
						</ul>

						<!-- Tab panes -->
						<div class="tab-content info-content">
							<div role="tabpanel" class="tab-pane active" id="food">
								<table class="table table-hover">
									<caption>맛 집 순 위(블로그 리뷰 기준)</caption>
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
									<caption>숙 박 순 위</caption>
									<thead>
										<tr>
											<th>순위</th>
											<th>업소명</th>
										</tr>
									</thead>
									<tbody id="stayTable">
										<!-- 데이터들이 들어감 -->
									</tbody>
								</table>
							</div>
							<div role="tabpanel" class="tab-pane" id="tour">
								<table class="table table-hover">
									<caption>관 광 지 순 위</caption>
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
				<div class='pagination'></div>
			</div>

			<div class="col-md-6 info2">
				<!-- 블로그 리뷰가 들어감 -->
	
			</div>
									
		</div>

		<div class="row graph">
			<div class="col-md-6 graphChart1">
				<div id="donutchart" style="width: 700px; height: 400px;"></div>
				<div class="col-md-6 graphChart2"></div>
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
	<!-- 구글 그래프  -->
	<script type="text/javascript"
		src="https://www.gstatic.com/charts/loader.js"></script>
	<!-- 개인 js -->
	<script src="/starrail/resources/js/map/mapsearch.js"
		type="text/javascript"></script>
</body>
</html>