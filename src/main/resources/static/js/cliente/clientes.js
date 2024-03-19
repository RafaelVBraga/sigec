function atribuir(e){
	var input_id = document.getElementById('testeId');
	input_id.setAttribute("text",e);	
}

const myModal = document.getElementById('modalProc')
const myInput = document.getElementById('showModalProc')

myModal.addEventListener('shown.bs.modal', () => {
  myInput.focus()
})

