$(function() {

	// --- 지도 초기 설정 ---
	var GREEN_FACTORY = new naver.maps.LatLng(37.3595953, 127.1053971);
	var location = GREEN_FACTORY; // 역을 담을 좌표 변수
	
	// 지도 설정
	var map = new naver.maps.Map('map', {
		center : GREEN_FACTORY,
		zoom : 12
	})
	
	// 반경 범위 설정
	var circle = new naver.maps.Circle({
		map : map,
		center : location,
		radius : 300, // 단위 m
		fillColor : 'crimson',
		fillOpacity : 0.3
	})
	
	// 지도 줌 제한 하기
	map.setOptions({
		minZoom : 10,
		maxZoom : 12
	});
	
	
	// 정보창
	var infoWindow = new naver.maps.InfoWindow({
		anchorSkew : true
	});
	// --------------------------------------------

	// 코스를 누르면 역들이 로드된다.
	$('.course').on('click', '.courseButton', function() {
		var c_id = $(this).val();
		
		alert('누른 코스는 ' + c_id);
		$('#stationButtons').empty();
		
		$.getJSON("/starrail/maprest/coursedetail/" + c_id, function(stations){
			
			for(var i=0; i<stations.length; i++){
				$('#stationButtons').append('<button type="submit" value="' +stations[i] +'" class="btn btn-default stationButton">' + stations[i] +'</button>');
			}
		})
	});

	// 역 버튼을 누르면 해당 지역의 관광지 정보가 로드된다
	$('.station').on('click','.stationButton', function() {

		var station = $(this).val();

		// 지도 API가 누른 역으로 뜨게 만든다.
		// 데이터베이스와 통신하여 그 역의 좌표를 가져온다
		$.getJSON("/starrail/maprest/stationXY/" + station, function(coord) {
			alert("DB에 있는 x좌표 " + coord.s_x);

			location = new naver.maps.LatLng(coord.s_x, coord.s_y);

			// 그 역으로 이동
			map.panTo(location);

			// 역 기준으로 반경 설정
			var standard = location.destinationPoint(0, 50);
			//console.log(standard.toString());
			
			// 영역 만큼 원을 그린다
			circle.setOptions({
				map : map,
				center : location,
				radius : 300, // 단위 m
				fillColor : 'crimson',
				fillOpacity : 0.3
			});
		})
		
		getTourList(station);
		
		/*
		 * ( $.ajax({ type : 'post', url : '/starrail/maprest/getTourList',
		 * dataType : 'text', headers : { "Content-type" : "application/text" },
		 * data : station, success : function(result) { alert(result); } });
		 */

		/*
		 * $('.').click(function(){ // 그래프에 데이터 도출
		 * $.getJSON("/starrail/maprest/datalab/"+station, function(data){
		 * alert("결과과돌아왔습니다"); }); });
		 */
	});
	
		// 반경 변경시 원의 범위 변경
		$('#mapScope').on('keydown', function(e){
			var keyCode = e.which;
			
			if(keyCode === 13){ // 엔터 입력시
				circle.setOptions({
					map : map,
					center : location,
					radius : $('#mapScope').val(),
					fillColor : 'crimson',
					fillOpacity : 0.5
				});
			}
		});
		$('#submit').on('click', function(e){
			e.preventDefault();

			circle.setOptions({
				map : map,
				center : location,
				radius : $('#mapScope').val(),
				fillColor : 'crimson',
				fillOpacity : 0.1
			});
		})
		
		//--------------- 함수들 ---------------
		function getTourList(station){ // 반경까지 전달해야하나 ...?
			$.getJSON("/starrail/maprest/tourlist/" + station, function(data) {
				
				var tbody = $('#foodList'); 
				tbody.empty();
				
				$(data.foodList).each(function(number){
					tbody.append('<tr> <th scope="row">'+(number+1)+ '</th> <td>' + this.title + '</td> </tr> <tr>')
				})
			});
		}
});
