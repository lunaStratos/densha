<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<script src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
	<script src="./resources/js/jquery-3.1.1.min.js"></script>
	<link rel="stylesheet" href="./resources/css/bootstrap.min2.css" type="text/css">
	<script src="./resources/js/bootstrap.min.js"></script>
	<script>
		window.onload = function() {
			CKEDITOR.replace("content");
		}
	</script>

	<style type="text/css">
		body, html {
			background: url(./resources/image/back/backapi.jpg) no-repeat center center fixed;
			-webkit-background-size: cover;
			-moz-background-size: cover;
			-o-background-size: cover;
			background-size: cover;
		}
		
		ul li.nav {
			float: left;
			height: 100px;
			padding: 20px;
		}
		
		.first {
			overflow: hidden;
		}
	
		.widthAll {
			width: 80%;
			float: left;
		}
		
		legend{
			font-size:  1.2em;
    		font-weight:  bold;
		}	
		
		#titleName th {
			text-align: center;
		}
		
		div #menu {
			padding: 20px;
			float: left;
		}

		div #gesipan {
			padding: 20px;
			float: left;
		}
		
		fieldset {
			border:2px solid #999;
			border-left-style: none;
   			border-right-style: none;
    		border-bottom-style: none;
			/*border-radius:8px;*/
			padding-bottom: 20px;
		}
			
	</style>
</head>

<body>
	<!--top header-->
	<jsp:include page="../header.jsp" />
	<!--top header-->
			<!-- 디자인 바  -->
	<div class="progress" style="position: relative; top: -23px;">
		<div class="progress-bar progress-bar-success" style="width: 35%"></div>
		<div class="progress-bar progress-bar-warning" style="width: 20%"></div>
		<div class="progress-bar progress-bar-danger" style="width: 10%"></div>
	</div>
	<!-- 디자인 바  -->

	<div style="margin: 50px; background: rgba(255, 255, 255, 0.85); border-radius: 30px;" class="first">
		<div style="margin: 25px; padding-top: 20px; padding-bottom: 20px;">
			<div id="container" style="width: 100%;">
				<div style="width:15%;" id="menu">
					<fieldset>
						<legend align="center">게시판</legend>
						<div  class="btn-group-vertical" style="width:100%; ">
							<a href="board?type=notice" class="btn btn-default">공지사항</a>
							<a href="board?type=qna" class="btn btn-default">건의&QnA</a>
							<a href="board?type=freeboard" class="btn btn-default">자유게시판</a>
						</div>
					</fieldset>
				</div>
				<div id="gesipan" style="width: 80%;">
					<form class="table table-striped table-hover" action="write" method="post" id="writeform" enctype="multipart/form-data" onsubmit="writeform;">
						<input type="hidden" id="type" name="type" value="qna">
						<table style="float: left; ">
						
						
							<tr>
								
								<td colspan="2">
								<img src = "./resources/image/board/qnaWrite.gif">
					
								
								</td>
							</tr>
							<tr>
								<td width="80"><h5><img src = "./resources/image/infoicon/arrow.png" width="30">제목</h5></td>
								<td><input type="text" name="title" id="title" style="width: 99%; " class="form-control"></td>
							</tr>
							<tr>
								<td>비밀번호</td>
								<td><input type="password" name="secretpassword" id="secretpassword" style="width: 20%;" class="form-control"></td>
							</tr>
							<tr>
							
								<td colspan="2"><textarea name="content" id="content" style="width: 400px; height: 200px; resize: none;"></textarea></td>
							</tr>
							<tr>
								<td>첨부파일</td>
								<td><input type="file" name="upload" id="upload"  size="30" multiple="multiple"></td>
							</tr>
							<tr>
								<th colspan="2"><input type="submit" value="저장" class="btn btn-primary" style="width: 100px; position: relative; left:90%"></th>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>