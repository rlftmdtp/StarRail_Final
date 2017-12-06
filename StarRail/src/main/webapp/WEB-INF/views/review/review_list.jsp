<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


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
	href="/starrail/resources/css/review/review_list.css">
</head>

<body>
	<div class="container">
		<div class="row">


			<div class="col-md-12">
				<h4>내일로 후기</h4>
				<!-- <div class="table-responsive"> -->
					<div class='box-body'>

						<select name="searchType">
							<option value="n"
								<c:out value="${cri.searchType == null?'selected':''}"/>>
								---</option>
							<option value="t"
								<c:out value="${cri.searchType eq 't'?'selected':''}"/>>
								Title</option>
							<option value="c"
								<c:out value="${cri.searchType eq 'c'?'selected':''}"/>>
								Content</option>
							<option value="w"
								<c:out value="${cri.searchType eq 'w'?'selected':''}"/>>
								Writer</option>

							<option value="tc"
								<c:out value="${cri.searchType eq 'tc'?'selected':''}"/>>
								Title OR Content</option>
							<option value="cw"
								<c:out value="${cri.searchType eq 'cw'?'selected':''}"/>>
								Content OR Writer</option>
							<option value="tcw"
								<c:out value="${cri.searchType eq 'tcw'?'selected':''}"/>>
								Title OR Content OR Writer</option>
						</select> <input type="text" name='keyword' id="keywordInput"
							value='${cri.keyword }'>
						<!-- 	<button id='searchBtn' onclick="searchBtn();">Search</button> -->
						<button id='searchBtn'>Search</button>
						<button id='newBtn'>New Board</button>

					</div>

					<table id="mytable" class="table table-bordred table-striped">

						<thead>

							<th>NO</th>
							<th>TITLE</th>
							<th>WRITER</th>
							<th>DATE</th>
							<th>VIEWCNT</th>

						</thead>
						<tbody>

							<c:forEach items="${list}" var="reviewVO">
								<tr>
									<td>${reviewVO.r_no}</td>
									<td><a
										href="/starrail/review/review_detail${pageMaker.makeQuery(pageMaker.cri.page) }&r_no=${reviewVO.r_no}">${reviewVO.r_title }</a></td>
									<td>${reviewVO.m_id }</td>
									<td><fmt:formatDate value="${reviewVO.r_date }"
											pattern="yyyy-MM-dd" /></td>
									<td>${reviewVO.r_hit }</td>
								</tr>
							</c:forEach>
						</tbody>

					</table>

					<div class="container">
						<ul class="pagination">
							<c:if test="${pageMaker.prev}">
								<li class="disabled"><a
									href="/starrail/review/review_list${pageMaker.makeQuery(pageMaker.startPage - 1) }">«</a></li>
							</c:if>

							<c:forEach begin="${pageMaker.startPage }"
								end="${pageMaker.endPage }" var="idx">
								<li class="active"
									<c:out value="${pageMaker.cri.page == idx?'class =active':''}"/>>
									<a
									href="/starrail/review/review_list${pageMaker.makeQuery(idx)}">${idx}
										<span class="sr-only">(current)</span>
								</a>
								</li>
							</c:forEach>

							<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
								<li><a
									href="/starrail/review/review_list${pageMaker.makeQuery(pageMaker.endPage +1) }">»</a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</div>

<!-- 
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
							<span class="glyphicon glyphicon-ok-sign"></span> Update
						</button>
					</div>
				</div>
				/.modal-content
			</div>
			/.modal-dialog
		</div> -->


<!-- 
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
							<span class="glyphicon glyphicon-ok-sign"></span> Yes
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span class="glyphicon glyphicon-remove"></span> No
						</button>
					</div>
				</div>
				/.modal-content
			</div>
			/.modal-dialog
		</div>
 -->
 
 <!-- 검색어 찾아주기 -->
		<script>
			$(document).ready(function(){
				$('#searchBtn').on("click", function(event) {
					self.location = "/starrail/review/review_list"
									+ '${pageMaker.makeQuery(1)}'
									+ "&searchType="
									+ $("select option:selected").val()
									+ "&keyword="
									+ $('#keywordInput').val();

					});

					$('#newBtn').on("click", function(evt) {
						self.location = "/starrail/review/review_insert";
					});
				});
		</script>

		<!-- <script>
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
		</script> -->
</body>
</html>