<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/message/msg_list.css">
	<!-- Bootstrap core CSS -->
<link href="/starrail/resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Custom fonts for this template -->
<link rel="stylesheet"
	href="/starrail/resources/vendor/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="/starrail/resources/vendor/simple-line-icons/css/simple-line-icons.css">
<link href="https://fonts.googleapis.com/css?family=Lato"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Catamaran:100,200,300,400,500,600,700,800,900"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Muli"
	rel="stylesheet">
<!-- Plugin CSS -->
<link rel="stylesheet"
	href="/starrail/resources/device-mockups/device-mockups.min.css">
<link href="/starrail/resources/css/partner/small-business.css"
	rel="stylesheet">
<!-- Custom styles for this template -->


<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="/starrail/resources/bootstrap/js/bootstrap.js"></script>
<!-- Bootstrap core JavaScript -->
<script
	src="/starrail/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Plugin JavaScript -->
<script
	src="/starrail/resources/vendor/jquery-easing/jquery.easing.min.js"></script>
</head>
<body>
	<header class="header_color">
	 <%@include file="../main/nav_page.jsp"%>
	 <div style="margin-top: 30px;"></div>
	 </header>
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
			<li><a href="/starrail/message/msg_sendmail"><i class="fa fa-envelope-o"></i> 보낸 쪽지함</a></li>
 			<li><a href="#"><i class="fa fa-bookmark-o"></i> 모아두기</a></li>
			<li><a href="#"><i class=" fa fa-external-link"></i> 이게 뭐지</a></li>
			<li><a href="/starrail/message/msg_garbage"><i class=" fa fa-trash-o"></i> 휴지통</a></li> 
		</ul>



		</aside>
		<aside class="lg-side">
		<div class="inbox-head">
			<h3>쪽지함</h3>

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
				<div class="btn-group">

					<a data-toggle="dropdown" href="#1" class="btn mini blue"
						aria-expanded="false" onclick="fn_delete()"> 삭제 </a>
				</div>


			</div>
			<table class="table table-inbox table-hover">
				<tbody>
					<tr class="unread">
						<th class="inbox-small-cells"></th>
						<th class="view-message ">수신</th>
						<th class="view-message ">보낸사람</th>
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
							<td class="view-message ">${messageVO.msg_sendid }</td>
							<td class="view-message "><a href="#"
								onclick="detail_click(${messageVO.msg_no })">
									${messageVO.msg_content } </a></td>
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
		<div style="margin-top: 0;">
        	<%@include file="../main/footer.jsp"%>	
	</div>
</body>

<!-- 쪽지보내기 버튼 눌렀을 때 자식창열리게 -->
<script type="text/javascript">
	function openTest() {
		var child = window.open(
						'/starrail/message/msg_insertform',
						'childWindow',
						'resizable=no, width=360, height=380, left=500, top=200, menubar=no, status=no, scrollbars=no');
	};
</script>

<!-- 쪽지창 상세보기 눌렀을 때 자식창 뜨게 -->
<script type="text/javascript">
	function detail_click(msg_no) {
		var child = window.open(
						'/starrail/message/msg_detail'+msg_no,
						'childWindow',
						'resizable=no, width=360, height=380, left=500, top=200, menubar=no, status=no, scrollbars=no');
	};
	
	
</script>



<!-- 체크박스 전체선택, 해제 -->
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

<!-- 쪽지 삭제하고 새로고침 -->
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

<!-- 리플래쉬 눌렀을 때 새로고침 -->
<script type="text/javascript">
	function refresh() {
		history.go(0);
	}
</script>