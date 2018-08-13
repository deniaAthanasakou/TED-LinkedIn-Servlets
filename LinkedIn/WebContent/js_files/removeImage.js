function removeImage() { 
	document.getElementById("imgInp").value = "";
	document.getElementById("uploadedImage").src = "";
	document.getElementById("uploadedImage").style.width = 0;
	document.getElementById("uploadedImage").style.height = 0;
}