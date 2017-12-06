<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


   <input type="button" value="쪽지보내기" onclick="openTest()">



<script type="text/javascript">
	/* 쪽지보내기 버튼 누르면 자식창이 뜨게 한다. msg_insertform.jsp로 연결 */
	function openTest(){
		var child = window.open('msg_insertform', 'childWindow',
				'resizable=no, width=360, height=380, left=500, top=200, menubar=no, status=no, scrollbars=no');
		}
		
</script>