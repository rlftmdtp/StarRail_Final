<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/main/header_footer.css">
<link rel="stylesheet" type="text/css"
	href="/starrail/resources/css/reservation/reservation.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<title>Insert title here</title>
</head>
<body>
<form action="reservation_result" method="get">
	<input type="hidden" name="res_no" value="${reservationVO.res_no}">
</form>

<div class="container">
        <div class="row text-center">
            <div class="col-md-12">
                <h2>예약 완료</h2>
            </div>
            <div class="col-md-12" style="margin-top: 20px;">
                <div class="pricing-table">
                    <div class="panel panel-primary" style="border: none;">
                        <div class="controle-header panel-heading panel-heading-landing">
                            <h1 class="panel-title panel-title-landing">
                                예약이 완료 되었습니다.
                            </h1>
                        </div>
                        <div class="controle-panel-heading panel-heading panel-heading-landing-box">
                          	예약 정보 입니다.
                        </div>
                        <div class="panel-body panel-body-landing">
                            <table class="table">
                                <tr>
                                    <td width="50px"><i class="fa fa-check"></i></td>
                                    <td>출발날짜</td>
                                </tr>
                                <tr>
                                    <td width="50px"><i class="fa fa-check"></i></td>
                                    <td>여행기간</td>
                                </tr>
                                <tr>
                                    <td width="50px"><i class="fa fa-check"></i></td>
                                    <td>	
                                    
                                </tr>
                            </table>
                        </div>
                        <div class="panel-footer panel-footer-landing">
                            <a href="#" class="btn btn-price btn-success btn-lg">확인</a>
                        </div>
                         <div class="panel-footer panel-footer-landing">
                            <a href="#" class="btn btn-price btn-success btn-lg">카카오톡으로 예약정보 받기</a>
                        </div>
                        <div class="panel-footer panel-footer-landing">
                            <a href="#" class="btn btn-price btn-success btn-lg">결제</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>