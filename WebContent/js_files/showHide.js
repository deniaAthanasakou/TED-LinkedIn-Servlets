function displayDesc(){
	var content = document.getElementById("switchContent");
	var input = document.getElementById("showHide");
	var glyph = document.getElementById("showHideGlyph");
	if(input.value == "See more"){
		input.value = "See less";
		glyph.className="glyphicon glyphicon-chevron-up";
		content.style.height = "auto";
	}else{
		input.value = "See more";
		glyph.className="glyphicon glyphicon-chevron-down";
		content.style.height = "20em";
	}
}
