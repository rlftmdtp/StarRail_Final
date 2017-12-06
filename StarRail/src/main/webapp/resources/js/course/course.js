$(function() {
		var lineCnt = 0;
		var check=false;
		var setStartDay;
		
		// 달력 UI (날짜 선택)
		$('#datepicker').datepicker({
			onSelect : function(dateText) {
				
				
				// 삭제
				$('#beds-baths-group').empty();	//n일차 버튼 비우기
				$('.departures div.btn-group').empty();	//출발역 목록 비우기
				$('.arrivals div.btn-group').empty();	//도착역 목록 비우기
				$('.trainListTable tbody').empty();	//열차 시간표 비우기
				$('.departTime').empty();	//출발희망시간 비우기
				$('.departTime').append('<option>--------------</option>');	//출발희망시간 디폴트옵션 재추가
				$('.addingBtn .btn-outline-success').prop('disabled', true);	//일정추가버튼 비활성화
				$('#allSavingBtn .saveBtn').prop('disabled',true);	//전체 저장 버튼 비활성화
				$('#couresDetailView .uls').empty();	//일정 세부 비우기
				$('.issuelist tbody').empty();	//발권역 혜택 비우기
				

				setStartDay = new Date(dateText);
				$('input[name="tripLong"]').removeAttr('disabled');
				$('input[name="tripLong"]').prop('checked', false);
				

			},
			minDate : 0, // 이전 날짜 선택불가
			showOn : "button",
			buttonImage : "/starrail/resources/images/course/littlecalendar.PNG",
			buttonImageOnly : true,
			showAnim: "slideDown",
			dateFormat: 'yy-mm-dd',
		});

		// 5일권 7일권 선택
		
		$('.tripLong').click(function(){

			$('#beds-baths-group').empty();	//n일차 버튼 비우기
			$('.departures div.btn-group').empty();	//출발역 목록 비우기
			$('.arrivals div.btn-group').empty();	//도착역 목록 비우기
			$('.trainListTable tbody').empty();	//열차 시간표 비우기
			$('.departTime').empty();	//출발희망시간 비우기
			$('.departTime').append('<option>--------------</option>');	//출발희망시간 디폴트옵션 재추가
			$('.addingBtn .btn-outline-success').prop('disabled', true);	//일정추가버튼 비활성화
			
			var startDay = new Date();
			
			var strArr = $('#datepicker').val().split('-');
			
			startDay.setMonth(Number(strArr[1])-1);
			startDay.setFullYear(strArr[0]);
			startDay.setDate(strArr[2]);
			
			var endDay = new Date();
			
			endDay.setMonth(Number(strArr[1])-1);
			endDay.setFullYear(strArr[0]);
			endDay.setDate(startDay.getDate()+Number($(this).val())-1);
			
			
			var interval = endDay.getTime() - startDay.getTime();
			interval = Math.floor(interval / (1000 *  60 * 60 * 24));
			interval.toString();
		
			for(var i=0; i<=parseInt(interval); i++){
				
				$('#beds-baths-group').append('<label class="btn btn-default beds-baths beds-baths-'+(i+1)+'">'
						+'<input type="radio" name="days" id="option'+(i+1)+'" autocomplete="off" value="' + (startDay.getFullYear() + '/'+ (startDay.getMonth()+1) + '/' + startDay.getDate()) + '">'
						+'<span class="icon icon-blank-space"></span><span class="beds-baths-word">'
						+(i+1)+'일차</span></label><span class="beds-baths-clearfix"></span>');
				startDay.setDate(startDay.getDate() + 1);
			}
			
		});

		
		// n일차 선택 ----> 출발역 목록 불러오기
		$(document).on("click",".beds-baths",function(){
			$('.departures div.btn-group').empty();	//출발역 목록 비우기
			$('.arrivals div.btn-group').empty();	//도착역 목록 비우기
			$('.trainListTable tbody').empty();	//열차 시간표 비우기
			$('.departTime').empty();	//출발희망시간 비우기
			$('.departTime').append('<option>--------------</option>');	//출발희망시간 디폴트옵션 재추가
			$('.addingBtn .btn-outline-success').prop('disabled', true);	//일정추가버튼 비활성화
			
			
			$.ajax({
				type:'post',
				url:'/starrail/course/depList',
				headers: {"Content-type":"application/text"},
				
			
				success:function(result){
					
					$('.departures div.btn-group').empty();
					$.each(result, function(key, value){
						
						$('.departures div.btn-group').append('<label class="btn btn-default">'
								+'<input type="radio" name="depStation" value="'+value.id +'">'
								+'<span>'+value.name+'</span></label>');
						})
				}
			})
		});
		
		
		
		// 출발역 선택 -----> 도착역 목록 불러오기
		$(document).on("click",'.departures div.btn-group label.btn input[type="radio"]',function() {
			
			$('.arrivals div.btn-group').empty();	//도착역 목록 비우기
			$('.trainListTable tbody').empty();	//열차 시간표 비우기
			$('.departTime').empty();	//출발희망시간 비우기
			$('.departTime').append('<option>--------------</option>');	//출발희망시간 디폴트옵션 재추가
			$('.addingBtn .btn-outline-success').prop('disabled', true);	//일정추가버튼 비활성화
			
			var selectedDep = $(this).parent().find('span').text();
			
			$.ajax({
				type : 'post',
				url : '/starrail/course/arrList',
				dataType : 'json',
				data : {
					"depNode": $(this).val(),
					"depDate": $('input:radio[name=days]:checked').val()
				},
				contenttype : "application/json; charset=utf-8",
			
				success:function(result){
					
					if(result==''){
						alert('선택한 날짜에 '+selectedDep+'역에서 운행하는 노선이 없습니다.');
					} else {
						$.each(result, function(key, value){
							
							$('.arrivals div.btn-group').append('<label class="btn btn-default">'
									+'<input type="radio" name="arrStation" value="'+value.id +'">'
									+'<span>'+value.name+'</span></label>');
							})
					}
				}
			})
		});
		
		
		//도착역 선택----->출발 희망시간 출력
		$(document).on("click", '.arrivals div.btn-group label.btn input[type="radio"]', function(){
			
			$('.trainListTable tbody').empty();	//열차 시간표 비우기
			$('.departTime').empty();	//출발희망시간 비우기
			$('.departTime').append('<option>--------------</option>');	//출발희망시간 디폴트옵션 재추가
			$('.addingBtn .btn-outline-success').prop('disabled',true);	//일정추가버튼 비활성화
			
			var selectedDate = $('input:radio[name=days]:checked').val();
			selectedDate = selectedDate.replace(/\//g,"");
			
			var current = new Date();
			var currDate = current.getFullYear() + ''+(current.getMonth() + 1) + ''+current.getDate();
			
			var startTime = 0;
			
			if(selectedDate==currDate){
				startTime = current.getHours() +1;
			}
			
			for(var i=startTime; i<=24; i++){
				var valNum = '';
				if(i<10){ valNum = '0'+ i; }
				else { valNum = ''+ i; }
				
				$('.departTime').append('<option value="'+ i +'">'+ valNum +':00</option>');
			}


		});
		
		
		//출발 희망 시각 선택 ------> 시간표 출력
		$('.departTime').change(function(){
			
			
			$('.trainListTable tbody').empty();	//열차 시간표 비우기
			$('.addingBtn .btn-outline-success').prop('disabled', true);	//일정추가버튼 비활성화
			
			
			$.ajax({
				type : 'post',
				url : '/starrail/course/trainTime',
				dataType : 'json',
				data : {
					'depNode': $('input:radio[name="depStation"]:checked').val(),	//출발역ID
					'depDate': $('input:radio[name=days]:checked').val(),	//출발일
					'arrNode': $('input:radio[name="arrStation"]:checked').val(),	//도착역ID
					'selectedTime': $('.departTime option:selected').val()
				},
				contenttype : "application/json; charset=utf-8",
			
				success:function(result){
					
					if(result==''){
						alert('선택한 시간에 운행하는 노선이 없습니다.');
					} else {
						$.each(result, function(key, value){
							
							$('.trainListTable tbody').append('<tr>'+'<td>'+ value.trainType +'</td>'
																	+'<td class="SelDepTime">'+ value.depTime+'</td>'
																	+'<td><input type="radio" name="selectedTrain" value="'+value.arrTime+'"></td>'
																	+'</tr>');
						})
					}
				}
			})
			
		});
		
		
		//탑승 열차 선택 -----> 일정 추가 버튼 활성화
		$(document).on('click', 'input:radio[name="selectedTrain"]', function(){
			$('.addingBtn .btn-outline-success').removeAttr('disabled');
			
		});
		
		
		//일정 추가 버튼 클릭 -----> 일정 세부에 추가 / 발권역 혜택 출력
		$('button.addBtn').click(function(){
			
			$('#allSavingBtn .saveBtn').removeAttr('disabled');	//전체 저장 버튼 활성화
			
			var selectedDate = $('input:radio[name=days]:checked').val();	//선택한 날짜(n일차)
			selectedDate = selectedDate.replace(/\//g,"-");	//yyyy-MM-dd 형태로
			var selectedDep =$('input:radio[name="depStation"]:checked').parent().find('span').text();	//선택한 출발역
			var selectedArr = $('input:radio[name="arrStation"]:checked').parent().find('span').text();	//선택한 도착역
			var selectedDepTime = $('input:radio[name="selectedTrain"]:checked').parent().prev().text();	//선택한 열차의 출발시간
			var selectedArrTime = $('input:radio[name="selectedTrain"]:checked').val();	//선택한 열차의 도착시간
			
			//일정 세부에 추가
			if($('h4[sDate="'+ selectedDate +'"]').length<=0){	//해당 날짜 첫 일정인지 확인
				$('#couresDetailView .uls')	//첫 일정인 경우 날짜 출력, ul 태그 생성
				.append('<h4 class="date" sDate="'+selectedDate+'">'+selectedDate+'</h4><ul name="'+ selectedDate +'"></ul>');
			}
			//ul 태그 자식으로 li 추가
			$('ul[name="'+ selectedDate +'"]')
			.append('<li sDep="'+selectedDep+'" sArr="'+ selectedArr +'">'+selectedDep+'('+selectedDepTime+') -->'+ selectedArr +'('+ selectedArrTime +')&emsp;&emsp;'
					+'<span class="delSchedule"><img src="/starrail/resources/images/course/x.png"></span>'
					+'<span class="coureDetail">'+selectedDep+'#'+selectedDate.replace(/-/g,"") +selectedDepTime.replace(/:/g,"")+'#'+selectedArr +'#'+selectedDate.replace(/-/g,"") +selectedArrTime.replace(/:/g,"")
					+'</span></li>');
			
			
			//발권역 혜택 불러오기
			$.ajax({
				type : 'post',
				url : '/starrail/course/issuelist',
				dataType : 'json',
				data : {
					"selectedDep" : selectedDep,
					"selectedArr" : selectedArr
				},
				contenttype : "application/json; charset=utf-8",
			
				success:function(result){
				
					
					$.each(result, function(key, value){
						if(value!=null){
							if($('.issuelist tbody tr td input:radio[value="'+value.i_name+'"]').length<=0){
								$('.issuelist tbody').append('<tr>'
										+'<td><center><input type="radio" name="selectedIssue" value="'+ value.i_name +'"></center></td>'
										+'<td>'+ value.i_name +'</td>'
										+'<td>'+ value.i_food +'</td>'
										+'<td>'+ value.i_stay +'</td>'
										+'<td>'+ value.i_tour +'</td>'
										+'<td>'+ value.i_etc +'</td>'
										+'</tr>');
							}
						}
						
						
					})
						
					
				}
			})
		});
		
		//일정 일부 삭제 (x 버튼 클릭 ----> 삭제)
		$(document).on('click', 'span[class="delSchedule"]', function(){
			
			var thisDate = $(this).parent().parent().attr('name');
			var dep = $(this).parent().attr('sDep');
			var arr = $(this).parent().attr('sArr');
			
			if($('ul[name="'+ thisDate +'"] > li').length<=1){
				$('ul[name="'+ thisDate +'"]').prev().remove();
				$('ul[name="'+ thisDate +'"]').remove();
				$('#allSavingBtn .saveBtn').prop('disabled',true);	//전체 저장 버튼 비활성화
				
			} else {
				$(this).parent().remove();
			}
			
			if($('li[sDep="'+dep+'"]').length<=0 && $('li[sArr="'+dep+'"]').length<=0 ){
				$('.issuelist tbody tr td input:radio[value="'+dep+'"]').parent().parent().parent().remove();
			}
			
			if($('li[sDep="'+arr+'"]').length<=0 && $('li[sArr="'+arr+'"]').length<=0){
				$('.issuelist tbody tr td input:radio[value="'+arr+'"]').parent().parent().parent().remove();
			}
			
		});


		//전체코스 저장 버튼 클릭 ------> 일정 세부에 있는 내용으로 코스저장
		$('#allSavingBtn .saveBtn').click(function(){
			var m_id = 'yuryna';	//추후 세션에서 받아오기로..
			var i_name = $('input:radio[name="selectedIssue"]:checked').val();
			var c_name = '코스이름';	//추후 input text로 수정
			
			var details = $('span[class="coureDetail"]').map(function() {
				return $(this).text();
			}).get();
			
			$.ajax({
				type : 'post',
				url : '/starrail/course/insertCourse',
				dataType : 'text',
				data : {
					'm_id' : m_id,
					'i_name' : i_name,
					'c_name' : c_name,
					'details' : JSON.stringify(details)
				},
				contenttype : "application/json; charset=utf-8",
			
				success:function(result){
					alert('코스를 저장했습니다.');
				}
				
			})
		});
		

		
		function dateCheck(){
			if(tripDateStart == null){
				alert("여행 날짜를 선택하세요");
				return false;
				
			}
			else{
				return true;
			}
		}
});


