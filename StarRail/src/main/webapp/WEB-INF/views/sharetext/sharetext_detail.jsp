<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<html>

<head>
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/main/header_footer.css">
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/sharetext/sharetext.css">
	   <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
</head>

<!-- Main content -->
    <style type="text/css">
    .popup {position: absolute;}
    .back { background-color: gray; opacity:0.5; width: 100%; height: 300%; overflow:hidden;  z-index:1101;}
    .front { 
       z-index:1110; opacity:1; boarder:1px; margin: auto; 
      }
     .show{
       position:relative;
       max-width: 1200px; 
       max-height: 800px; 
       overflow: auto;       
     } 
  	
    </style>

<body>

	<!-- 해더 -->
 	<%@include file="../main/nav_page.jsp"%>
		<div style="margin-top: 60px;"></div>   
	

	
 	<div class='popup back' style="display:none;"></div>
    <div id="popup_front" class='popup front' style="display:none;">
     <img id="popup_img">
    </div>

		<br>
		<div class="form-group" 
				style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto;">
				<img
					src="/starrail/resources/images/reservation/reservation.png" style="position: top:0; left: 0; width: 100%;" height=20%>
		</div>
	<!-- 히든으로 글번호 가져오기 -->
	<form role="form" method="post" action="sharetext_update">
		<input type='hidden' name='sh_no' value="${shareTextVO.sh_no}">
		<div class="box-body">

			<!-- 여행일수 -->
			<div class="form-group"
				style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;">
				<label for="exampleInputEmail1">여행일수</label> <input type="text"
					class="form-control" value="${shareTextVO.sh_subject}"
					readonly="readonly">
			</div>

			<!-- 제목 -->
			<div class="form-group"
				style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;">
				<label for="exampleInputEmail1">제목</label> <input type="text"
					name='sh_title' class="form-control"
					value="${shareTextVO.sh_title}" readonly="readonly">
			</div>

			<!-- 코스정보  -->
			<div class="form-group"
				style="border: 1px solid #48BAE4; height: 20%; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;">
				<label for="exampleInputPassword1">코스</label>
				<textarea class="form-control" name="c_id" rows="3"	readonly="readonly" style="resize: none;width:100%; height:84%">				 
				 <c:forEach var="courseDetailObject" items="${list }" >  [${courseDetailObject.cd_start}] : [${courseDetailObject.cd_stime }] ▶▷▶▷[ ${courseDetailObject.cd_end }]: [${courseDetailObject.cd_etime } ]
				 </c:forEach>			
				</textarea>
			</div>

			<!-- 내용 -->
			<div class="form-group"
				style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;">
				<label for="exampleInputPassword1">내용</label>
				<textarea class="form-control" name="content" rows="3"
					readonly="readonly" style="resize: none;">${shareTextVO.sh_content}</textarea>
					
			</div>

			<!-- 작성자 -->
			<div class="form-group"
				style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;">
				<label for="exampleInputEmail1">작성자</label> <input type="text"
					name="writer" class="form-control" value="${shareTextVO.m_id}"
					readonly="readonly">
			</div>

			<!-- 작성날짜 -->
			<div class="form-group"
				style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;">
				<label for="exampleInputEmail1">작성날짜</label>
				
				<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
					value="${shareTextVO.sh_date }" />
			</div>
		</div>
		
				
		
		
		
			
		<!-- 버튼 -->
	</form>
	
	
	<!-- SNS공유 -->
	<div align="center">
	<a href="#" onclick="javascript:window.open('https://twitter.com/intent/tweet?text=[%EA%B3%B5%EC%9C%A0]%20' +encodeURIComponent(document.URL)+'%20-%20'+encodeURIComponent(document.title), 'twittersharedialog', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=300,width=600');return false;" target="_blank" alt="Share on Twitter" >
				<img src="/starrail/resources/images/sharetext/Twitter.jpg" height="4%" width="4%"></a>

				<a href="#" onclick="javascript:window.open('https://www.facebook.com/sharer/sharer.php?u=' +encodeURIComponent(document.URL)+'&t='+encodeURIComponent(document.title), 'facebooksharedialog', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=300,width=600');return false;" target="_blank" alt="Share on Facebook" >
				<img src="/starrail/resources/images/sharetext/Facebook.png" height="4%" width="4%"></a>
				
				<a href="#" onclick="javascript:window.open('https://story.kakao.com/s/share?url=' +encodeURIComponent(document.URL), 'kakaostorysharedialog', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes, height=300,width=600');return false;" target="_blank" alt="Share on kakaostory">
				<img src="/starrail/resources/images/sharetext/KakaoStory.png" height="4%" width="4%"></a>
				
				<a href="http://localhost:8081/starrail/sharetext/sharetext_detail?sh_no=118" onclick="javascript:window.open('http://share.naver.com/web/shareView.nhn?url=' +encodeURIComponent(document.URL)+'&title='+encodeURIComponent(document.title), 'naversharedialog', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=300,width=600');return false;" target="_blank" alt="Share on Naver" >
				<img src="/starrail/resources/images/sharetext/NaverBand.jpg" height="4%" width="4%"></a><br><br><br>
	
	
	</div>
	
	
	
	<div class="box-footer" align="center">
		<c:if test="${login.m_id==shareTextVO.m_id }">
			<button type="submit" class="btn btn-warning" id="modifyBtn">수정</button>
			<button type="submit" class="btn btn-danger" id="removeBtn">삭제</button>
		</c:if>
		<button type="submit" class="btn btn-primary" id="goListBtn">목록</button>
	</div>

	<br>
	<br>
	<br>

	<!-- <div>
	<div>
		작성자 <input type="text" name="replyer" id="newReplyWriter">
	</div>
	<div>
		내용 <input type="text" name="sr_content" id="newReplyText">
	</div>
	<button id="replyaddBtn">추가</button>
