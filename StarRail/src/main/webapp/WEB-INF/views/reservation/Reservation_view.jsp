<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<style type="text/css">
.container {
	width: 80%;
}
.first>div{
	padding: 1px;
}
input[type=text] {
	border: dotted 3px blue
}
.table table-bordered table-hover {
	width: 100%;
}
</style>
<title>StarRail ReservaionPage</title>
<!-- datePicker CSS File -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
	<link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/css/bootstrap-datepicker3.standalone.css'>
	<link rel="stylesheet" href="/starrail/resources/css/main/style.css">
	
	<link rel="stylesheet" type="text/css"	href="/starrail/resources/css/reservation/reservation.css">
</head>

<body>
 <!-- 해더 -->
 <%@include file="../main/nav_page.jsp"%>
	<div style="margin-top: 60px;"></div>   
 
 	<br/>
 		<center>
		<font size="19" color="#92B3B7"> <span class="fa fa-train"></span> StarRail Reservation</font>
		</center>
		<center>
		<div
			style="border: 1px solid #48BAE4; height: 5%; width: 60%; margin: auto;">
			<img src="/starrail/resources/images/reservation/7305_12899_1845.jpg"
				style="position: top:0; left: 0; width: 100%;">
		</div>

		
		
		<!-- ######################################## -->
		<!-- 출발일 선택 -->
		<!-- ######################################## -->
		<form action="Reservation_view" method="POST">
		<div class=firstbox style="border: 1px solid #48BAE4; height: 50%; width: 60%; margin: auto; color: #92B3B7; font-weight: bold; font-size: 1.0em;">
		<input type="hidden" name="m_id" value='${login.m_id }'>
		<br>
			<div align="center">
				<font size="5px;">내일로 티켓 예약</font>
			</div>
			<br><br>
			<div id="secondbox" style="border:1px solid #8c8c8c;">
			<div >
				<font size="3px;">내일로 출발 날짜 선택</font>
			</div>
			<div class="input-daterange input-group start-pick" id="flight-datepicker">
    			<div class="form-item">
    			<span class="fontawesome-calendar"></span>
      				<input class="input-sm form-control" type="text" id="start-date" name="res_sdate" placeholder="내일로 출발 날짜를 선택해주세요 :)" data-date-format="DD, MM d"/><span class="date-text date-depart"></span>
    			</div>
    		</div>
    	
	
	
		<!-- ######################################## -->
		<!-- 여행 일수 선택 -->
		<!-- ######################################## -->
		<br>
		<br>
		<div >
				<font size="3px;">여행 일수 선택</font>
				<br><br>
		</div>
		<div class="btn-group" data-toggle="buttons">
			<label class="btn btn-warning">
				<input type="radio"  id="option2" name="res_tcount" autocomplete="off" value="5일">5일
				<span class="glyphicon glyphicon-ok"></span>
			</label>

			<label class="btn btn-danger">
				<input type="radio"  id="option2" name="res_tcount" autocomplete="off" value="7일">7일
				<span class="glyphicon glyphicon-ok"></span>
			</label>
		</div>
		
		<br><br>
		<div>
		<font size="3px;">발권역 선택</font>
		</div>
		
		<!-- ######################################## -->
		<!-- 발권역 선택  -->
		<!-- ######################################## -->
		
		<div style="width: 40%" align="center">		
		<select name="i_name" id="sselectbox"
							class="form-control"
							style="font-family: 굴림체; text-align-last: center;" >
								<option>--- 발권역 선택 ---</option>
								<option value="서울">&nbsp;&nbsp;서울</option>
								<option value="용산">&nbsp;&nbsp;용산</option>
								<option value="청량리">&nbsp;&nbsp;청량리</option>
								<option value="정동진">&nbsp;&nbsp;정동진</option>
								<option value="제천">&nbsp;&nbsp;제천</option>
								<option value="영월">&nbsp;&nbsp;영월</option>
								<option value="단양">&nbsp;&nbsp;단양</option>
								<option value="충주">&nbsp;&nbsp;충주</option>
								<option value="부산">&nbsp;&nbsp;부산</option>
								<option value="동대구">&nbsp;&nbsp;동대구</option>
								<option value="순천">&nbsp;&nbsp;순천</option>
						</select>	
						
		</div>
		<br><br>
	</div>
	
	<div id="thirdbox" style="border: 1px solid #48BAE4;">
			<img src="/starrail/resources/images/reservation/railroMap.PNG" style="width: 50%;">
	</div>
		<br>
	<div class="ui-group-buttons">
						<button type="button" class="btn btn-primary btn-lg">취소</button>
						<div class="or or-lg"></div>
						<button type="submit" class="btn btn-success btn-lg">저장</button>
					</div><br><br>
			</div>
		</form>
	
		
	
		<!-- ######################################## -->
		<!-- 기차좌석 선택 -->
		<!-- ######################################## -->
		
		<div id="fourbox">
		<br>
		<div >
		<font size="5px;">기차 좌석 선택</font>
		</div>
		<br>
		<div style="border: 1px solid #48BAE4; height: auto; width: 70%; margin: auto;" >
		<div class="input-daterange input-group end-pick" id="flight-datepicker">
			<div class="form-item">
				<span class="fontawesome-calendar"></span>
     		 	<input class="input-sm form-control red_sdate" type="text" id="end-date" name="end" placeholder="좌석을 예약할 날짜를 선택해주세요." data-date-format="DD, MM d"/><span class="date-text date-return"></span>
  			 </div>
		</div>
		
		
		<div class="selectboxAll" style="width: 10%">
		<select name="red_start" id="red_start" class="form-control" style="text-align: center;">
		<option>출발역</option>
		<c:forEach items="${list }" var="station">
			<option value="${station.s_id }">${station.s_name }</option>
		</c:forEach>

		</select>
		</div >
		
		
		
		<div class="selectboxAll" style="width:10%">
			<select name="red_end" id="red_end" class="form-control" style="text-align:center;">
				<option>도착역</option>
				<c:forEach items="${list }" var="station">
					<option value="${station.s_id }">${station.s_name }</option>
				</c:forEach>
			</select>
		</div>
		
		<div class="selectboxAll" style="width:10%">
			<select name="red_time" id="red_time" class="form-control" style="text-align:center;">
				<option>시간</option>
				<option value="000000">00:00</option>
				<option value="010000">01:00</option>
				<option value="020000">02:00</option>
				<option value="030000">03:00</option>
				<option value="040000">04:00</option>
				<option value="050000">05:00</option>
				<option value="060000">06:00</option>
				<option value="070000">07:00</option>
				<option value="080000">08:00</option>
				<option value="090000">09:00</option>
				<option value="100000">10:00</option>
				<option value="110000">11:00</option>
				<option value="120000">12:00</option>
				<option value="130000">13:00</option>
				<option value="140000">14:00</option>
				<option value="150000">15:00</option>
				<option value="160000">16:00</option>
				<option value="170000">17:00</option>
				<option value="180000">18:00</option>
				<option value="190000">19:00</option>
				<option value="200000">20:00</option>
				<option value="210000">21:00</option>
				<option value="220000">22:00</option>
				<option value="230000">23:00</option>
			</select>
		</div>
		<div style="width: 90%; border:1px solid #8c8c8c;">
		<br>
			<table id="searchtrain" style="text-align: center; border: 1px solid #dddddd; width:95% " class="table table-bordred table-striped">
				<thead>
					<tr class='info loading'>
						<th>열차종류</th>
						<th>출발시간</th>
						<th>출발역</th>
						<th>도착시간</th>
						<th>도착역</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<br>
			</div>
			</div>
				<br><br>
	
					<!-- <div class="ui-group-buttons">
						<button type="button" class="btn btn-primary btn-lg">취소</button>
						<div class="or or-lg"></div>
						<button type="submit" class="btn btn-success btn-lg">저장</button>
					</div><br><br>
						</div>
					</form>  -->
					
