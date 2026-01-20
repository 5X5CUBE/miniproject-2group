/**
 * 
 */
function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = ''; 
                var extraAddr = ''; 

                if (data.userSelectedType === 'R') { 
                    addr = data.roadAddress;
                } else { 
                    addr = data.jibunAddress;
                }

                if(data.userSelectedType === 'R'){
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                } 

                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }
$(function() {
    
    $("#joinForm").on("submit", function() {
        return joinFormCheck();
    });
	
	
	$("#btnIdCheck").on("click", function() {
	        var id = $("#loginId").val();
	        
	        if(id.length == 0) {
	            alert("아이디를 입력해주세요.");
	            $("#loginId").focus();
	            return;
	        }
	        if(id.length < 5) {
	            alert("아이디는 5자 이상이어야 합니다.");
	            $("#loginId").focus();
	            return;
	        }


	        $.ajax({
	            url: "/idCheck",       
	            type: "GET",           
	            data: {"loginId": id},  
	            dataType: "json",
	            success: function(isAvailable) {

	                if(isAvailable) {
	                    alert("사용 가능한 아이디입니다.");
	                    $("#isIdCheck").val("true");
	                } else {
	                    alert("이미 사용 중인 아이디입니다.");
	                    $("#isIdCheck").val("false");
	                    $("#loginId").focus();
	                }
	            },
	            error: function() {
	                alert("서버 요청 에러");
	            }
	        });
	    });
		
		$("#btnNickCheck").on("click", function() {
			        var nickname = $("#nickname").val();
			        
			        if(nickname.length == 0) {
			            alert("닉네임을 입력해주세요.");
			            $("#nickname").focus();
			            return;
			        }
			        if(nickname.length < 2) {
			            alert("닉네임은 2자 이상이어야 합니다.");
			            $("#nickname").focus();
			            return;
			        }


					$.ajax({
					        url: "/nickCheck", 
					        type: "GET",
					        data: {"nickname": nickname},
					        dataType: "json",
					        success: function(isAvailable) {
					            if(isAvailable) {
					                alert("사용 가능한 닉네임입니다.");
					                $("#isNickCheck").val("true");
					            } else {
					                alert("이미 사용 중인 닉네임입니다.");
					                $("#isNickCheck").val("false");
					                $("#nickname").focus();
					            }
					        },
					        error: function() {
					            alert("서버 요청 에러");
					        }
					    });
			    });
		   
   
	
    function joinFormCheck() {

        var name = $("#username").val();
		var nickname = $("#nickname").val();    
        var id = $("#loginId").val();         
        var pass1 = $("#pass1").val();      
        var pass2 = $("#pass2").val();      

        var mobile2 = $("#mobile2").val();   
        var mobile3 = $("#mobile3").val();   
        
        var isIdCheck = $("#isIdCheck").val();
		var isNickCheck = $("#isNickCheck").val(); 

        if(id.length == 0) {
            alert("아이디가 입력되지 않았습니다.\n아이디를 입력해주세요");
            $("#loginId").focus();
            return false;
        }
        

        if(isIdCheck == 'false') {
            alert("아이디 중복 체크를 하지 않았습니다.\n아이디 중복 체크를 해주세요");
            return false;
        }
		
		if(isNickCheck == 'false') {
		            alert("닉네임 중복 체크를 하지 않았습니다.\n닉네임 중복 체크를 해주세요");
		            return false;
		        }


        if(pass1.length == 0) {
            alert("비밀번호가 입력되지 않았습니다.\n비밀번호를 입력해주세요");
            $("#pass1").focus();
            return false;
        }
        
        if(pass2.length == 0) {
            alert("비밀번호 확인이 입력되지 않았습니다.\n비밀번호 확인을 입력해주세요");
            $("#pass2").focus();
            return false;
        }

        if(pass1 != pass2) {
            alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            $("#pass2").val(""); 
            $("#pass2").focus();
            return false;
        }
		
		if(name.length == 0) {
            alert("이름이 입력되지 않았습니다.\n이름을 입력해주세요");
            $("#username").focus();
            return false;
        }
        
        if(nickname.length == 0) {
            alert("닉네임이 입력되지 않았습니다.\n닉네임을 입력해주세요");
            $("#username").focus();
            return false;
        }

        if(mobile2.length == 0 || mobile3.length == 0) {
            alert("휴대폰 번호가 입력되지 않았습니다.\n휴대폰 번호를 입력해주세요");
            $("#mobile2").focus();
            return false;
        }


		

        return true;
    }
});

function resetIdCheck() {
		    $("#isIdCheck").val("false");
		}
function resetNickCheck() {
		    $("#isNickCheck").val("false");
		}