</div> -->


	
	<c:if test="${empty login }">
		<div class="box-body">
			<div style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto; color: green; font-weight: bold; font-size: 1.0em;" align="center">
				<img
					src="/starrail/resources/images/sharetext/sad.PNG"><br>
				<a href="javascript:goLogin();"> 로그인이 필요합니다. <br> 로그인해주세요. 
				</a>
			</div>
		</div>
	</c:if>


	<!-- **************댓글************** -->




	<!-- ----------------------------------댓글 입력 , 댓글 리스트 ----------------------------------------- -->


		<div class="row" style="border: 1px solid #48BAE4; height: auto; width: 60%; margin: auto;">
			<div class="col-md-12">
				<c:if test="${not empty login }">
					<div class="box box-success">
						<div class="box-header">
						<br><br>
						<h3 class="box-title" align="center">아름다운 댓글 문화를 만듭시다. 
						<img src="/starrail/resources/images/sharetext/smiley.png" height="10%" width="10%"></h3>
					</div>
					<div class="box-body">
						<label for="exampleInputEmail1">작성자</label> <input
							class="form-control" type="text" placeholder="USER ID"
							id="newReplyWriter" value="${login.m_id}"> <label
							for="exampleInputEmail1"> 내용</label> 
							<input class="form-control"
							type="text" placeholder="타인에게 상처를 주는 글은 남기지 맙시다."
							id="sr_content">
					</div>
					

					
					<!-- /.box-body -->
					<div class="box-footer">
						<button type="button" class="btn btn-primary" id="replyAddBtn">등록</button>
						<br><br>
					</div>
				</div>
	</c:if>
	
		<!-- timeline time label -->
		<!--  목록출력 -->
			<ul class="timeline">
				<li class="time-label" id="repliesDiv">
				<span class="bg-green">
						댓글을 보고 싶다면 Click!! <img src="/starrail/resources/images/sharetext/click.png" height="3%" width="3%">  <small id='replycntSmall'> [ ${shareTextVO.replycnt} ]  </small>
				</span>
				</li>
			</ul>
		
			<!-- 페이징처리 -->
			<div class='text-center'>
				<ul id="pagination" class="pagination pagination-sm no-margin ">
		
				</ul>
			</div>
		</div>
	</div>
	
	<!-- Modal -->
