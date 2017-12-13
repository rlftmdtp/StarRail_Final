<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 반응형 웹 하기 위함 -->
<meta name="viewport" content="width=divice-width" , initial-scale="1">
<title>coding booster</title>
<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
<!-- bootstrap에서 받아온 js파일+css파일 사용 하기 위함 -->
<script src="/starrail/resources/bootstrap/js/bootstrap.js"></script>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!--  <script>
	var result = '${msg}';
	
	if(result = 'SUCCESS'){
		alert("처리가 완료 되었습니다.");
	}
</script> -->
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/main/header_footer.css">

</head>
<body>
	<script>
    
    					var result = '${msg}';
    
   						 if(result == 'SUCCESS'){
    						alert("처리가 완료되었습니다.");
   						 }
    
   					</script>
	<div class="container">
		<div class="row">


			<div class="col-md-12">
				<h4><공유게시판> </h4>
				<div class="table-responsive">
					<div class='box-body'>

						<select name="searchType" style="width: 300px">
							<option value="n"
								<c:out value="${scri.searchType == null?'selected':''}"/>>
								---------</option>
							<option value="t"
								<c:out value="${scri.searchType eq 't'?'selected':''}"/>>
								제목</option>
							<option value="c"
								<c:out value="${scri.searchType eq 'c'?'selected':''}"/>>
								내용</option>
							<option value="w"
								<c:out value="${scri.searchType eq 'w'?'selected':''}"/>>
								작성자</option>
							<option value="tc"
								<c:out value="${scri.searchType eq 'tc'?'selected':''}"/>>
								제목 + 내용</option>
							<option value="cw"
								<c:out value="${scri.searchType eq 'cw'?'selected':''}"/>>
								내용 + 작성자</option>
							<option value="tcw"
								<c:out value="${cri.searchType eq 'tcw'?'selected':''}"/>>
								제목 + 내용 + 작성자</option>
						</select> <input type="text" name='keyword' id="keywordInput"
							value='${cri.keyword }'>
						
						<button id='searchBtn'>검색</button>
						<c:if test="${not empty login}">
						<button id='newBtn'>새로운 글</button>
						</c:if>

					</div>
			
					<table id="mytable" class="table table-bordred table-striped">
					<tr>
						<thead>
							<th>글번호</th>
							<th>여행일수</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성날짜</th>
							<th>조회수</th>
						
						</thead>
						</tr>
						<tbody>

							<c:forEach items="${list}" var="ShareTextVO">
								<tr>
									<td>${ShareTextVO.sh_no}</td>
									<td>${ShareTextVO.sh_subject}</td>
									<td><a
										href="/starrail/sharetext/sharetext_detail?sh_no=${ShareTextVO.sh_no}">${ShareTextVO.sh_title}</a></td>
									<td>${ShareTextVO.m_id }</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ShareTextVO.sh_date }"/></td>
									
									<td><span class="badge bg-blue">${ShareTextVO.sh_hit}</span></td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
					
			<!-- 		<script>
    
    					var result = '${msg}';
    
   						 if(result == 'SUCCESS'){
    						alert("처리가 완료되었습니다.");
   						 }
    
   					</script> -->

					<div class="clearfix"></div>
					<ul class="pagination pull-right">
						<c:if test="${pageMaker.prev}">
							<li class="disabled"><a
								href="/starrail/sharetext/sharetext_listAll${pageMaker.makeQuery(pageMaker.startPage - 1) }"><span
									class="glyphicon glyphicon-chevron-left"></span> </a></li>
						</c:if>

						<c:forEach begin="${pageMaker.startPage }"
							end="${pageMaker.endPage }" var="idx">
							<li class="active"
								<c:out value="${pageMaker.scri.page == idx?'class =active':''}"/>>
								<a href="/starrail/sharetext/sharetext_listAll${pageMaker.makeQuery(idx)}">${idx}</a>
							</li>
						</c:forEach>
						

						<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
							<li><a
								href="/starrail/sharetext/sharetext_listAll${pageMaker.makeQuery(pageMaker.endPage +1) }">
									<span class="glyphicon glyphicon-chevron-right"></span>
							</a></li>
						</c:if>
					</ul>

				</div>

			</div>
		</div>
	</div>


	<div class="modal fade" id="edit" tabindex="-1" role="dialog"
		aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button>
					<h4 class="modal-title custom_align" id="Heading">Edit Your
						Detail</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<input class="form-control " type="text" placeholder="Mohsin">
					</div>
					<div class="form-group">

						<input class="form-control " type="text" placeholder="Irshad">
					</div>
					<div class="form-group">
						<textarea rows="2" class="form-control"
							placeholder="CB 106/107 Street # 11 Wah Cantt Islamabad Pakistan"></textarea>


					</div>
				</div>
				<div class="modal-footer ">
					<button type="button" class="btn btn-warning btn-lg"
						style="width: 100%;">
						<span class="glyphicon glyphicon-ok-sign"></span>?Update
					</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>



	<div class="modal fade" id="delete" tabindex="-1" role="dialog"
		aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button>
					<h4 class="modal-title custom_align" id="Heading">Delete this
						entry</h4>
				</div>
				<div class="modal-body">

					<div class="alert alert-danger">
						<span class="glyphicon glyphicon-warning-sign"></span> Are you
						sure you want to delete this Record?
					</div>

				</div>
				<div class="modal-footer ">
					<button type="button" class="btn btn-success">
						<span class="glyphicon glyphicon-ok-sign"></span>?Yes
					</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove"></span>?No
					</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>


	<script>
		$(document).ready(
				function() {

					$('#searchBtn').on(
							"click",
							function(event) {

								self.location = "/starrail/sharetext/share_listAll"
										+ '${pageMaker.makeQuery(1)}'
										+ "&searchType="
										+ $("select option:selected").val()
										+ "&keyword="
										+ $('#keywordInput').val();

							});

					$('#newBtn').on("click", function(evt) {

						self.location = "/starrail/sharetext/sharetext_insert";

					});

				});
	</script>

	<script>
		$(document).ready(function() {
			$("#mytable #checkall").click(function() {
				if ($("#mytable #checkall").is(':checked')) {
					$("#mytable input[type=checkbox]").each(function() {
						$(this).prop("checked", true);
					});

				} else {
					$("#mytable input[type=checkbox]").each(function() {
						$(this).prop("checked", false);
					});
				}
			});

			$("[data-toggle=tooltip]").tooltip();
		});
	</script>
</body>
</html>