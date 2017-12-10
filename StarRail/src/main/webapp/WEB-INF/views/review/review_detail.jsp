<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" type="text/css"	href="/starrail/resources/css/review/Bootstrap_review_detail.css?ver=1">
<link rel="stylesheet" type="text/css"	href="/starrail/resources/css/review/review_detail.css">
<link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style type="text/css">
div #reviewDetail_container{
	margin: 30px 40px;   
    padding: 19px;
    margin-bottom: 20px;
    background-color: #f5f5f5;
    border: 1px solid #e3e3e3;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
    height: 530px;
}
</style>


	<%@include file="../main/nav_page.jsp"%>
	<div style="margin-top: 30px;"></div>   


<div class='popup back' style="display: none;"></div>
<div id="popup_front" class='popup front' style="display: none;">
	<img id="popup_img">
</div>

<section class="content">
	<form action="review_modify" method="get">
		<input type="hidden" name="r_no" value="${reviewVO.r_no}"> 
		<input type="hidden" name="rf_fullname" value="${review_file.rf_fullname}"> 
		<input type="hidden" name="page" value="${cri.page }"> 
		<input type="hidden" name="perPageNum" value="${cri.perPageNum }"> 
		<input type="hidden" name="searchType" value="${cri.searchType }"> 
		<input type="hidden" name="keyword" value="${cri.keyword }">
	
<%-- 	<table border="1" align="center">
		<tr height="30">
			<td width="50">글번호</td>
			<td width="50">${reviewVO.r_no}</td>
			<td width="50">조회수</td>
			<td width="50">${reviewVO.r_hit}</td>
		</tr>
		<tr height="30">
			<td width="150">작성자</td>
			<td width="150">${reviewVO.m_id }</td>
			<td width="150">작성일</td>
			<td width="150"><fmt:formatDate value="${reviewVO.r_date }"
					pattern="yyyy-MM-dd" /></td>
		</tr>


		<tr height="30">
			<td width="150">제목</td>
			<td colspan="3">${reviewVO.r_title }</td>
		</tr>

		<tr height="60">
			<td align=center colspan="4">
			${reviewVO.r_content}
			</td>
		</tr>
		<tr class = "hashtag" >
			<td colspan="4">
				
					<input type = "text" class="form-control" value="${hasgTag}">
				
			</td>
		</tr>
		<tr>
			<td colspan="4">
		 		<ul class="mailbox-attachments clearfix uploadedList"></ul>
	 		</td>
		</tr>
		<tr height="30">
			<td colspan="4"><input type="submit" value="수정">&nbsp;&nbsp;
			
			 <input type="button" value="삭제" onclick="remove()">&nbsp;&nbsp; 
			 <input type="button" value="목록" onclick="list()">
			 <div class="box-footer">
			 <c:if test="${login.m_id == review.m_id}">
						<input type="submit" value="수정">
						<input type="button" value="삭제" onclick="remove()">
					</c:if>
					</div>
			 </td>
		</tr>		
	</table> --%>
	


    <!-- start Bootstrap으로 바꾼 Html코드 -->
	<div class="container" >
			<div class="">
	        	 <h1 class="my-4" style="margin-top: 10px; margin-left: 20px;">
			     <span style="font-size: 30px; color: #F0AD4E; font-weight: bold;">Review Detail&nbsp;</span>
			     <small>${reviewVO.r_no}번 글</small>
			     </h1> 
	               
	         
		    	 <div  id="reviewDetail_container">
		            <div class="col-md-12">
	            
	            	<!-- start 글 내용 -->
	                <div class="col-md-12 col-sm-8">
	                    <h2>${reviewVO.r_title }</h2>
	                    <p><h3><strong>Writer: </strong> ${reviewVO.m_id } </h3></p>
	                    <p><h3><strong>Contents: </strong> ${reviewVO.r_content} </h3></p>
	                    <br><br><br>
	                    
	                    <c:if test="${!empty hasgTag}">
	                    	<p><h3><strong>Tag: </strong>		                    
		                    	<c:forEach items="${hasgTag}" var="hashTag" varStatus="status">
		                    		<span class="tags">${status.current}</span>
		                    	</c:forEach>		                                       	
	                    	</h3></p>
	                    </c:if>	 
	                </div>         
	                <!-- end 글 내용 -->
	                
	                <!-- start 별점 -->    
	                <div class="col-xs-12 col-sm-4 text-center1">
	                    <figure><h3>
	                        <img src="http://www.localcrimenews.com/wp-content/uploads/2013/07/default-user-icon-profile.png" alt="" class="img-circle img-responsive">
	                        <figcaption class="ratings">
	                            <p>Ratings
	                            <a href="#">
	                                <span class="fa fa-star"></span>
	                            </a>
	                            <a href="#">
	                                <span class="fa fa-star"></span>
	                            </a>
	                            <a href="#">
	                                <span class="fa fa-star"></span>
	                            </a>
	                            <a href="#">
	                                <span class="fa fa-star"></span>
	                            </a>
	                            <a href="#">
	                                 <span class="fa fa-star-o"></span>
	                            </a> 
	                            </p>
	                        </figcaption></h3>
	                    </figure>
	                </div>	                
	            </div>                   
	             <!-- end 별점 -->     
	                 
	             <div class="col-xs-12 divider1 text-center" style="margin: 20px 20px;">
	                <div class="col-xs-12 col-sm-6 emphasis">
	                    <h2><strong> ${reviewVO.r_hit} </strong></h2>    
	                    <p><strong>조회수</strong></p>                
	                    </div>
	                <div class="col-xs-12 col-sm-6 emphasis">
	                    <h2><strong>${reviewVO.r_recomm}</strong></h2>  
	                    <p><strong>추천수</strong></p>                  
	                    </div>
