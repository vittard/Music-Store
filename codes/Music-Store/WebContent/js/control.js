function setMessage(message) {
	var element = document.getElementById("message");
	element.style.color = "red";
	element.innerHTML = message;
	document.getElementById("sectionMessage").style.display = "block";
}

function replaceAll(str, cerca, sostituisci) {
  return str.split(cerca).join(sostituisci);
}

function clean(obj) {
	obj.value = "";
	obj.style.borderColor = "#ced4da";
}

function isEmail(obj) {
	var string = obj.value;
	var regexp = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	if (!regexp.test(string) && string.localeCompare("")!=0 )
	{
		obj.style.borderColor = "red"; 
		setMessage("L'email deve rispettare il seguente formato example@example.it");
		return false;
	} else {
		return true;
	}
}

function isNumeric(obj) {
	var string = obj.value;
	var regexp = /^[0-9]+$/;
	if (!regexp.test(string) && string.localeCompare("")!=0)
	{
		obj.style.borderColor = "red";
		setMessage("Questo &egrave; un campo di tipo numerico");
		return false;
	}
	return true;
}

function isLetteral(obj) {
	var string = obj.value;
	var regexp = /^[A-Za-z]+$/;
	if (!regexp.test(string) && string.localeCompare("")!=0 )
	{
		obj.style.borderColor = "red";

		setMessage("questo campo pu&ograve; essere solo di tipo letterale");
		return false;
	} else {
		return true;
	}
	
}

function isName(obj) {
	var string = obj.value;
	string = string.replace("'", "");
	var regexp = /^[A-Za-z]+$/;
	if (!regexp.test(string) && string.localeCompare("")!=0 )
	{
		obj.style.borderColor = "red";

		setMessage("questo campo pu&ograve; essere solo di tipo letterale");
		return false;
	}
	return true;
}


function isPassword(obj) {
	var string = obj.value;
	if (string.length >= 8 && string.length <= 30 && string.localeCompare("")!=0 ) {
		return true;
	} else {
		obj.style.borderColor = "red";
		setMessage("La Lunghezza della password deve essere compresa tra 8 e 30");
		return false;
	}
}

function isDate(obj) {
	var string = obj.value;
	var regexp = /\d{4}\-\d{1,2}\-\d{1,2}$/;
	if (!regexp.test(string) && string.localeCompare("")!=0)
	{
		obj.style.borderColor = "red";
		setMessage("Inserire dati validi nei campi data");
		return false;
	}
	return true;
}

function isAlphanumeric(obj) {
	var string = obj.value;
	var regexp = /^[a-zA-Z0-9]+$/;
	if (!regexp.test(string) && string.localeCompare("")!=0)
	{
		obj.style.borderColor = "red";
		setMessage("Questo &egrave; un campo di tipo alphanumerico");
		return false;
	}
	return true;
}

function isStreet(obj) {
	var string = obj.value;
	string = string.replaceAll(",", "");
	string = string.replaceAll(" ", "");
	console.log(string);
	var regexp = /^[a-zA-Z0-9]+$/;
	if (!regexp.test(string)&& string.localeCompare("")!=0 )
	{
		obj.style.borderColor = "red";
		setMessage("I campi strada possono contenere caratteri alphanumerici");
		return false;
	}
	return true;
}

function isPrice(obj) {
	var string = obj.value;
	string = string.replace(",", "");
	var regexp = /(\d{1,2,3,4}\.(?=\d{1,2}))/;
	if (!regexp.test(string) && string>-1 && string.localeCompare("")!=0)
	{
		obj.style.borderColor = "red";
		setMessage("Il prezzo pu&ograve; contenere solo numeri che possono essere separati da un .");
		return false;
	}
	return true;
}

function isCard(obj) {
	var string = obj.value;
	string = string.replace(",", "");
	var regexp = /^\d{16}$/;
	if (!regexp.test(string) && string > -1)
	{
		obj.style.borderColor = "red";
		setMessage("La carta deve avere 16 numeri");
		return false;
	}
	return true;
}

function isCVV(obj) {
	var string = obj.value;
	string = string.replace(",", "");
	var regexp = /^\d{3}$/;
	if (!regexp.test(string) && string > -1)
	{
		obj.style.borderColor = "red";
		setMessage("il cvv è di 3 numeri");
		return false;
	}
	return true;
}

function isZipCode(obj) {
	var string = obj.value;
	string = string.replace(",", "");
	var regexp = /^\d{5}$/;
	if (!regexp.test(string) && string < 0 && string.localeCompare("")!=0)
	{
		obj.style.borderColor = "red";
		setMessage("il cap &egrave; di 5 numeri");
		return false;
	}
	return true;
}

function isNumberPhone(obj) {
	var string = obj.value;
	string = string.replace(",", "");
	var regexp = /^\d{12}$/;
	if (!regexp.test(string) && string>-1 && string.localeCompare("")!=0 )
	{
		obj.style.borderColor = "red";
		setMessage("Il numero telefonico &egrave; di massimo 12 numeri");
		return fasle;
	}
	return true;
}

function isDisponibility(obj) {
	var string = obj.value;
	string = string.replace(",", "");
	var regexp = /^\d{4}$/;
	if (!regexp.test(string) && string>-1 && string.localeCompare("")!=0)
	{
		obj.style.borderColor = "red";
		setMessage("La dispinibitlità &egrave; di massimo 4 cifre");
		return false;
	}
	return true;
}