<%@page import="starrail.main.domain.UserVO"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<%
	HttpSession session = request.getSession();
	String m_name = "";
	
	if((session.getAttribute("login")) != null){
		UserVO user = (UserVO)session.getAttribute("login");
		m_name = user.getM_name();
	}else{
		m_name = "User";
	}	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>New Age - Start Bootstrap Theme</title>


<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700"rel="stylesheet">
<link rel="stylesheet" type="text/css"href="/starrail/resources/bootstrap/css/bootstrap.css?ver=1">
<link href="/starrail/resources/vendor/bootstrap/css/bootstrap.min.css"rel="stylesheet">
<link rel="stylesheet"href="/starrail/resources/vendor/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"href="/starrail/resources/vendor/simple-line-icons/css/simple-line-icons.css">
<link href="https://fonts.googleapis.com/css?family=Lato"rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Catamaran:100,200,300,400,500,600,700,800,900"rel="stylesheet">
<link href="https://fonts.googleapis.com/css?fammily=Muli"rel="stylesheet">
<link rel="stylesheet"href="/starrail/resources/device-mockups/device-mockups.min.css">
<link href="/starrail/resources/css/main/nav_main.css?ver=1"rel="stylesheet">

<style type="text/css">
.dropdown {
    width:150px;    
}
.dropdown ul.dropdown-menu {
    border-radius:4px;
    box-shadow:none;
    margin-top:20px;
    width:150px;
}
.dropdown ul.dropdown-menu:before {
    content: "";
    border-bottom: 10px solid #fff;
    border-right: 10px solid transparent;
    border-left: 10px solid transparent;
    position: absolute;
    top: -10px;
    right: 16px;
    z-index: 10;
}
.dropdown ul.dropdown-menu:after {
    content: "";
    border-bottom: 12px solid #ccc;
    border-right: 12px solid transparent;
    border-left: 12px solid transparent;
    position: absolute;
    top: -12px;
    right: 14px;
    z-index: 9;
}
</style>	
	

<script src="/starrail/resources/vendor/jquery/jquery.min.js"></script>
<script src="/starrail/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/starrail/resources/vendor/jquery-easing/jquery.easing.min.js"></script>		
</head>

<body>
<header>
	<!-- Navigation -->
	<nav	class="navbar navbar-expand-lg navbar-light fixed-top navbar-shrink"
		id="mainNav">
	<div class="container">
		<a class="navbar-brand js-scroll-trigger" href="/starrail/main/main">
			<h1 style="font-weight: bold; font-size: 40px;">Hello StarRail</h1>
		</a>
		<button style="color: white;"
			class="navbar-toggler navbar-toggler-right" type="button"
			data-toggle="collapse" data-target="#navbarResponsive"
			aria-controls="navbarResponsive" aria-expanded="false"
			aria-label="Toggle navigation">
			Menu <i class="fa fa-bars"></i>
		</button>
		<hr>

		<div class="collapse navbar-collapse" id="navbarResponsive">
			<ul class="nav navbar-nav">
		        <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Course</a>
		          <ul class="dropdown-menu">
		            <li><a href="/starrail/course/makeCourse">Planner <span class="glyphicon glyphicon glyphicon-pencil pull-right"></span></a></li>
		            <li class="divider"></li>
		            <li><a href="/starrail/partner/partner">Partner  <span class="glyphicon glyphicon-sunglasses pull-right"></span></a></li>
		            <li class="divider"></li>
		            <li><a href="/starrail/expenses/railro_expenses">Expense <span class="glyphicon glyphicon-piggy-bank pull-right"></span></a></li>
		            <!-- <li class="divider"></li> -->
		            <!-- <li><a href="#"> <span class="glyphicon glyphicon-log-out pull-right"></span></a></li> -->
		          </ul>
		        </li>
		      </ul>&nbsp;&nbsp;&nbsp;&nbsp;	
		      
			 
			 <ul class="nav navbar-nav">	<!-- id="navbarResponsive" -->
				<li class="nav-item"><a class="nav-link js-scroll-trigger"	href="/starrail/map/search">&nbsp;&nbsp;Map&nbsp;&nbsp;</a></li>&nbsp;&nbsp;&nbsp;&nbsp;					
				<li class="nav-item"><a class="nav-link js-scroll-trigger"	href="/starrail/sharetext/sharetext_listPage">&nbsp;&nbsp;Share&nbsp;&nbsp;</a></li>&nbsp;&nbsp;&nbsp;&nbsp;					
				<li class="nav-item"><a class="nav-link js-scroll-trigger"	href="/starrail/review/review_list">Postscript</a></li>
			</ul>
			
			
			
	      <ul class="nav navbar-nav" style="margin-left: 140px;">
	        <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><%=m_name %><span class="glyphicon glyphicon-user pull-right"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="/starrail/main/login">Login <span class="glyphicon glyphicon glyphicon-log-in pull-right"></span></a></li>
	            <li class="divider"></li>
	            <li><a href="#">My Page <span class="glyphicon glyphicon-cog pull-right"></span></a></li>
	            <li class="divider"></li>
	            <li><a href="/starrail/message/msg_list">Messages <span class="badge pull-right"> 42 </span></a></li>
	            <li class="divider"></li>
	            <li><a href="/starrail/reservation/Reservation_view">Reservation <span class="glyphicon glyphicon glyphicon-credit-card pull-right"></span></a></li>
	            <!-- <li class="divider"></li> -->
	            <!-- <li><a href="#"> <span class="glyphicon glyphicon-log-out pull-right"></span></a></li> -->
	          </ul>
	        </li>
	      </ul>
		</div>
	</div>
	</nav>
</header>
   <script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
   <script src="/starrail/resources/bootstrap/js/bootstrap.js"></script>
   <script src="/starrail/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
   <script src="/starrail/resources/vendor/jquery-easing/jquery.easing.min.js"></script>
</body>
</html>