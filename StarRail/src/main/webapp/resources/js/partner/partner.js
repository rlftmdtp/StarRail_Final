$(function() {

	/* Prev, Next 버튼 이벤트 */
	$(document).ready(function() {
		$('#media').carousel({
			pause : true,
			interval : false,
		});
	});

	
	
	/* 썸네일 이미지 클릭시 대표 이미지 바뀜 + ajax로 일정 가져오고 버튼 생성 시작 */
	var dataIndex = null;
	var cd_id = null;
	var c_id = null;
	var cd_start = null;
	var cd_stime = null;
	var cd_end = null;
	var cd_etime = null;
	
	$("#thumnail a").click(
			function(e) {
				e.preventDefault();

				var largeImgPath = $(this).attr("href");
				var largeImgName = $(this).attr("data-name");
				var $c_id = $(this).attr("data");	


				// 대표 이미지를 바꾸고 코스 이름을 동적으로 바꿔준다
				$("#largeimg img").attr({
					src : largeImgPath
				});
				$("#courseName").text(largeImgName);
				
				// ajax사용해서 클릭한 썸네일에 해당하는 일정정보를 비동기로 가져오기
				$.ajax({
					url : "/starrail/partner/scheduleSearch",
					type : "post",
					data : {
						c_id : $c_id
					},
					dataType : "json",
					success : function(data) {
						if (data != null) {
							var str = "";
							var input = "";
							$("#scedule-button").empty();
							$("#scheduleTable tbody").empty();
							
							$(data).each(	function(index,	item) {
								/* 나중에 coursedetail정보 쓰기 위함 */
								dataIndex = index + 1;
								cd_id = this.cd_id;
								c_id = this.c_id;
								cd_start = this.cd_start;
								cd_stime = this.cd_stime;
								cd_end = this.cd_end;
								cd_etime = this.cd_etime;
								
								
								/* <button class="btn btn-danger">Danger</button>*/
								schedule = "<button  data-index = '"+dataIndex+"'class='btn btn-danger' value='' name='off' id='sDetail"+ dataIndex
												+ "' data='"
												+ this.cd_id
												+ "'>"
												+ (index + 1)
												+ "일차</button>&nbsp;&nbsp;&nbsp;";
								
								scheduleDetail = "<tr><td>"
														+ dataIndex
														+ "일차</td><td>"
														+ cd_start
														+ "</td><td>"
														+ cd_end
														+ "</td><td>"
														+ cd_stime.substring(0,	10)
														+ "</td></tr>";
								$(	"#scedule-button")	.append(schedule);
								$("#scheduleTable tbody").append(scheduleDetail);
								})	}
								else {
									alert("실패");
									}
								}
				});						
				
				
				
				var preindex = 0;
				$(document).on("click", ".btn-danger", function() {	
					
					var index = $(this).attr('data-index');
					var $cd_id = $(this).attr('data');
					
					if($(this).attr('name') == 'off'){ /*클릭 시 테이블 색상 변화주기*/
						$("tr:eq("+preindex+")").css({
							'background-color' : '#ffffff'
						});
						$("tr:eq("+index+")").css({
							'background-color' : '#FFEAEA'
						});
						$(this).attr('name','on');
						$("#cd_idInput").attr('name','on');
						preindex = index;
					}
					
				      $.ajax({
				    	  url : "/starrail/partner/partnerSearch",
				    	  type : "post",
				    	  data : {
				    		  cd_id : $cd_id
				    	  },
				    	  dataType : "json",
				    	  success : function(data) {     
				    		  $("#partner-Profile").empty();
				    		  $.each(data, function(index, item) {
				    			  	$.each(item, function (index1, item1) {
				    			  		if(index1=='m_name'){
											//alert(item1);
											var profile = "<div class='col-sm-3' id ='partner-profile-list'>" 
			        					 		+ "<div class='card'>" 
			        				 			+ "<canvas class='header-bg' width='250' height='70' id='header-blur'></canvas>" 
			        				 			+ "<div class='avatar'>" 
			        				 			+ "<img src='/starrail/resources/images2/partner/F.png' alt='' />" 
			        				 			+ "</div>" 
			        				 			+ "<div class='content'>" 
			        				 			+ "<p>" + item1 + "<br>" 
			        				 			+ "More description here</p>" 
			        				 			+ "<p><button type='button' class='btn btn-default' id='partner-list-button'>" 
			        				 			+ "<span class='glyphicon glyphicon-envelope' aria-hidden='true'>쪽지</span></button></p>" 
			        				 			+ "</div>" 
			        				 			+ "</div>" 
			        				 			+ "</div>";
											
											$("#partner-Profile").append(profile);	
				    			  		}
				    			  	})
				    		  })
				    		  
				    		  
					      }   
					 });				      
				});
				
			});	
	/* 썸네일 이미지 클릭시 대표 이미지 바뀜 + ajax로 일정 가져오고 버튼 생성 끝 */
});