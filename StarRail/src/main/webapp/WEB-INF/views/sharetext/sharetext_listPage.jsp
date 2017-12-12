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
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/main/header_footer.css">

</head>
<body>

 <!-- 해더 -->
 <%@include file="../main/nav_page.jsp"%>
	<!-- <div style="margin-top: 60px;"></div>   
	 -->
	
	<section class="content">
	<div class="container">
		<div class="row">
				<div class="form-group" 
				style="border: 1px solid #48BAE4; height: auto; width: 100%; margin: auto;">
				<img
					src="/starrail/resources/images/sharetext/shareimg.jpg" style="position: top:0; left: 0; width: 100%;">
				</div>

			<div class="col-md-12">
				<div class="table-responsive">
					
			
					<table id="mytable" class="table table-bordred table-striped">
					
						<thead>
						<tr>
							<th>글번호</th>
							<th>여행일수</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성날짜</th>
							<th>조회수</th>
							
						</tr>
						</thead>
						
						<tbody>
							
							<!-- 게시물 목록에서 댓글 수 처리 -->
							
							<c:forEach items="${list}" var="ShareTextVO">
								<tr>
									<td>${ShareTextVO.sh_no}</td>
									<td>${ShareTextVO.sh_subject}</td>
									<td><a
										href="/starrail/sharetext/sharetext_detail?sh_no=${ShareTextVO.sh_no}">${ShareTextVO.sh_title}
										<strong>[${ShareTextVO.replycnt}]</strong></a></td>
									<td>${ShareTextVO.m_id }</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ShareTextVO.sh_date }"/></td>
									
									<td><span class="badge bg-blue">${ShareTextVO.sh_hit}</span></td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
					<!-- 검색 조건 -->
					<div class='box-body' align="center">
						<select name="searchType">
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
								<c:out value="${scri.searchType eq 'tcw'?'selected':''}"/>>
								제목 + 내용 + 작성자</option>
						</select> <input type="text" name='keyword' id="keywordInput" value='${scri.keyword }' placeholder="검색어를 입력해주세요 :)" size="25">
	
						<button id='searchBtn'>검색</button>	
						<div align="right">			
							<button id='newBtn'>새로운 글</button>
						</div>	

					</div>
				
				<!-- 페이징 -->
				<div align="center">
				<div class="box-footer" >
                  <div class="text-center" >
					
					
					<ul class="pagination pull-center">
						<c:if test="${pageMaker.prev}">
							<li class="disabled" ><a
								href="/starrail/sharetext/sharetext_listPage${pageMaker.makeQuery(pageMaker.startPage - 1) }">&laquo;<span
									class="glyphicon glyphicon-chevron-left"></span> </a></li>
						</c:if>

						<c:forEach begin="${pageMaker.startPage }"
							end="${pageMaker.endPage }" var="idx">
							<li class="active"
								<c:out value="${pageMaker.scri.page == idx?'class =active':''}"/>>
								<a href="/starrail/sharetext/sharetext_listPage${pageMaker.makeQuery(idx)}">${idx}</a>
							</li>
						</c:forEach>
						

						<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
							<li><a
								href="/starrail/sharetext/sharetext_listPage${pageMaker.makeQuery(pageMaker.endPage +1) }">&raquo;
									<span class="glyphicon glyphicon-chevron-right"></span>
							</a></li>
						</c:if>
					</ul>
					</div>
					</div>
					</div>
					
					
					
					</div>
				</div>
		</div>
	</div>
</section>


	<script>
		var result = '${msg}';

		if (result == 'SUCCESS') {
			alert("처리가 완료되었습니다.");
		}
	</script>

<script>
		$(document).ready(
				function() {

					$('#searchBtn').on(
							"click",
							function(event) {

								self.location = "/starrail/sharetext/sharetext_listPage"
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