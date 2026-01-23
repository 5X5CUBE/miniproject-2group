/**
 * 
 */


$(function() {
	
	
	$(document).on("click", "#btnDeleteAction", function() {
	    var password = $("#deletePass").val();
	    var confirmText = $("#deleteConfirmText").val().trim(); 

	    if(!password) {
	        alert("비밀번호를 입력해주세요.");
	        $("#deletePass").focus();
	        return;
	    }
	    
	    if(confirmText !== "회원탈퇴") {
	        alert("'회원탈퇴' 문구를 정확히 입력해주세요.");
	        $("#deleteConfirmText").focus();
	        return;
	    }

	    if(!confirm("정말로 탈퇴하시겠습니까?\n삭제된 데이터는 복구할 수 없습니다.")) {
	        return;
	    }

	    $.ajax({
	        url: "/userDelete",
	        type: "POST",
	        data: { "password": password },
	        success: function(result) {
	            if(result === "success") {
	                alert("탈퇴가 완료되었습니다.\n이용해주셔서 감사합니다.");
	                location.href = "/userLogout"; 
	            } else if(result === "wrong") {
	                alert("비밀번호가 일치하지 않습니다.");
	                $("#deletePass").val(""); 
	                $("#deletePass").focus();
	            } else {
	                alert("탈퇴 처리에 실패했습니다.");
	            }
	        },
	        error: function() {
	            alert("서버 통신 에러");
	        }
	    });
	});
	
	
	$("#btnSubmitPhone").off("click").on("click", function() {
	    var m1 = $("#newMobile1").val();
	    var m2 = $("#newMobile2").val();
	    var m3 = $("#newMobile3").val();
	    
	    if(!m2 || !m3) { alert("휴대폰 번호를 모두 입력해주세요."); return; }
	    
	    var fullPhone = m1 + "-" + m2 + "-" + m3;

	    $.ajax({
	        url: "/userPhoneUpdate",
	        type: "POST",
	        data: { "phone": fullPhone },
	        success: function(result) {
	            if(result === "success") {
	                alert("휴대폰 번호가 수정되었습니다.");
	                $("#displayPhone").val(fullPhone); 
	                $("#newMobile2").val("");
	                $("#newMobile3").val("");
	                $("#phoneModal").modal("hide");
	            } else {
	                alert("수정 실패");
	            }
	        },
	        error: function() { alert("서버 에러"); }
	    });
	});


	$("#btnSubmitPass").off("click").on("click", function() {
	    var currentPass = $("#currentPass").val();
	    var newPass1 = $("#newPass1").val();
	    var newPass2 = $("#newPass2").val();

	    if(!currentPass || !newPass1 || !newPass2) { alert("모든 항목을 입력해주세요."); return; }
	    if(newPass1 !== newPass2) { alert("새 비밀번호가 일치하지 않습니다."); return; }

	    $.ajax({
	        url: "/userPassUpdate",
	        type: "POST",
	        data: { 
	            "currentPass": currentPass,
	            "newPass": newPass1
	        },
	        success: function(result) {
	            if(result === "success") {
	                alert("비밀번호가 변경되었습니다.\n보안을 위해 다시 로그인해주세요.");
	                
	                location.href = "/userLogout"; 
	                
	            } else if(result === "wrong") {
	                alert("기존 비밀번호가 틀렸습니다.");
	                $("#currentPass").focus();
	            } else {
	                alert("수정 실패 (로그인 상태를 확인해주세요)");
	            }
	        },
	        error: function() { alert("서버 에러"); }
	    });
	});


	$("#btnSubmitAddress").off("click").on("click", function() {
	    var zip = $("#newZipcode").val();
	    var addr1 = $("#newAddr1").val();
	    var addr2 = $("#newAddr2").val();

	    if(!zip || !addr1) { alert("주소를 검색해주세요."); return; }

	    $.ajax({
	        url: "/userAddressUpdate",
	        type: "POST",
	        data: { 
	            "zipcode": zip,
	            "addr1": addr1,
	            "addr2": addr2
	        },
	        success: function(result) {
	            if(result === "success") {
	                alert("주소가 수정되었습니다.");
	                
	                $("#displayZipcode").val(zip);
	                $("#displayAddr1").val(addr1);
	                $("#displayAddr2").val(addr2);
	                
	                $("#newAddr2").val(""); 
	                $("#addModal").modal("hide");
	            } else {
	                alert("수정 실패");
	            }
	        },
	        error: function() { alert("서버 에러"); }
	    });
	});
    
    $("#newNickname").on("input", function() {
        $("#isNickUpdateCheck").val("false");
    });

    
    $("#btnNickCheckInModal").on("click", function() {
        var nickname = $("#newNickname").val().trim();
		
		if(nickname === "") {
		            alert("수정할 닉네임을 입력해주세요.");
		            return;
		        }
		
        if(nickname.length < 2) {
            alert("닉네임은 2글자 이상 입력해주세요.");
            return;
        }

        $.ajax({
            url: "/nickCheck",
            type: "GET",
            data: { "nickname": nickname },
            success: function(isAvailable) {
                if(isAvailable) {
                    alert("사용 가능한 닉네임입니다.");
                    $("#isNickUpdateCheck").val("true");
                } else {
                    alert("이미 사용 중인 닉네임입니다.");
                }
            }
        });

    });
	
	$("#btnSubmitNickname").off("click").on("click", function() {
			    var nickname = $("#newNickname").val().trim();
			    var isCheck = $("#isNickUpdateCheck").val();


			    if(isCheck !== "true") {
			        alert("닉네임 중복확인을 먼저 진행해주세요.");
			        return; 
			    }


			    $.ajax({
			        url: "/userNicknameUpdate",
			        type: "POST",
			        data: { "nickname": nickname },
			        success: function(result) {
			            if(result === "success") {
			                alert("닉네임이 수정되었습니다.");
			                $("#displayNickname").val(nickname);
							$("#newNickname").val("");
							$("#isNickUpdateCheck").val("false");
			               $("#nicknameModal").modal("hide");
			            } else {
			                alert("수정에 실패했습니다.");
			            }
			        },
					 error: function() {
		                alert("서버 통신 에러");
		            }
			    });
			});
	
});
	
	
	



function execDaumPostcodeForUpdate() {
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

            document.getElementById('newZipcode').value = data.zonecode;
            
            document.getElementById("newAddr1").value = addr;
            
            document.getElementById("newAddr2").focus();
        }
    }).open();
}