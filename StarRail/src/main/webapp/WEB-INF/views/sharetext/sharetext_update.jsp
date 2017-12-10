<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/main/header_footer.css">
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/sharetext/sharetext.css">
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
</head>
<body>
<!-- 해더 -->
 	<%@include file="../main/nav_page.jsp"%>
		<div style="margin-top: 60px;"></div>   

		<div class="form-group" 
				style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto;" align="center">
				<label for="exampleInputEmail1"><img
					src="/starrail/resources/images/sharetext/update2.png" style="position: top:0; left: 0; width: 890;" height="30%"></label>
		</div>
<form role="form" action="/starrail/sharetext/sharetext_update" method="post">

	<div class="box-body">
		
		<!-- 글번호 -->
	
			 <input type="hidden"
				name='sh_no' class="form-control" value="${shareTextVO.sh_no}"
				readonly="readonly">
		
		<!-- 여행일수 -->
		<div class="form-group" style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;">
			<label for="exampleInputEmail1">여행일수</label> <input type="text"
				 class="form-control" value="${shareTextVO.sh_subject}" readonly="readonly">
		</div>
		<!-- 제목 -->
		<div class="form-group" style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;">
			<label for="exampleInputEmail1">제목</label> <input type="text"
				name='sh_title' class="form-control" value="${shareTextVO.sh_title}">
		</div>
		
		<!-- 코스정보  -->
			<div class="form-group"
				style="border: 1px solid #48BAE4; height: 20%; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;">
				<label for="exampleInputPassword1">코스</label>
				<textarea class="form-control"  rows="3"	readonly="readonly" style="resize: none;width:100%; height:84%">				 
				 <c:forEach var="courseDetailObject" items="${list }" >  [${courseDetailObject.cd_start}] : [${courseDetailObject.cd_stime }] ▶▷▶▷[ ${courseDetailObject.cd_end }]: [${courseDetailObject.cd_etime } ]
				 </c:forEach>			
				</textarea>
			</div>
		
		<!-- 내용 -->
		<div class="form-group" style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;">
			<label for="exampleInputPassword1">내용</label>
			<textarea class="form-control" name="sh_content" rows="3">${shareTextVO.sh_content}</textarea>
		</div>
		
		<!-- 작성자 -->
		<div class="form-group" style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;">
			<label for="exampleInputEmail1">작성자</label> <input
				type="text" class="form-control"
				value="${shareTextVO.m_id}" readonly="readonly">
		</div>
	</div>
	<!-- /.box-body -->
</form>


<div class="box-footer" align="center">
	<button type="submit" class="btn btn-primary">저장</button>
	<button type="submit" class="btn btn-warning">취소</button>
</div>

<script>
	$(document).ready(function() {

		var formObj = $("form[role='form']");

		console.log(formObj);

		$(".btn-warning").on("click", function() {
			self.location = "/starrail/sharetext/sharetext_listPage";
		});
		
		//값 저장
		$(".btn-primary").on("click", function() {
			formObj.submit();
		});

	});
</script>

<!--푸터  -->
    <div style="margin-top: 30px;">
    	<%@include file="../main/footer.jsp"%>   
    </div>   

<!-- datepicker JS File : 무조건 코드 맨마지막에 넣어야 실행됨 -->   
 <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/js/bootstrap-datepicker.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery-dateFormat/1.0/jquery.dateFormat.js'></script>
    <script src="/starrail/resources/js/main/index.js"></script> 
<!-- JS File -->
   <script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
</body>

</html>




