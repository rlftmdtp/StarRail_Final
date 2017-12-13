<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<link rel="stylesheet" type="text/css" href="/starrail/resources/css/review/review_insert.css">
<!-- <script src='//production-assets.codepen.io/assets/editor/live/console_runner-
	079c09a0e3b9ff743e39ee2d5637b9216b3545af0de366d4b9aad9dc87e26bfd.js'></script>
<script src='//production-assets.codepen.io/assets/editor/live/events_runner-73716630
	c22bbc8cff4bd0f07b135f00a0bdc5d14629260c3ec49e5606f98fdd.js'></script>
<script src='//production-assets.codepen.io/assets/editor/live/css_live_reload_
	init-2c0dc5167d60a5af3ee189d570b1835129687ea2a61bee3513dee3a50c115a77.js'></script>
<link rel="shortcut icon" type="image/x-icon" href="//production-assets.codepen.io/assets/favicon
	/favicon-8ea04875e70c4b0bb41da869e81236e54394d63638a1ef12fa558a4a835f1164.ico" />
<link rel="mask-icon" type="" href="//production-assets.codepen.io/assets/favicon/logo-pin-f2d2b6d
	2c61838f7e76325261b7195c27224080bc099486ddd6dccb469b8e8e6.svg" color="#111" />
<link rel="canonical" href="https://codepen.io/soulrider911/pen/ugnyl?limit=all&page=5&q=form" />
<link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
<link href='https://fonts.googleapis.com/css?family=Lato:300,400' rel='stylesheet' type='text/css'> -->

</head>

<body>
	 <header class="header_color">
	 	<%@include file="../main/nav_page.jsp"%>
		 <div style="margin-top: 30px;"></div>
	 </header> 
	
<div class="controller">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
		
<%-- 		<form id="registerForm" action="review_insert" method="post">
					
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
				</form> --%>
		
	 <form action="review_insert" id="registerForm" method="post">
  		<!--  General -->
  		<div class="form-group">
  			<h2 class="heading">Review Board</h2>
    		<div class="controls">
     			<input type="text" id="r_title" class="floatLabel" name="r_title">
    		</div>
   
    		<textarea rows="10" cols="123" name="r_content" id="hashSearchForm"></textarea>
  		</div>
  
 		<div class="form-group" style="margin-top: 10px;">
    		<h2 class="heading">FileUpload</h2>
    			<div class="panel-body">
    			<div class="fileDrop"></div>
    			<div class="box-footer">
					<ul class="mailbox-attachments clearfix uploadedList"></ul>
				</div>
				 	<div class="input-group image-preview" style="margin-left: 220px;">
									
						<!-- don't give a name === doesn't send on POST/GET  -->
						<span class="input-group-btn"> 
						<!-- image-preview-clear button -->
						<button type="button" class="btn btn-default image-preview-clear" style="display:none;"> 
							<span class="glyphicon glyphicon-remove"> </span> 
								Clear 
						</button>
						<!-- image-preview-input -->
						<div class="btn btn-default image-preview-input"> 
							<span class="glyphicon glyphicon-folder-open"></span> 
							<span class="image-preview-input-title">Browse</span>
							<input type="file" accept="image/png, image/jpeg, image/gif" name="input-file-preview"/>
						<!-- 	rename it  -->
						</div>
						<button type="button" class="btn btn-labeled btn-primary" style="background-color: #ff8b19b3; border-color: #ff8b19b3;">
						 <span class="btn-label">
						 	<i class="glyphicon glyphicon-upload"></i> 
						 </span>Upload
						</button>
						</span> 
					</div> 
					<!-- /input-group image-preview [TO HERE]--> 
					
					<br />
					
					<!-- Drop Zone -->
					<div class="upload-drop-zone" id="drop-zone"> Or drag and drop files here </div>
					<br />
					<!-- Progress Bar -->
					<div class="progress">
						<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 100%; background-color: rgba(255, 255, 255, .15);">
						</div>
					</div>
					<br />

				</div>
				<button class="btn btn-primary" style="font-size:20px; 
					background-color: #f39818; border-color: #f39818; margin-left:450px; margin-top: -20px;">Submit</button>
  </div>	
  
  
	</form>			
				
			</div>
			<!-- /.box -->
		</div>
		<!--/.col (left) -->

	</div>
	<!-- /.row -->

 <!-- footer -->
		<div style="margin-bottom: 0px;">
        	<%@include file="../main/footer.jsp"%>	
		</div> 
</body>

<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="/starrail/resources/bootstrap/js/bootstrap.js"></script>
<script src="/starrail/resources/js/review/autocomplete.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="/starrail/resources/js/upload.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<!-- <script src='//production-assets.codepen.io/assets/common/stopExecutionOnTimeou
		t-b2a7b3fe212eaa732349046d8416e00a9dec26eb7fd347590fbced3ab38af52e.js'></script>
<script src='//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script> -->






<script id="template" type="text/x-handlebars-template">

<form class='form-horizontal well' action='#' style='margin-top : 10px;'>
	
		<div class='row'>
			<div class='col-md-4'>
				<img src='{{imgsrc}}' alt="Attachment" class='img-responsive img-radio'/>
				<div style='background-color: #FF8224; border-color: #FF8224;' 
					class='btn btn-primary btn-radio' id='thumbnailBtn' >
					{{fileName}}
					<a href="{{fullName}}" class="glyphicon glyphicon-remove"></a>
				</div>
			</div>
		</div>
	
</form>

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

