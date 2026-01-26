/**
 * 
 */
$(function() {
	
	
	$(document).on("click", "#btnCheckUser", function() {
	        var id = $("#searchPassId").val().trim();
	        var m1 = $("#searchPassMobile1").val();
	        var m2 = $("#searchPassMobile2").val();
	        var m3 = $("#searchPassMobile3").val();
	        
	        if(!id || !m2 || !m3) { alert("정보를 모두 입력해주세요."); return; }
	        
	        var phone = m1 + "-" + m2 + "-" + m3;

	        $.ajax({
	            url: "/checkUserForReset",
	            type: "POST",
	            data: { "loginId": id, "phone": phone },
	            success: function(result) {
	                if(result === "success") {
	                    $("#passSearchModal").modal("hide");
	                    $("#passChangeModal").modal("show");
	                } else {
	                    alert("일치하는 회원 정보가 없습니다.");
	                }
	            },
	            error: function() { alert("서버 에러"); }
	        });
	    });

	    $(document).on("click", "#btnRunResetPass", function() {
	        var p1 = $("#newResetPass1").val();
	        var p2 = $("#newResetPass2").val();

	        if(!p1) { alert("새 비밀번호를 입력해주세요."); return; }
	        if(p1 !== p2) { alert("비밀번호가 일치하지 않습니다."); return; }

	        $.ajax({
	            url: "/resetPassword",
	            type: "POST",
	            data: { "password": p1 },
	            success: function(result) {
	                if(result === "success") {
	                    alert("비밀번호가 변경되었습니다. 로그인해주세요.");
	                    location.href = "/loginForm"; 
	                } else {
	                    alert("변경 실패 (세션 만료 등)");
	                }
	            },
	            error: function() { alert("서버 에러"); }
	        });
	    });
	
	
	$(document).on("click", "#btnFindId", function() {
	    var username = $("#username").val().trim();
	    var m1 = $("#searchMobile1").val();
	    var m2 = $("#searchMobile2").val();
	    var m3 = $("#searchMobile3").val();
	    
	    if(username === "") {
	        alert("이름을 입력해주세요.");
	        $("#username").focus();
	        return;
	    }
	    if(m2.length === 0 || m3.length === 0) {
	        alert("전화번호를 모두 입력해주세요.");
	        $("#searchMobile2").focus();
	        return;
	    }

	    var fullPhone = m1 + "-" + m2 + "-" + m3;

	    $.ajax({
	        url: "/findId", 
	        type: "POST",
	        data: { 
	            "username": username,
	            "phone": fullPhone 
	        },
	        success: function(result) {
	            if(result === "fail") {
	                alert("일치하는 회원 정보가 없습니다.");
	            } else {
	                alert("회원님의 아이디는 [ " + result + " ] 입니다.");
					
					$("#username").val(""); 
					$("#searchMobile2").val("");
					$("#searchMobile3").val(""); 
					$("#idSearchModal").modal("hide");
	            }
	        },
	        error: function() {
	            alert("서버 통신 에러");
	        }
	    });
	});


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