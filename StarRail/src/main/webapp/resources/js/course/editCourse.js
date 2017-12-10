$(function() {
	$('#allSavingBtn .saveBtn').prop('disabled',false);	//수정완료 버튼 활성화
	//기존 데이터 받기
	var c_id=$('#c_id').val();
	var	i_name=$('#i_name').val();
	var c_filename=$('#c_filename').val();
	
	var cd_id = $('span[id="cd_id"]').map(function() {
		return $(this).text();
	}).get();
	
	var cd_start = $('span[id="cd_start"]').map(function() {
		return $(this).text();
	}).get();
	
	var cd_stime = $('span[id="cd_stime"]').map(function() {
		return $(this).text();
	}).get();
	
	var cd_end = $('span[id="cd_end"]').map(function() {
		return $(this).text();
	}).get();
	
	var cd_etime = $('span[id="cd_etime"]').map(function() {
		return $(this).text();
	}).get();
	
	
	//기존 데이터 뷰에 뿌려주기
	$('#start-date').val(cd_stime[0].substring(0,4)+"-"+cd_stime[0].substring(4,6)+"-"+cd_stime[0].substring(6,8));	//여행출발일
	$('input[name="tripLong"]').removeAttr('disabled');	//5,7일권 선택버튼 활성화
	
	for(var i=0; i<cd_id.length; i++){
		var ms_date = cd_stime[i].substring(0,4)+"-"+cd_stime[i].substring(4,6)+"-"+cd_stime[i].substring(6,8);	//yyyy-MM-dd
		var ms_depTime = cd_stime[i].substring(8,10)+":"+cd_stime[i].substring(10);	//HH:mm
		var ms_arrTime = cd_etime[i].substring(8,10)+":"+cd_etime[i].substring(10);
		
		//일정 세부 뿌려주기
		if($('h4[sDate="'+ ms_date +'"]').length<=0){
			$('#couresDetailView .uls')	//첫 일정인 경우 날짜 출력, ul 태그 생성
			.append('<h4 class="date" sDate="'+ms_date+'">'+ms_date+'</h4><ul name="'+ ms_date +'"></ul>');
		}
		
		$('ul[name="'+  ms_date+'"]')
		.append('<li sDep="'+cd_start[i]+'" sArr="'+ cd_end[i] +'">'+cd_start[i]+'('+ms_depTime+') -->'+ cd_end[i] +'('+ ms_arrTime +')&emsp;&emsp;'
				+'<span class="delSchedule"><img src="/starrail/resources/images/course/x.png"></span>'
				+'<span class="coureDetail">'+cd_start[i]+'#'+ms_date.replace(/-/g,"") +ms_depTime.replace(/:/g,"")+'#'+cd_end[i] +'#'+ms_date.replace(/-/g,"") +ms_arrTime.replace(/:/g,"")
				+'</span></li>');
		
//발권역 정보 가져오기
		$.ajax({
			type : 'post',
			url : '/starrail/course/issuelist',
			dataType : 'json',
			data : {
				"selectedDep" : cd_start[i],
				"selectedArr" : cd_end[i]
			},
			contenttype : "application/json; charset=utf-8",
		
			success:function(result){
				
				var depCoor = result.depCoor;
				var arrCoor = result.arrCoor;
				$('li[sDep="'+depCoor.stc_name+'"]').attr('dep_x', depCoor.stc_x);
				$('li[sDep="'+depCoor.stc_name+'"]').attr('dep_y', depCoor.stc_y);
				$('li[sArr="'+arrCoor.stc_name+'"]').attr('arr_x', arrCoor.stc_x);
				$('li[sArr="'+arrCoor.stc_name+'"]').attr('arr_y', arrCoor.stc_y);
				
				$.each(result.issues, function(key, value){
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
				$('.issuelist tbody tr td input:radio[value="'+i_name+'"]').attr('checked',true);	//기존 설정한 발권역에 체크
				
			}
			
		});
		
		
	}
	
	
	//코스디테일 수에 따라 n일차 버튼 생성
	
	var savedStartDay = new Date();
	
	var savedStrArr = $('#start-date').val().split('-');
	
	savedStartDay.setMonth(Number(savedStrArr[1])-1);
	savedStartDay.setFullYear(savedStrArr[0]);
	savedStartDay.setDate(savedStrArr[2]);
	
	var savedEndDay = new Date();
	
	savedEndDay.setMonth(Number(savedStrArr[1])-1);
	savedEndDay.setFullYear(savedStrArr[0]);
	savedEndDay.setDate(savedStartDay.getDate()+cd_id.length -1);
	
	
	var old_interval = savedEndDay.getTime() - savedStartDay.getTime();
	old_interval = Math.floor(old_interval / (1000 *  60 * 60 * 24));
	old_interval.toString();
	$('.tripLong[value="'+cd_id.length+'"]').prop('checked',true);
	
	for(var i=0; i<=parseInt(old_interval); i++){
		var savedMon = (savedStartDay.getMonth()+1);
		if(savedMon <10){savedMon="0"+savedMon;}
		var savedDa = savedStartDay.getDate();
		if(savedDa <10){savedDa = "0"+savedDa;}
		$('#beds-baths-group').append('<label class="nthBtnLabel btn btn-default beds-baths beds-baths-'+(i+1)+'">'
				+'<input type="radio" name="days" id="option'+(i+1)+'" autocomplete="off" value="' + (savedStartDay.getFullYear() + '/'+ savedMon + '/' + savedDa) + '">'
				+'<span class="icon icon-blank-space"></span><span class="beds-baths-word">'
				+(i+1)+'일차</span></label><span class="beds-baths-clearfix"></span>');
		savedStartDay.setDate(savedStartDay.getDate() + 1);
	}
	
	
	//캔버스 설정
	var canvas = document.getElementById("drowMap");
	var ctx = canvas.getContext("2d");
	
	var canvas_bg = new Image();
	canvas_bg.src="/starrail/resources/images/course/map.png";
	

	var savedCanvasBg = new Image();
	savedCanvasBg.src=c_filename;
	
	savedCanvasBg.onload=function(){
		ctx.drawImage(savedCanvasBg,0,0);
	}	//배경이미지 설정

	
	
		var setStartDay;
		
		// 달력: 데이트피커
		$('#datepicker').datepicker({
			
			autoclose: true,
			format: "yyyy-mm-dd",
			maxViewMode: 0,
			altField: ".input-sm",
			//startDate: "now"
				
		}).on('change', function() {
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
			ctx.clearRect(0,0,canvas.width, canvas.height);
			ctx.drawImage(canvas_bg,0,0);	//캔버스 초기화
			
			$('input[name="tripLong"]').removeAttr('disabled');
			$('input[name="tripLong"]').prop('checked', false);
			
		 
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
			
			var strArr = $('#start-date').val().split('-');
			
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
				var mon = (startDay.getMonth()+1);
				if(mon <10){month="0"+month;}
				var da = startDay.getDate();
				if(da <10){da = "0"+da;}
				$('#beds-baths-group').append('<label class="nthBtnLabel btn btn-default beds-baths beds-baths-'+(i+1)+'">'
						+'<input type="radio" name="days" id="option'+(i+1)+'" autocomplete="off" value="' + (startDay.getFullYear() + '/'+ mon + '/' + da) + '">'
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
			var MM = current.getMonth()+1;
			if((current.getMonth()+1) < 10 ){var MM = '0'+ MM;}
			var dd = current.getDate();
			if( current.getDate() < 10 ){var dd='0'+dd;}
			
			var currDate = current.getFullYear() + ''+MM + ''+dd;
			
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
			var tempArr = selectedDate.split("/");
			if(tempArr[1].length<2){tempArr[1]="0"+tempArr[1];}
			if(tempArr[2].length<2){tempArr[2]="0"+tempArr[2];}
			selectedDate = tempArr[0]+"-"+tempArr[1]+"-"+tempArr[2];	//yyyy-MM-dd 형태로
			
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
			
			
			//발권역 혜택 불러오기, 맵에 그림그리기
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
					var depCoor = result.depCoor;
					var arrCoor = result.arrCoor;
					$('li[sDep="'+depCoor.stc_name+'"]').attr('dep_x', depCoor.stc_x);
					$('li[sDep="'+depCoor.stc_name+'"]').attr('dep_y', depCoor.stc_y);
					$('li[sArr="'+arrCoor.stc_name+'"]').attr('arr_x', arrCoor.stc_x);
					$('li[sArr="'+arrCoor.stc_name+'"]').attr('arr_y', arrCoor.stc_y);
					
					
					ctx.beginPath();
					ctx.moveTo(depCoor.stc_x, depCoor.stc_y);
					ctx.lineTo(arrCoor.stc_x, arrCoor.stc_y);
					ctx.lineWidth=2;
					
					var nth=$('input:radio[name=days]:checked').attr('id');
					if(nth=='option1'){
						ctx.strokeStyle="#F85555";
					} else if(nth=='option2'){
						ctx.strokeStyle="#12A9F5";
					}else if(nth=='option3'){
						ctx.strokeStyle="#FCA736";
					}else if(nth=='option4'){
						ctx.strokeStyle="#8181FC";
					}else if(nth=='option5'){
						ctx.strokeStyle="#E6ED1E";
					}else if(nth=='option6'){
						ctx.strokeStyle="#DA44E5";
					}else if(nth=='option7'){
						ctx.strokeStyle="#5DF478";
					}
					
					
					
					ctx.closePath();
					ctx.stroke();
					
					ctx.beginPath();	//그리기 시작
					ctx.font="15pt 고딕";
					
					ctx.fillText(depCoor.stc_name, depCoor.stc_x, depCoor.stc_y);	//출발역 이름 출력
					ctx.arc(depCoor.stc_x, depCoor.stc_y, 3, 0, (Math.PI/180)*360, false);	//출발역 점 찍기
					ctx.fill();	//점 안쪽 색칠
					
					ctx.fillText(arrCoor.stc_name, arrCoor.stc_x, arrCoor.stc_y);	//도착역 이름 출력
					ctx.arc(arrCoor.stc_x, arrCoor.stc_y, 3, 0, (Math.PI/180)*360, false);	//도착역 점 찍기
					ctx.fill();	//점 안쪽 색칠
					ctx.closePath();
					
					$.each(result.issues, function(key, value){
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
			ctx.clearRect(0,0,canvas.width, canvas.height);
			ctx.drawImage(canvas_bg,0,0);
			
			
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
		
			$('#couresDetailView li').each(function(){
				
				
				
				var dep_name = $(this).attr('sDep');
				var dep_x = $(this).attr('dep_x');
				var dep_y = $(this).attr('dep_y');
				
				var arr_name = $(this).attr('sArr');
				var arr_x = $(this).attr('arr_x');
				var arr_y = $(this).attr('arr_y');
				
				
				ctx.beginPath();
				ctx.moveTo(dep_x, dep_y);
				ctx.lineTo(arr_x, arr_y);
				ctx.lineWidth=2;
				
				var dateCheck = $(this).parent().attr('name').replace(/-/g,"/");
			
				if(dateCheck==$('input:radio[id="option1"]').val()){
					ctx.strokeStyle="#F85555";
				} else if(dateCheck==$('input:radio[id="option2"]').val()){
					ctx.strokeStyle="#12A9F5";
				}else if(dateCheck==$('input:radio[id="option3"]').val()){
					ctx.strokeStyle="#FCA736";
				}else if(dateCheck==$('input:radio[id="option4"]').val()){
					ctx.strokeStyle="#8181FC";
				}else if(dateCheck==$('input:radio[id="option5"]').val()){
					ctx.strokeStyle="#E6ED1E";
				}else if(dateCheck==$('input:radio[id="option6"]').val()){
					ctx.strokeStyle="#DA44E5";
				}else if(dateCheck==$('input:radio[id="option7"]').val()){
					ctx.strokeStyle="#5DF478";
				}
				
				
				
				ctx.closePath();
				ctx.stroke();
				
				ctx.beginPath();	//그리기 시작
				ctx.font="15pt 고딕";
				
				ctx.fillText(dep_name, dep_x, dep_y);	//출발역 이름 출력
				ctx.arc(dep_x, dep_y, 3, 0, (Math.PI/180)*360, false);	//출발역 점 찍기
				ctx.fill();	//점 안쪽 색칠
				
				ctx.fillText(arr_name, arr_x, arr_y);	//도착역 이름 출력
				ctx.arc(arr_x, arr_y, 3, 0, (Math.PI/180)*360, false);	//도착역 점 찍기
				ctx.fill();	//점 안쪽 색칠
				ctx.closePath();
				
				
				
			});
			
			//발권역 정보 삭제
			if($('li[sDep="'+dep+'"]').length<=0 && $('li[sArr="'+dep+'"]').length<=0 ){
				$('.issuelist tbody tr td input:radio[value="'+dep+'"]').parent().parent().parent().remove();
			}
			
			if($('li[sDep="'+arr+'"]').length<=0 && $('li[sArr="'+arr+'"]').length<=0){
				$('.issuelist tbody tr td input:radio[value="'+arr+'"]').parent().parent().parent().remove();
			}
			
		});

		//수정완료 버튼 클릭 ------> 코스 업데이트, 기존 코스디테일 삭제, 현재 일정 세부로 코스디테일 재삽입 
		$('#allSavingBtn .saveBtn').click(function(){
			var newI_name = $('input:radio[name="selectedIssue"]:checked').val();
			var newC_name = $('#courseName input:text[name="c_name"]').val();	//추후 input text로 수정
			
			var myImage = document.getElementById('myImage');
			myImage.src = canvas.toDataURL("image/png");
			
			var c_filename = canvas.toDataURL("image/png");
			
			var details = $('span[class="coureDetail"]').map(function() {
				return $(this).text();
			}).get();
			
			$.ajax({
				type : 'post',
				url : '/starrail/course/updateCourse',
				dataType : 'text',
				data : {
					'c_id' : c_id,
					'i_name' : newI_name,
					'c_name' : newC_name,
					'c_filename' : c_filename,
					'details' : JSON.stringify(details)
				},
				contenttype : "application/json; charset=utf-8",
			
				success:function(result){
					alert('코스를 수정했습니다.');
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


