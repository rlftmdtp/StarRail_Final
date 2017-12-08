$(function () {
	
	$(".btn-bordered-warning1").on('click', function () {
		
		var tag = $(this).attr('data');
		var m_id = "";
		
		if($("#m_id").val() != null){
			m_id = $("#m_id").val();
		}else{
			m_id = "wkdgmlwjd";
		}
		
		
		$("#reviewTable").empty();
		$(".pagination").empty();
		$.ajax({
			url : "/starrail/review/review_list",
			type : "post",
			data : {
				tag : tag
			},
			dataType : "json",
			success : function(data) {
				var paging = "";
				var list = "";
				
				var totalCount = 0;
				var startPage = 0;
				var endPage = 0;
				var prev = "";
				var next = "";
				var displayPageNum = 0;
				var perPageNum = 0;
				var page = 0;
				
				var r_no = 0;
				
				$.each(data, function (index, item) {
					
					if(item["totalCount"] != null){
						totalCount = item["totalCount"];
						startPage = item["startPage"];
						endPage = item["endPage"];
						prev = item["prev"];
						next = item["next"];
						displayPageNum = item["displayPageNum"];
						
						//alert(totalCount + " " + startPage+ " " + endPage + " " + prev + " " + next + " " + displayPageNum);
					}
					
					$.each(item, function (index1, item1) {
						
						// 페이징
						if(item1["page"] != null){
							perPageNum = item1["perPageNum"];
							page = item1["page"];
							
							for(var i=startPage; i<endPage; i++){
								if(item1["page"] == i){
									paging += "<li class='active'>"
										+ "<a href='/starrail/review/review_list?page="+ item1["page"]
									    + "&perPageNum=" + item1["perPageNum"] +"'>" 
										+ i
										+ "<span class='sr-only'>(current)</span>"
										+ "</li>";
								} 
							}						
						}					
						
						
						// 후기 리스트
						if(item1["r_no"] != null){
							var d = new Date(item1["r_date"]);
							var curr_date = d.getDate();
							var curr_month = d.getMonth() + 1;
							var curr_year = d.getFullYear();
							
							r_no = item1["r_no"];
							
							
							list += "<tr>"
								+ "<td>" + item1["r_no"] + "</td>"
								+ "<td><a  class='review_click1' data='"+ item1["r_no"] + "' href='/starrail/review/review_detail?page="+ page
								+ "&perPageNum=" + perPageNum +"&r_no="+ r_no +"&m_id=" + m_id
								+"'>" 
								+ item1["r_title"] + "</a></td>"
								+ "<td>" + item1["m_id"] + "</a></td>"
								+ "<td>" + curr_year + "-" + curr_month + "-" + curr_date +"</td>"
								+ "<td>" + item1["r_hit"] + "</td>"
								+ "</tr>";
						}					
					})
				})
				$("#reviewTable").append(list);
				$(".pagination").append(paging);
			}
		});					
		
	})
	
	
	
	//----------------------------------------------------------------------------------------------------------------
	

	
	
	
	
})