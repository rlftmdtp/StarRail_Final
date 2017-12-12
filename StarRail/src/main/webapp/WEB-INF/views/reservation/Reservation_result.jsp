<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<style type="text/css">
.container {
	width: 30%;
	}
</style>
<title>예약 확인</title>
</head>
<body>


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
                        ◁◀ [ ${login.m_name} ] ▶▷ 님의 예약 정보 입니다.
                        </div>
                        <div class="panel-body panel-body-landing">
                        <c:forEach var="reservation" items="${list }">
                            <table class="table" >
                                <tr align="left">
                                    <td width="50px" ><i class="fa fa-check" ></i></td>
                                    <td>[내일로 시작일] <img src="/starrail/resources/images/reservation/arrow2.png" width="20%" height="20%"> ${reservation.res_sdate} </td>       
                                </tr>
                                <tr align="left">
                                    <td width="50px" name="res_tcount"><i class="fa fa-check"></i></td>
                                    <td>[여 행 기 간] <img src="/starrail/resources/images/reservation/arrow2.png" width="20%" height="20%"> ${reservation.res_tcount}</td>
                                </tr>
                                
                                <tr align="left">
                                    <td width="50px" ><i class="fa fa-check"></i></td>
                                    <td>[발 권 역] <img src="/starrail/resources/images/reservation/arrow2.png" width="20%" height="20%"> ${reservation.i_name }역</td>	
                                    
                                </tr>
                                <tr align="left">
                                    <td width="50px" ><i class="fa fa-check"></i></td>
                                    <td>[총 가 격] <img src="/starrail/resources/images/reservation/arrow2.png" width="20%" height="20%"> ${reservation.res_price }</td>	
                                    
                                </tr>
                            </table>
                            </c:forEach>
                        </div>
                        <div class="panel-footer panel-footer-landing">
                            <a href="/starrail/main/main" class="btn btn-price btn-success btn-lg">확인</a>
                            <a href="#" class="btn btn-price btn-success btn-lg">결제</a>
                        </div>
                         <div class="panel-footer panel-footer-landing">
                            <a href="#" class="btn btn-price btn-success btn-lg">카카오톡으로 예약정보 받기</a>
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>