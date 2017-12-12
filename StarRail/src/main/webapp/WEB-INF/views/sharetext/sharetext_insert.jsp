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
	
	<style type="text/css">
		#selectCourse {
		 border: 1px solid #48BAE4;
		 height: 30%;
		 width: 100%;
		 margin: auto;
		 color: green;
		 resize: none;
		 font-weight: bold;
		 font-size: 1.0em;
		 text-align: center;
		}
	</style>
</head>
<body>

 <!-- 해더 -->
 <%@include file="../main/nav_page.jsp"%>
	<!-- <div style="margin-top: 60px;"></div>    -->
	
	
<form role="form" method="POST" name="writeform">
<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
	
		<div class="box-body">
	
		<br><br>
		<div class="form-group" 
				style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto;">
				<label for="exampleInputEmail1"><img
					src="/starrail/resources/images/reservation/7305_12899_1845.jpg"></label>
		</div>
			
		<div class="form-group" style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;"
			align="center">
			<label for="exampleInputEmail1">여행일수</label><br>
			
						<select name="sh_subject" style="font-family: 굴림체; text-align-last: center;">
							<option value="  ">여행기간 선택</option>
								<option value="[5일]">[5일]</option>
								<option value="[7일]">[7일]</option>
							</select>
							<br><br>
		</div>

		<div class="form-group" style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;"
			align="center">
			<label for="exampleInputEmail1">제목</label> 
			<input type="text"
				name='sh_title' class="form-control" placeholder="제목을 입력해주세요."><br><br>
		</div>
		<div class="form-group" style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;"
			align="center">
			<label for="exampleInputEmail1">코스</label> <br>
			<%-- <input type="hidden" name="c_id" value="${courseDetailList.get(0).c_id}"> --%>
			
			<select id="course" name="c_id" onchange="selectCourse();" >
				<option selected="selected">원하는 코스를 선택 하세요</option>
				<c:forEach var="CourseObject" items="${courseList }">
					<option value="${CourseObject.c_id}">${CourseObject.c_name}</option>
				</c:forEach>			
			</select><br><br>
			<ul class="form-control" id="selectCourse" rows="3" placeholder="코스를 입력해주세요.">
			
			</ul>
		</div>
		<div class="form-group" style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;"
			align="center">
			<br><label for="exampleInputPassword1">내용</label><br>
			<textarea class="form-control" name="sh_content" rows="3"
				placeholder="내용을 입력해주세요." style="border: 1px solid #48BAE4; height: 30%; width: 100%; margin: auto; color: green; resize: none; font-weight: bold; font-size: 1.0em;"
			align="center"></textarea>
		</div>
		<div class="form-group" style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;"
			align="center">
			<label for="exampleInputEmail1">아이디</label> 
			<input type="text"
				name="m_id" class="form-control" value='${login.m_id }' readonly>
		</div>
		<div class="form-group" style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;"
			align="center">
			<label for="exampleInputEmail1">비밀번호</label> 
			<input type="text"
				name="sh_pw" class="form-control" placeholder="비밀번호를 입력해주세요.">
		</div>
		
		
	</div>
	<!-- /.box-body -->

	</table>
		<div class="box-footer" align="center">
<!-- 		<button type="submit" class="btn btn-success">저장</button>
		<button type="submit" class="btn btn-primary">취소</button> -->
		<input type="button" value="등록" onclick="writeCheck();"> <input
						type="reset" value="취소">
	</div>
</form>

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
   <script type="text/javascript">
   		function selectCourse() {
   	   		var c_id = $("#course option:selected").val();
   			$.ajax({
   				url : "/starrail/sharetext/selectCourse/" + c_id,
   				dataType : "json",
   				success : function(list){
						$("#selectCourse").empty();
   					$.each(list, function(index){
	   					$("#selectCourse").append(
		   						'<li class="form-control" >'
		   						+[index+1]+'일차 <img src="/starrail/resources/images/reservation/start.jpg"> ['+this.CD_START+'] : ['+this.CD_STIME+']   <img src="/starrail/resources/images/reservation/abc.jpg">   '
		   						+'[ '+this.CD_END+']: ['+this.CD_ETIME+'] <img src="/starrail/resources/images/reservation/finish.jpg">'
		   						+'</li>'
		   					);				
   					});
   				}
   			});
   			
   		}
   		
   		
   		function gotoshareInsert() {
   			location.href = "shareForm.jsp";
   		}

   		/* 입력안된 곳 입력하라는 경고창 */
   		function writeCheck() {
   			var form = document.writeform;
   			
   			


   			if (!form.sh_title.value) {
   				alert("제목을 입력해주세요.")
   				form.sh_title.focus();
   				return;
   			}
   			if (!form.sh_content.value) {
   				alert("내용을 입력해주세요.")
   				form.sh_content.focus();
   				return;
   			}
   			
   			if (!form.sh_pw.value) {
   				alert("비밀번호를 입력해주세요.");
   				form.sh_pw.focus();
   				return;
   			}

   			form.submit();
   		}
   	</script>
</body>
</html>
