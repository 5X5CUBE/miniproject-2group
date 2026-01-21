/**
 * 
 */
$(function() {


	$("#loginForm").submit(function(e) {
		e.preventDefault();
		
		var id = $("#loginId").val();
		var pass = $("#password").val();
		if(id.length <= 0) {
			alert("아이디가 입력되지 않았습니다.\n아이디를 입력해주세요");
			$("#loginId").focus();
			return false;
		}
		if(pass.length <= 0) {
			alert("비밀번호가 입력되지 않았습니다.\n비밀번호를 입력해주세요");
			$("#password").focus();
			return false;
		}
		
			$.ajax({
	           url: "/userlogin",
	           type: "POST",
	           data: {
	               "loginId": id,
	               "password": pass
	           },
				   
		   		success: function(result) {
		                   
	               if (result == -1) {
	                   alert("존재하지 않는 아이디입니다.");
	                   $("#loginId").focus();
	               } else if (result == 0) {
	                   alert("비밀번호가 틀렸습니다.");
	                   $("#password").focus();
	               } else if (result == 1){
	                   location.href = "/mainhome";
	               }
	           },
	           error: function() {
	               alert("서버 통신 에러 (로그인 실패)");
	           }
		 });
	});
});