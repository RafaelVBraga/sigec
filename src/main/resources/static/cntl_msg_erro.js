function updateMessage(){
	var mensagem_erro = document.querySelector('.mensagem_erro');
	var mensagem_sucesso = document.querySelector('.mensagem_sucesso');
	
	if(mensagem_erro.getAttribute('visibility')==true){
		setTimeOut(function(){mensagem_erro.style.visibility=false;}, 1000);
		
	}
	if(mensagem_sucesso.getAttribute('visibility')==true){
		setTimeOut(function(){mensagem_sucesso.style.visibility=false;}, 1000);
		
	}
		
}