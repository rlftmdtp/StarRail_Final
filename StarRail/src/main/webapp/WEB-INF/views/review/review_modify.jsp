<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
.fileDrop {
	width: 80%;
	height: 100px;
	border: 1px dotted gray;
	background-color: lightslategrey;
	margin: auto;
}
</style>


<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">MODIFY BOARD</h3>
				</div>

				<form action="review_modify" method="post">
					<input type="hidden" name="r_no" value="${reviewVO.r_no}">
					<input type="hidden" name="files" value="${review_file.rf_fullname}">
					<input type="hidden" name="page" value="${cri.page }"> <input
						type="hidden" name="perPageNum" value="${cri.perPageNum }">
					<input type="hidden" name="searchType" value="${cri.searchType}">
					<input type="hidden" name="keyword" value="${cri.keyword }">
					<table border="1">
						<tr height="30">
							<td width="80">작성자</td>
							<td width="170"><input type="text" name="m_id" size="10"
								value="${reviewVO.m_id}"></td>
							
						</tr>
						<tr height="30">
							<td width="80">제목</td>
							<td align="left" colspan="3"><input type="text"
								name="r_title" size="50" value="${reviewVO.r_title }"></td>
						</tr>
						<tr height="30">
							<td colspan="4"><textarea rows="10" cols="70"
									name="r_content">${reviewVO.r_content }</textarea></td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label for="exampleInputEmail1">File DROP Here</label>
									<div class="fileDrop"></div>
									<ul class="mailbox-attachments clearfix uploadedList"></ul>
	 		
								</div>
							</td>
						</tr>
						<tr height="30">
							<td colspan="4" align="center">
							<input type="submit" value="글쓰기">&nbsp;&nbsp; 
							<input type="reset" value="취소" 	onclick="cancle()"></td>
						</tr>
					</table>
				</form>
			</div>

			<script type="text/javascript" src="/starrail/resources/js/upload.js"></script>
			<script
				src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

			<script id="template" type="text/x-handlebars-template">
<li>
  <span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}" alt="Attachment"></span>
  <div class="mailbox-attachment-info">
	<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
	<a href="{{fullName}}" 
     class="btn btn-default btn-xs pull-right delbtn"><i class="fa fa-fw fa-remove"></i></a>
	</span>
  </div>
</li>                
</script>
			<script>
				function cancle() {
					self.location = "/starrail/review/review_list?page=${cri.page}&perPageNum=${cri.perPageNum}"
							+ "&searchType=${cri.searchType}&keyword=${cri.keyword}";
				};
			</script>

			<script>
				$(document)
					.ready(function() {
						var formObj = $("form[role='form']");
							formObj.submit(function(event) {event.preventDefault();
							var that = $(this);
							var str = "";
							$(".uploadedList .delbtn").each(function(index) {
							str += "<input type='hidden' name='files["+ index+ "]' value='"
							+ $(this).attr("href")+ "'> ";
							});
							that.append(str);

							console.log(str);

							that.get(0).submit();
						});

				$(".btn-warning").on("click", function() {
					self.location = "/starrail/review/review_list?page=${cri.page}&perPageNum=${cri.perPageNum}"
						+ "&searchType=${cri.searchType}&keyword=${cri.keyword}";
				});

			});

				var template = Handlebars.compile($("#template").html());

				$(".fileDrop").on("dragenter dragover", function(event) {
					event.preventDefault();
				});

				$(".fileDrop").on("drop", function(event) {
					event.preventDefault();

					var files = event.originalEvent.dataTransfer.files;

					var file = files[0];

					//console.log(file);

					var formData = new FormData();

					formData.append("file", file);

					$.ajax({
						url : '/starrail/review/uploadAjax',
						data : formData,
						dataType : 'text',
						processData : false,
						contentType : false,
						type : 'POST',
						success : function(data) {

							var fileInfo = getFileInfo(data);

							var html = template(fileInfo);

							$(".uploadedList").append(html);
						}
					});
				});

				$(".uploadedList").on("click", ".delbtn", function(event) {

					event.preventDefault();

					var that = $(this);

					$.ajax({
						url : "/starrail/review/deleteFile",
						type : "post",
						data : {
							fileName : $(this).attr("href")
						},
						dataType : "text",
						success : function(result) {
							if (result == 'deleted') {
								that.closest("li").remove();
							}
						}
					});
				});
</script>

<script>
				var r_no = ${reviewVO.r_no};
				var template = Handlebars.compile($("#template").html());

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


		</div>
		<!-- /.box -->
	</div>
	<!--/.col (left) -->

</section>


