$(function() {
	
	// 데이트피커
	$('#datepicker_expense').datepicker({
		autoclose : true,
		format : "yyyy-mm-dd",
		startDate : "now",
		onSelect : function(dateText, inst) {
			alert("Working");
		}
	});
	
	var c_id;
	var areaCode; // 지역검색 코드
	var fetivalDate; // 축제 검색 날짜 기준
	var sort = '4'; // 지도 종류

	var foodTable = $('#foodTable');
	var stayTable = $('#stayTable');
	var tourTable = $('#tourTable');

	// 맛집
	var infoFoodList = []; // 맛집마커에 띄울 정보창들
	var markerFoodList = []; // 맛집마커들
	var foods = []; // 맛집 정보

	// 숙박
	var infoStayList = []; // 숙박마커에 띄울 정보창들
	var markerStayList = [];
	var stays = []; // 숙박 정보

	// 관광지
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

	// 지도 줌 제한 하기
	map.setOptions({
		minZoom : 6,
		maxZoom : 12
	});

	// 가게 정보창
	var infoWindow = new naver.maps.InfoWindow({
		anchorSkew : true
	});
	// --------------------------------------------

	// 코스를 누르면 코스 디테일의 역들이 로드된다.
	$('.course')
			.on(
					'click',
					'.courseButton',
					function() {
						c_id = $(this).val();

						$('#stationButtons').empty();

						$
								.getJSON(
										"/starrail/maprest/coursedetail/"
												+ c_id,
										function(data) {

											$(data)
													.each(
															function() {
																$(
																		'#stationButtons')
																		.append(
																				'<button type="submit" value="'
																						+ this.cd_start
																						+ '" class="btn btn-default stationButton" data-time="'
																						+ this.cd_stime
																						+ '">'
																						+ this.cd_start
																						+ '</button>');
															})
											/*
											 * for(var i=0; i<stations.length;
											 * i++){
											 * $('#stationButtons').append('<button
											 * type="submit" value="'
											 * +stations[i] +'" class="btn
											 * btn-default stationButton">' +
											 * stations[i] +'</button>'); }
											 */
										})
					});

	// 역 버튼을 누르면 해당 지역의 관광지 정보가 로드된다
	$('.station').on('click', '.stationButton', function() {

		var station = $(this).val();
		fetivalDate = $(this).attr('data-time');

		if (station.match('서울')) {
			areaCode = '1';
		} else if (station.match('대전')) {
			areaCode = '3';
		} else if (station.match('대구')) {
			areaCode = '4';
		} else if (station.match('광주')) {
			areaCode = '5';
		} else if (station.match('부산')) {
			areaCode = '6';
		}

		// 지도 API가 누른 역으로 뜨게 만든다.
		// 데이터베이스와 통신하여 그 역의 좌표를 가져온다
		$.getJSON("/starrail/maprest/stationXY/" + station, function(coord) {

			location = new naver.maps.LatLng(coord.s_x, coord.s_y);
			map.panTo(location); // 그 역으로 이동

			// 역 중심으로 정보를 가져오는 함수들.
			// getJson(비동기식)을 이용하였기 떄문에 해당 함수가 종료가 되기전에 아래의 resetMarker() 함수가 먼저
			// 실행되었다
			// 그래서 콜백함수로 해결을 했다.
			getFestival(areaCode, '1', fetivalDate); // 지역코드. 페이지번호, 날짜

			getFood(areaCode, updateMarkers);
			getFoodTable(areaCode, '1');
			getStay(areaCode, updateMarkers);
			//getStayTable(areaCode, '1');
			getTour(areaCode,updateMarkers);
			//getTourTable(areaCode, '1');
		})
	});

	// 차트 순위 테이블에 마우스를 클릭했을 때
	$(document).on('click', '.chartTr', function() {
		var chartSort = $(this).parent().attr('id'); // 어떤 종류의 차트인가
		var contentid = this.getAttribute("id");
		var markerNumber = $(this).attr('data-markerFoodList');
		alert("markerNumber " + markerNumber);

		switch (chartSort) {
		case 'foodTable':
			foodDetail(contentid,markerNumber);
			foodmarkerZoomOut();
			foodmarkerZoomIn(markerNumber);
			break;
		case 'stayTable':
			stayDetail(contentid,markerNumber);
			staymarkerZoomOut();
			staymarkerZoomIn(markerNumber);
			break;
		case 'tourTable':
			tourDetail(contentid,markerNumber);
			tourmarkerZoomOut();
			tourmarkerZoomIn(markerNumber);
			break;
		}
	})
	/*
	 * $(document).on('mouseout', '.chartTr', function() { var seq =
	 * this.getAttribute("id"); seq = Number(seq); // String에서 Number형태로 변환
	 * 
	 * var marker = marker.get()
	 * 
	 * marker.setIcon({ url : "/starrail/resources/images/map/food32.png" })
	 * 
	 * infoWindow.close(map, marker); // infoRadius.open(map,location); })
	 */

	// 축제 정보이벤트 모달 띄우기
	$('.festivalimg')
			.on('click',function() {
						// 사진에 존재하는 컨텐츠 아이디를 가져와서 해당 축제의 자세한 정보를 가져와야 한다.... ㅅㅂ
						var contentid = $(this).attr('data-contentid');
						$('#myModalLabel').empty();
						$('.modal-body').empty();

						$.getJSON("/starrail/maprest/festivaldetail/"+ contentid,
										function(data) {
											$(data.festivaldetailList).each(function() {
																$('#myModalLabel').append(
																				'<p>'
																						+ this.title
																						+ '</p>');
																$('.modal-body')
																		.append(
																				'<div class="row">')
																$('.modal-body')
																		.append(
																				'<div class="col-md-6">')
																$('.modal-body')
																		.append(
																				'<img src="'
																						+ this.firstimage
																						+ '" height=50% width="50%" />')
																$('.modal-body')
																		.append(
																				'</div>')
																$('.modal-body')
																		.append(
																				'<div class="col-md-6">')
																$('.modal-body')
																		.append(
																				'<div> 주소 : '
																						+ this.addr1
																						+ "</div>")
																$('.modal-body')
																		.append(
																				'<div> 전화명 : '
																						+ this.telname
																						+ "</div>")
																$('.modal-body')
																		.append(
																				'<div> 전화번호 : '
																						+ this.tel
																						+ "</div>")
																$('.modal-body')
																		.append(
																				'<div> 홈페이지 : '
																						+ this.homepage
																						+ "</div>")
																$('.modal-body')
																		.append(
																				'<div> 소개 : '
																						+ this.overview
																						+ "</div>")
																$('.modal-body')
																		.append(
																				'</div>')
																$('.modal-body')
																		.append(
																				'</div>')
															})
										})

						$("#myModal").modal();
					});

	// ---페이지 처리관련 모음 ---
	$("#pagefestival").on("click", "li a", function(event) {

		event.preventDefault();

		replyPage = $(this).attr("href");

		getFestival(areaCode, replyPage, fetivalDate);
	});
	$("#foodtab").on("click",function(){
		event.preventDefault();
		
		getFoodTable(areaCode, '1');
	})
	$("#staytab").on("click",function(){
		event.preventDefault();
		
		getStayTable(areaCode, '1');
	})
	$("#tourtab").on("click",function(){
		event.preventDefault();
		
		getTourTable(areaCode, '1');
	})
	$("#pagefood").on("click", "li a", function(event) {

		event.preventDefault();

		replyPage = $(this).attr("href");

		getFoodTable(areaCode, replyPage);
	});
	$("#pagestay").on("click", "li a", function(event) {

		event.preventDefault();

		replyPage = $(this).attr("href");

		getStayTable(areaCode, replyPage);
	});
	$("#pagetour").on("click", "li a", function(event) {

		event.preventDefault();

		replyPage = $(this).attr("href");

		getTourTable(areaCode, replyPage);
	});
	// ------
	
	$('#sortButtons').on('click', '.sortButton', function() {
		sort = $(this).attr('data-sort');
		updateMarkers(sort);
	})
	
	$('#saveButtons').on('click','#saveButton',function(){
		var number = $(this).attr('data-number');
		var sort = $(this).attr('data-sort');
		alert("데이터베이스에 위치를 저장시키는 함수 발동 ! number " +number+ " sort " + sort );
		savelocation(number,sort);
	})

	// --- 각종 함수들 ---
	function getFestival(areaCode, pageNo, date) {
		$('.festivalimg').empty();

		alert(date);
		$.getJSON("/starrail/maprest/festival/" + areaCode + "/" + pageNo + "/"
				+ date, function(data) {
			$(data.festivalList).each(
					function(number) {
						$('#festivalimg' + number).append(
								'<img src="' + this.firstimage
										+ '"  width="100%" height="100%" />');
						$('#festivalimg' + number).attr('data-contentid',
								this.contentid);
					})
			printPaging(data.pageMaker, '1');
		})
	}

	function getFood(areaCode, updateMarkers) {

		$.getJSON("/starrail/maprest/food/" + areaCode, function(data) {

			// 초기화 작
			infoFoodList = []; // 맛집 마커에 띄울 정보창들
			markerFoodList = []; // 맛집 마커들
			foods = []; // 맛집 정보

			// -------------------- 맛집 -----------------
			$(data.foodList).each(function(number) {
				/*
				 * // 검색 API에서 얻은 좌표는 TM128(카텍좌표계) 이므로 지도 API에서 사용하기 위해서는
				 * LatLng좌표로 변경해야 한다. // number객체를 => naver.maps.Point객체로 변경 후 =>
				 * fromTM128ToLatLng(naver.maps.Point객체)로 이용한다 var tm128= new
				 * naver.maps.Point(this.mapx,this.mapy); var latlng =
				 * naver.maps.TransCoord.fromTM128ToLatLng(tm128);
				 */
				var latlng = new naver.maps.LatLng(this.mapy, this.mapx);
				// foodLatlngs.push(latlng);
				
				// Info 정보창생성
				var contentString = [
				'<div>',
				'   <h3> 음식점 명:' + this.title + '</h3>',
				'	<div class="iw_inner"><img src="'+this.firstimage+'" width=100% height=100%;/></div>',
				'   <p> 주소지: ' + this.addr1 + '</p><br />',
				, '</div>' ]
				.join('') // join함수는배열을문자열로바꾼다.
				infoFoodList.push(contentString);
				
				// 해당 정보생성
				var information = new Information(this.title, this.tel, this.addr1, this.contentid, this.contenttypeid, this.firstimage, this.mapx, this.mapy )
				foods.push(information);
				
				// 마커정보생성
				var icon = {
					url : "/starrail/resources/images/map/food24.png"
				}

				var marker = new naver.maps.Marker({
					position : latlng,
					map : map,
					icon : icon
				});
				
				marker.set('seq',number);
				marker.set('contentid', this.contentid); // ecma6
				marker.set('sort', '1'); // ecma6

				markerFoodList.push(marker);

				marker.addListener('mouseover', onMouseOver);
				marker.addListener('mouseout', onMouseOut);
			})
			updateMarkers('4');
		});
	}
	function getFoodTable(areaCode, pageNo) {
		foodTable.empty();
		$
				.getJSON(
						"/starrail/maprest/foodtable/" + areaCode + "/"
								+ pageNo,
						function(data) {
							$(data.foodList)
									.each(
											function(number) {
												foodTable
														.append('<tr id="'
																+ this.contentid
																+ '" class="chartTr" data-markerFoodList="'
																+ number
																+ '"> <th scope="row">'
																+ number
																+ '</th>'
																+ '<td>'
																+ this.title
																+ '</td></tr>'); // 차트
												// 목록에
												// 추가
											})
							printPaging(data.pageMaker, '2');
						})
	}
	// 해당 음식점의 자세한 정보를 가져온다.
	function foodDetail(contentid,markerNumber) {
		/*
		 * var marker = markerFoodList[seq]; // 해당 마커 위치로 이동하고 정보창을 띄운다.
		 * map.panTo(marker.getPosition()); marker.setIcon({ url :
		 * "/starrail/resources/images/map/food32.png" }) // 1.정보창의 내용을 바꾸고 지도에
		 * 정보창을 띄운다. infoWindow.setContent(infoFoodList[seq]);
		 * infoWindow.open(map, marker); // 2.지도 하단오른쪽에 블로그 정보로드. //var title =
		 * foods[seq].title; //info2.empty();
		 */
		$('.swiper-slide').empty();
		$('.info2detail').empty();
		$('#saveButtons').empty();
		$
				.getJSON(
						"/starrail/maprest/fooddetail/" + contentid,
						function(data) {
							alert("음식 디테일 정보가 들어왔습니다");

							$(data.foodDetailVO).each(
									function() {
										$('.info2detail').append(
												'<p>대표메뉴 :' + this.firstmenu
														+ '<br />'
														+ '<p>취급메뉴 :'
														+ this.treatmenu
														+ '<br />' + '<p>연락처 :'
														+ this.infocenterfood
														+ '<br />'
														+ '<p>영업시간 :'
														+ this.opentimefood
														+ '<br />' + '<p>쉬는날 :'
														+ this.restdatefood
														+ '<br />');
										
										$('#saveButtons').append('<button type="submit" class="btn btn-default" id="saveButton" data-number="'+markerNumber+'" data-sort="1">맛집 저장하기</button>');

									});

							$(data.foodImageList)
									.each(
											function(number) {
												$('#foodimg' + number)
														.append(
																'<img src="'
																		+ this.smallimageurl
																		+ '" width="100%" class="img-thumbnail"/>');
											});
						});
	}

	function getStay(areaCode, updateMarkers) {

		$.getJSON("/starrail/maprest/stay/" + areaCode, function(data) {

			// 초기화 작업
			infoStayList = []; // 맛집 마커에 띄울 정보창들
			markerStayList = []; // 맛집 마커들
			stays = []; // 맛집 정보

			// -------------------- 숙소 -----------------
			$(data.stayList).each(function(number) {

				var latlng = new naver.maps.LatLng(this.mapy, this.mapx);
				
				// Info 정보창생성
				var contentString = [
				'<div>',
				'   <h3> 숙소 명:' + this.title + '</h3>',
				'	<div class="iw_inner"><img src="'+this.firstimage+'" width=100% height=100%;/></div>',
				'   <p> 주소지: ' + this.addr1 + '</p><br />',
				, '</div>' ]
				.join('') // join함수는배열을문자열로바꾼다.
				infoStayList.push(contentString);
				
				// 해당정보생성
				var information = new Information(this.title, this.tel, this.addr1, this.contentid, this.contenttypeid, this.firstimage, this.mapx, this.mapy )
				stays.push(information);
				
				// 마커정보생성
				var icon = {
					url : "/starrail/resources/images/map/stay24.png"
				}

				var marker = new naver.maps.Marker({
					position : latlng,
					map : map,
					icon : icon
				});
				
				marker.set('seq', number);
				marker.set('contentid', this.contentid); // ecma6
				marker.set('sort', '2'); // ecma6

				markerStayList.push(marker);

				marker.addListener('mouseover', onMouseOver);
				marker.addListener('mouseout', onMouseOut);
			})
			updateMarkers('4');
		});
	}
	function getStayTable(areaCode, pageNo) {
		stayTable.empty();
		$
				.getJSON(
						"/starrail/maprest/staytable/" + areaCode + "/"
								+ pageNo,
						function(data) {
							$(data.stayList)
									.each(
											function(number) {
												stayTable
														.append('<tr id="'
																+ this.contentid
																+ '" class="chartTr" data-markerFoodList="'
																+ number
																+ '"> <th scope="row">'
																+ number
																+ '</th>'
																+ '<td>'
																+ this.title
																+ '</td></tr>'); // 차트
												// 목록에
												// 추가
											})
							printPaging(data.pageMaker, '3');
						})
	}
	// 해당 숙소의 자세한 정보를 가져온다.
	function stayDetail(contentid,markerNumber) {

		$('.swiper-slide').empty();
		$('.info2detail').empty();
		$('#saveButtons').empty();
		
		$
				.getJSON(
						"/starrail/maprest/staydetail/" + contentid,
						function(data) {
							alert("숙소 디테일 정보가 들어왔습니다");

							$(data.stayDetailVO)
									.each(
											function(number) {
												$('.info2detail')
														.append(
																'<p>체크인 :'
																		+ this.checkintime
																		+ '<br />'
																		+ '<p>체크아웃 :'
																		+ this.checkouttime
																		+ '<br />'
																		+ '<p>연락처 :'
																		+ this.reservationlodging
																		+ '<br />'
																		+ '<p>홈페이지 :'
																		+ this.reservationurl
																		+ '<br />'
																		+ '<p>객실 수 :'
																		+ this.roomcount
																		+ '<br />');
												
												$('#saveButtons').append('<button type="submit" class="btn btn-default" id="saveButton" data-number="'+markerNumber+'" data-sort="2">숙소 저장하기</button>');
											});
							$(data.stayImageList)
									.each(
											function(number) {
												$('#foodimg' + number)
														.append(
																'<img src="'
																		+ this.smallimageurl
																		+ '" width="100%" class="img-thumbnail"/>');
											});
						});
	}

	function getTour(areaCode, updateMarkers) {

		$.getJSON("/starrail/maprest/tour/" + areaCode, function(data) {

			infoTourList = []; // 맛집 마커에 띄울 정보창들
			markerTourList = []; // 맛집 마커들
			tours = []; // 맛집 정보
			// -------------------- 관광지 -----------------
			$(data.tourList).each(function(number) {

				var latlng = new naver.maps.LatLng(this.mapy, this.mapx);
				
				// Info 정보창생성
				var contentString = [
				'<div>',
				'   <h3> 관광지 명:' + this.title + '</h3>',
				'	<div class="iw_inner"><img src="'+this.firstimage+'" width=100% height=100%;/></div>',
				'   <p> 주소지 :' + this.addr1 + '</p><br />',
				, '</div>' ]
				.join('') // join함수는배열을문자열로바꾼다.
				infoTourList.push(contentString);
				
				var information = new Information(this.title, this.tel, this.addr1, this.contentid, this.contenttypeid, this.firstimage, this.mapx, this.mapy )
				tours.push(information);
				
				// 마커정보생성
				var icon = {
					url : "/starrail/resources/images/map/tour24.png"
				}

				var marker = new naver.maps.Marker({
					position : latlng,
					map : map,
					icon : icon
				});
				
				marker.set('seq', number);
				marker.set('contentid', this.contentid); // ecma6
				marker.set('sort', '3'); // ecma6

				markerTourList.push(marker);

				marker.addListener('mouseover', onMouseOver);
				marker.addListener('mouseout', onMouseOut);
			})
			updateMarkers('4');
		});
	}
	function getTourTable(areaCode, pageNo) {
		tourTable.empty();
		$
				.getJSON(
						"/starrail/maprest/tourtable/" + areaCode + "/"
								+ pageNo,
						function(data) {
							$(data.tourList)
									.each(
											function(number) {
												tourTable
														.append('<tr id="'
																+ this.contentid
																+ '" class="chartTr" data-markerFoodList="'
																+ number
																+ '"> <th scope="row">'
																+ number
																+ '</th>'
																+ '<td>'
																+ this.title
																+ '</td></tr>'); // 차트
												// 목록에
												// 추가
											})
							printPaging(data.pageMaker, '4');
						})
	}
	// 해당 숙소의 자세한 정보를 가져온다.
	function tourDetail(contentid,markerNumber) {

		$('.swiper-slide').empty();
		$('.info2detail').empty();
		$('#saveButtons').empty();
		
		$
				.getJSON(
						"/starrail/maprest/tourdetail/" + contentid,
						function(data) {
							alert("관광지 디테일 정보가 들어왔습니다");

							$(data.tourDetailVO).each(
									function(number) {
										$('.info2detail').append(
												'<p>문의 및 안내 :'
														+ this.infocenter
														+ '<br />'
														+ '<p>이용시기 :'
														+ this.useseason
														+ '<br />'
														+ '<p>이용시간 :'
														+ this.usetime);
										
										$('#saveButtons').append('<button type="submit" class="btn btn-default" id="saveButton" data-number="'+markerNumber+'" data-sort="3">관광지 저장하기</button>');
									});
							$(data.tourImageList)
									.each(
											function(number) {
												$('#foodimg' + number)
														.append(
																'<img src="'
																		+ this.smallimageurl
																		+ '" width="100%" class="img-thumbnail"/>');
											});
						});
	}

	function printPaging(pageMaker, sort) {
		// sort 1번 축제 2번 음식 3번 숙소 4번 관광지
		var str = "";

		if (pageMaker.prev) {
			str += "<li><a href='" + (pageMaker.startPage - 1)
					+ "'> << </a></li>";
		}

		for (var i = pageMaker.startPage, len = pageMaker.endPage; i <= len; i++) {
			var strClass = pageMaker.cri.page == i ? 'class=active' : '';
			str += "<li " + strClass + "><a href='" + i + "'>" + i
					+ "</a></li>";
		}

		if (pageMaker.next) {
			str += "<li><a href='" + (pageMaker.endPage + 1)
					+ "'> >> </a></li>";
		}

		switch (sort) {
		case '1':	// 페스티벌 아래의 페이징
			$('#pagefestival').empty();
			$('#pagefestival').append(str);
			break;
		case '2': // 차트 테이블 아래의 페이징
			$('#pagefood').empty();
			$('#pagestay').empty();
			$('#pagetour').empty();
			$('#pagefood').append(str);
			break;
		case '3': // 차트 테이블 아래의 페이징
			$('#pagefood').empty();
			$('#pagestay').empty();
			$('#pagetour').empty();
			$('#pagestay').append(str);
			break;
		case '4': // 차트 테이블 아래의 페이징
			$('#pagefood').empty();
			$('#pagestay').empty();
			$('#pagetour').empty();
			$('#pagetour').append(str);
			break;
		}
	}

	function updateMarkers(number) {

		// 카테고리를 검사한다
		switch (number) {
		case '1':
			resetMarker();
			foodMarker();
			break;
		case '2':
			resetMarker();
			stayMarker();
			break;
		case '3':
			resetMarker();
			tourMarker();
			break;
		case '4':
			resetMarker();
			foodMarker();
			stayMarker();
			tourMarker();
			break;
		}
	}

	function foodMarker() {
		for (var i = 0; i < markerFoodList.length; i++) {
			var marker = markerFoodList[i];
			marker.setMap(map);
		}
	}
	function stayMarker() {
		for (var i = 0; i < markerStayList.length; i++) {
			var marker = markerStayList[i];
			marker.setMap(map);
		}
	}
	function tourMarker() {
		for (var i = 0; i < markerTourList.length; i++) {
			var marker = markerTourList[i];
			marker.setMap(map);
		}
	}
	function resetMarker() {
		for (var i = 0; i < markerFoodList.length; i++) {
			var marker = markerFoodList[i];
			marker.setMap(null);
		}
		for (var i = 0; i < markerStayList.length; i++) {
			var marker = markerStayList[i];
			marker.setMap(null);
		}
		for (var i = 0; i < markerTourList.length; i++) {
			var marker = markerTourList[i];
			marker.setMap(null);
		}
	}

	function foodmarkerZoomIn(markerNumber) {

		// 어떤 마커인지 알아야 한다.
		var marker = markerFoodList[markerNumber];

		map.panTo(marker.getPosition());

		marker.setIcon({
			url : "/starrail/resources/images/map/food32.png"
		})
		infoWindow.setContent(infoFoodList[markerNumber]);
		infoWindow.open(map, marker);
	}
	function foodmarkerZoomOut() {
		for (var i = 0; i < markerFoodList.length; i++) {
			var marker = markerFoodList[i];
			marker.setIcon({
				url : "/starrail/resources/images/map/food24.png"
			})
		}
	}

	// 숙소
	function staymarkerZoomIn(markerNumber) {

		// 어떤 마커인지 알아야 한다.
		var marker = markerStayList[markerNumber];

		map.panTo(marker.getPosition());

		marker.setIcon({
			url : "/starrail/resources/images/map/stay32.png"
		})
		infoWindow.setContent(infoStayList[markerNumber]);
		infoWindow.open(map, marker);
	}
	function staymarkerZoomOut() {
		for (var i = 0; i < markerStayList.length; i++) {
			var marker = markerStayList[i];
			marker.setIcon({
				url : "/starrail/resources/images/map/stay24.png"
			})
		}
	}

	// 관광지
	function tourmarkerZoomIn(markerNumber) {

		// 어떤 마커인지 알아야 한다.
		var marker = markerTourList[markerNumber];

		map.panTo(marker.getPosition());

		marker.setIcon({
			url : "/starrail/resources/images/map/tour32.png"
		})
		infoWindow.setContent(infoTourList[markerNumber]);
		infoWindow.open(map, marker);
	}
	function tourmarkerZoomOut() {
		for (var i = 0; i < markerTourList.length; i++) {
			var marker = markerTourList[i];
			marker.setIcon({
				url : "/starrail/resources/images/map/tour24.png"
			})
		}
	}

	// 마커위에 마우스 이벤트 함수
	function onMouseOver(e) {

		var marker = e.overlay;
		var seq = marker.get('seq');
		var contentid = marker.get('contentid');
		var sort = marker.get('sort');

		// 정보창의 내용을 바꾸고 지도에 정보창을 띄운다 ==> 나중에 네비게이션 API써서 정보를 바꿔야함
		switch (sort) {
		case '1':
			marker.setIcon({
				url : "/starrail/resources/images/map/food32.png"
			})
			infoWindow.setContent(infoFoodList[seq]);
			infoWindow.open(map, marker);
			foodDetail(contentid,seq);
			break;
		case '2':
			marker.setIcon({
				url : "/starrail/resources/images/map/stay32.png"
			})
			infoWindow.setContent(infoStayList[seq]);
			infoWindow.open(map, marker);
			stayDetail(contentid,seq);
			break;
		case '3':
			marker.setIcon({
				url : "/starrail/resources/images/map/tour32.png"
			})
			infoWindow.setContent(infoTourList[seq]);
			infoWindow.open(map, marker);
			tourDetail(contentid,seq);
			break;
		}
	}
	function onMouseOut(e) {
		var marker = e.overlay
		var seq = marker.get('seq');
		var sort = marker.get('sort');

		// 정보창의 내용을 바꾸고 지도에 정보창을 띄운다 ==> 나중에 네비게이션 API써서 정보를 바꿔야함
		switch (sort) {
		case '1':
			marker.setIcon({
				url : "/starrail/resources/images/map/food24.png"
			})
			// infoWindow.setContent(infoFoodList[seq]);
			// infoWindow.open(map, marker);
			break;
		case '2':
			marker.setIcon({
				url : "/starrail/resources/images/map/stay24.png"
			})
			// infoWindow.setContent(infoStayList[seq]);
			// infoWindow.open(map, marker);
			break;
		case '3':
			marker.setIcon({
				url : "/starrail/resources/images/map/tour24.png"
			})
			// infoWindow.setContent(infoTourList[seq]);
			// infoWindow.open(map, marker);
			break;
		}
	}
	
	function savelocation(number,sort){
		
		switch (sort) {
		case '1':
			$.ajax({
	            type : 'post',
	            url : '/starrail/maprest/savalocation/',            // post방식으로 /replies 로 간다 
	            headers : {
	               "Content-Type" : "application/json",
	               "X-HTTP-Method-Override" : "post"
	            },
	            dataType : 'text',
	            data : JSON.stringify({
	               title : foods[number].title,  
	               c_id : c_id,
	               tel : foods[number].tel,
	               addr1 : foods[number].addr1,
	               contentid : foods[number].contentid,
	               contenttypeid : foods[number].contenttypeid,
	               firstimage : foods[number].firstimage,
	               mapx : foods[number].mapx,
	               mapy : foods[number].mapy,
	               sort : sort
	            }),
	            success : function(result){
	               if (result == 'SUCCESS'){
	                  alert("등록 되었습니다.");
	               }
	            },
	            error:function(){
	            	alert("이미 저장되어 있는 장소입니다");
	            }
	         });
			break;
		case '2':
			$.ajax({
	            type : 'post',
	            url : '/starrail/maprest/savalocation/',            // post방식으로 /replies 로 간다 
	            headers : {
	               "Content-Type" : "application/json",
	               "X-HTTP-Method-Override" : "post"
	            },
	            dataType : 'text',
	            data : JSON.stringify({
	               title : stays[number].title,  
	               c_id : c_id,
	               tel : stays[number].tel,
	               addr1 : stays[number].addr1,
	               contentid : stays[number].contentid,
	               contenttypeid : stays[number].contenttypeid,
	               firstimage : stays[number].firstimage,
	               mapx : stays[number].mapx,
	               mapy : stays[number].mapy,
	               sort : sort
	            }),
	            success : function(result){
	               if (result == 'SUCCESS'){
	                  alert("등록 되었습니다.");
	               }
	            },
	            error:function(){
	            	alert("이미 저장되어 있는 장소입니다");
	            }
	         });
			break;
		case '3':
			$.ajax({
	            type : 'post',
	            url : '/starrail/maprest/savalocation/',            // post방식으로 /replies 로 간다 
	            headers : {
	               "Content-Type" : "application/json",
	               "X-HTTP-Method-Override" : "post"
	            },
	            dataType : 'text',
	            data : JSON.stringify({
	               title : tours[number].title,  
	               c_id : c_id,
	               tel : tours[number].tel,
	               addr1 : tours[number].addr1,
	               contentid : tours[number].contentid,
	               contenttypeid : tours[number].contenttypeid,
	               firstimage : tours[number].firstimage,
	               mapx : tours[number].mapx,
	               mapy : tours[number].mapy,
	               sort : sort
	            }),
	            success : function(result){
	               if (result == 'SUCCESS'){
	                  alert("등록 되었습니다.");
	               }
	            },
	            error:function(){
	            	alert("이미 저장되어 있는 장소입니다");
	            }
	         });
			break;

		}
	}
	
	// 정보생성자함수
	function Information(title, tel, addr1, contentid, contenttypeid,
			firstimage, mapx, mapy) {
	this.title = title;
	this.tel = tel;
	this.addr1 = addr1;
	this.contentid = contentid;
	this.contenttypeid = contenttypeid;
	this.firstimage = firstimage;
	this.mapx = mapx;
	this.mapy = mapy;
	// 메서드...?
	}

	// 가게정보 사진
	var swiper = new Swiper('.swiper-container', {
		navigation : {
			nextEl : '.swiper-button-next',
			prevEl : '.swiper-button-prev',
		},
	});

});