<!-- 	                <div class="col-xs-12 col-sm-4 emphasis">
	                    <h2><strong>43</strong></h2> 
	                    <p><small>뭐넣지?</small></p>                   
	                    </div> -->
	            </div>	            
	            
	             <!-- start 수정 삭제 목록으로 돌아가는 버튼이 들어가는 div --> 
	            <div class="text-center">
	            
	                <div class="col-xs-12 col-sm-4 emphasis">
	                <!-- start 수정 버튼을 누르면 form을 submit하게됨 -->
	                    <button class="btn btn-success btn-block" type="submit">
	                    <span class="fa fa-plus-circle"></span> Modify </button>
	                </div>
	                
	                <!-- start 삭제 버튼을 누르면 onclick=remove() 실행하게됨 -->
	                <div class="col-xs-12 col-sm-4 emphasis">
	                    <button class="btn btn-info btn-block"  onclick="remove()">
	                    <span class="fa fa-user"></span> Remove </button>
	                </div>
	                
	                <!-- start 목록 버튼을 누르면 onclick = list()실행하게됨 -->
	                <div class="col-xs-12 col-sm-4 emphasis">
						<button class="btn btn-danger btn-block" onclick="list()">
						<span class="fa fa-user"></span> List </button>
	                </div>
	                
	            </div>
	            <!-- end 수정 삭제 목록으로 돌아가는 버튼이 들어가는 div --> 
	    	 </div>  
	    	 
	    	 <hr>
	    	 
	    	 <div id="recommendList_container" class="col-md-12" style="margin-top: 100px;">
		    	 <h1 class="my-4" style="margin-top: 10px; margin-left: 20px;">
			     <span style="font-size: 30px; color: #F0AD4E; font-weight: bold;">Review Recommendation&nbsp;</span>
			     <small>이 후기를 조회한 사람이 읽은 다른 후기 추천</small>
			     </h1>
			     
		     <c:if test="${!empty recommendList}">
		     <c:forEach items="${recommendList}" var="reviewVO" varStatus="status">
		     	<div id="recommendList"  style="margin: 60px 40px;" class="col-md-3" >
					
		            	<h3>		                    
			            				                   	
			                   	<div >
								    <div id="tb-testimonial" class="testimonial testimonial-warning">
								       <div class="testimonial-section">
								            추천수 : <strong>${status.current.r_recomm}</strong>       
								       </div>
								       <div class="testimonial-desc">
								           <img src="/starrail/resources/images2/partner/M.png" alt="" />
								           <div class="testimonial-writer">
								               <div class="testimonial-writer-name" style="padding-top: 5px;">${status.current.r_no}번 글</div>
								               <div class="testimonial-writer-designation" style="padding-top: 5px; padding-bottom: 5px;">${status.current.m_id}</div>
								               <a href="/starrail/review/review_detail?page=${cri.page }&perPageNum=${cri.perPageNum }&r_no=${reviewVO.r_no}&m_id=${m_id}" class="testimonial-writer-company" style="font-weight: bold;">${status.current.r_title}</a>
								            </div>
								       </div>
								    </div>   
							   </div> 	              	
			                   	
			             		                                       	
		                 </h3>
		         	   	
	         	</div>	
	         	</c:forEach>	
	         	</c:if>  
	    	 </div>	    	 
	    	                
			</div>
		</div>
    <!-- end Bootstrap으로 바꾼 Html코드 -->
    </form>
</section>


    <div style="margin-top: 30px;">
    	<%@include file="../main/footer.jsp"%>   
    </div> 
    
    
    

    
    
    
    
    
    
    
    
    
    
    





<!-- 후기삭제, 수정, 목록 -->
<script type="text/javascript">
	function remove() {
		location.href = "/starrail/review/review_remove?r_no=${reviewVO.r_no }";
	}

/* 	function modify() {
		alert("수정~");
		location.href = "review_modify?r_no=${review.r_no}";
	} */

	function list() {
		alert("리스트러 가자");
		location.href = "/starrail/review/review_list";
	}
</script>

<script src="/starrail/resources/js/upload.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

<!-- 파일 업로드 뷰 -->
<script id="templateAttach" type="text/x-handlebars-template">
<li data-src='{{fullName}}'>
  <span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}" alt="Attachment"></span>
  <div class="mailbox-attachment-info">
	<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
	</span>
  </div>
</li>                
</script>

<script>
	var r_no = ${reviewVO.r_no};
	var template = Handlebars.compile($("#templateAttach").html());
	$.getJSON("/starrail/review/getAttach/" + r_no, function(list) {
		$(list).each(function() {

			var fileInfo = getFileInfo(this);

			var html = template(fileInfo);

			$(".uploadedList").append(html);

		});
	});

	$(".uploadedList").on("click", ".mailbox-attachment-info a",
			function(event) {

				var fileLink = $(this).attr("href");

				if (checkImageType(fileLink)) {

					event.preventDefault();

					var imgTag = $("#popup_img");
					imgTag.attr("src", fileLink);

					console.log(imgTag.attr("src"));

					$(".popup").show('slow');
					imgTag.addClass("show");
				}
			});

	$("#popup_img").on("click", function() {

		$(".popup").hide('slow');

	});
</script>

<!-- 파일 삭제 -->
<script>
	$(document).ready(function() {

		var formObj = $("form[role='form']");

		console.log(formObj);

		$("#removeBtn").on("click", function() {

			var arr = [];
			$(".uploadedList li").each(function(index) {
				arr.push($(this).attr("data-src"));
			});

			if (arr.length > 0) {
				$.post("/starrail/review/deleteAllFiles", {
					files : arr
				}, function() {

				});
			}

			formObj.attr("action", "/starrail/review/reivew_remove");
			formObj.submit();
		});
	});
</script>
