<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=divice-width" , initial-scale="1">
<title>My Page</title>
<!-- 마이페이지 css -->
<link rel="stylesheet" type="text/css" href="/starrail/resources/css/mypage/mypage.css?ver=1">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">


</head>
<body>
<%@include file="../main/nav_page.jsp"%>
<div style="margin-top: 60px;"></div>

<div id="mypageWrap">
	<!-- 왼쪽 사이드 바 시작 -->
	<div id = "left-side-area">

		<!-- 회원 정보 불러오기 시작 -->
		<div class="usser-info">
			<h3 class="user-info-title">내 정보</h3>
			<h4>${user.m_name } (${user.m_id })</h4>
			<c:set var="gender" value="${user.m_gender }"></c:set>
			<c:choose>
				<c:when test="${gender == 'F' }">
					성별: 여성
				</c:when>
				<c:when test="${gender == 'M' }">
					성별: 남성
				</c:when>
			</c:choose>
			<br/>
			나이: ${user.m_age }
			
		</div>
		<!-- 회원 정보 불러오기 종료-->

	</div>
	<!-- 왼쪽 사이드 바 종료 -->
	
	
	<!-- 오른쪽 컨텐츠 영역 시작-->
	<div id="right-side-area">
	
		<!-- 내 코스 불러오기 시작 -->
		<div id ="myCourse">
			<h4 id = "myCourseTitle">내 코스</h4>
			<c:choose>
				<c:when test="${fn:length(courses) == 0}">
					<span class="nullCourse">저장한 코스가 없습니다.</span>
				</c:when>
				<c:otherwise>
				<table class="course-list-table">
					<c:forEach items="${courses}" var="course">
						<tr>
							<td class="td_c_img"><img src = "${course.c_filename}" class="c_img"></td>
							<td class="td_c_info">
								<h3>${course.c_name}</h3>
								<span>발권역: ${course.i_name}</span>
							</td>
							<td class="td_c_edit">
								<button type="button" class="btn btn-outline-success editBtn" c_id="${course.c_id }">보기/수정</button>
							</td>
							<td class="td_c_del">
								<button type="button" class="btn btn-outline-success delBtn" c_id="${course.c_id }">삭제</button>
							</td>
						</tr>
					</c:forEach>
				
				</table>
				</c:otherwise>
			
			</c:choose>
			

		</div>
		<!-- 내 코스 불러오기 종료 -->
		
		
		<!-- 내 예약 정보 불러오기 시작 -->
		
		<div id ="myReserv">
			<h4 id = "myReservTitle">내 예약 정보</h4>
			 <c:choose>
				<c:when test="${fn:length(reserves) == 0}">
					<span class="nullReserv">예약 내역이 없습니다.</span>
				</c:when>
				<c:otherwise>
				<table class="reserve-list-table">
				<thead>
					<tr>
						<th class="reserveDate">예약일</th>
						<th class="reserveTCount">티켓 종류</th>
						<th class="reserveIName">발권역</th>
					</tr>
				</thead>
				<c:forEach items="${reserves}" var="reserve">
					<tr>
						<td>${reserve.res_sdate }</td>
						<td>${reserve.res_tcount }</td>
						<td>${reserve.i_name }</td>
					</tr>
				</c:forEach>
				
				</table>
				
				</c:otherwise>
			</c:choose> 
		
		</div>
		<!-- 내 예약 정보 불러오기 종료 -->
	
		<!-- 지도 시작 -->
		<div id="myMap">
			<h4 id = "myMapTitle">이 영역 제목</h4>
		
			<!-- 길승세가 만들 예정  -->
		
		</div>
		<!-- 지도 종료 -->
	</div>
	<!-- 오른쪽 컨텐츠 영역 종료 -->

</div>

<div style="margin-top: 30px;">
<%@include file="../main/footer.jsp"%>   
</div>

<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/js/bootstrap-datepicker.min.js'></script>

<!-- 마이페이지 js -->
<script src="/starrail/resources/js/mypage/mypage.js" type="text/javascript"></script>

</body>
</html>