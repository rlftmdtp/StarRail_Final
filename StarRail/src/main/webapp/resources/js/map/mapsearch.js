$(function() {
	
	var sort = '4';
	
	var foodTable = $('#foodTable');
	var stayTable = $('#stayTable');
	var tourTable = $('#tourTable');
	var blogReply = $('#replies');
	var info2 = $('.info2');
	
	// 맛집
	var foodLatlngs = []; // 맛집 좌표들
	var infoFoodList = [];  // 맛집마커에 띄울 정보창들
	var markerFoodList = []; // 맛집마커들
	var foods = []; // 맛집 정보
	
	// 숙박
	var stayLatlngs = []; // 숙박 좌표들
	var infoStayList = []; // 숙박마커에 띄울 정보창들
	var markerStayList = [];
	var stays = []; // 숙박 정보
	
	// 관광지
	var tourLatlngs = []; // 관광지 좌표들
	var infoTourList = []; // 관광지마커에 띄울 정보창들
	var markerTourList = [];
	var tours = []; // 관광지 정보
	
	// --------------------- 지도 초기 설정 -------------------------------
	var GREEN_FACTORY = new naver.maps.LatLng(37.3595953, 127.1053971);
	var location = GREEN_FACTORY; // 역을 담을 좌표 변수
	
	// 지도 설정
	var map = new naver.maps.Map('map', {
		center : GREEN_FACTORY,
		zoom : 12
	})
	
	// 반경 원 범위 설정
	var circle = new naver.maps.Circle({
		map : map,
		center : location,
		radius : 300, // 단위 m
		fillColor : 'crimson',
		fillOpacity : 0.3
	})
	
	// 반경 길이를 나타내는 직선
	var radiusLine = new naver.maps.Polyline({
		map : map,
		path : [location,location.destinationPoint(0, 300)],
	    strokeWeight: 5
	})
	
	// 지도 줌 제한 하기
	map.setOptions({
		minZoom : 10,
		maxZoom : 12
	});
	
	// 가게 정보창
	var infoWindow = new naver.maps.InfoWindow({
		anchorSkew : true
	});
	
	var infoRadius = new naver.maps.InfoWindow({
		anchorSkew : true,
		content : 300 + 'm'
	});
	infoRadius.open(map,location);
	// --------------------------------------------

	// 코스를 누르면 코스 디텔이의 역들이 로드된다.
	$('.course').on('click', '.courseButton', function() {
		var c_id = $(this).val();
		
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
			map.panTo(location); // 그 역으로 이동
			
			draw(300);	// 범위를 그린다
			// 역 중심으로 정보를 가져오는 함수들.
			// getJson(비동기식)을 이용하였기 떄문에 해당 함수가 종료가 되기전에 아래의 resetMarker() 함수가 먼저 실행되었다
			// 그래서 콜백함수로 해결을 했다.
			getFood(station,updateMarkers);
			getStay(station,updateMarkers);
			getTour(station,updateMarkers);
			
		})
	});
	
		// 반경 변경시 원의 범위 변경
		$('#mapScope').on('keydown', function(e){
			var keyCode = e.which;
			
			if(keyCode === 13){ // 엔터 입력시
				draw($('#mapScope').val());
				updateMarkers(sort);
			}
		});
		$('#submit').on('click', function(e){
			e.preventDefault();
			draw($('#mapScope').val());
			updateMarkers(sort);
		})
		
		// 차트 순위 테이블에 마우스를 올렸을 때
		$(document).on('click','.chartTr',function(){
			var chartSort = $(this).parent().attr('id');
			var seq = this.getAttribute("id");
			seq = Number(seq); // String에서 Number형태로 변환
			
			switch (chartSort) {
			case 'foodTable':
				foodBlog(seq);
				break;
			case 'stayTable':
				stayBlog(seq);
				break;
			case 'tourTable':
				tourBlog(seq);
				break;
			}
		})
		$(document).on('mouseout','.chartTr',function(){
			var seq = this.getAttribute("id");
			seq = Number(seq); // String에서 Number형태로 변환

			var marker = markerFoodList[seq];
			
			marker.setIcon({
				url : "/starrail/resources/images/map/food32.png"
			})
			
			infoWindow.close(map, marker);
			infoRadius.open(map,location);
		})
		
		// 블로그 리뷰 페이지 번호선택 이벤트
		 $(".pagination").on("click", "li a", function(event){
         
         event.preventDefault();
         
         replyPage = $(this).attr("href");
         var title = $(this).attr("id");

         getDataBlog(title,replyPage);
      });
		
		$('#sortButtons').on('click','.sortButton',function(){
			sort = $(this).attr('data-sort');
			updateMarkers(sort);
		})
		
		//--------------------------------------- 함수들 ---------------------------------
		function getFood(station, callback){
			$.getJSON("/starrail/maprest/food/" + station, function(data) {
				
				// 초기화 작업
				foodLatlngs = []; // 맛집 가게 좌표들
				infoFoodList = [];  // 맛집 마커에 띄울 정보창들
				markerFoodList = []; // 맛집 마커들
				foods = []; // 맛집 정보
				
				//-------------------- 맛집 -----------------
				$(data.foodList).each(function(number){
					// 검색 API에서 얻은 좌표는 TM128(카텍좌표계) 이므로 지도 API에서 사용하기 위해서는 LatLng좌표로 변경해야 한다.
					// number객체를 => naver.maps.Point객체로 변경 후 => fromTM128ToLatLng(naver.maps.Point객체)로 이용한다
					var tm128= new naver.maps.Point(this.mapx,this.mapy);
					var latlng = naver.maps.TransCoord.fromTM128ToLatLng(tm128);
					foodLatlngs.push(latlng);
					
					// Info 정보창 생성
					var contentString = [
						 '<div class="iw_inner">',
					        '   <h3>'+this.title+'</h3>',
					        '   <p>' + this.address +'<br />',
					        '       '+ this.category +'<br />',
					        '       '+ this.telephone +'<br />',
					        '       '+ this.description +'<br />',
					        '       <a href="'+ this.link +'" target="_blank">'+ this.link +'</a>',
					        '   </p>',
					        '</div>'
					].join('') // join함수는 배열을 문자열로 바꾼다.
					infoFoodList.push(contentString);
					
					var information = new Information(this.title, this.address, this.category, this.telephone, this.description, this.link, number);
					foods.push(information);
				})
				
				// 마커정보생성
				for(var i=0; i<foodLatlngs.length; i++){
					var icon = {
							url : "/starrail/resources/images/map/food24.png"
					}
					
					var marker = new naver.maps.Marker({
						position : foodLatlngs[i],
						map : map,
						icon : icon
					});
					
					marker.set('seq', i); //ecma6 문법
					marker.set('sort', '1'); //ecma6 문법
					markerFoodList.push(marker);
					
					marker.addListener('mouseover', onMouseOver);
					marker.addListener('mouseout', onMouseOut);
				}
				callback('4');
			});
		}
		
		function getStay(station,updateMarkers){
			$.getJSON("/starrail/maprest/stay/" + station, function(data) {
				
				// 초기화 작업
				stayLatlngs = []; // 숙박 좌표들
				infoStayList = []; // 숙박마커에 띄울 정보창들
				markerStayList = [];
				stays = []; // 숙박 정보
				
				//-------------------- 숙박 -----------------
				$(data.stayList).each(function(number){
					// 검색 API에서 얻은 좌표는 TM128(카텍좌표계) 이므로 지도 API에서 사용하기 위해서는 LatLng좌표로 변경해야 한다.
					// number객체를 => naver.maps.Point객체로 변경 후 => fromTM128ToLatLng(naver.maps.Point객체)로 이용한다
					var tm128= new naver.maps.Point(this.mapx,this.mapy);
					var latlng = naver.maps.TransCoord.fromTM128ToLatLng(tm128);
					stayLatlngs.push(latlng);
					
					// Info 정보창 생성
					var contentString = [
						 '<div class="iw_inner">',
					        '   <h3>'+this.title+'</h3>',
					        '   <p>' + this.address +'<br />',
					        '       '+ this.category +'<br />',
					        '       '+ this.telephone +'<br />',
					        '       '+ this.description +'<br />',
					        '       <a href="'+ this.link +'" target="_blank">'+ this.link +'</a>',
					        '   </p>',
					        '</div>'
					].join('') // join함수는 배열을 문자열로 바꾼다.
					infoStayList.push(contentString);
					
					var information = new Information(this.title, this.address, this.category, this.telephone, this.description, this.link, number);
					stays.push(information);
				})
				
				// 마커정보생성
				for(var i=0; i<stayLatlngs.length; i++){
					var icon = {
							url : "/starrail/resources/images/map/stay24.png"
					}
					
					var marker = new naver.maps.Marker({
						position : stayLatlngs[i],
						map : map,
						icon : icon
					});
					
					marker.set('seq', i); //ecma6 문법
					marker.set('sort', '2'); //ecma6 문법
					markerStayList.push(marker);
					
					marker.addListener('mouseover', onMouseOver);
					marker.addListener('mouseout', onMouseOut);
				}
				updateMarkers('4');
			});
		}
		
		function getTour(station,updateMarkers){
			$.getJSON("/starrail/maprest/tour/" + station, function(data) {
				
				// 초기화 작업
				// 관광지
				tourLatlngs = []; // 관광지 좌표들
				infoTourList = []; // 관광지마커에 띄울 정보창들
				markerTourList = [];
				tours = []; // 관광지 정보
				
				//-------------------- 관광지 -----------------
				$(data.tourList).each(function(number){
					// 검색 API에서 얻은 좌표는 TM128(카텍좌표계) 이므로 지도 API에서 사용하기 위해서는 LatLng좌표로 변경해야 한다.
					// number객체를 => naver.maps.Point객체로 변경 후 => fromTM128ToLatLng(naver.maps.Point객체)로 이용한다
					var tm128= new naver.maps.Point(this.mapx,this.mapy);
					var latlng = naver.maps.TransCoord.fromTM128ToLatLng(tm128);
					tourLatlngs.push(latlng);
					
					// Info 정보창 생성
					var contentString = [
						 '<div class="iw_inner">',
					        '   <h3>'+this.title+'</h3>',
					        '   <p>' + this.address +'<br />',
					        '       '+ this.category +'<br />',
					        '       '+ this.telephone +'<br />',
					        '       '+ this.description +'<br />',
					        '       <a href="'+ this.link +'" target="_blank">'+ this.link +'</a>',
					        '   </p>',
					        '</div>'
					].join('') // join함수는 배열을 문자열로 바꾼다.
					infoTourList.push(contentString);
					
					var information = new Information(this.title, this.address, this.category, this.telephone, this.description, this.link, number);
					tours.push(information);
				})
				
				// 마커정보생성
				for(var i=0; i<tourLatlngs.length; i++){
					var icon = {
							url : "/starrail/resources/images/map/tour24.png"
					}
					
					var marker = new naver.maps.Marker({
						position : tourLatlngs[i],
						map : map,
						icon : icon
					});
					
					marker.set('seq', i); //ecma6 문법
					marker.set('sort', '3'); //ecma6 문법
					markerTourList.push(marker);
					
					marker.addListener('mouseover', onMouseOver);
					marker.addListener('mouseout', onMouseOut);
				}
				updateMarkers('4');
			});
		}
		
		// 순위테이블 해당 블로그 글 가져오는 함수
		function foodBlog(seq){
			var marker = markerFoodList[seq];
			
			marker.setIcon({
				url : "/starrail/resources/images/map/food32.png"
			})
			
			// 1.정보창의 내용을 바꾸고  지도에 정보창을 띄운다.
			infoWindow.setContent(infoFoodList[seq]);
			infoWindow.open(map, marker);
			
			// 2.지도 하단오른쪽에 블로그 정보로드. 
			var title = foods[seq].title;
			info2.empty();
			getDataBlog(title,1);
	
			// 3.제일 하단에 그래프 보여주기
			getDataLab(foods[seq].title);
		}
		
		// 블로그 정보 가져오는 함수.
		function getDataBlog(blogtitle,page){			
			$.getJSON("/starrail/maprest/datablog/"+blogtitle +"/"+ page, function(data){
				
				 var str = "";
				 info2.empty(); // 차트 테이블 초기화
				
				  $(data.blogList).each(function(number){
					  info2.append('<div class="well"><div class="media"><a class="pull-left" href="#"> <img class="media-object" src="http://placekitten.com/150/150"></a><div class="media-body">'
							  + '<h4 class="media-heading"><a href="' + this.bloggerlink +'">' + this.title + '</a></h4>'
							  + '<p class="text-right">' + this.bloggername + '</p>'
							  + '<p>' + this.description + '</p>'
							  + '<ul class="list-inline list-unstyled"><li><span><i class="glyphicon glyphicon-calendar"></i>'
							  + this.postdate + '</span></li>');
					  
					  /*blogReply.append('<div class="blogtitle"> <a href="' + this.bloggerlink + '">' + this.title + '</a></div>'
					  		+ '<div>' + this.description + '</div>' + '<div>' + this.bloggername + '<span>&nbsp;&nbsp;' + this.postdate + '</span></div>');*/
					  //str += "<li data-rno='" + number + "' class='replyLi'>" + this.title +":" + this.description + "</li>";
					  //info2.append('<div>'+this.title+'</div>' + '<div>' + this.description + '</div>');
				  });
				  //info2.append(str);
				  //$('#replies').append(str);
				  printPaging(blogtitle,data.pageMaker);
				  
			});
		}
		function printPaging(blogtitle,pageMaker){
			var str = "";
			
			if(pageMaker.prev){
	            str += "<li><a id='"+blogtitle+"' href='"+(pageMaker.startPage-1)+"'> << </a></li>";
	         }
	         
	         for(var i=pageMaker.startPage, len = pageMaker.endPage; i <= len; i++){            
	               var strClass= pageMaker.cri.page == i?'class=active':'';
	              str += "<li "+strClass+"><a id='"+blogtitle+"' href='"+i+"'>"+i+"</a></li>";
	         }
	         
	         if(pageMaker.next){
	            str += "<li><a id='"+blogtitle+"' href='"+(pageMaker.endPage + 1)+"'> >> </a></li>";
	         }
	         $('.pagination').empty();
	         $('.pagination').append(str);   
		}
		
		// Data lab 정보 가져오는 함수
		function getDataLab(labtitle){
			$.getJSON("/starrail/maprest/datalab/"+labtitle, function(data){
				  
				  //alert('DataLab 결과가 돌아 왓습니다' + data.ratio);
			});
		}
		  
		function draw(radius){
			// 영역 만큼 원과 직선을 그린다
						circle.setOptions({
							map : map,
							center : location,
							radius : radius, // 단위 m
							fillColor : 'crimson',
							fillOpacity : 0.3
						});
						var point = location.destinationPoint(0, radius);
						radiusLine.setOptions({
							path : [location, point]
						})
						infoRadius.setContent(radius + 'm');
						infoRadius.open(map,location);
		}
		
		function updateMarkers(number){
						
			// 카테고리를 검사한다
			switch (number) {
			case '1':
				// 지도에 존재하는 모든 마커들을 한번 다 삭제해줘야 한다.
				resetMarker();
				foodMarker();
				break;
			case '2':
				// 지도에 존재하는 모든 마커들을 한번 다 삭제해줘야 한다.
				resetMarker();
				stayMarker();
				break;
			case '3':
				// 지도에 존재하는 모든 마커들을 한번 다 삭제해줘야 한다.
				resetMarker();
				tourMarker();
				break;
			case '4':
				// 지도에 존재하는 모든 마커들을 한번 다 삭제해줘야 한다.
				resetMarker();
				foodMarker();
				stayMarker();
				tourMarker();
				break;
			}
		}
		
		// -- 위도 경도를 이용한 좌표간의 거리 구하기 공식 함수
		/*
		function distance(circleRadius) {  
			
			for(var i=0; i<markerFoodList.length; i++)
			{
				var marker = markerFoodList[i];
				var position = marker.getPosition();
				
				 var p = 0.017453292519943295;    // Math.PI / 180
				 var c = Math.cos;
				 var a = 0.5 - c((position.lat() - location.lat()) * p)/2 + 
				          c(location.lat() * p) * c(position.lat() * p) * 
				          (1 - c((position.lng() - location.lng()) * p))/2;

				 var distance = 1000 * 12742 * Math.asin(Math.sqrt(a)); // 2 * R; R = 6371 km
			
				 if(distance.toFixed(0) < circleRadius){
					 showMarker(marker);
				 }
				 else{
					 hideMarker(marker);
				 }
			}
			}
			*/
		//------------------------------------------
		function foodMarker(){
			var circleBounds = circle.getBounds();
			// 기존에 있던 차트들을 삭제한다.
			foodTable.empty();
			var rank = 1;
			
			for(var i=0; i < markerFoodList.length; i++){
				var marker = markerFoodList[i];
				var position = marker.getPosition();
				
				if(circleBounds.hasLatLng(position)){
					// 범위 안에 존재하면 chart 생성
					if(rank <= 15){
					foodTable.append('<tr id="' + foods[i].seq + '" class="chartTr"> <th scope="row">' + rank + '</th>' + '<td>' +  foods[i].title  + '</td></tr>'); // 차트 목록에 추가
					rank ++;
					}
					showMarker(marker);
				}
				else{
					hideMarker(marker);
				}
			}
		}
		function stayMarker(){
			var circleBounds = circle.getBounds();
			// 기존에 있던 차트들을 삭제한다.
			stayTable.empty();
			var rank = 1;
			
			for(var i=0; i < markerStayList.length; i++){
				var marker = markerStayList[i];
				var position = marker.getPosition();
				
				if(circleBounds.hasLatLng(position)){
					// 범위 안에 존재하면 chart 생성
					if(rank <= 15){
					stayTable.append('<tr id="' + stays[i].seq + '" class="chartTr"> <th scope="row">' + rank + '</th>' + '<td>' +  stays[i].title  + '</td></tr>'); // 차트 목록에 추가
					rank ++;
					}
					showMarker(marker);
				}
				else{
					hideMarker(marker);
				}
			}
		}
		function tourMarker(){
			var circleBounds = circle.getBounds();
			// 기존에 있던 차트들을 삭제한다.
			tourTable.empty();
			var rank = 1;
			
			for(var i=0; i < markerTourList.length; i++){
				var marker = markerTourList[i];
				var position = marker.getPosition();
				
				if(circleBounds.hasLatLng(position)){
					// 범위 안에 존재하면 chart 생성
					if(rank <= 15){
					tourTable.append('<tr id="' + tours[i].seq + '" class="chartTr"> <th scope="row">' + rank + '</th>' + '<td>' +  tours[i].title  + '</td></tr>'); // 차트 목록에 추가
					rank ++;
					}
					showMarker(marker);
				}
				else{
					hideMarker(marker);
				}
			}
		}
		function showMarker(marker) {
		    if (marker.getMap()) return;
		    marker.setMap(map);
		}

		function hideMarker(marker) {
		    if (!marker.getMap()) return;
		    marker.setMap(null);
		}
		function resetMarker(){
			for(var i=0; i < markerFoodList.length; i++){
				var marker = markerFoodList[i];
				marker.setMap(null);
			}
			for(var i=0; i < markerStayList.length; i++){
				var marker = markerStayList[i];
				marker.setMap(null);
			}
			for(var i=0; i < markerTourList.length; i++){
				var marker = markerTourList[i];
				marker.setMap(null);
			}
		}
		
		// 마커위에 마우스 이벤트 함수
		function onMouseOver(e){

			var marker = e.overlay;
			var seq = marker.get('seq');
			var sort = marker.get('sort');

			// 정보창의 내용을 바꾸고  지도에 정보창을 띄운다 ==> 나중에 네비게이션 API써서 정보를 바꿔야함
			switch (sort) {
			case '1':
				marker.setIcon({
					url : "/starrail/resources/images/map/food32.png"
				})
				infoWindow.setContent(infoFoodList[seq]);
				infoWindow.open(map, marker);
				break;
			case '2':
				marker.setIcon({
					url : "/starrail/resources/images/map/stay32.png"
				})
				infoWindow.setContent(infoStayList[seq]);
				infoWindow.open(map, marker);
				break;
			case '3':
				marker.setIcon({
					url : "/starrail/resources/images/map/tour32.png"
				})
				infoWindow.setContent(infoTourList[seq]);
				infoWindow.open(map, marker);
				break;
			}
		}
		function onMouseOut(e){
			var marker = e.overlay
			var seq = marker.get('seq');
			var sort = marker.get('sort');
			
			// 정보창의 내용을 바꾸고  지도에 정보창을 띄운다 ==> 나중에 네비게이션 API써서 정보를 바꿔야함
			switch (sort) {
			case '1':
				marker.setIcon({
					url : "/starrail/resources/images/map/food32.png"
				})
				infoWindow.setContent(infoFoodList[seq]);
				infoWindow.open(map, marker);
				break;
			case '2':
				marker.setIcon({
					url : "/starrail/resources/images/map/stay32.png"
				})
				infoWindow.setContent(infoStayList[seq]);
				infoWindow.open(map, marker);
				break;
			case '3':
				marker.setIcon({
					url : "/starrail/resources/images/map/tour32.png"
				})
				infoWindow.setContent(infoTourList[seq]);
				infoWindow.open(map, marker);
				break;
			}
		}
		
        // 정보 생성자함수
		function Information(title, address, category, telephone, description, link, number){
			this.title = title;
			this.address = address;
			this.category = category;
			this.telephone = telephone;
			this.description = description;
			this.link = link;
			this.seq = number;
			// 메서드...?
		}
		
		function BlogInfomation(title, description, bloggername, bloggerlink){
			this.title = title;
			this.description = description;
			this.bloggername = bloggername;
			this.bloggerlink = bloggerlink;
		}
		
		//구글 차트
		google.charts.load("current", {packages:["corechart"]});
		google.charts.setOnLoadCallback(drawChart);
		function drawChart() {
		  var data = new google.visualization.DataTable();
		  data.addColumn('string', '남녀성비');
		  data.addColumn('number', 'Percentage');
		  data.addRows([
			  ['남자', 45.0],
			  ['여자', 35.0]
		  ]);

		  var options = {
		    title: '남녀성비',
		  };

		  var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
		  chart.draw(data, options);
		}
		
});


