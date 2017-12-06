<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" , initial-scale=1">
<title>Map Search</title>

<!-- 부트스트랩 -->
<link rel="stylesheet"
	href="/starrail/resources/bootstrap/css/bootstrap.css">

<!--  개인 css -->
<link rel="stylesheet" href="/starrail/resources/css/map/mapsearch.css">

<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
<script type="text/javascript"
	src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=FeSf9NchU5GMk0kap7Kn"></script>
<script type="text/javascript"
	src="/starrail/resources/bootstrap/js/bootstrap.js"></script>
<script src="/starrail/resources/js/map/mapsearch.js"
	type="text/javascript"></script>

</head>
<body>
	<div class="container-fluid">
		<div class="row rails">star 레일</div>

		<div class="text">왜안됨</div>

		<div class="row course">
			여행코스고르는 곳
			<div class="row" id="courseButtons">
				<c:forEach items="${courseList }" var="courseVO">
					<button type="submit" value="${courseVO.c_id }" class="btn btn-default courseButton">${courseVO.c_name }</button>
				</c:forEach>
			</div>
		</div>

		<div class="row station">
			역 고르는 곳 
			<div class="row" id="stationButtons">
				
			</div>
		</div>

		<div id="map" class="row map">
			<div class="search">
				<input type="text" id="mapScope" placeholder="300m">
				<input type="button" id="submit" value="반경검색">
			</div>
		</div>
		
		<div class="row info">
			<div class="col-md-6 info1">
				<div class="row">

					<div role="tabpanel">
						<!-- Nav tabs -->
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation" class="active"><a href="#food"
								aria-controls="home" role="tab" data-toggle="tab">맛 집</a></li>
							<li role="presentation"><a href="#stay"
								aria-controls="profile" role="tab" data-toggle="tab">숙 소</a></li>
							<li role="presentation"><a href="#travel"
								aria-controls="messages" role="tab" data-toggle="tab">관광지</a></li>
						</ul>

						<!-- Tab panes -->
						<div class="tab-content info-content">
							<div role="tabpanel" class="tab-pane active" id="food">
								<table>
									<caption>맛 집 순 위</caption>
									<thead>
										<tr>
											<th>순위</th>
											<th>업소명</th>
										</tr>
									</thead>
									<tbody id="foodList">
										<!-- 데이터들이 들어감 -->
									</tbody>
								</table>
							</div>
							<div role="tabpanel" class="tab-pane" id="stay">.b..</div>
							<div role="tabpanel" class="tab-pane" id="travel">c...</div>
						</div>
					</div>

				</div>

			</div>
			<div class="col-md-6 info2"></div>
		</div>
		<div class="row graph"></div>
	</div>
	<!-- 
	<script>
		var map = new naver.maps.Map('map');
		var myaddress = '불정로 6';// 도로명 주소나 지번 주소만 가능 (건물명 불가!!!!)
		naver.maps.Service
				.geocode(
						{
							address : myaddress
						},
						function(status, response) {
							if (status !== naver.maps.Service.Status.OK) {
								return alert(myaddress
										+ '의 검색 결과가 없거나 기타 네트워크 에러');
							}
							var result = response.result;
							// 검색 결과 갯수: result.total
							// 첫번째 결과 결과 주소: result.items[0].address
							// 첫번째 검색 결과 좌표: result.items[0].point.y, result.items[0].point.x
							var myaddr = new naver.maps.Point(
									result.items[0].point.x,
									result.items[0].point.y);
							map.setCenter(myaddr); // 검색된 좌표로 지도 이동
							// 마커 표시
							var marker = new naver.maps.Marker({
								position : myaddr,
								map : map
							});
							// 마커 클릭 이벤트 처리
							naver.maps.Event.addListener(marker, "click",
									function(e) {
										if (infowindow.getMap()) {
											infowindow.close();
										} else {
											infowindow.open(map, marker);
										}
									});
							// 마크 클릭시 인포윈도우 오픈
							var infowindow = new naver.maps.InfoWindow(
									{
										content : '<h4> [네이버 개발자센터]</h4><a href="https://developers.naver.com" target="_blank"><img src="https://developers.naver.com/inc/devcenter/images/nd_img.png"></a>'
									});
						});
	</script>
 -->

</body>
</html>