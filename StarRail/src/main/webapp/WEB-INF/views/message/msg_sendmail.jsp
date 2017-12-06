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
	href="/starrail/resources/css/message/msg_list.css">
</head>
<body>

	<div class="mail-box">
		<aside class="sm-side">
		<div class="user-head">

			<div class="user-name">
				<h5>${messageVO.m_id }</h5>
				<span>>${messageVO.m_id }@kosta.167</span>
			</div>

		</div>
		<div class="inbox-body">
			<input type="button" value="쪽지보내기" data-toggle="modal"
				onclick="openTest()" class="btn btn-compose">


		</div>
		<ul class="inbox-nav inbox-divider">
			<li class="active"><a href="/starrail/message/msg_list"><i class="fa fa-inbox"></i>
					받은쪽지함 <span class="label label-danger pull-right">${updatenum }</span></a></li>
			<li><a href="#"><i class="fa fa-envelope-o"></i> 보낸 쪽지함</a></li>
<!-- 			<li><a href="#"><i class="fa fa-bookmark-o"></i> 모아두기</a></li>
			<li><a href="#"><i class=" fa fa-external-link"></i> 이게 뭐지</a></li>
			<li><a href="/starrail/message/msg_garbage"><i class=" fa fa-trash-o"></i> 휴지통</a></li> -->
		</ul>



		</aside>
		<aside class="lg-side">
		<div class="inbox-head">
			<h3>보낸 쪽지함</h3>

		</div>
		<div class="inbox-body">
			<div class="mail-option">
				<div class="chk-all">
					<input type="checkbox" class="mail-checkbox mail-group-checkbox"
						id="all">
					<div class="btn-group">
						<a data-toggle="dropdown" href="#" class="btn mini all"
							aria-expanded="false"> All </a>
					</div>
				</div>

				<div class="btn-group">
					<a data-original-title="Refresh" data-placement="top"
						data-toggle="dropdown" href="#" onclick="refresh()"
						class="btn mini tooltips"> <i class=" fa fa-refresh"></i>
					</a>
				</div>
				<div class="btn-group hidden-phone">
					<a data-toggle="dropdown" href="#" class="btn mini blue"
						aria-expanded="false"> ??? </a>

				</div>
<!-- 				<div class="btn-group">

					<a data-toggle="dropdown" href="#1" class="btn mini blue"
						aria-expanded="false" onclick="fn_delete()"> 삭제 </a>
				</div> -->


			</div>
			<table class="table table-inbox table-hover">
				<tbody>
					<tr class="unread">
						<th class="inbox-small-cells"></th>
						<th class="view-message ">수신</th>
						<th class="view-message ">받는사람</th>
						<th class="view-message ">쪽지내용</th>
						<th class="view-message">날짜</th>
					</tr>
					<c:forEach items="${list}" var="messageVO">
						<tr class="unread">

							<td class="inbox-small-cells"><input type="checkbox"
								class="mail-checkbox" name="check" id="checkbox_id"
								value="${messageVO.msg_no }"></td>

							<c:choose>
								<c:when test="${messageVO.msg_hit > 0 }">
									<td class="view-message "><img
										src="/starrail/resources/images/message/receivemail_icon.png"
										name="msg_hit"></td>
								</c:when>
								<c:otherwise>
									<td class="view-message "><img
										src="/starrail/resources/images/message/sendmail_icon.png"
										name="msg_hit"></td>
								</c:otherwise>
							</c:choose>
							<td class="view-message ">${messageVO.m_id }</td>
							<td class="view-message ">${messageVO.msg_content }</td>
							<td class="view-message"><fmt:formatDate
									pattern="yyyy-MM-dd (HH:mm)" value="${messageVO.msg_date}" />

							</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
		</aside>
	</div>
</body>

<!-- 쪽지보내기 눌렀을 때 자식창 -->
<script type="text/javascript">
	function openTest() {
		var child = window.open(
						'/starrail/message/msg_insertform',
						'childWindow',
						'resizable=no, width=360, height=380, left=500, top=200, menubar=no, status=no, scrollbars=no');
	};
</script>



<!-- 전체 선택 삭제 -->
<script type="text/javascript">
	$(".mail-group-checkbox").on("click", function(){
		var mailCheckBox = document.getElementsByName('check');
		var allCheckBox = document.getElementById('all').checked;
		
		if(allCheckBox) // 체크가 안되어있으면
		{
			for(var i=0; i<mailCheckBox.length; i++)
			{
				mailCheckBox[i].checked = true;
			}
		}
		
		if(!allCheckBox){
			for(var i=0; i<mailCheckBox.length; i++)
			{
				mailCheckBox[i].checked = false;
			}
		}
		
		
	})
</script>

<!-- 삭제 눌렀을 때 -->
<script type="text/javascript">
	function fn_delete() {
		
		$("input[name=check]:checked").each(function() {
			  var test = $(this).val();
			  
			  $.ajax({
				  url : "/starrail/message/message_delete",
				  type : "POST",
				  headers : {
						'Content-Type' : 'application/json'
					},
				  data : test,
				  dataType : "text",
				  success: function(data){
					  history.go(0);
				  }
			  });
			});	
	}
</script>

<!-- 새로고침 -->
<script type="text/javascript">
	function refresh() {
		history.go(0);
	}
</script>