<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/review/review_detail.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


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
	
	<table border="1" align="center">
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
		
	</table>
</form>	
	

</section>

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
