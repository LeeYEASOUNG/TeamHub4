<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko" style="--vh:5.02px;">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>비밀번호찾기 재설정</title>
	<style>
		*{
			margin:0;
			padding:0;
			list-style: none;
		}
		html,
		body{
			margin:0 auto;
			padding:0;
			overflow-x: hidden;
			}
		body{
			overflow-y: hidden;
		}
		.idMatch_area{
			margin:0 auto;
			width:60%;
			height:900px;
			display:flex;
			position:relative;
		}
		.idMatch_place{
			margin:auto auto;
			
			width:80%;
			height:80%;
			
		}
		.img_place{
			position:relative;
			
			width:100%;
			height:300px;
			text-align: center;
			
		}
		.img_place img{
			position:absolute;
			width:150px;
			top:34%;
			left:36%;
			cursor: pointer;
			
		}
		#p1{
			font-size:20px;
			text-align:center;
			font-weight: bold;
			color:rgb(7, 121, 7);
			margin-bottom:35px;
		    cursor: pointer;
		}
	
		form{
			text-align: center;
		}

		input{
			border:1px solid green;
			width:400px;
			height:50px;
			font-size:17px;
			padding-left:10px;
		}
		input:focus{
			outline:none;
		}
	
		#email_injeung{
			margin-bottom:40px;
		}

		#btn{
			width:412px;
			font-size:23px; 
			border:none;
			cursor: pointer;
			background-color:rgba(24, 167, 24, 0.925);
		}
	</style>
</head>
<body>
	<div class="idMatch_area">
		<div class="idMatch_place">
			<div class="img_place">
				<img src="../resources/images/check.png"/>
			</div><!--img_place-->
			<p id="p1">메일로 인증번호가 보내졌습니다.</br>인증번호를 입력해주세요.</p>
			<form id="frm" action="pw_set.do" method="post">
				<input type="hidden" name="num" value="${num}">
				<input type="text" id="email_injeung" name="email_injeung" autocomplete="off" required placeholder="인증번호를 입력하세요.">
				<br>
				<input type="submit" value="확인" id="btn" >
			</form>	
		</div><!--idMatch_place-->
	</div><!--id_match-->
</body>
</html> 
