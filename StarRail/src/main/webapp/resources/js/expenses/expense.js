// 선택한회원 용돈기입장 가져오기 
$(function() {
	var e_title = "";
	var e_sdate = "";
	var e_edate = "";
	var ed_date = [];
	var ed_kategorie = "";
	var ed_katename = "";
	var e_total = 0;
	var ed_amount = [];
	var e_no = 0;
	var ed_no = 0;
	var startDate = new Date();
	var strArr;
	var strArr2;
	var todayTotal = 0;
	var interval;
	var date;

	$('.dropdown-menu').on('click', '#recallCome', function() {
						var obj = new Object();
						obj.e_no = $(this).attr('data');
						$.ajax({
									url : '/starrail/expenses/recallData',
									type : 'POST',
									headers : {
										'Content-Type' : 'application/json'
									},
									data : obj.e_no,
									dataType : "json",
									success : function(data) {

										$.each(data, function(index, item) {
											$('#ddol').append('<input type="hidden" value="' + item["e_no"] + '" class="e_no"/>');
										/* 출발일과 도착일을 date로  바꿔서 일수 구해주기  */
											strArr = item["e_sdate"].split('-');

											startDate.setMonth(Number(strArr[1]) - 1);
											startDate.setFullYear(strArr[0]);
											startDate.setDate(strArr[2]);

											var endDate = new Date();
											strArr2 = item["e_edate"].split('-');
											endDate.setMonth(Number(strArr2[1]) - 1);
											endDate.setFullYear(strArr2[0]);
											endDate.setDate(strArr2[2]);
											/* 일 수 구하는 공식 */
											interval = endDate.getTime() - startDate.getTime();
											interval = Math.floor(interval / (1000 * 60 * 60 * 24));
											interval.toString();

								
										/* 일수구해서 버튼만들어주기 */
										for (var i = 0; i <= parseInt(interval); i++) {
											$('.dayButton').append('<input type="button"  value='
																	+ (i + 1)
																	+ '일차  class="ed_date btn btn-primary" style="background-color: #FF8224; border-color: #FF8224" id="'
																	+ (startDate.getFullYear()
																	+ '/'
																	+ (startDate.getMonth() + 1)
																	+ '/' + startDate.getDate())
																	+ '"> </input>');

											$('#tabnavi').append('<input type="button"  value='
																	+ (i + 1)
																	+ '일차  class="listed_date btn btn-primary" style="background-color: #FF8224; border-color: #FF8224" id="'
																	+ (startDate.getFullYear()
																	+ '/'
																	+ (startDate.getMonth() + 1)
																	+ '/' + startDate.getDate())
																	+ '"> </input>');
											startDate.setDate(startDate.getDate() + 1);

										}
										});
										$.each(data, function(index, item) {
											e_no = item["e_no"];
										});

									}
								});
					});

	// -----------------------------------------------------------------------------
	/* list button클릭했을 때 */
	$(document).on('click', '.listed_date', function() {
		$('.lead').empty();
		$('.pay').empty();
		$('.totalmoney').empty();
		todayTotal = 0;
				date = $(this).attr('id');
				$.ajax({
		               url : '/starrail/expenses/listData',
		               type : 'post',
		               headers: {
		            	   "Content-Type":"application/json",
		            	   "X-HTTP-Method_Override":"POST"
		               },
		               data :JSON.stringify({
		            	   e_no : e_no,
		            	   ed_date : date
		               }),
		               success : function(data) {
		            	   todayTotal = 0;
		            	   $.each(data, function(index, item) {
		            		   ed_amount = item["ed_amount"];
		            		   e_total =item["e_total"];
		            		   todayTotal += ed_amount;
		            		   alert(todayTotal);
								if (item["ed_kategorie"] == 'food') {
									$('.pay').append('<div class="list-group-item"><img src ="/starrail/resources/images/expenses/dinner.png">('
															+ item["ed_katename"]
															+ ')&nbsp&nbsp'
															+ item["ed_amount"] + '</div>');
								} else if (item["ed_kategorie"] == 'shopping') {
									$('.pay').append(
													'<div class="list-group-item"><img src ="/starrail/resources/images/expenses/shopping.png">('
															+ item["ed_katename"]
															+ ')&nbsp&nbsp'
															+ item["ed_amount"] + '</div>');
								} else if (item["ed_kategorie"] == 'hotel') {
									$('.pay').append(
													'<div class="list-group-item"><img src ="/starrail/resources/images/expenses/bed.png">('
															+ item["ed_katename"]
															+ ')&nbsp&nbsp'
															+ item["ed_amount"] + '</div>');
								} else if (item["ed_kategorie"] == 'bus') {
									$('.pay').append(
													'<div class="list-group-item"><img src ="/starrail/resources/images/expenses/bus.png">('
															+ item["ed_katename"]
															+ ')&nbsp&nbsp'
															+ item["ed_amount"] + '</div>');
								} else if (item["ed_kategorie"] == 'etc') {
									$('.pay').append(
													'<div class="list-group-item"><img src ="/starrail/resources/images/expenses/001-speech-bubble.png">('
															+ item["ed_katename"]
															+ ')&nbsp&nbsp'
															+ item["ed_amount"] + '</div>');
								}

							});
		            	    $('.totalmoney').append('<p>쓴 돈 : ' + todayTotal + '</p>');
							$('.totalmoney').append('<p>남은 돈 : ' + e_total + '</p>');
		               },error:function(){
		            	   alert("error");
		               }
		       });
	});
	

})
