<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Star Raile Partner</title>


<!-- Css File -->
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/expenses/expenses.css?ver=1">
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
	<br>
	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="panel panel-primary" style="width: 95%; border-color: #F0AD4E;">
				<div class="panel-heading" style="background-color: #F0AD4E; border-color: #F0AD4E; ">
					<h3 class="panel-title">여행 경비 관리</h3>
					<span class="pull-right"> </span>
				</div>

				<!-- Tab안 -->
				<div class="panel-body">
					<div class="col-md-4" style="margin-left: -15px; margin-right: 15px;">
						<div class="tab-content">

							<!-- 예산경비 등록 폼 -->
							<form action="#" id="expenseslist">
								<div class="hero-widget well well-sm"
									style="background-color: #FFFFFF; height: 630px;">
									<input type="hidden" class="m_id" value="${m_id }" name="m_id">
									<div class="input-group">
										<span class="input-group-addon" id="basic-addon1"
											style="width: 30px;"> <span
											class="glyphicon glyphicon-book" aria-hidden="true"> </span></span>
										<input type="text" class="form-control" id="e_title"
											name="e_title" placeholder="제목"
											aria-describedby="basic-addon1">
									</div>
									<div class="input-group"
										style="margin-top: 30px; margin-bottom: -35px;">
										<input id="courseId" type="checkbox" name="course"
											class="course" value="couse" onclick="courseclick()">&nbsp;저장된
										코스 가져오기
									</div>
									<div class="thumbnail" style="height: 200px; margin-top: 20px;">
										<form class='form-horizontal well' action='#'>
											<div class='row'>
												<div class='col-md-12' id="thumbnail" "></div>
											</div>
										</form>
									</div>


									<div class="input-group" id="flight-datepicker" style="margin-top: 10px;">
										<span class="input-group-addon" id="basic-addon1" style="width: 32px;"> 
											<span class="glyphicon glyphicon-calendar" aria-hidden="true"> </span>
										</span>
										<!-- <span class="fontawesome-calendar"></span> -->
										<input class="form-control" type="text" id="datepicker_expense" name="e_sdate" placeholder="출발일"
											data-date-format="DD, MM d" style="font-size: 11px;  font-weight:normal; size:50px; width: 50px; " />
										<span class="date-text date-depart"></span>
									</div>

									<div class="input-group"
										style="margin-top: 15px; margin-bottom: -30px;">
										<input id="checkboxId" type="checkbox" name="tripLong"
											class="tripLong" value="5" onclick="oneclick(this)">5일권&nbsp;&nbsp;
										<input id="checkboxId" type="checkbox" name="tripLong"
											class="tripLong" value="7" onclick="oneclick(this)">7일권
									</div>

									<div class="input-group">
										<span class="input-group-addon" id="basic-addon1" style="width: 30px;"> 
											<span class="glyphicon glyphicon-calendar" aria-hidden="true"> </span>
										</span> 
									<input type="text" class="form-control" id="endDate" name="e_edate" placeholder="도착일" aria-describedby="basic-addon1">
									</div>

									<div class="input-group">
										<span class="input-group-addon" id="basic-addon1"
											style="width: 30px;"> <span
											class="glyphicon glyphicon-piggy-bank" aria-hidden="true">
										</span></span> <input type="text" class="form-control" id="travelAmount"
											name="e_total" placeholder="예산"
											aria-describedby="basic-addon1" />
									</div>

									<div class="input-group">
										<input type="button" value="저장" id="submit1"
											class="btn btn-primary" style="background-color: #F0AD4E; border-color: #F0AD4E" data-toggle="modal" >
										&nbsp;&nbsp;

										<div class="modal-footer" style="border: 1px #eee;">
											<div class="btn-group pull-left">
												<button type="button"
													class="btn btn-default dropdown-toggle"
													data-toggle="dropdown" id="recall">불러오기</button>
												<ul class="dropdown-menu">



												</ul>
											</div>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>

				<div class="col-md-4">
						<!-- 지출내역 등록하는 카테고리 -->
						<form action="#" id="katelist">
							<div class="hero-widget well well-sm"
								style="background-color: #FFFFFF;">

								<div class="dayButton" id="ddol">
									<!--경비 저장하는 form에서 선택한 일수만큼 버튼 생김 -->
								</div>

								<!-- 카테고리 -->
								<div class="btn-toolbar"
									style="margin-top: 10px; margin-bottom: 10px;">
									<div class="btn-group">
										<button class="btn btn-primary" data="food"
											style="width: 40px; height: 40px; background-color: #F0AD4E; border-color: #F0AD4E;">
											<img src="/starrail/resources/images/expenses/dinner.png" />
										</button>

										<button class="btn btn-primary" data="shopping"
											style="width: 40px; height: 40px; background-color: #F0AD4E; border-color: #F0AD4E;">
											<img src="/starrail/resources/images/expenses/shopping.png" />
										</button>

										<button class="btn btn-primary" data="hotel"
											style="width: 40px; height: 40px; background-color: #F0AD4E; border-color: #F0AD4E;">
											<img src="/starrail/resources/images/expenses/bed.png" />
										</button>

										<button class="btn btn-primary" data="bus"
											style="width: 40px; height: 40px; background-color: #F0AD4E; border-color: #F0AD4E;">
											<img src="/starrail/resources/images/expenses/bus.png" />
										</button>

										<button class="btn btn-primary" data="etc"
											style="width: 40px; height: 40px; background-color: #F0AD4E; border-color: #F0AD4E;">
											<img src="/starrail/resources/images/expenses/001-speech-bubble.png" />
										</button>
									</div>
								</div>


								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1"
										style="width: 30px;"> <span
										class="glyphicon glyphicon-list-alt" aria-hidden="true">
									</span></span> <input type="text" class="form-control" placeholder="항목"
										aria-describedby="basic-addon1" id="ed_katename">
								</div>

								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1"
										style="width: 30px;"> <img
										src="/starrail/resources/images/expenses/money.png"></span> <input
										type="text" class="form-control" id="ed_amount"
										name="ed_amount" placeholder="사용 금액"
										aria-describedby="basic-addon1" />
								</div>

								<div class="input-group">
									<input type="button" value="등록" class="btn btn-primary"
										data-toggle="modal" onclick="expense_save()" style="background-color: #F0AD4E; border-color: #F0AD4E">
								</div>
							</div>
						</form>
					</div>
					<div class="col-md-1">
						<!-- 지출된 금액 계산해주고 view에 뿌려주는 list -->
						<form action="#" id="amountlist" >
							<div class="hero-widget well well-sm" style="background-color: #FFFFFF; width : 320px; height: 300px; margin-left: -15px;">
								<div id="tabnavi" class="btn-group row-md-3" data-toggle="buttons-radio">
									<!-- tab생성하기 -->
								</div>

								<div class="tab-content row-md-2">
									<p class="lead">
										<!-- 날짜 -->
									</p>
								</div>

								<div class="tab-content row-md-5">
									<ul class="list-group">
										<li class="list-group-item pay" style="height: 150px"></li>
									</ul>
								</div>

								<div class="totallist row-md-2">
									<div class="col-md-6 totalmoney">
										<!-- 총사용금액, 총남은돈  -->
									</div>

								</div>
							</div>
						</form>
					</div>

						<!-- 실시간 도표 -->

							<div id="piechart" style="width: 500px; height: 400px; margin-top: 300px; margin-left: 450px; "></div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<div style="margin-top: 30px;">
		<%@include file="../main/footer.jsp"%>
	</div>

	<!-- datepicker JS File : 무조건 코드 맨마지막에 넣어야 실행됨 -->
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js'></script>
	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/js/bootstrap-datepicker.min.js'></script>
	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/jquery-dateFormat/1.0/jquery.dateFormat.js'></script>
	<!-- 내꺼 -->
	<script type="text/javascript" src='/starrail/resources/js/expenses/expense.js'></script>
	<!-- chart -->
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</body>

  <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);
      var doubleArray;
	
      
      function drawChart() {

        var data = google.visualization.arrayToDataTable(
        		[
          ['Task', 'Hours per Day'],
          ['No Data',1]
         
        ] 
        		
        );

        var options = {
          title: 'My Expense'
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

        chart.draw(data, options);
      }
    </script>


<!--5일차,7일차 체크박스 클릭했을 때, 하나만 눌리게 -->
<script type="text/javascript">
	function oneclick(a) {
		var obj = document.getElementsByName("tripLong");
		for (var i = 0; i < obj.length; i++) {
			if (obj[i] != a) {
				obj[i].checked = false;
			} else {
				obj[i].checked = true;
			}
		}
	}
</script>
 
 <!-- 코스불러오기버튼 눌렀을 때 -->
<script type="text/javascript">
	
	$('#recall').on('click', function(){
		
		var obj = new Object();
		obj.m_id = $('.m_id').attr('value');
		$.ajax({
			url : '/starrail/expenses/expense_recall',
			type : 'POST',
			headers : {
				'Content-Type' : 'application/text'
			},
			data : obj.m_id,
			dataType : "json",
			success: function(data) {

				var e_title = "";
				var e_no = 0;
				$('.dropdown-menu').empty();
				 $.each(data, function(index, item) {
					$('.dropdown-menu').append("<li><a href='#' id='recallCome' data='" +item["e_no"] +"'>"
							+item["e_title"] +"</a></li>");
					
				}); 
			}
		})
	});
	
	
</script>  

 
<!--코스가져오기 눌렀을 때-->
<script type="text/javascript">
	function courseclick() {
		$('#thumbnail').empty();
		 var obj = new Object();
			obj.m_id = $('.m_id').attr('value');
		$.ajax({
			url : '/starrail/expenses/expense_course',
			type : 'POST',
			headers : {
				'Content-Type' : 'application/text'
			},
			data : obj.m_id,
			dataType : "json",
			success : function(data) {
				var c_name = "";
				var c_id =0;
				 //map에서 꺼내기 위한 each문 두번
				$.each(data, function(index, item) {
					alert(item)
					$('#thumbnail').append("<div class='col-md-4'><img src ='" + item["c_filename"]
							+ "' class='img-responsive img-radio'> <button type='button' style='background-color: #FF8224; border-color: #FF8224;' class='btn btn-primary btn-radio' id='thumbnailBtn' data='"+ item["c_id"] +"'>"
							+ item["c_name"]
							+ "</button></div>");
				}); 
			}
		});
	}

</script>

<!-- 출발일과 체크박스 선택시 도착일 계산 // 함수 datepicker사용 -->
<script>
 $(function() {

		var tripLong = document.getElementsByName("tripLong"); // 체크박스 전용 변수
 			$('#datepicker_expense').datepicker({
			onSelect : function(StartDate) {
				$('#datepicker_expense').empty(); //출발역 목록 비우기
				$('#endDate').empty(); //도착역 목록 비우기
				tripDateStart = new Date(StartDate);
				$('input[name="tripLong"]').removeAttr('disabled');
				$('input[name="tripLong"]').prop('checked', false);
			},
			autoclose: true,
			format: "yyyy-mm-dd",
			startDate: "now"
		});
		 		
		$('.tripLong').click(function() {
							var tripDateStart = new Date();
							var strArr = $('#datepicker_expense').val().split('-');
							/* 날짜계산을 년월일 같이 set해주게되면 12월로 안넘어간다 따로 해주어야함 */
							tripDateStart.setMonth(Number(strArr[1]) - 1);
							tripDateStart.setFullYear(strArr[0]);
							tripDateStart.setDate(strArr[2]);

							var endDate = new Date();
							endDate.setMonth(Number(strArr[1]) - 1);
							endDate.setFullYear(strArr[0]);
							endDate.setDate(tripDateStart.getDate() + Number($(this).val()) - 1);

							/* 일 수 구하는 공식 */
							var interval = endDate.getTime() - tripDateStart.getTime();
							interval = Math.floor(interval / (1000 * 60 * 60 * 24));
							interval.toString();
							alert(interval.toString());

							$('#endDate').attr("value", (endDate.getFullYear() + '-'
													+ (endDate.getMonth() + 1)
													+ '-' + endDate.getDate()));

							/* 체크박스를 클릭할때 마다 생기는 버튼값을 지워주고 새로 만들어줌 for문이용하여 출발일기준 일수만큼 더해주기 */
							$('.dayButton').empty();
							$('#tabnavi').empty();
							for (var i = 0; i <= parseInt(interval); i++) {
								$('.dayButton').append('<input type="button" style="background-color: #FF8224; border-color: #FF8224" value='
														+ (i + 1)
														+ '일차  class="ed_date btn btn-primary" id="'
														+ (tripDateStart.getFullYear()
														+ '/'
														+ (tripDateStart.getMonth() + 1)
														+ '/' + tripDateStart.getDate())
														+ '"> </input>');
								$('#tabnavi').append('<input type="button" style="background-color: #FF8224; border-color: #FF8224" value='
										+ (i + 1)
										+ '일차  class="listed_date btn btn-primary" id="'
										+ (tripDateStart.getFullYear()
										+ '/'
										+ (tripDateStart.getMonth() + 1)
										+ '/' + tripDateStart.getDate())
										+ '"> </input>');
								tripDateStart.setDate(tripDateStart.getDate() + 1);
	
							}
			});
	});

</script>

<!-- 예산 경비 저장 눌렀을 때  -->
<script type="text/javascript">
var thumbnailBtn;

$('#thumbnail').on('click','#thumbnailBtn' ,function(){
	thumbnailBtn = $(this).attr('data');
})

	$('#submit1').on('click', function(){
		$.ajax({
					url : '/starrail/expenses/railro_expenses',
					type : 'POST',
					headers : {
						'Content-Type' : 'application/json'
					},
					data : JSON.stringify({
						'm_id' : $('.m_id').attr('value'),
						'e_title' : $('#e_title').val(),
						'e_sdate' : $('#datepicker_expense').val(),
						'e_edate' : $('#endDate').val(),
						'e_total' : $('#travelAmount').val()
					}),
					dataType : "text",
					success : function(data) {
						/* 지출경비form에 e_no를 hidden으로 숨겨 누가 지출하는지 알게하기 */
						$('#ddol').append('<input type="hidden" value="' + data + '" class="e_no"/>');
					}
				});
});
</script>



<!-- 지출내역 저장 눌렀을 때 -->
<script type="text/javascript">
	var kategorie="";
	var day;
	var food = 0;
	var shopping = 0;
	var hotel = 0;
	var bus = 0;
	var etc = 0;
	
	/* 카테고리중 클릭한 값 가져오기 */
	$('.btn').on('click', function() {
		kategorie = $(this).attr('data');
		alert(kategorie);
		/* 클릭한 버튼에 이벤트 걸어 뭐 눌렀는지 알려주자 */
		$(this).addClass("selected");
		$(this).siblings().removeClass("selected");
	});

	/* 일차 버튼중 클릭한 날짜 가져오기 */
	$(document).on('click', '.ed_date', function() {
		day = ($(this).attr('id'));
		/* 클릭한 버튼에 이벤트 걸어 뭐 눌렀는지 알려주자 */
		$(this).addClass("selected");
		$(this).siblings().removeClass("selected");
	});

	/* 지출내역 저장 눌렀을 때  DB로 보내고 새로고침*/
//	$('#expense_save').on('click', function() {
		function expense_save(){
			
		alert("저장합니다");
		var newNO = $('.e_no').attr('value');
		var html = "";
		$.ajax({
			url : '/starrail/expenses/railro_amount',
			type : 'POST',
			headers : {
				'Content-Type' : 'application/json'
			},
			data : JSON.stringify({
				'ed_kategorie' : kategorie,
				'ed_katename' : $('#ed_katename').val(),
				'ed_amount' : $('#ed_amount').val(),
				'ed_date' : day,
				'e_no' : newNO
			}),
			dataType : "json",
			success : function(data) {
				alert("데이터 :  -----------" +data);
				/* 내가 사용한 지출 내역을 가져와 view에 뿌려줌 */
					$(document).on('click', '.listed_date', function() {
						$('.lead').empty();
						$('.pay').empty();
						$('.totalmoney').empty();
						
					//day:지출된날짜   $('listed_date').attr('id') : 내가누른버튼
				if(data.ed_date.match($(this).attr('id'))){
					$('.lead').append(
							'<p>' + data.ed_date + '</p>');
					$('.lead').append(
							'<input type="hidden" value="'+data.e_no+'" >');

					if (data.ed_kategorie == 'food') {
						$('.pay').append(
										'<div class="list-group-item"><img src ="/starrail/resources/images/expenses/dinner.png">('
												+ data.ed_katename
												+ ')&nbsp&nbsp'
												+ data.ed_amount + '</div>');
					} else if (data.ed_kategorie == 'shopping') {
						$('.pay').append(
										'<div class="list-group-item"><img src ="/starrail/resources/images/expenses/shopping.png">('
												+ data.ed_katename
												+ ')&nbsp&nbsp'
												+ data.ed_amount + '</div>');
					} else if (data.ed_kategorie == 'hotel') {
						$('.pay').append(
										'<div class="list-group-item"><img src ="/starrail/resources/images/expenses/bed.png">('
												+ data.ed_katename
												+ ')&nbsp&nbsp'
												+ data.ed_amount + '</div>');
					} else if (data.ed_kategorie == 'bus') {
						$('.pay').append(
										'<div class="list-group-item"><img src ="/starrail/resources/images/expenses/bus.png">('
												+ data.ed_katename
												+ ')&nbsp&nbsp'
												+ data.ed_amount + '</div>');
					} else if (data.ed_kategorie == 'etc') {
						$('.pay').append(
										'<div class="list-group-item"><img src ="/starrail/resources/images/expenses/001-speech-bubble.png">('
												+ data.ed_katename
												+ ')&nbsp&nbsp'
												+ data.ed_amount + '</div>');
					}
					
					$('.totalmoney').append('<p>쓴 돈 : ' + data.todayTotal + '</p>');
					$('.totalmoney').append('<p>남은 돈 : ' + data.e_total + '</p>');

					}
				});
				
					
	$.ajax({
		url : '/starrail/expenses/chart',
		type : 'POST',
		headers : {
			'Content-Type' : 'application/json'
		},
		data : JSON.stringify({
				'e_no' : newNO
		}),
		dataType : "json",
		success : function(data) {
			google.charts.load('current', {'packages':['corechart']});
		    google.charts.setOnLoadCallback(drawChart);
		      var title = new Array();
		      title =  [['Task', 'Hours per Day']];

		      $.each(data, function(index, item) {
		      var keys = Object.keys(item);
		      	for(var i in keys){
		    		title.push([keys[i],item[keys[i]]]);
		    	  }	      
		      });
		      function drawChart() {
		        var data = google.visualization.arrayToDataTable(
		        		title
		        );

		        var options = {
		          title: 'My Expense'
		        };
		        var chart = new google.visualization.PieChart(document.getElementById('piechart'));
		        chart.draw(data, options);
		      }
		}
	});
					}//success
				});//ajax
	};
</script>

</html>