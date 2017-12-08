$(function () {
	
	// 추천된 태그 클릭 시 ajax통신을 이용하여 추천수가 높은 순서대로 후기 리스트 가져옴
	
	$(".btn-bordered-warning1").on('click', function () {
		//alert($(this).attr('data'));
		var $tag = $(this).attr('data');
		
		$("#reviewList").empty();
		$.ajax({
			url : "/starrail/recommend/reviewSearch",
			type : "post",
			data : {
				tag : $tag
			},
			dataType : "json",
			success : function(data) {
				var template = Handlebars.compile($("#template").html());
				
				if(data != null){
					$.each(data, function (index, item) {						
						var html = template(item);
						
						$("#reviewList").append(html);
					})
				}
				

				
			}
		});						
		
	});
	
	
	
	
	
	
	
	
	
	
	
})
