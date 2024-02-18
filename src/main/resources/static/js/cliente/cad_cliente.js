function sendData(){
			var input = document.getElementById('input_prof');
			var select = document.getElementById('select_prof');

			console.log(input.value)
			console.log(select.value)
			input.value = select.value
			}