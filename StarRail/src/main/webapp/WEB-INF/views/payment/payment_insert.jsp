<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<title>StarRail 결제</title>
</head>
<body>

 <!-- 해더 -->
 <%@include file="../main/nav_page.jsp"%>
	

<div class="container" style="margin-right: 50px; margin-top: 70px">

    <div class="row">
        <!-- You can make it whatever width you want. I'm making it full width
             on <= small devices and 4/12 page width on >= medium devices -->
        <div class="col-xs-12 col-md-4">
        
        
            <!-- CREDIT CARD FORM STARTS HERE -->
            <div class="panel panel-default credit-card-box">
                <div class="panel-heading display-table" >
                    <div class="row display-tr" >
                        <h3 class="panel-title display-td" >Payment Details</h3>
                        <div class="display-td" >
                            <img class="img-responsive pull-right" src="/starrail/resources/images/payment/card.png">
                        </div>
                    </div>                    
                </div>
                <div class="panel-body">
                    <form id="payment-form" action="/starrail/payment/payment_insert" method="post" >
                       <input type="hidden" name="res_no" value="${res_no}">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label for="cardNumber">카드번호</label>
                                    <div class="input-group">
                                      <input type="text" class="form-control" name="pay_cardnum" placeholder="Valid Card Number" > 
                                        <span class="input-group-addon"><i class="fa fa-credit-card"></i></span>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                             <div class="col-xs-5 col-md-5 pull-right">
                                <div class="form-group">
                                    <label for="cardMM">MM</label>
                                    <input type="text" class="form-control" name="pay_mm" placeholder="MM" value="" />
                                </div>
                            </div>
                             <div class="col-xs-5 col-md-5 pull-right">
                                <div class="form-group">
                                    <label for="cardYY">YY</label>
                                    <input type="text" class="form-control" name="pay_yy" placeholder="YY"   />
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-5 pull-right">
                                <div class="form-group">
                                    <label for="cardCVC">CVC CODE</label>
                                    <input type="text" class="form-control" name="pay_cvc" placeholder="CVC" />
                                </div>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label for="couponCode">은행</label>
                                   	<select style="width: 100%; height: 35px;" name="pay_bank" >
                                   		<option value="">은행을 선택하세요.</option>
                                   		<option value="신한">신한</option>
                                   		<option value="국민">국민</option>
                                   		<option value="카카오">카카오</option>
                                   		<option value="농협">농협</option>
                                   	</select> 
                                </div>
                            </div>                        
                        </div>
                        
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label for="couponCode">비밀번호</label>
                                   <input type="text" class="form-control" name="pay_cardpw" />
                                </div>
                            </div>                        
                        </div>
                         <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label for="couponCode">결제가격</label>
                                     <input type="text" class="form-control" name="pay_price" value="${res_price}" readonly="readonly" >
                                    
                                </div>
                            </div>                        
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <input class="subscribe btn btn-success btn-lg btn-block" type="submit" value="결제"/>
                            </div>
                        </div>
                        <div class="row" style="display:none;">
                            <div class="col-xs-12">
                                <p class="payment-errors"></p>
                            </div>
                        </div>
                    </form>
                </div>
            </div>            
            <!-- CREDIT CARD FORM ENDS HERE -->
            
            
        </div>            
        
     <div class="col-xs-12 col-md-8" style="font-size: 12pt; line-height: 2em;">
            	<img alt="" src="/starrail/resources/images/payment/RAILRO2.PNG" width="70%">
            	
            <p>즐거운 추억이 만들 수 있는 여행이 되기를 Starrail이 응원합니다.</p>
            
            <p>사이트주소: 코레일, 
                <a href="http://www.letskorail.com/">래츠 코레일-Let' korail</a>, 
                <a href="http://www.letskorail.com/ebizprd/EbizPrdPassRailroIntroW_hc11901.do">대한민국 청춘여행 내일로</a>,
                and <a href="https://stripe.com/docs/stripe.js">StarRail</a>
            </p>
        </div> 
        
    </div>
</div>




<!--푸터  -->
    <div style="margin-top: 30px;">
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