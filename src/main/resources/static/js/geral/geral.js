function toggle(){
		
		var password_input = document.getElementById('password');
		if(password_input.getAttribute('type')=='password'){
	 		password_input.setAttribute('type','text');
	 		document.getElementById('eye_slash_image').style.display='initial';
	 		document.getElementById('eye_image').style.display='none';
	 	}else {
			 password_input.setAttribute('type','password');
			 document.getElementById('eye_slash_image').style.display='none';
	 		 document.getElementById('eye_image').style.display='initial';
	 		 }
		}