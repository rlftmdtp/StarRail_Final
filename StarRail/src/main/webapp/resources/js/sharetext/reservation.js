$(function(){
	

	$('.start-pick').datepicker({
		  autoclose: true,
		  format: "yyyy-mm-dd",
		  maxViewMode: 0,
		  startDate: "now"
		}).on('change', function() {
		  
		 
		});



		$('.end-pick').datepicker({
		     autoclose: true,
		     format: "yyyy-mm-dd",
		     maxViewMode: 0,
		     startDate: "now"
		   }).on('change', function() {
		    
		      
		   });


	$('#red_time').change(function(){
		$('#searchtrain tbody').empty('<tr></tr>')
		$.ajax({
			
			type:'post',
			url:'/starrail/reservation/trainTime',
			dataType : 'JSON',
			data:{
				's_station' :$('#red_start option:selected').val(),
				'e_station': $('#red_end option:selected').val(),
				's_date' : $('.red_sdate').val(),
				's_time' : $('#red_time option:selected').val()	
			},contenttype : "application/json; charset=utf-8",
			
			success:function(result){
				
				$.each(result,function(key,value){
					
					$('#searchtrain tbody').append('<tr class="choiceseat" data-toggle="modal" data-target="#myModal"><td class="trType">'+value.trainType+'</td>'
											+'<td>'+value.train_stime.substring(8,10)+' 시 '+ value.train_stime.substring(10,12) +' 분  </td>'
											+'<td>'+$('#red_start option:selected').text()+'</td>'
											+'<td>'+value.train_etime.substring(8,10)+' 시 '+ value.train_etime.substring(10,12) +' 분  </td>'
											+'<td>'+$('#red_end option:selected').text()+'</td></tr>')
					
				})
				
			}
			
			
		})
		
	})
	
	
	$('#searchtrain tbody').on('click', '.choiceseat', function(){
		$.ajax({
			type:'post',
			url:'/starrail/reservation/seats',
			dataType : 'JSON',
			data:{'traintype': $(this).find('.trType').text()},
			contenttype : "application/json; charset=utf-8",
			
			success:function(result){
				$('div.trainSeat .first').empty();
				$('div.trainSeat .second').empty();
				$('div.trainSeat .third').empty();
				$('div.trainSeat .forth').empty();
				
				$.each(result,function(key,value){
					if(key%4==0){
						$('div.trainSeat .first').append('<div class = "eachseat able" seatID="'+value.seat_id+'">'+value.seat_num+'</div>');
					}
					if(key%4==1){
						$('div.trainSeat .second').append('<div class = "eachseat able" seatID="'+value.seat_id+'">'+value.seat_num+'</div>');
					}
					if(key%4==2){
						$('div.trainSeat .third').append('<div class = "eachseat able" seatID="'+value.seat_id+'">'+value.seat_num+'</div>');
					}
					if(key%4==3){
						$('div.trainSeat .forth').append('<div class = "eachseat able" seatID="'+value.seat_id+'">'+value.seat_num+'</div>');
					}
					
					
				
					/*$('div.trainSeat').append(value.train_type + ' / ' + value.seat_id + ' / ' +value.seat_num + ' / ' + value.train_count + ' / ' + '<br/>')
					*/
				})
				
			}
			
		})
		
		
		
	});
	
	
	$('div.trainSeat').on('click','.eachseat', function(){
		alert($(this).attr("seatID"));
		var thisSeat = $(this);
		alert( "선택하신 좌석은 " + $(this).text() + "입니다.");
		if(thisSeat.hasClass("able")){
			alert( "jdasdsaf  "+thisSeat.hasClass("able"));
			$(this).removeClass("able");
			$(this).addClass("eachseat");
			$(this).addClass("currentSelect");
//			thisSeat.addClass('currentSelect');
			$(this).css("background-image","url(/starrail/resoures/images/reservation/choseat.png)");
			
		}
		
		if(thisSeat.hasClass("disable")){
			thisSeat.off();
		}
		
		if(thisSeat.hasClass("currentSelect")){
			thisSeat.addClass("able");
			thisSeat.removeClass("currentSelect");
		}
		
	})
	


});

