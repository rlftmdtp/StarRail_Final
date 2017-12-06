<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/main/header_footer.css">
<link rel='stylesheet prefetch'
	href='http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css'>
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/message/msg_detail.css">
</head>

<body>

	<section class="box-position">

		<div class="  col-xs-12 col-md-3 col-sm-4 pull-right">

			<div class="panel panel-default">

				<!-- Message box title	-->
				<div class="panel-heading top-bar ">
					<div class="col-md-10 col-xs-10">
						<p class="panel-title">
							<span class="glyphicon glyphicon-comment"></span> Message
						</p>
					</div>
				</div>

				<!-- Message body	-->
				<div class="panel-body msg_container_base">
					<br />
					<form action="#">
						<table>
							<tr class="form-group">
								<th id="msg_sendid">보낸사람 : ${messageVO.msg_sendid }</th>
							</tr>
							<tr class="form-group">
								<th id="msg_sendid">
									<fmt:formatDate pattern="yyyy-MM-dd (HH:mm)" value="${messageVO.msg_date}"/>
								</th>
							</tr>
							<tr class="form-group">
								<td colspan="2" class="form-control" id="msg_content">
									${messageVO.msg_content }
								</td>
							</tr>

							<tr class="form-group">
								<td colspan="2">
								<input type = "submit" id="submit" class="form-control" value="확인">
								</td>
							</tr>

						</table>
					</form>
				</div>


				<div class="panel-footer">
					<div class="input-group"></div>
				</div>

			</div>

		</div>

	</section>
</body>


<!-- 확인 버튼 눌렸을 때 자식창 닫히게 -->
<script type="text/javascript">
$("#submit").on('click', function(){
	$.ajax({
		url : '/starrail/message/msg_detail',
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