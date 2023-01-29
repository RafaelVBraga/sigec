function mascara(i){
   
   var v = i.value;
   
   if(isNaN(v[v.length-1])){ // impede entrar outro caractere que não seja número
      i.value = v.substring(0, v.length-1);
      return;
   }
   
   i.setAttribute("maxlength", "14");
   if (v.length == 3 || v.length == 7) i.value += ".";
   if (v.length == 11) i.value += "-";

}
function mascara_telefone(i){
   
   var v = i.value;
   
   if(isNaN(v[v.length-1])){ // impede entrar outro caractere que não seja número
      i.value = v.substring(0, v.length-1);
      return;
   }
   if(v.length==0){i.value+="(";}   
   i.setAttribute("maxlength", "14");      
   if ( v.length == 3) i.value += ")";
   if (v.length == 9) i.value += "-";
}
function focus_telefone(i){
	
	if(i.value<1) i.value+="(";
}
function alerta(){
	alert('Javascript funcionando no thymeleaf!')
}