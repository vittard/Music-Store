function showNavResult(str) {
	var xhttp;
	if (str == "") {
		document.getElementById("resultNav").innerHTML = "";
		return;
	}
	xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("resultNav").innerHTML = this.responseText;
		}
	};
	xhttp.open("GET", "SearchProduct?q="+str, true);
	xhttp.send();
}

function selectResultInNav(obj) {
	searchNav = document.getElementById("searchNav"); //searches for and detects the input element from the 'myButton' id
	var x = obj.textContent; 
	searchNav.value = x; //changes the value
}