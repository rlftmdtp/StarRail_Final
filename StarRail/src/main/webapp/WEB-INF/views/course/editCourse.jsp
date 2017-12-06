<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=divice-width" , initial-scale="1">
<title>Edit Course</title>
<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
<!-- bootstrap에서 받아온 js파일+css파일 사용 하기 위함 -->
<script src="/starrail/resources/bootstrap/js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/bootstrap/css/bootstrap.css">


<!-- 코스 수정 페이지 css -->
<link rel="stylesheet" type="text/css" href="/starrail/resources/css/course/editCourse.css?ver=1">

<!-- 달력 ui css -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">

<!-- 달력 js -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- 코스 js -->
<script src="/starrail/resources/js/course/editCourse.js" type="text/javascript"></script>

<!-- n일차 버튼 css -->
<link rel="stylesheet" href="/starrail/resources/css/course/nthBtn.css">

<!-- n일차 js -->
<script src="/starrail/resources/js/course/nthBtn.js" type="text/javascript"></script>

<!-- 역 선택 버튼 css -->
<link rel="stylesheet" href="/starrail/resources/css/course/stationBtn.css">

</head>
<body>
<input type="hidden" id="c_id" value="${course.c_id}">
<input type="hidden" id="i_name" value="${course.i_name}">
<input type="hidden" id="c_filename" value="${course.c_filename }">
<div id = "getdetaildata">
<c:forEach items="${details}" var="detail">
	<span id="cd_id">${detail.cd_id}</span>
	<span id="cd_start">${detail.cd_start}</span>
	<span id="cd_stime">${detail.cd_stime}</span>
	<span id="cd_end">${detail.cd_end}</span>
	<span id="cd_etime">${detail.cd_etime}</span>
</c:forEach>
</div>
	<div id="courseWrap">
		<div id ="courseName">
			코스명:&nbsp;<input type="text" name="c_name" value="${course.c_name}">
		</div>
		<!-- 여행 기간 설정 -->

		<div id="periodSetting">
			출발일 선택&nbsp;<input type="text" id="datepicker" size="10">&nbsp;&nbsp;
			<input type="radio" class="tripLong" name="tripLong" value="5" disabled>5일권
			<input type="radio" class="tripLong" name="tripLong" value="7" disabled>7일권<br />

		</div>
		<!-- 여행 기간 설정 closing -->



		<!-- n일차 선택 -->
		<div id="dateSetting">
			<div class="btn-group beds-baths-group" id="beds-baths-group" data-toggle="buttons">
				<!-- n일차 버튼 동적 생성 -->
			</div>
		</div>
		<!-- n일차 선택 closing -->


		<!-- 출발 도착역 선택 -->
		<div id="stationSetting">

			<!-- 출발역 리스트 -->
			<div class="departures">				
			    <div class="btn-group">
			        <!-- 출발역 데이터 -->
			    </div>
			</div>
			
			<!-- 도착역 리스트 -->
			<div class="arrivals">
				<div class="btn-group">
			        <!-- 도착역 데이터 -->
			    </div>
			</div>
			<!-- 도착역 리스트 closing-->
			

		</div>
		<!-- 출발 도착역 선택 closing -->


		<!-- 열차시간표 조회/선택, 지도 표시, 일정 리스트 출력 -->
		<div id="courseSetting">

			<!-- 열차시간표 조회/선택 -->
			<div id="trainSetting">

				<!-- 출발 희망 시간 선택 -->
				<div class="hopingTime">
					<h4>출발 시간</h4>
					<select class="departTime" name="departTime">
						<option>--------------</option>
					</select> &nbsp;시
				</div>
				<!-- 출발 희망 시간 선택 closing -->

				<!-- 열차 시간표 -->
				<h4 class="trainTimeTableTitle">탑승 가능 열차</h4>
				<div class="trainTimeTable">
					<table class="trainListTable">
						<thead>
							<tr>
								<th class="th1">열차 종류</th>
								<th class="th2">탑승 시간</th>
								<th class="th3">선택</th>
							</tr>
						</thead>
						<tbody>
							<!-- 여기에 동적으로 열차시간표 tr, td 추가 -->
						</tbody>
					</table>
				</div>
				<!-- 열차 시간표 closing -->

				<!-- 일정추가버튼영역 -->
				<div class="addingBtn">
					<button type="button" class="btn btn-outline-success addBtn" disabled>일정 추가</button>
				</div>
				<!-- 일정추가버튼영역 closing -->

			</div>
			<!-- 열차시간표 조회/선택 closing -->

			<!-- 지도 표시 -->
			<div id="mapArea">mapArea</div>
			<!-- 지도 표시 closing -->

			<!-- 일정 리스트 -->
			<div id="couresDetailView">
				<h4 class="title">일정 세부</h4>
				<div class="uls">
					
				</div>
			</div>
			<!-- 일정 리스트 closing -->

		</div>
		<!-- 열차시간표 조회/선택, 지도 표시, 일정 리스트 출력 closing -->


		<!-- 발권역 선택 -->
		<div id="issueSetting">
			<table class="issuelist">
				<thead>
					<tr>
						<th class="th6">선택</th>
						<th class="th1">역</th>
						<th class="th2">먹거리 혜택</th>
						<th class="th3">숙박 혜택</th>
						<th class="th4">관광 혜택</th>
						<th class="th5">기타</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<!-- 발권역 선택 closing -->


		<!-- 전체 코스 저장 버튼 영역 -->
		<div id="allSavingBtn">
			<button type="button" class="btn btn-outline-success saveBtn" disabled>수정 완료</button>
		</div>
		<!-- 전체 코스 저장 버튼 영역 closing -->

	</div>
	<!-- courseWrap closing -->
</body>
</html>