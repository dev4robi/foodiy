<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Javascript and css library -->
	<!-- jquery 3.4.0 -->
	<script type="text/javascript" src="/common/lib/jquery-3.4.0/jquery-3.4.0.min.js"></script>
	<script type="text/javascript" src="/common/lib/jquery-3.4.0/jquery-cookie-1.4.1.js"></script>
	<!-- bootstrap 4.3.1 -->
	<link rel="stylesheet" href="/common/lib/bootstrap-4.3.1/css/bootstrap.min.css">
	<script type="text/javascript" src="/common/lib/bootstrap-4.3.1/js/bootstrap.min.js"></script>
	<!-- popper 1.14.7 -->
	<script type="text/javascript" src="/common/lib/popper-1.14.7/popper-1.14.7.js"></script>
	<script type="text/javascript" src="/common/lib/popper-1.14.7/tooltip-1.3.2.js"></script>
	<!-- fontawesome 5.8.1 -->
	<link rel="stylesheet" href="/common/lib/fontawesome-5.8.1/css/fontawesome-5.8.1.css">
	<script type="text/javascript" src="/common/lib/fontawesome-5.8.1/js/fontawesome-5.8.1.js"></script>
	<!-- bootstrap-datetimepicker 0.0.11 -->
	<link rel="stylesheet" href="/common/lib/bootstrap-datetimepicker-0.0.11/css/bootstrap-datetimepicker.min.css">
	<script type="text/javascript" src="/common/lib/bootstrap-datetimepicker-0.0.11/js/bootstrap-datetimepicker.min.js"></script>
	<!-- common.js -->
	<script type="text/javascript" src="/common/js/common.js?ver=<%=System.currentTimeMillis()%>"></script>
	<!-- diary_record.css/js -->
	<script type="text/javascript" src="/foodiy/js/common.js?ver=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="/foodiy/js/diary_record.js?ver=<%=System.currentTimeMillis()%>"></script>
</head>
<body class="container-fluid">
	<div class="row align-items-center justify-content-center">
		<div clss="col">
			<div id="div_datepicker" class="input-append">
				<input id="input_date" data-format="yyyy-MM-dd" type="text">
				<span class="add-on">
					<i class="far fa-calendar-check fa-lg fa-border fa-pull-right"></i>
				</span>
			</div>
		</div>
		<div clss="col">
			<div id="div_timepicker" class="input-append">
				<input id="input_time" data-format="hh:mm" type="text">
				<span class="add-on">
					<i class="far fa-clock fa-lg fa-border fa-pull-right"></i>
				</span>
			</div>
		</div>
	</div>
</body>
</html>
