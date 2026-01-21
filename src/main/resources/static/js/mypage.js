/**
 * 
 */
$(function(){
	
	$("#userNicknameUpdate").submit(function(e){
		
		
		
		
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
});



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