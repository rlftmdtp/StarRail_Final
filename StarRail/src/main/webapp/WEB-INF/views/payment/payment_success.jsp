<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/main/header_footer.css">
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/payment/payment.css">
<title>Insert title here</title>
</head>
<body>
	<!-- 해더 -->
 	<%@include file="../main/nav_page.jsp"%>
	
<div class="container">
    <div class="row" style="margin-top: 150px">
        <div class="col-md-12">
            <div class="error-template">
                <h1>
                    Congratulation</h1>
                <h2>
                     결제가 완료 되었습니다.</h2>
                <div class="error-details">
                   소중한 추억을 담을수 있는 즐거운 내일로 여행이 되시길 StarRail이 기원합니다. :)
                </div><br><br>
                <div class="error-actions">
                    <a href="/starrail/main/main" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-home"></span>
                        Take Me Home </a><a href="http://www.jquery2dotnet.com" class="btn btn-default btn-lg"><span class="glyphicon glyphicon-envelope"></span> Contact Support </a>
                </div>
            </div>
        </div>
    </div>
</div>

	
	
	<!--푸터  -->
    <div style="margin-top: 15%;">
    	<%@include file="../main/footer.jsp"%>   
    </div>   
    
<!-- datepicker JS File : 무조건 코드 맨마지막에 넣어야 실행됨 -->   
 	<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.0/js/bootstrap-datepicker.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery-dateFormat/1.0/jquery.dateFormat.js'></script> 
<!-- JS File -->
   <script src="/starrail/resources/js/payment/payment.js"></script>
</body>
</html>