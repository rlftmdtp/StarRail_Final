
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
<!-- bootstrap에서 받아온 js파일+css파일 사용 하기 위함 -->
<script src="/starrail/resources/bootstrap/js/bootstrap.js"></script>
<script src="/starrail/resources/js/review/autocomplete.js"></script>


<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/main/header_footer.css">
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/review/review_insert.css">


<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">REGISTER BOARD</h3>
				</div> 
				<form id="registerForm" action="review_insert" method="post">
					
					<table border="1" align="center">


						<tr height="30">
							<td align="center" width="80" colspan="2">제목</td>
							<td align="left" colspan="2"><input type="text"
								name="r_title" size="50" /></td>
						</tr>


						<tr height="30">
							<td colspan="4"><textarea rows="10" cols="138"
									name="r_content" id="hashSearchForm"></textarea></td>
						</tr>

						<tr>
							<td colspan="4">
								<div class="form-group">
									<label for="exampleInputEmail1">File DROP Here</label>
									<div class="fileDrop"></div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div class="box-footer">
									<ul class="mailbox-attachments clearfix uploadedList"></ul>
									<input type="submit" value="글쓰기">&nbsp;&nbsp; <input
										type="reset" value="취소">&nbsp;&nbsp;
								</div>
							</td>

						</tr>

					</table>
				</form>
			</div>
			<!-- /.box -->
		</div>
		<!--/.col (left) -->

	</div>
	<!-- /.row -->
</section>

<script src="/starrail/resources/js/upload.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>




<script id="template" type="text/x-handlebars-template">
 <li>
  <span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}" alt="Attachment"></span>
  <div class="mailbox-attachment-info">
	<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
	
		<a href="{{fullName}}" class="glyphicon glyphicon-remove"></a>

	</span>
  </div>
</li> 

                
</script>

<script>
	var template = Handlebars.compile($("#template").html());

	$(".fileDrop").on("dragenter dragover", function(event) {
		event.preventDefault();
	});

	$(".fileDrop").on("drop", function(event) {
		event.preventDefault();

		var files = event.originalEvent.dataTransfer.files;

		var file = files[0];

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

	$("#registerForm").submit(
			function(event) {
				event.preventDefault();

				var that = $(this);

				var str = "";
				$(".uploadedList .glyphicon-remove").each(
						function(index) {
							alert("??");
							str += "<input type='hidden' name='files[" + index
									+ "]' value='" + $(this).attr("href")
									+ "'> ";
						});

				that.append(str);
				alert(str);
				that.get(0).submit();
			});
</script>


<!-- 자동완성 -->
<script type="text/javascript">
	
		$("#hashSearchForm").keyup(function() {
			var inputTag = $(this).val();
			var autocomplete_text = ${list};
				$("#hashSearchForm").autocomplete({
					source : autocomplete_text 
			});
		
	});
</script>

