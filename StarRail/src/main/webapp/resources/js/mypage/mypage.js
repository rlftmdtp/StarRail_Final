$(function() {
	
	
	
	$('.course-list-table').on('click','.editBtn',function(){	//코스 수정 페이지로 이동
		var c_id = $(this).attr('c_id');
		
		location.href='/starrail/course/editCourse'+c_id;
	});
	
	
	$('.course-list-table').on('click','.delBtn',function(){
		var c_id = $(this).attr('c_id');
		
		$.ajax({
			type : 'post',
			url : '/starrail/mypage/deleteCourse',
			dataType : 'text',
			data:{'c_id':c_id},
			success:function(result){
				alert('코스를 삭제했습니다.');
				location.reload();
			}
			
			
		});
	});
})