<!-- ######################################## -->
<!-- Modal -->
<!-- ######################################## -->
				
		<div class="container">
	<div class="row">
    
    
	<div id="myModal" class="modal fade in">
        <div class="modal-dialog">
            <div class="modal-content">
 
                <div class="modal-header">
                    <a class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span></a>
                    <h4 class="modal-title"><span class="fa fa-train"></span> StarRail 좌석 예약 시스템</h4>
                </div>
                
                <div class="form-group" 
				style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto;">
				<label for="exampleInputEmail1"><img
					src="/starrail/resources/images/reservation/Mu1_11.jpg"></label>
				</div>
                <div class="modal-body">
                    <h4>기차 좌석 예약</h4>
                   
                   	<div class="container">			

	<div class="well well-sm text-center">

		<h3>기차 호수 선택</h3>
		
		<div class="btn-group" data-toggle="buttons">
			
			<label class="btn btn-success active">
				<input type="radio" name="train_type" id="option2" autocomplete="off">1호차
				<span class="glyphicon glyphicon-ok"></span>
			</label>
			
			<label class="btn btn-warning">
				<input type="radio" name="train_type" id="option2" autocomplete="off">2호차
				<span class="glyphicon glyphicon-ok"></span>
			</label>

			<label class="btn btn-primary">
				<input type="radio" name="train_type" id="option1" autocomplete="off">3호차
				<span class="glyphicon glyphicon-ok"></span>
			</label>

			<label class="btn btn-info">
				<input type="radio" name="train_type" id="option2" autocomplete="off">4호차
				<span class="glyphicon glyphicon-ok"></span>
			</label>

			<label class="btn btn-primary">
				<input type="radio" name="train_type" id="option1" autocomplete="off">5호차(식당칸)
				<span class="glyphicon glyphicon-ok"></span>
			</label>
			
			<label class="btn btn-default">
				<input type="radio" name="train_type" id="option2" autocomplete="off">6호차
				<span class="glyphicon glyphicon-ok"></span>
			</label>

			<label class="btn btn-warning">
				<input type="radio" name="train_type" id="option2" autocomplete="off">7호차
				<span class="glyphicon glyphicon-ok"></span>
			</label>

			<label class="btn btn-danger">
				<input type="radio" name="train_type" id="option2" autocomplete="off">8호차
				<span class="glyphicon glyphicon-ok"></span>
			</label>
		
		</div>


	</div>
	
	<div class="trainSeat">
		<div class="first"></div>
		<div class="second"></div>
		<div align="center"> <img src="/starrail/resources/images/reservation/abc.jpg"></div>
		<div class="third"></div>
		<div class="forth"></div>
	
	</div>


</div>
                   
                </div>
                
                
                
                <div class="modal-footer">
                    <div class="btn-group">
                        <button class="btn btn-danger" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
                        <button class="btn btn-primary"><span class="glyphicon glyphicon-check"></span> Save</button>
                    </div>
                </div>
 
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dalog -->
    </div><!-- /.modal -->
    
	<!-- <a data-toggle="modal" href="#myModal" class="btn btn-primary">Launch demo modal</a> -->


	</div>
</div>

<!-- ######################################## -->
<!-- 버튼 -->
<!-- ######################################## -->


</center>


	<!--푸터  -->
    <div style="margin-top: 30px;">
    	<%@include file="../main/footer.jsp"%>   
    </div>   


<!-- datepicker JS File : 무조건 코드 맨마지막에 넣어야 실행됨 -->   
 	<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/js/bootstrap-datepicker.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery-dateFormat/1.0/jquery.dateFormat.js'></script> 
<!-- JS File -->
  
   <script src="/starrail/resources/js/sharetext/reservation.js?ver=1"></script>

</body>
</html>