<div id="modifyModal" class="modal modal-primary fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body" data-rno>
        <p><input type="text" id="sr_content" class="form-control"></p>
      </div>
      <div class="modal-footer">
      	<c:if test="${login.m_id==shareTextVO.m_id }">
        <button type="button" class="btn btn-info" id="replyModBtn">수정</button>
        <button type="button" class="btn btn-danger" id="replyDelBtn">삭제</button>
        </c:if>
        <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>


<script id="template" type="text/x-handlebars-template">
{{#each .}}
<li class="replyLi" data-rno={{sr_no}}>
<i class="fa fa-comments bg-blue"></i>
 <div class="timeline-item" >
  <span class="time">
    <i class="fa fa-clock-o"></i>{{prettifyDate sr_date}}
  </span>
  <h3 class="timeline-header"><strong>{{sr_no}}</strong> -{{replyer}}</h3>
  <div class="timeline-body">{{sr_content}} </div>
    <div class="timeline-footer">
     <a class="btn btn-primary btn-xs" 
	    data-toggle="modal" data-target="#modifyModal">Modify</a>
    </div>
  </div>			
</li>
{{/each}}
</script>

<!-- 로그인 함수 -->
<script type="text/javascript">
 function goLogin(){
	 location.href="/starrail/main/login";
 }

</script>

<script>
	Handlebars.registerHelper("prettifyDate", function(timeValue) {
		var dateObj = new Date(timeValue);
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth() + 1;
		var date = dateObj.getDate();
		return year + "/" + month + "/" + date;
	});
	
	var printData = function(replyArr, target, templateObject) {

		var template = Handlebars.compile(templateObject.html());

		var html = template(replyArr);
		$(".replyLi").remove();
		target.after(html);

	}
	
	var sh_no = ${shareTextVO.sh_no};
	
	var replyPage = 1;

	//내가 원하는 페이지 함수
	function getPage(pageInfo){
		
		$.getJSON(pageInfo,function(data){
			printData(data.list, $("#repliesDiv") ,$('#template'));
			printPaging(data.pageMaker, $(".pagination"));
			
			$("#modifyModal").modal('hide');
			$("#replycntSmall").html("[ " + data.pageMaker.totalCount +" ]");
			
		});
	}

	var printPaging = function(pageMaker, target) {

		var str = "";

		if (pageMaker.prev) {
			str += "<li><a href='" + (pageMaker.startPage - 1)
					+ "'> << </a></li>";
		}

		for (var i = pageMaker.startPage, len = pageMaker.endPage; i <= len; i++) {
			var strClass = pageMaker.cri.page == i ? 'class=active' : '';
			str += "<li "+strClass+"><a href='"+i+"'>" + i + "</a></li>";
		}

		if (pageMaker.next) {
			str += "<li><a href='" + (pageMaker.endPage + 1)
					+ "'> >> </a></li>";
		}

		target.html(str);
	};
	

</script>
<script>
$(function(){
	
	var formObj = $("form[role='form']");
	
	console.log(formObj);
	
	$("#modifyBtn").on("click", function(){
		formObj.attr("action", "/starrail/sharetext/sharetext_update");
		formObj.attr("method", "get");		
		formObj.submit();
	});
	
/* 	$("#removeBtn").on("click", function(){
		formObj.attr("action", "/sboard/removePage");
		formObj.submit();
	}); */

	
	$("#removeBtn").on("click", function(){
		
		var replyCnt =  $("#replycntSmall").html().replace(/[^0-9]/g,"");
		
		if(replyCnt > 0 ){
			alert("댓글이 달린 게시물을 삭제할 수 없습니다.");
			return;
		}	
		
		formObj.attr("action", "/starrail/sharetext/remove");
		formObj.submit();
	});	
	
	
	$("#goListBtn ").on("click", function(){
		formObj.attr("method", "get");
		formObj.attr("action", "/starrail/sharetext/sharetext_listPage");
		formObj.submit();
	});
	
	$("#repliesDiv").on("click", function() {

		if ($(".timeline li").size() > 1) {
			return;
		}
		getPage("/starrail/replies/" + sh_no + "/1");

	});
	
	//페이징 이벤트 처리
	$(".pagination").on("click", "li a", function(event){
		
		event.preventDefault();
		
		replyPage = $(this).attr("href");
		
		getPage("/starrail/replies/"+sh_no+"/"+replyPage);
		
	});
	
	var sh_no = ${shareTextVO.sh_no};
	var replyPage = 1;
	
	$("#replyAddBtn").on("click",function(){
		 
		 var replyer = $("#newReplyWriter").val();
		 var sr_content = $("#sr_content").val();
		  
		  $.ajax({
				type:'post',
				url:'/starrail/replies/',
				headers: { 
				      "Content-Type": "application/json",
				      "X-HTTP-Method-Override": "POST" },
				dataType:'text',
				data: JSON.stringify({sh_no:sh_no, replyer:replyer, sr_content:sr_content}),
				success:function(result){
					console.log("result: " + result);
					if(result == 'SUCCESS'){
						alert("등록 되었습니다.");
						replyPage = 1;
						getPage("/starrail/replies/"+sh_no+"/"+replyPage );
						$("#newReplyWriter").val("");
						$("#sr_content").val("");
					}
				}
			});
	});

	 
	$(".timeline").on("click", function(event){
		getPage("/starrail/replies/"+sh_no+"/"+replyPage );
		
	});

	$(".timeline").on("click", ".replyLi", function(event){
		
		var reply = $(this);
		
		$("#sr_content").val(reply.find('.timeline-body').text());
		$(".modal-title").html(reply.attr("data-rno"));
		
	});
	$("#replyModBtn").on("click",function(){
		  
		  var sr_no = $(".modal-title").html();
		  var sr_content = $("#sr_content").val();
		  
		  $.ajax({
				type:'put',
				url:'/starrail/replies/'+sr_no,
				headers: { 
				      "Content-Type": "application/json",
				      "X-HTTP-Method-Override": "PUT" },
				data:JSON.stringify({sr_content:sr_content}), 
				dataType:'text', 
				success:function(result){
					console.log("result: " + result);
					if(result == 'SUCCESS'){
						alert("수정 되었습니다.");
						getPage("/starrail/replies/"+sh_no+"/"+replyPage );
					}
			}});
	});
	
	$("#replyDelBtn").on("click",function(){
		  
		  var sr_no = $(".modal-title").html();
		  var sr_content = $("#sr_content").val();
		  
		  $.ajax({
				type:'delete',
				url:'/starrail/replies/'+sr_no,
				headers: { 
				      "Content-Type": "application/json",
				      "X-HTTP-Method-Override": "DELETE" },
				dataType:'text', 
				success:function(result){
					console.log("result: " + result);
					if(result == 'SUCCESS'){
						alert("삭제 되었습니다.");
						getPage("/starrail/replies/"+sh_no+"/"+replyPage );
					}
			}});
	}); 
});
	</script>
	
	
	
<!--푸터  -->
    <div style="margin-top: 30px;">
    	<%@include file="../main/footer.jsp"%>   
    </div>   


<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!-- datepicker JS File : 무조건 코드 맨마지막에 넣어야 실행됨 -->   
 <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/js/bootstrap-datepicker.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery-dateFormat/1.0/jquery.dateFormat.js'></script>
    <script src="/starrail/resources/js/main/index.js"></script> 
<!-- JS File -->
   <script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>

</body>
</html>





