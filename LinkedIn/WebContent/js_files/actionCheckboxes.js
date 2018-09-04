//button check all
function checkAll(){
	var checkboxes = document.getElementsByClassName("check_item");
	var i;
	for(i=0; i < checkboxes.length ;i++){
		checkboxes[i].checked = true;
	}
}

function uncheckAll(){
	var checkboxes = document.getElementsByClassName("check_item");
	var i;
	for(i=0; i < checkboxes.length ;i++){
		checkboxes[i].checked = false;
	}
}
