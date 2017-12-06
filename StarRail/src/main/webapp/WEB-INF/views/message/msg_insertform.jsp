<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String rec_id = request.getParameter("rec_id");
	String m_id = (String) session.getAttribute("m_id");

	if (rec_id == null) {
		rec_id = "";
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/message/msg_insertform.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

</head>
<body>

	<div class="container">
		<div class="col-md-5">
			<div class="form-area">
				<form action="msg_insertform" method="POST">
					<br style="clear: both">
					<h3 style="margin-bottom: 20px; text-align: center;">Contact
						Form</h3>
					<div class="form-group">
						<input type="text" class="form-control" name="m_id"
							value="<%=m_id%>" placeholder="받는 사람" size="35px" required>
					</div>

					<div class="form-group">
						<textarea class="form-control" type="textarea" id="message"
							name="msg_content" placeholder="Message" maxlength="300" rows="7"
							cols="37"></textarea>
						<span class="help-block">
							<p id="characterLeft" class="help-block ">You have reached
								the limit</p>
						</span>
					</div>

					<div class="form-group">
						<input type="text" class="form-control" id="msg_sendid"
							name="msg_sendid" value="msg_sendid" placeholder="보내는 사람"
							size="35px" required>
					</div>

					<button type="submit" name="submit"
						class="btn btn-primary pull-right" id="submitBtn">Submit
						Form</button>
				</form>
			</div>
		</div>
	</div>
</body>

<!-- 글자수 체크 300자 -->
<script type="text/javascript">
	$(document).ready(function() {
		$('#characterLeft').text('300 characters left');
		$('#message').keydown(function() {
			var max = 300;
			var len = $(this).val().length;
			if (len >= max) {
				$('#characterLeft').text('You have reached the limit');
				$('#characterLeft').addClass('red');
				$('#btnSubmit').addClass('disabled');
			} else {
				var ch = max - len;
				$('#characterLeft').text(ch + ' characters left');
				$('#btnSubmit').removeClass('disabled');
				$('#characterLeft').removeClass('red');
			}
		});
	});
</script>

<!-- 보내기 버튼 클릭시 보내지고 문 닫히기 -->
<script type="text/javascript">
	$("#submitBtn").on('click', function() {
		$.ajax({
			url : '/starrail/message/msg_insertok',
			type : 'POST',
			headers : {
				'Content-Type' : 'application/json'
			},
			dataType : "text",
			success : function(data) {
				window.self.close();
			}
		});
	});
</script>

</html>